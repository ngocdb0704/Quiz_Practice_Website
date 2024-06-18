
package app.controller;

import app.dal.DAOPackage;
import app.utils.Parsers;
import java.io.IOException;
import java.io.PrintWriter;
import app.entity.Package;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name="SubjectPricePackageController", urlPatterns={"/admin/subjectdetail/pricepackage"})
public class SubjectPricePackageController extends HttpServlet {
    private final static String PRICE_PACKAGE = "/admin/subjectdetail/pricepackage/PricePackage.jsp";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        DAOPackage dpkg = new DAOPackage();
        
        int id = Parsers.parseIntOrDefault(request.getParameter("subjectId"), -1);
        
        if (id == -1) {
            response.sendRedirect(request.getContextPath() + "/admin/subjectlist");
            return;
        }
        
        request.setAttribute("packages", dpkg.getPricePackagesBySubjectId(id));
        request.getRequestDispatcher(PRICE_PACKAGE).forward(request, response);
    } 

    private void switchPackage(int id, boolean isActive) {
        if (id == -1) return;

        DAOPackage dpkg = new DAOPackage();

        Package pkg = dpkg.getPricePackageByPackageId(id);
        if (pkg == null) return;
        pkg.setActive(isActive);

        dpkg.modifyPackage(pkg);
    }
    
    private void addPackage(int subjectId, HttpServletRequest req) {
        if (subjectId == -1) return;
        
        DAOPackage dpkg = new DAOPackage();
        
        int price = Parsers.parseIntOrDefault(req.getParameter("price"), 1000);
        int duration = Parsers.parseIntOrDefault(req.getParameter("duration"), 1);
        int percentage = Parsers.parseIntOrDefault(req.getParameter("percentage"), 0);
        String name = req.getParameter("name");
        
        Package pkg = new Package();
        pkg.setActive(false);
        pkg.setDuration(duration);
        pkg.setPackageName(name);
        pkg.setListPriceVND(price);
        pkg.applySale(percentage);
        
        dpkg.createPackage(subjectId, pkg);
    }
    
    private void editPackage(int packageId, HttpServletRequest req) {
        if (packageId == -1) return;
        
        DAOPackage dpkg = new DAOPackage();
        Package pkg = dpkg.getPricePackageByPackageId(packageId);
        
        if (pkg == null) return;
        
        int price = Parsers.parseIntOrDefault(req.getParameter("price"), 1000);
        int duration = Parsers.parseIntOrDefault(req.getParameter("duration"), 1);
        int percentage = Parsers.parseIntOrDefault(req.getParameter("percentage"), 0);
        String name = req.getParameter("name");
        
        pkg.setDuration(duration);
        pkg.setPackageName(name);
        pkg.setListPriceVND(price);
        pkg.applySale(percentage);
        
        dpkg.modifyPackage(pkg);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String action = request.getParameter("action");
        int id = Parsers.parseIntOrDefault(request.getParameter("id"), -1);
        int subjectId = Parsers.parseIntOrDefault(request.getParameter("subjectId"), -1);
        
        switch (action) {
            case "activate" -> switchPackage(id, true);
            case "deactivate" -> switchPackage(id, false);
            case "add" -> addPackage(subjectId, request);
            case "edit" -> editPackage(id, request);
        }

        response.sendRedirect("pricepackage?subjectId=" + subjectId);
    }

    @Override
    public String getServletInfo() {
        return "PricePackageController Servlet";
    }
}
