/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package app.controller;

import app.dal.DAOSubject;
import app.entity.Subject;
import app.entity.SubjectCategory;
import app.utils.Config;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Vector;

/**
 *
 * @author admin
 */
@WebServlet(name = "SubjectController", urlPatterns = {"/public/SubjectsList"})
public class SubjectController extends HttpServlet {

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
        DAOSubject daoSubject = new DAOSubject();
        HttpSession session = request.getSession();
        Config cfg = new Config(getServletContext());
        int numPerCarousel = cfg.getIntOrDefault("subjectList.carousel.size", 3);
        String service = request.getParameter("service");
        String goToPara = request.getParameter("goToPos");
        String userEmail = "";
        String redirectTo;
        int i;
        int page;
        int start, end;
        int goToPosOnWeb = 0;
        int numPerPage = cfg.getIntOrDefault("subjectList.pagination.size", 4);
        Vector<Subject> newSubjectList = daoSubject.getNewSubject();
        Vector<Subject> saleSubjectList = daoSubject.getBigSaleSubject();
        Vector<Subject> featuredSubjectList = daoSubject.getFeaturedSubject();
        Vector<SubjectCategory> listOfCategory = daoSubject.getFilterList();
        Vector<SubjectCategory> listOfLevel = daoSubject.getLevelList();
        //list of checked categories from form on jsp
        String[] parentTier1Raw = request.getParameterValues("idTier1");
        String[] parentTier2Raw = request.getParameterValues("idTier2");
        String[] parentTier3Raw = request.getParameterValues("idTier3");
        String[] levelRaw = request.getParameterValues("level");
        String inputKey = request.getParameter("key");
        String inputOrder = request.getParameter("orderList");
        int order = 1;
        if (inputOrder != null) {
            order = Integer.parseInt(inputOrder);
        }
        int[] parentTier1 = null;
        int[] parentTier2 = null;
        int[] parentTier3 = null;
        int[] level = null;
        parentTier1 = cookRawIngredient(parentTier1Raw, parentTier1);
        parentTier2 = cookRawIngredient(parentTier2Raw, parentTier2);
        parentTier3 = cookRawIngredient(parentTier3Raw, parentTier3);
        level = cookRawIngredient(levelRaw, level);
        boolean[] checkId = new boolean[listOfCategory.size()];
        boolean[] checkLevel = new boolean[listOfLevel.size()];
        //check which category check box is ticked 
        for (i = 0; i < checkId.length; i++) {
            checkId[i] = isCheck(listOfCategory.get(i).getCateId(), parentTier1)
                    || isCheck(listOfCategory.get(i).getCateId(), parentTier2)
                    || isCheck(listOfCategory.get(i).getCateId(), parentTier3);
        }
        //check which level check box is ticked 
        for (i = 0; i < checkLevel.length; i++) {
            checkLevel[i] = isCheck(listOfLevel.get(i).getCateId(), level);
        }
        if (service == null) {
            service = "listAll";
        }
        if (goToPara != null) {
            goToPosOnWeb = Integer.parseInt(goToPara);
        }
        if (service.equals("listAll")) {
            String sendFilter = sendFilter(parentTier1, parentTier2, parentTier3, level);
            int sizeOfNewCarousel = newSubjectList.size();
            int sizeOfSaleCarousel = saleSubjectList.size();
            int sizeOfFeaturedCarousel = featuredSubjectList.size();
            int numOfCarouselNew = sizeOfNewCarousel / numPerCarousel;
            int numOfCarouselSale = sizeOfSaleCarousel / numPerCarousel;
            int numOfCarouselFeatured = sizeOfFeaturedCarousel / numPerCarousel;
            String listOfIdNew = listOfAnyThing(newSubjectList);
            String listOfIdSale = listOfAnyThing(saleSubjectList);
            String listOfIdFeat = listOfAnyThing(featuredSubjectList);
            Vector<Subject> allSubjectList = daoSubject.getWithToken(parentTier1, parentTier2, parentTier3, inputKey, order, level);
            int sizeOfAllSubjects = allSubjectList.size();
            int numOfAllSubjects = (sizeOfAllSubjects % numPerPage == 0 ? (sizeOfAllSubjects / numPerPage) : ((sizeOfAllSubjects / numPerPage) + 1));
            String xpage = request.getParameter("page");
            //get currently requested page
            //if null, show first page
            if (xpage == null) {
                page = 1;
            } else {
                page = Integer.parseInt(xpage);
            }
            //pagination
            start = (page - 1) * numPerPage;
            end = Math.min(page * numPerPage, sizeOfAllSubjects);
            Vector<Subject> responseVector = daoSubject.getVectorByPage(allSubjectList, start, end);
            if (session.getAttribute("userEmail") != null) {
                userEmail = session.getAttribute("userEmail").toString();
                Vector<Subject> registeredSubjects = daoSubject.getRegisteredSubjects(userEmail);
                String bankCode = cfg.getStringOrDefault("bankcode", null);
                String ownerBankAccount = cfg.getStringOrDefault("owner.bankaccount", null);
                String listOfRegistrations = listOfAnyThing(registeredSubjects);
                request.setAttribute("listOfIdRegist", listOfRegistrations);
                request.setAttribute("bankCode", bankCode);
                request.setAttribute("ownerAccount", ownerBankAccount);
            }
            request.setAttribute("check", checkId);
            request.setAttribute("checkLevel", checkLevel);
            request.setAttribute("list", listOfCategory);
            request.setAttribute("levels", listOfLevel);
            request.setAttribute("listOfIdNew", listOfIdNew);
            request.setAttribute("listOfIdSale", listOfIdSale);
            request.setAttribute("listOfIdFeat", listOfIdFeat);
            request.setAttribute("numPerCarousel", numPerCarousel);
            request.setAttribute("numOfCarouselNew", numOfCarouselNew);
            request.setAttribute("numOfCarouselSale", numOfCarouselSale);
            request.setAttribute("numOfCarouselFeatured", numOfCarouselFeatured);
            request.setAttribute("numOfAllSubjects", numOfAllSubjects);
            request.setAttribute("dataSaleSubject", saleSubjectList);
            request.setAttribute("dataNewSubject", newSubjectList);
            request.setAttribute("dataFeaturedSubject", featuredSubjectList);
            request.setAttribute("dataAllSubject", responseVector);
            request.setAttribute("sendFilter", sendFilter);
            request.setAttribute("page", page);
            request.setAttribute("key", inputKey);
            request.setAttribute("goTo", goToPosOnWeb);
            request.setAttribute("order", order);
            redirectTo = "/SubjectList.jsp";
            dispath(request, response, redirectTo);
        }
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

    public void dispath(HttpServletRequest request, HttpServletResponse response, String url)
            throws ServletException, IOException {
        RequestDispatcher dispth = request.getRequestDispatcher(url);
        //run, show
        dispth.forward(request, response);
    }

    public String listOfAnyThing(Vector<Subject> input) {
        String output = "";
        for (int i = 0; i < input.size(); i++) {
            output += input.get(i).getSubjectName() + "//";
        }
        return output;
    }

    private boolean isCheck(int d, int[] parent) {
        //if parent's checkboxes aren't checked then false
        if (parent == null) {
            return false;
        } else {
            //find index of checked id
            for (int i = 0; i < parent.length; i++) {
                if (parent[i] == d) {
                    return true;
                }
            }
            return false;
        }
    }

    private String sendFilter(int[] parentTier1, int[] parentTier2, int[] parentTier3, int[] level) {
        String url = "";
        int i;
        if (parentTier1 != null) {
            for (i = 0; i < parentTier1.length; i++) {
                url += "idTier1=" + parentTier1[i] + "&";
            }
        }
        if (parentTier2 != null) {
            for (i = 0; i < parentTier2.length; i++) {
                url += "idTier2=" + parentTier2[i] + "&";
            }
        }
        if (parentTier3 != null) {
            for (i = 0; i < parentTier3.length; i++) {
                url += "idTier3=" + parentTier3[i] + "&";
            }
        }
        if (level != null) {
            for (i = 0; i < level.length; i++) {
                url += "level=" + level[i] + "&";
            }
        }
        return url;
    }

    //little joke, transform all valid string[] to int[] 
    private int[] cookRawIngredient(String[] raw, int[] parent) {
        //check if any parent's checkbox is checked
        if (raw != null) {
            parent = new int[raw.length];
            //put checked parent to array
            for (int i = 0; i < raw.length; i++) {
                parent[i] = Integer.parseInt(raw[i]);
            }
        }
        return parent;
    }
}
