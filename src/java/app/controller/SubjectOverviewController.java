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

        DAOSubject daoSubject = new DAOSubject();
        DAOUser daoUser = new DAOUser();

        int id = Integer.parseInt(request.getParameter("subjectId"));
        Subject displaySubject = daoSubject.getSubjectById(id);

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
        request.getRequestDispatcher(OVERVIEW_PAGE).forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "PricePackageController Servlet";
    }
}
