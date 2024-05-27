package app.controller;

import app.dal.DAOResetTokens;
import app.dal.DAOUser;
import app.entity.ResetRecord;
import app.entity.User;
import app.utils.Config;
import app.utils.GmailService;
import app.utils.URLUtils;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Objects;

@WebServlet(name="ResetPasswordController", urlPatterns={"/user/reset"})
public class ResetPasswordController extends HttpServlet {

    //in case the path changes, just change this line
    private final String RESET_PAGE = "/user/ResetPassword.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try (
            var daoUser = new DAOUser();
            var daoResetTokens = new DAOResetTokens()
        ) {
            String token = request.getParameter("token");

            if (token != null) {
                ResetRecord resetRecord = daoResetTokens.getByToken(token);

                if (resetRecord == null) {
                    request.setAttribute("error", "error_invalid_token");
                    request.getRequestDispatcher(RESET_PAGE).forward(request, response);
                    return;
                }

                if (!resetRecord.isValid()) {
                    request.setAttribute("screen", "expired");
                } else {
                    request.setAttribute("screen", "change_pw");
                    request.setAttribute("user", daoUser.getUserById(resetRecord.getUserId()));
                }
            }
        }

        request.getRequestDispatcher(RESET_PAGE).forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "send_email" -> handleSendEmail(request, response);
            case "reset_password" -> handleChangePassword(request, response);
        }
    }
    
    private void handleChangePassword(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try (
            var daoUser = new DAOUser();
            var daoResetTokens = new DAOResetTokens()
        ) {
            String token = request.getParameter("token");
            String newPassword = request.getParameter("newpw");
            String confirmNewPassword = request.getParameter("confirmnewpw");
            boolean same = newPassword.equals(confirmNewPassword);

            if (token != null) {
                ResetRecord resetRecord = daoResetTokens.getByToken(token);

                if (resetRecord == null) {
                    request.setAttribute("error", "error_invalid_token");
                    request.getRequestDispatcher(RESET_PAGE).forward(request, response);
                    return;
                }

                if (!resetRecord.isValid()) {
                    request.setAttribute("screen", "expired");
                } else if (same) {
                    request.setAttribute("screen", "success");
                    response.setHeader("Refresh", "3; url=" + URLUtils.getBaseURL(request) + "/");
                    daoUser.updatePasswordById(resetRecord.getUserId(), newPassword);
                    daoResetTokens.deleteToken(resetRecord.getUserId());
                } else {
                    request.setAttribute("user", daoUser.getUserById(resetRecord.getUserId()));
                    request.setAttribute("screen", "change_pw");
                    request.setAttribute("error", "error_pw_not_same");
                }
            }
        }
        
        request.getRequestDispatcher(RESET_PAGE).forward(request, response);
    }
    
    private String generateResetUrl(HttpServletRequest req, String token) {
        return String.format(
                "%s://%s:%s%s/user/reset?token=" + token,
                req.getScheme(),
                req.getServerName(),
                req.getServerPort(),
                req.getContextPath()
        );
    }

    private void handleSendEmail(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try (
            var daoUser = new DAOUser();
            var daoResetTokens = new DAOResetTokens()
        ) {
            String email = request.getParameter("email");

            User user = daoUser.getUserByEmail(email);

            int timeout = 1;
            try {
                String val = Config.getConfig(getServletContext()).getOrDefault(
                        "pw.reset.timeout_secs",
                        "1"
                ).toString();

                timeout = Integer.parseInt(val);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            if (user != null) {
                String token = daoResetTokens.createForUserId(user.getUserId(), timeout);
                
                GmailService service = new GmailService(getServletContext());
                
                String content = "Dear user! Someone sent a password request to your account\n"
                        + "I hope this email finds you well, here's the link to reset your password:\n"
                        + generateResetUrl(request, token);
                
                try {
                    service.sendMailTo("Reset Password", content, new String[] { email });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            request.setAttribute("screen", "sent");
            request.setAttribute("timeout", timeout);
            request.getRequestDispatcher(RESET_PAGE).forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "ResetPassword Servlet";
    }
}
