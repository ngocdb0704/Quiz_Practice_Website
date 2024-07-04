/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package app.controller;

import app.dal.DAOSubject;
import app.entity.Subject;
import app.utils.Config;
import app.utils.GmailService;
import app.utils.RSAEncryption;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.nio.file.Paths;

/**
 *
 * @author admin
 */
@WebServlet(name = "SubjectRegisterController", urlPatterns = {"/public/registerSubjectGuest"})
public class SubjectRegisterController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Config cfg = new Config(getServletContext());
        DAOSubject daoSubject = new DAOSubject();
        String service = request.getParameter("service");
        int packageId = Integer.valueOf(request.getParameter("selectedPackage"));
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        String fullName = request.getParameter("fullName");
        String gender = request.getParameter("gender");
        if (service.equals("payRegister")) {
            String secure = request.getParameter("secure");
            String redirectTo = "/ContactUs.jsp";
            if (authenData(email, mobile, fullName, gender, packageId, secure)) {
                redirectTo = "/index.jsp";
            }
            dispath(request, response, redirectTo);
        }
        if (service.equals("freshRegister")) {
            String reachURL = buildRegisterUrl(email, mobile, fullName, gender, packageId);
            Subject registeredSubject = daoSubject.getSubjectByPackageId(packageId);
            GmailService gmailService = new GmailService(getServletContext());
            //note issue: cannot use subject's thumbnail in email YET, thumbnails should be go online
            String emailBody = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
                    + "<html dir=\"ltr\" xmlns=\"http://www.w3.org/1999/xhtml\" "
                    + "xmlns:o=\"urn:schemas-microsoft-com:office:office\" lang=\"en\">\n"
                    + " <head>\n"
                    + "  <meta charset=\"UTF-8\">\n"
                    + "  <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">\n"
                    + "  <meta name=\"x-apple-disable-message-reformatting\">\n"
                    + "  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
                    + "  <meta content=\"telephone=no\" name=\"format-detection\">\n"
                    + "  <title>New Template</title>\n"
                    + "  <link href=\"https://fonts.googleapis.com/css2?family=Montaga&display=swap\" rel=\"stylesheet\">\n"
                    + "  <link href=\"https://fonts.googleapis.com/css2?family=Lexend&display=swap\" rel=\"stylesheet\"><!--<![endif]-->\n"
                    + "  <style type=\"text/css\">\n"
                    + "#outlook a {\n"
                    + "	padding:0;\n"
                    + "}\n"
                    + ".es-button {\n"
                    + "	mso-style-priority:100!important;\n"
                    + "	text-decoration:none!important;\n"
                    + "}\n"
                    + "a[x-apple-data-detectors] {\n"
                    + "	color:inherit!important;\n"
                    + "	text-decoration:none!important;\n"
                    + "	font-size:inherit!important;\n"
                    + "	font-family:inherit!important;\n"
                    + "	font-weight:inherit!important;\n"
                    + "	line-height:inherit!important;\n"
                    + "}\n"
                    + ".es-desk-hidden {\n"
                    + "	display:none;\n"
                    + "	float:left;\n"
                    + "	overflow:hidden;\n"
                    + "	width:0;\n"
                    + "	max-height:0;\n"
                    + "	line-height:0;\n"
                    + "	mso-hide:all;\n"
                    + "}\n"
                    + "@media only screen and (max-width:600px) {p, ul li, ol li, a { line-height:150%!important } h1, h2, h3, h1 a, h2 a, h3 a { line-height:120%!important } h1 { font-size:30px!important; text-align:left } h2 { font-size:24px!important; text-align:left } h3 { font-size:20px!important; text-align:left } .es-header-body h1 a, .es-content-body h1 a, .es-footer-body h1 a { font-size:30px!important; text-align:left } .es-header-body h2 a, .es-content-body h2 a, .es-footer-body h2 a { font-size:24px!important; text-align:left } .es-header-body h3 a, .es-content-body h3 a, .es-footer-body h3 a { font-size:20px!important; text-align:left } .es-menu td a { font-size:14px!important } .es-header-body p, .es-header-body ul li, .es-header-body ol li, .es-header-body a { font-size:14px!important } .es-content-body p, .es-content-body ul li, .es-content-body ol li, .es-content-body a { font-size:14px!important } .es-footer-body p, .es-footer-body ul li, .es-footer-body ol li, .es-footer-body a { font-size:14px!important } .es-infoblock p, .es-infoblock ul li, .es-infoblock ol li, .es-infoblock a { font-size:12px!important } *[class=\"gmail-fix\"] { display:none!important } .es-m-txt-c, .es-m-txt-c h1, .es-m-txt-c h2, .es-m-txt-c h3 { text-align:center!important } .es-m-txt-r, .es-m-txt-r h1, .es-m-txt-r h2, .es-m-txt-r h3 { text-align:right!important } .es-m-txt-l, .es-m-txt-l h1, .es-m-txt-l h2, .es-m-txt-l h3 { text-align:left!important } .es-m-txt-r img, .es-m-txt-c img, .es-m-txt-l img { display:inline!important } .es-button-border { display:inline-block!important } a.es-button, button.es-button { font-size:18px!important; display:inline-block!important } .es-adaptive table, .es-left, .es-right { width:100%!important } .es-content table, .es-header table, .es-footer table, .es-content, .es-footer, .es-header { width:100%!important; max-width:600px!important } .es-adapt-td { display:block!important; width:100%!important } .adapt-img { width:100%!important; height:auto!important } .es-m-p0 { padding:0!important } .es-m-p0r { padding-right:0!important } .es-m-p0l { padding-left:0!important } .es-m-p0t { padding-top:0!important } .es-m-p0b { padding-bottom:0!important } .es-m-p20b { padding-bottom:20px!important } .es-mobile-hidden, .es-hidden { display:none!important } tr.es-desk-hidden, td.es-desk-hidden, table.es-desk-hidden { width:auto!important; overflow:visible!important; float:none!important; max-height:inherit!important; line-height:inherit!important } tr.es-desk-hidden { display:table-row!important } table.es-desk-hidden { display:table!important } td.es-desk-menu-hidden { display:table-cell!important } .es-menu td { width:1%!important } table.es-table-not-adapt, .esd-block-html table { width:auto!important } table.es-social { display:inline-block!important } table.es-social td { display:inline-block!important } .es-desk-hidden { display:table-row!important; width:auto!important; overflow:visible!important; max-height:inherit!important } .es-m-p5 { padding:5px!important } .es-m-p5t { padding-top:5px!important } .es-m-p5b { padding-bottom:5px!important } .es-m-p5r { padding-right:5px!important } .es-m-p5l { padding-left:5px!important } .es-m-p10 { padding:10px!important } .es-m-p10t { padding-top:10px!important } .es-m-p10b { padding-bottom:10px!important } .es-m-p10r { padding-right:10px!important } .es-m-p10l { padding-left:10px!important } .es-m-p15 { padding:15px!important } .es-m-p15t { padding-top:15px!important } .es-m-p15b { padding-bottom:15px!important } .es-m-p15r { padding-right:15px!important } .es-m-p15l { padding-left:15px!important } .es-m-p20 { padding:20px!important } .es-m-p20t { padding-top:20px!important } .es-m-p20r { padding-right:20px!important } .es-m-p20l { padding-left:20px!important } .es-m-p25 { padding:25px!important } .es-m-p25t { padding-top:25px!important } .es-m-p25b { padding-bottom:25px!important } .es-m-p25r { padding-right:25px!important } .es-m-p25l { padding-left:25px!important } .es-m-p30 { padding:30px!important } .es-m-p30t { padding-top:30px!important } .es-m-p30b { padding-bottom:30px!important } .es-m-p30r { padding-right:30px!important } .es-m-p30l { padding-left:30px!important } .es-m-p35 { padding:35px!important } .es-m-p35t { padding-top:35px!important } .es-m-p35b { padding-bottom:35px!important } .es-m-p35r { padding-right:35px!important } .es-m-p35l { padding-left:35px!important } .es-m-p40 { padding:40px!important } .es-m-p40t { padding-top:40px!important } .es-m-p40b { padding-bottom:40px!important } .es-m-p40r { padding-right:40px!important } .es-m-p40l { padding-left:40px!important } }\n"
                    + "@media screen and (max-width:384px) {.mail-message-content { width:414px!important } }\n"
                    + "</style>\n"
                    + " </head>\n"
                    + " <body style=\"width:100%;font-family:Lexend, Arial, sans-serif;"
                    + "-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0\">\n"
                    + "  <div dir=\"ltr\" class=\"es-wrapper-color\" lang=\"en\" style=\"background-color:#EDEDED\">\n"
                    + "   <table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" "
                    + "role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;"
                    + "border-spacing:0px;padding:0;Margin:0;width:100%;height:100%;background-repeat:repeat;"
                    + "background-position:center top;background-color:#EDEDED\">\n"
                    + "     <tr>\n"
                    + "      <td valign=\"top\" style=\"padding:0;Margin:0\">\n"
                    + "       <table class=\"es-header\" cellspacing=\"0\" cellpadding=\"0\" "
                    + "align=\"center\" role=\"none\" style=\"mso-table-lspace:0pt;"
                    + "mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;"
                    + "table-layout:fixed !important;width:100%;background-color:transparent;"
                    + "background-repeat:repeat;background-position:center top\">\n"
                    + "         <tr>\n"
                    + "          <td align=\"center\" style=\"padding:0;Margin:0\">\n"
                    + "           <table class=\"es-header-body\" cellspacing=\"0\" "
                    + "cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\" role=\"none\" "
                    + "style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;"
                    + "border-spacing:0px;background-color:#2C2C2C;width:600px\">\n"
                    + "             <tr>\n"
                    + "              <td align=\"left\" style=\"padding:30px;Margin:0\">\n"
                    + "               <table cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" "
                    + "role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;"
                    + "border-collapse:collapse;border-spacing:0px\">\n"
                    + "                 <tr>\n"
                    + "                  <td class=\"es-m-p0r\" valign=\"top\" align=\"center\" "
                    + "style=\"padding:0;Margin:0;width:540px\">\n"
                    + "                   <table width=\"100%\" cellspacing=\"0\" "
                    + "cellpadding=\"0\" role=\"none\" style=\"mso-table-lspace:0pt;"
                    + "mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                    + "                     <tr>\n"
                    + "                      <td align=\"center\" style=\"padding:0;Margin:0;display:none\"></td>\n"
                    + "                     </tr>\n"
                    + "                   </table></td>\n"
                    + "                 </tr>\n"
                    + "               </table></td>\n"
                    + "             </tr>\n"
                    + "           </table></td>\n"
                    + "         </tr>\n"
                    + "       </table>\n"
                    + "       <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" "
                    + "align=\"center\" role=\"none\" style=\"mso-table-lspace:0pt;"
                    + "mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;"
                    + "table-layout:fixed !important;width:100%\">\n"
                    + "         <tr>\n"
                    + "          <td align=\"center\" style=\"padding:0;Margin:0\">\n"
                    + "           <table class=\"es-content-body\" cellspacing=\"0\" "
                    + "cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\" role=\"none\" "
                    + "style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;"
                    + "border-spacing:0px;background-color:#FFFFFF;width:600px\">\n"
                    + "             <tr>\n"
                    + "              <td align=\"left\" style=\"padding:0;Margin:0\">\n"
                    + "               <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" "
                    + "role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;"
                    + "border-collapse:collapse;border-spacing:0px\">\n"
                    + "                 <tr>\n"
                    + "                  <td class=\"es-m-p0r es-m-p20b\" valign=\"top\" "
                    + "align=\"center\" style=\"padding:0;Margin:0;width:600px\">\n"
                    + "                   <table width=\"100%\" cellspacing=\"0\" "
                    + "cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;"
                    + "mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                    + "                     <tr>\n"
                    + "                      <td align=\"center\" style=\"padding:0;Margin:0;"
                    + "font-size:0px\"><img class=\"adapt-img\" "
                    + "src=\"https://fixsugv.stripocdn.email/content/guids/CABINET_c6828601e8e913f8c24c26e3873c9a3c72f452b84ecab44f0fb51679f208566a/images/sub47.jpeg\" "
                    + "alt style=\"display:block;border:0;outline:none;text-decoration:none;"
                    + "-ms-interpolation-mode:bicubic\" width=\"600\" height=\"300\"></td>\n"
                    + "                     </tr>\n"
                    + "                   </table></td>\n"
                    + "                 </tr>\n"
                    + "               </table></td>\n"
                    + "             </tr>\n"
                    + "             <tr>\n"
                    + "              <td align=\"left\" style=\"Margin:0;padding-top:30px;"
                    + "padding-left:30px;padding-right:30px;padding-bottom:40px\">\n"
                    + "               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" "
                    + "role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;"
                    + "border-collapse:collapse;border-spacing:0px\">\n"
                    + "                 <tr>\n"
                    + "                  <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:540px\">\n"
                    + "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" "
                    + "role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;"
                    + "border-collapse:collapse;border-spacing:0px\">\n"
                    + "                     <tr>\n"
                    + "                      <td align=\"center\" style=\"padding:0;Margin:0;"
                    + "padding-top:20px;padding-bottom:30px\">"
                    + "<h1 style=\"Margin:0;line-height:36px;"
                    + "mso-line-height-rule:exactly;font-family:Montaga, Arial, serif;font-size:30px;"
                    + "font-style:normal;font-weight:normal;color:#2C2C2C\">"
                    + "Subject Registration"
                    + "</h1>"
                    + "</td>\n"
                    + "                     </tr>\n"
                    + "                     <tr>\n"
                    + "                      <td align=\"left\" style=\"padding:0;Margin:0;padding-bottom:30px\">"
                    + "<p style=\"Margin:0;-webkit-text-size-adjust:none;"
                    + "-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:Lexend, Arial, sans-serif;"
                    + "line-height:21px;color:#2C2C2C;font-size:14px\">"
                    + "Dear " + fullName + ","
                    + "</p>"
                    + "<p style=\"Margin:0;-webkit-text-size-adjust:none;"
                    + "-ms-text-size-adjust:none;mso-line-height-rule:exactly;"
                    + "font-family:Lexend, Arial, sans-serif;line-height:21px;"
                    + "color:#2C2C2C;font-size:14px\"><br></p><p style=\"Margin:0;"
                    + "-webkit-text-size-adjust:none;-ms-text-size-adjust:none;"
                    + "mso-line-height-rule:exactly;font-family:Lexend, Arial, sans-serif;"
                    + "line-height:21px;color:#2C2C2C;font-size:14px\">"
                    + "Thank you for having registered online subjects on our website. To complete your registration, please check the information below and click the button to purchase the subject."
                    + "<br>"
                    + "</p>"
                    + "</td>\n"
                    + "                     </tr>\n"
                    + "                   </table></td>\n"
                    + "                 </tr>\n"
                    + "               </table></td>\n"
                    + "             </tr>\n"
                    + "             <tr>\n"
                    + "              <td align=\"left\" style=\"padding:0;Margin:0;padding-top:20px;"
                    + "padding-left:30px;padding-right:30px\"><!--[if mso]>"
                    + "<table style=\"width:540px\" cellpadding=\"0\" cellspacing=\"0\">"
                    + "<tr>"
                    + "<td style=\"width:252px\" valign=\"top\">"
                    + "<![endif]-->\n"
                    + "               <table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" "
                    + "align=\"left\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;"
                    + "border-collapse:collapse;border-spacing:0px;float:left\">\n"
                    + "                 <tr>\n"
                    + "                  <td class=\"es-m-p20b\" align=\"left\" style=\"padding:0;Margin:0;width:252px\">\n"
                    + "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" "
                    + "role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;"
                    + "border-collapse:collapse;border-spacing:0px\">\n"
                    + "                     <tr>\n"
                    + "                      <td align=\"center\" class=\"es-m-txt-c\" style=\"padding:0;Margin:0;"
                    + "padding-bottom:10px\"><h2 class=\"name\" style=\"Margin:0;line-height:24px;"
                    + "mso-line-height-rule:exactly;font-family:Montaga, Arial, serif;font-size:20px;"
                    + "font-style:normal;font-weight:normal;color:#2C2C2C\">Your Information</h2></td>\n"
                    + "                     </tr>\n"
                    + "                     <tr>\n"
                    + "                      <td align=\"left\" style=\"padding:0;Margin:0;padding-top:5px\">"
                    + "<p style=\"Margin:0;-webkit-text-size-adjust:none;"
                    + "-ms-text-size-adjust:none;mso-line-height-rule:exactly;"
                    + "font-family:Lexend, Arial, sans-serif;line-height:21px;"
                    + "color:#2C2C2C;font-size:14px\">"
                    + "Email: " + email + ""
                    + "</p>"
                    + "</td>\n"
                    + "                     </tr>\n"
                    + "                     <tr>\n"
                    + "                      <td align=\"left\" style=\"padding:0;Margin:0;padding-top:5px\">"
                    + "<p style=\"Margin:0;-webkit-text-size-adjust:none;"
                    + "-ms-text-size-adjust:none;mso-line-height-rule:exactly;"
                    + "font-family:Lexend, Arial, sans-serif;line-height:21px;"
                    + "color:#2C2C2C;font-size:14px\">"
                    + "Mobile: " + mobile + ""
                    + "</p>"
                    + "</td>\n"
                    + "                     </tr>\n"
                    + "                     <tr>\n"
                    + "                      <td align=\"left\" style=\"padding:0;"
                    + "Margin:0;padding-top:5px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;"
                    + "-ms-text-size-adjust:none;mso-line-height-rule:exactly;"
                    + "font-family:Lexend, Arial, sans-serif;line-height:21px;"
                    + "color:#2C2C2C;font-size:14px\">"
                    + "Full Name: " + fullName + ""
                    + "</p>"
                    + "</td>\n"
                    + "                     </tr>\n"
                    + "                     <tr>\n"
                    + "                      <td align=\"left\" style=\"padding:0;Margin:0;padding-top:5px;"
                    + "padding-bottom:20px\">"
                    + "<p style=\"Margin:0;-webkit-text-size-adjust:none;"
                    + "-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:Lexend, Arial, sans-serif;"
                    + "line-height:21px;color:#2C2C2C;font-size:14px\">"
                    + "Gender: " + gender + ""
                    + "</p>"
                    + "</td>\n"
                    + "                     </tr>\n"
                    + "                   </table></td>\n"
                    + "                 </tr>\n"
                    + "               </table>\n"
                    + "               <table class=\"es-right\" cellspacing=\"0\" "
                    + "cellpadding=\"0\" align=\"right\" role=\"none\" style=\"mso-table-lspace:0pt;"
                    + "mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right\">\n"
                    + "                 <tr>\n"
                    + "                  <td align=\"left\" style=\"padding:0;Margin:0;width:248px\">\n"
                    + "                   <table style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;"
                    + "border-collapse:collapse;border-spacing:0px;background-position:left top\" width=\"100%\" "
                    + "cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\">\n"
                    + "                     <tr>\n"
                    + "                      <td class=\"es-m-txt-c\" align=\"center\" "
                    + "style=\"padding:0;Margin:0;padding-bottom:10px\">"
                    + "<h2 class=\"name\" style=\"Margin:0;line-height:24px;"
                    + "mso-line-height-rule:exactly;font-family:Montaga, Arial, serif;"
                    + "font-size:20px;font-style:normal;font-weight:normal;color:#2C2C2C\">"
                    + "Subject's information"
                    + "</h2>"
                    + "</td>\n"
                    + "                     </tr>\n"
                    + "                     <tr>\n"
                    + "                      <td class=\"es-m-txt-c\" align=\"left\" "
                    + "style=\"padding:0;Margin:0;padding-top:5px;padding-bottom:10px\">"
                    + "<p class=\"description\" style=\"Margin:0;-webkit-text-size-adjust:none;"
                    + "-ms-text-size-adjust:none;mso-line-height-rule:exactly;"
                    + "font-family:Lexend, Arial, sans-serif;line-height:21px;"
                    + "color:#2C2C2C;font-size:14px\">"
                    + "Title: " + registeredSubject.getSubjectName() + "&nbsp;</p>"
                    + "</td>\n"
                    + "                     </tr>\n"
                    + "                     <tr>\n"
                    + "                      <td class=\"es-m-txt-c\" align=\"left\" "
                    + "style=\"padding:0;Margin:0;padding-top:5px;padding-bottom:10px\">"
                    + "<p class=\"description\" style=\"Margin:0;-webkit-text-size-adjust:none;"
                    + "-ms-text-size-adjust:none;mso-line-height-rule:exactly;"
                    + "font-family:Lexend, Arial, sans-serif;line-height:21px;"
                    + "color:#2C2C2C;font-size:14px\">"
                    + "\" " + registeredSubject.getTagLine() + "\""
                    + "</p>"
                    + "</td>\n"
                    + "                     </tr>\n"
                    + "                     <tr>\n"
                    + "                      <td class=\"es-m-txt-c\" align=\"left\" "
                    + "style=\"padding:0;Margin:0;padding-top:5px;padding-bottom:10px\">"
                    + "<p class=\"description\" style=\"Margin:0;-webkit-text-size-adjust:none;"
                    + "-ms-text-size-adjust:none;mso-line-height-rule:exactly;"
                    + "font-family:Lexend, Arial, sans-serif;line-height:21px;"
                    + "color:#2C2C2C;font-size:14px\">"
                    + "Package: " + registeredSubject.getLowestPackageName() + "&nbsp;"
                    + "</p>"
                    + "</td>\n"
                    + "                     </tr>\n"
                    + "                     <tr>\n"
                    + "                      <td class=\"es-m-txt-c\" align=\"left\" "
                    + "style=\"padding:0;Margin:0;padding-top:5px;"
                    + "padding-bottom:10px\"><p class=\"description\" "
                    + "style=\"Margin:0;-webkit-text-size-adjust:none;"
                    + "-ms-text-size-adjust:none;mso-line-height-rule:exactly;"
                    + "font-family:Lexend, Arial, sans-serif;line-height:21px;"
                    + "color:#2C2C2C;font-size:14px\">"
                    + "Sale Price: " + (int) registeredSubject.getPackageSalePrice() * 1000 + "VND&nbsp;"
                    + "</p>"
                    + "</td>\n"
                    + "                     </tr>\n"
                    + "                     <tr>\n"
                    + "                      <td align=\"center\" style=\"padding:0;Margin:0\">\n"
                    + "                        <span class=\"msohide es-button-border\" "
                    + "style=\"border-style:solid;border-color:#2CB543;"
                    + "background:#55f646;border-width:0px;display:inline-block;"
                    + "border-radius:30px;width:auto;mso-hide:all\">"
                    + "<a href=\"" + generateRegisterUrl(request, reachURL) + "\" class=\"es-button\" target=\"_blank\" "
                    + "style=\"mso-style-priority:100 !important;text-decoration:none;"
                    + "-webkit-text-size-adjust:none;-ms-text-size-adjust:none;"
                    + "mso-line-height-rule:exactly;color:#ffffff;font-size:18px;"
                    + "display:inline-block;background:#55f646;border-radius:30px;"
                    + "font-family:Lexend, Arial, sans-serif;font-weight:normal;"
                    + "font-style:normal;line-height:22px;width:auto;text-align:center;"
                    + "padding:10px 30px 10px 30px;mso-padding-alt:0;mso-border-alt:10px solid #55f646\">"
                    + " Purchase Now </a>"
                    + "</span><!--<![endif]--></td>\n"
                    + "                     </tr>\n"
                    + "                   </table></td>\n"
                    + "                 </tr>\n"
                    + "               </table></td>\n"
                    + "             </tr>\n"
                    + "           </table></td>\n"
                    + "         </tr>\n"
                    + "       </table>\n"
                    + "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" "
                    + "align=\"center\" role=\"none\" style=\"mso-table-lspace:0pt;"
                    + "mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;"
                    + "table-layout:fixed !important;width:100%\">\n"
                    + "         <tr>\n"
                    + "          <td align=\"center\" style=\"padding:0;Margin:0\">\n"
                    + "           <table bgcolor=\"#ffffff\" class=\"es-content-body\" "
                    + "align=\"center\" cellpadding=\"0\" cellspacing=\"0\" role=\"none\" "
                    + "style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;"
                    + "border-spacing:0px;background-color:#FFFFFF;width:600px\">\n"
                    + "             <tr>\n"
                    + "              <td class=\"es-m-p0r\" align=\"left\" style=\"padding:0;Margin:0;padding-right:30px\">\n"
                    + "               <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-left\" "
                    + "align=\"left\" role=\"none\" style=\"mso-table-lspace:0pt;"
                    + "mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\">\n"
                    + "                 <tr>\n"
                    + "                  <td class=\"es-m-p20b\" align=\"left\" style=\"padding:0;Margin:0;width:275px\">\n"
                    + "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" "
                    + "role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;"
                    + "border-collapse:collapse;border-spacing:0px\">\n"
                    + "                     <tr>\n"
                    + "                      <td align=\"center\" style=\"padding:0;Margin:0;font-size:0px\">"
                    + "<a target=\"_blank\" href=\"https://viewstripo.email\" "
                    + "style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;"
                    + "mso-line-height-rule:exactly;text-decoration:underline;color:#2C2C2C;"
                    + "font-size:14px\"><img class=\"adapt-img\" src=\"https://assets.entrepreneur.com/content/3x2/2000/20160603173131-businesswoman-working-desk-workplace.jpeg\" "
                    + "alt=\"Doreen Henry\" style=\"display:block;border:0;outline:none;"
                    + "text-decoration:none;-ms-interpolation-mode:bicubic\" "
                    + "width=\"275\" title=\"Doreen Henry\"></a></td>\n"
                    + "                     </tr>\n"
                    + "                   </table></td>\n"
                    + "                 </tr>\n"
                    + "               </table>\n"
                    + "               <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-right\" "
                    + "align=\"right\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;"
                    + "border-collapse:collapse;border-spacing:0px;float:right\">\n"
                    + "                 <tr>\n"
                    + "                  <td align=\"left\" style=\"padding:0;Margin:0;width:275px\">\n"
                    + "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" "
                    + "role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;"
                    + "border-collapse:collapse;border-spacing:0px\">\n"
                    + "                     <tr>\n"
                    + "                      <td align=\"left\" class=\"es-m-p20t es-m-p20b es-m-p30r es-m-p30l\" "
                    + "style=\"padding:0;Margin:0;padding-right:15px;padding-bottom:30px\">"
                    + "<p style=\"Margin:0;"
                    + "-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;"
                    + "font-family:Lexend, Arial, sans-serif;line-height:21px;color:#2C2C2C;font-size:14px\">"
                    + "Thank you for choosing <strong>Quiz_Practice</strong>&nbsp;for your career needs. We are committed to providing you with outstanding education services."
                    + "</p>"
                    + "<p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;"
                    + "mso-line-height-rule:exactly;font-family:Lexend, Arial, sans-serif;"
                    + "line-height:21px;color:#2C2C2C;font-size:14px\">"
                    + "<br>"
                    + "</p>"
                    + "<p style=\"Margin:0;-webkit-text-size-adjust:none;"
                    + "-ms-text-size-adjust:none;mso-line-height-rule:exactly;"
                    + "font-family:Lexend, Arial, sans-serif;line-height:21px;"
                    + "color:#2C2C2C;font-size:14px\">"
                    + "Warm regards,"
                    + "</p>"
                    + "<p style=\"Margin:0;-webkit-text-size-adjust:none;"
                    + "-ms-text-size-adjust:none;mso-line-height-rule:exactly;"
                    + "font-family:Lexend, Arial, sans-serif;line-height:21px;"
                    + "color:#2C2C2C;font-size:14px\">"
                    + "<b>Ngoc Dinh</b>"
                    + "</p>"
                    + "<p style=\"Margin:0;-webkit-text-size-adjust:none;"
                    + "-ms-text-size-adjust:none;mso-line-height-rule:exactly;"
                    + "font-family:Lexend, Arial, sans-serif;line-height:21px;"
                    + "color:#2C2C2C;font-size:14px\">"
                    + "<b>Team Lead</b>"
                    + "</p>"
                    + "<p style=\"Margin:0;-webkit-text-size-adjust:none;"
                    + "-ms-text-size-adjust:none;mso-line-height-rule:exactly;"
                    + "font-family:Lexend, Arial, sans-serif;line-height:21px;"
                    + "color:#2C2C2C;font-size:14px\">"
                    + "<b>Quiz_Practice</b>"
                    + "</p>"
                    + "</td>\n"
                    + "                     </tr>\n"
                    + "                   </table></td>\n"
                    + "                 </tr>\n"
                    + "               </table></td>\n"
                    + "             </tr>\n"
                    + "           </table></td>\n"
                    + "         </tr>\n"
                    + "       </table>\n"
                    + "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-footer\" align=\"center\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\">\n"
                    + "         <tr>\n"
                    + "          <td align=\"center\" style=\"padding:0;Margin:0\">\n"
                    + "           <table class=\"es-footer-body\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#939393;width:600px\">\n"
                    + "             <tr>\n"
                    + "              <td align=\"left\" style=\"Margin:0;padding-left:30px;padding-right:30px;padding-top:35px;padding-bottom:35px\"><!--[if mso]><table style=\"width:540px\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"width:235px\" valign=\"top\"><![endif]-->\n"
                    + "               <table cellspacing=\"0\" cellpadding=\"0\" align=\"left\" class=\"es-left\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\">\n"
                    + "                 <tr>\n"
                    + "                  <td class=\"es-m-p20b\" align=\"left\" style=\"padding:0;Margin:0;width:235px\">\n"
                    + "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                    + "                     <tr>\n"
                    + "                      <td align=\"left\" style=\"padding:0;Margin:0;padding-bottom:5px\"><h1 style=\"Margin:0;line-height:36px;mso-line-height-rule:exactly;font-family:Montaga, Arial, serif;font-size:30px;font-style:normal;font-weight:normal;color:#2C2C2C\">Contact Us</h1></td>\n"
                    + "                     </tr>\n"
                    + "                     <tr>\n"
                    + "                      <td align=\"left\" style=\"padding:0;Margin:0;padding-bottom:5px;padding-top:15px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:Lexend, Arial, sans-serif;line-height:21px;color:#2C2C2C;font-size:14px\"><b>quizPractice4U@gmail.com</b></p></td>\n"
                    + "                     </tr>\n"
                    + "                     <tr>\n"
                    + "                      <td align=\"left\" style=\"padding:0;Margin:0;padding-top:5px;padding-bottom:5px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:Lexend, Arial, sans-serif;line-height:21px;color:#2C2C2C;font-size:14px\"><a target=\"_blank\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:none;color:#2C2C2C;font-size:14px\" href=\"tel:+(000)123456789\">+(84)&nbsp;</a>888899990</p></td>\n"
                    + "                     </tr>\n"
                    + "                     <tr>\n"
                    + "                      <td align=\"left\" style=\"padding:0;Margin:0;padding-top:5px;padding-bottom:5px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:Lexend, Arial, sans-serif;line-height:21px;color:#2C2C2C;font-size:14px\">Thach That, Ha Noi</p></td>\n"
                    + "                     </tr>\n"
                    + "                   </table></td>\n"
                    + "                 </tr>\n"
                    + "               </table>\n"
                    + "               <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-right\" align=\"right\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right\">\n"
                    + "                 <tr>\n"
                    + "                  <td align=\"left\" style=\"padding:0;Margin:0;width:285px\">\n"
                    + "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                    + "                     <tr>\n"
                    + "                      <td align=\"left\" style=\"padding:0;Margin:0;padding-bottom:5px\"><h1 style=\"Margin:0;line-height:36px;mso-line-height-rule:exactly;font-family:Montaga, Arial, serif;font-size:30px;font-style:normal;font-weight:normal;color:#2C2C2C\">Follow Us</h1></td>\n"
                    + "                     </tr>\n"
                    + "                     <tr>\n"
                    + "                      <td align=\"left\" style=\"padding:0;Margin:0;padding-top:10px;font-size:0\">\n"
                    + "                       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-table-not-adapt es-social\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                    + "                         <tr>\n"
                    + "                          <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;padding-right:15px\"><img src=\"https://fixsugv.stripocdn.email/content/assets/img/social-icons/rounded-black/facebook-rounded-black.png\" alt=\"Fb\" title=\"Facebook\" width=\"32\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic;font-size:12px\"></td>\n"
                    + "                          <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;padding-right:15px\"><img src=\"https://fixsugv.stripocdn.email/content/assets/img/social-icons/rounded-black/instagram-rounded-black.png\" alt=\"Ig\" title=\"Instagram\" width=\"32\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic;font-size:12px\"></td>\n"
                    + "                          <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;padding-right:15px\"><img src=\"https://fixsugv.stripocdn.email/content/assets/img/social-icons/rounded-black/youtube-rounded-black.png\" alt=\"Yt\" title=\"Youtube\" width=\"32\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic;font-size:12px\"></td>\n"
                    + "                          <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0\"><img src=\"https://fixsugv.stripocdn.email/content/assets/img/social-icons/rounded-black/x-rounded-black.png\" alt=\"X\" title=\"X.com\" width=\"32\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic;font-size:12px\"></td>\n"
                    + "                         </tr>\n"
                    + "                       </table></td>\n"
                    + "                     </tr>\n"
                    + "                   </table></td>\n"
                    + "                 </tr>\n"
                    + "               </table><!--[if mso]></td></tr></table><![endif]--></td>\n"
                    + "             </tr>\n"
                    + "           </table></td>\n"
                    + "         </tr>\n"
                    + "       </table>\n"
                    + "       <table class=\"es-footer\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\">\n"
                    + "         <tr>\n"
                    + "          <td align=\"center\" style=\"padding:0;Margin:0\">\n"
                    + "           <table class=\"es-footer-body\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;width:600px\" role=\"none\">\n"
                    + "             <tr>\n"
                    + "              <td align=\"left\" style=\"Margin:0;padding-top:20px;padding-bottom:20px;padding-left:20px;padding-right:20px\">\n"
                    + "               <table cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                    + "                 <tr>\n"
                    + "                  <td align=\"left\" style=\"padding:0;Margin:0;width:560px\">\n"
                    + "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                    + "                     <tr>\n"
                    + "                      <td align=\"center\" style=\"padding:0;Margin:0;display:none\"></td>\n"
                    + "                     </tr>\n"
                    + "                   </table></td>\n"
                    + "                 </tr>\n"
                    + "               </table></td>\n"
                    + "             </tr>\n"
                    + "           </table></td>\n"
                    + "         </tr>\n"
                    + "       </table></td>\n"
                    + "     </tr>\n"
                    + "   </table>\n"
                    + "  </div>\n"
                    + " </body>\n"
                    + "</html>";
            try {
                gmailService.sendMailWithContent("Quiz_Practice Subject Register", emailBody, new String[]{email});
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.sendRedirect("SubjectsList");
        }
    }

    private String fixData(String input) {
        input = input.trim();
        input = input.replaceAll(" ", "+");
        return input;
    }

    private boolean authenData(String email, String mobile, String fullName, String gender, int packageId, String secure) {
        RSAEncryption rsa = new RSAEncryption();
        String path = String.valueOf(Paths.get("E:\\summer2024-swp391.se1830-g6\\web\\private\\privateKey.rsa"));
        String initData = email + mobile + fullName + gender + packageId;
        //there's a big bug that the request remove my plus (+) by a space ( )
        //I fixed it, wasted 1 hour trying to figure out what happen
        secure = fixData(secure);
        String truthData = rsa.RSAdecryption(secure, path);
        return initData.equals(truthData);
    }

    private String buildRegisterUrl(String email, String mobile, String fullName, String gender, int packageId) {
        RSAEncryption rsa = new RSAEncryption();
        String toUrl = "service=payRegister";
        String path = String.valueOf(Paths.get("E:\\summer2024-swp391.se1830-g6\\web\\private\\publicKey.rsa"));
        String initData = email + mobile + fullName + gender + packageId;
        String add;
        add = "&email=" + email;
        toUrl += add;
        add = "&mobile=" + mobile;
        toUrl += add;
        add = "&fullName=" + fullName;
        toUrl += add;
        add = "&gender=" + gender;
        toUrl += add;
        add = "&selectedPackage=" + packageId;
        toUrl += add;
        add = "&secure=" + rsa.RSAencryption(initData, path);
        toUrl += add;
        return toUrl;
    }

    private String generateRegisterUrl(HttpServletRequest req, String token) {
        return String.format(
                "%s://%s:%s%s/public/registerSubjectGuest?" + token,
                req.getScheme(),
                req.getServerName(),
                req.getServerPort(),
                req.getContextPath()
        );
    }

    public void dispath(HttpServletRequest request, HttpServletResponse response, String url)
            throws ServletException, IOException {
        RequestDispatcher dispth = request.getRequestDispatcher(url);
        //run, show
        dispth.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
