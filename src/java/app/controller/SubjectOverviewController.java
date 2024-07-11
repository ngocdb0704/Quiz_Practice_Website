package app.controller;

import app.dal.DAOSubject;
import app.dal.DAOUser;
import app.entity.Subject;
import app.entity.SubjectCategory;
import app.entity.User;
import app.utils.Config;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet(name = "SubjectOverviewController", urlPatterns = {"/admin/subjectdetail/overview"})
@MultipartConfig
public class SubjectOverviewController extends HttpServlet {

    private final static String OVERVIEW_PAGE = "/admin/subjectdetail/overview/Overview.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        ServletContext ctx = request.getServletContext();

        String service = request.getParameter("service");

        DAOUser daoUser = new DAOUser();
        DAOSubject daoSubject = new DAOSubject();

        int uId = -1;

        //Just to be safe
        try {
            uId = (int) session.getAttribute("userId");
        } catch (Exception e) {
            if (session.getAttribute("userEmail") != null) {
                try {
                    User fetched = daoUser.getUserByEmail(session.getAttribute("userEmail").toString());
                    uId = fetched.getUserId();
                    System.out.println("D" + uId);
                    session.setAttribute("userId", uId);
                } catch (Exception e1) {
                }
            } else {
                request.getRequestDispatcher("/Unauthorized.jsp").forward(request, response);
                return;
            }
        }

        int userRole;
        try {
            userRole = (int) session.getAttribute("userRole");
        } catch (Exception e) {
            userRole = daoUser.getUserById(uId).getRoleId();
            session.setAttribute("userRole", userRole);
            System.out.println("Dc" + userRole);
        }

        if (userRole != 2 && userRole != 4) {
            request.getRequestDispatcher("/Unauthorized.jsp").forward(request, response);
            return;
        }

        int id = Integer.parseInt(request.getParameter("subjectId"));
        Subject displaySubject = daoSubject.getSubjectById(id);

        if (userRole == 2) {
            request.setAttribute("role", "admin");
        } else if (userRole == 2) {
            request.setAttribute("role", "owner");
        }
        request.setAttribute("subjectId", id);
        request.setAttribute("subjectTitle", displaySubject.getSubjectName());
        request.setAttribute("subjectCategory", displaySubject.getCategoryId());
        request.setAttribute("featured", displaySubject.getIsFeatured());
        request.setAttribute("subjectStatus", displaySubject.getStatusId());
        User owner = daoUser.getUserById(displaySubject.getOwnerId());
        request.setAttribute("ownerName", owner.getFullName());
        request.setAttribute("ownerEmail", owner.getEmail());

        request.setAttribute("subjectTagline", displaySubject.getTagLine());
        request.setAttribute("subjectBrief", displaySubject.getBriefInfo());
        request.setAttribute("subjectDescription", displaySubject.getSubjectDescription());

        Config cfg = new Config(ctx);
        String thumbnailDir = cfg.getStringOrDefault("subjectThumbnail.dir", "/").replaceAll("\\s+", "/");

        request.setAttribute("thumbnailUrl", thumbnailDir + "/" + displaySubject.getThumbnail());

        request.setAttribute("subjectCategoryList", daoSubject.getAllSubjectCategories());
        ConcurrentHashMap<String, String> expertList = daoUser.ExpertsEmailNameMap();

        System.out.println(expertList);

        String jsonMap = (!expertList.isEmpty()) ? expertList
                .reduce(0, (key, val) -> String.format("{\"email\": \"%s\", \"name\": \"%s\"}", key, val), (obj, obj1) -> obj + ", " + obj1).toString()
                : "";
        request.setAttribute("expertList", "[" + jsonMap + "]");

        request.getRequestDispatcher(OVERVIEW_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        ServletContext ctx = request.getServletContext();
        String service = request.getParameter("service");
        DAOUser daoUser = new DAOUser();
        DAOSubject daoSubject = new DAOSubject();

        if (service.equals("edit")) {
            String subjectTitle = request.getParameter("subjectTitle");
            int subjectCategory = Integer.parseInt(request.getParameter("subjectCategory"));
            boolean featured = (request.getParameter("featured") != null);
            int subjectStatus = Integer.parseInt(request.getParameter("subjectStatus"));
            String expertEmail = request.getParameter("expertEmail");
            User owner = daoUser.getUserByEmail(expertEmail);
            String subjectTagline = request.getParameter("subjectTagline");
            String subjectBrief = request.getParameter("subjectBrief");
            String subjectDescription = request.getParameter("subjectDescription");
            String uploadName = request.getParameter("uploadName");

            String thumbnailUrl = "";
            int subjectId = daoSubject.getLatestSubjectId();
            Part filePart = request.getPart("uploadData");
            if (filePart.getSize() > 0 && uploadName != null && (uploadName.endsWith(".png") || uploadName.endsWith(".jpg") || uploadName.endsWith(".jpeg") || uploadName.endsWith(".webm"))) {
                Config cfg = new Config(ctx);
                String thumbnailDir = cfg.getStringOrDefault("subjectThumbnail.dir", "/");
                thumbnailDir = thumbnailDir.replaceAll("\\s+", File.separator);
                String path = ctx.getRealPath(thumbnailDir);

                subjectId = (subjectId < 0) ? 1 : subjectId; //If the table is empty then getLatestSubjectId() will return -1, in that case set subjectId to 1 (as the first element of the table

                String newFileName = path + File.separator + subjectId + "_" + uploadName;
                System.out.println("Save file as:" + newFileName);

                File file = new File(newFileName);
                InputStream ins = filePart.getInputStream();
                byte[] uploaded = new byte[ins.available()];
                ins.read(uploaded);
                try (FileOutputStream outputStream = new FileOutputStream(file)) {
                    outputStream.write(uploaded);
                    thumbnailUrl = subjectId + "_" + uploadName;
                }
            }

            if (owner == null) {
                System.out.println("WHY ARE YOU HERE");
                if (daoSubject.updateSubjectOverview(new Subject(subjectId
                        , subjectTitle
                        , subjectTagline
                        , subjectBrief
                        , subjectDescription
                        , thumbnailUrl
                        , subjectCategory
                        , featured
                        , subjectStatus
                        , -1)) == 1) {
                    session.setAttribute("notification", "<p>Subject created succefully!</p>");
                    session.setAttribute("notification", "Subject updated succefully!");
                }
            }
            else if (owner.getRoleId() == 4 && daoSubject.updateSubjectOverview(new Subject(subjectId, subjectTitle, subjectTagline, subjectBrief, subjectDescription, thumbnailUrl, subjectCategory, featured, subjectStatus, owner.getUserId()), owner.getUserId()) == 1) {
                //System.out.println("Ye" + new Subject(0, subjectTitle, subjectTagline, subjectBrief, subjectDescription, thumbnailUrl, subjectCategory));
                if (daoSubject.updateSubjectOverview(new Subject(subjectId, subjectTitle, subjectTagline, subjectBrief, subjectDescription, thumbnailUrl, subjectCategory, featured, subjectStatus, owner.getUserId())) == 1) {
                    session.setAttribute("notification", "<p>Subject created succefully!</p>");
                    session.setAttribute("notification", "Subject updated succefully!");
                }
            } else {
                //out.print("User was not an Expert");
                //session.setAttribute("notification", "<p style='color: red'>Error: The user email submitted was not of an Expert</p>");
                session.setAttribute("notification", "Error: The user email submitted was not of an Expert");
                System.out.println("User was not an Expert");
            }
            doGet(request, response);
        }

        //request.getRequestDispatcher(OVERVIEW_PAGE).forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "PricePackageController Servlet";
    }
}
