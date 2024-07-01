/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package app.controller;

import app.dal.DAOSubject;
import app.dal.DAOUser;
import app.entity.Organization;
import app.entity.Subject;
import app.entity.SubjectCategory;
import app.entity.Package;
import app.entity.User;
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
import java.util.ArrayList;
import java.util.HashMap;
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
        DAOUser daoUser = new DAOUser();
        HttpSession session = request.getSession();
        Config cfg = new Config(getServletContext());
        int numPerCarousel = cfg.getIntOrDefault("subjectList.carousel.size", 3);
        String service = request.getParameter("service");
        String goToPara = request.getParameter("goToPos");
        String userEmail;
        String redirectTo;
        int i;
        int page;
        int start, end;
        int goToPosOnWeb = 0;
        int numPerPageIndividual = 8;
        int numPerPageBusiness = 6;
        Vector<Subject> newSubjectList = daoSubject.getNewSubject();
        Vector<Subject> saleSubjectList = daoSubject.getBigSaleSubject();
        Vector<Subject> featuredSubjectList = daoSubject.getFeaturedSubject();
        Vector<SubjectCategory> listOfCategory = daoSubject.getFilterList();
        Vector<SubjectCategory> listOfLevel = daoSubject.getLevelList();
        Vector<Organization> listOfOrg = daoSubject.getOrgList();
        HashMap<Integer, ArrayList<Package>> map = daoSubject.getSubjectPackagesMap();
        //list of checked categories from form on jsp
        String[] parentTier1Raw = request.getParameterValues("idTier1");
        String[] parentTier2Raw = request.getParameterValues("idTier2");
        String[] parentTier3Raw = request.getParameterValues("idTier3");
        String[] levelRaw = request.getParameterValues("level");
        String[] orgRaw = request.getParameterValues("org");
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
        int[] org = null;
        parentTier1 = cookRawIngredient(parentTier1Raw, parentTier1);
        parentTier2 = cookRawIngredient(parentTier2Raw, parentTier2);
        parentTier3 = cookRawIngredient(parentTier3Raw, parentTier3);
        level = cookRawIngredient(levelRaw, level);
        org = cookRawIngredient(orgRaw, org);
        boolean[] checkId = new boolean[listOfCategory.size()];
        boolean[] checkLevel = new boolean[listOfLevel.size()];
        boolean[] checkOrg = new boolean[listOfOrg.size()];
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
        for (i = 0; i < checkOrg.length; i++) {
            checkOrg[i] = isCheck(listOfOrg.get(i).getOrgId(), org);
        }
        if (service == null) {
            service = "individual";
        }
        if (goToPara != null) {
            goToPosOnWeb = Integer.parseInt(goToPara);
        }
        String sendFilter = sendFilter(parentTier1, parentTier2, parentTier3, level, org);
        String listOfIdNew = listOfAnyThing(newSubjectList);
        String listOfIdSale = listOfAnyThing(saleSubjectList);
        String listOfIdFeat = listOfAnyThing(featuredSubjectList);
        request.setAttribute("map", map);
        request.setAttribute("listOfIdNew", listOfIdNew);
        request.setAttribute("listOfIdSale", listOfIdSale);
        request.setAttribute("listOfIdFeat", listOfIdFeat);
        request.setAttribute("sendFilter", sendFilter);
        request.setAttribute("check", checkId);
        request.setAttribute("checkLevel", checkLevel);
        request.setAttribute("checkOrg", checkOrg);
        request.setAttribute("list", listOfCategory);
        request.setAttribute("levels", listOfLevel);
        request.setAttribute("org", listOfOrg);
        request.setAttribute("key", inputKey);
        request.setAttribute("goTo", goToPosOnWeb);
        request.setAttribute("order", order);
        Vector<Subject> subjectListForIndividual
                = daoSubject.getForIndividual(parentTier1, parentTier2,
                        parentTier3, inputKey, order, level);
        if (service.equals("individual")) {
            if (session.getAttribute("userEmail") != null) {
                userEmail = session.getAttribute("userEmail").toString();
                if (session.getAttribute("userId") == null) {
                    User u = daoUser.getUserByEmail(userEmail);
                    session.setAttribute("userId", u.getUserId());
                }
                Vector<Subject> registeredSubjects = daoSubject.getRegisteredSubjects(userEmail);
                String listOfRegistrations = listOfAnyThing(registeredSubjects);
                HashMap<Integer, String> getSponsor = daoSubject.getSponsor(userEmail);
                request.setAttribute("sponsor", getSponsor);
                request.setAttribute("listOfIdRegist", listOfRegistrations);
            }
            int sizeOfNewCarousel = newSubjectList.size() - 1;
            int sizeOfSaleCarousel = saleSubjectList.size() - 1;
            int sizeOfFeaturedCarousel = featuredSubjectList.size() - 1;
            int sizeOfAllSubjects = subjectListForIndividual.size();
            int numOfAllSubjects = (sizeOfAllSubjects % numPerPageIndividual == 0 ? (sizeOfAllSubjects / numPerPageIndividual) : ((sizeOfAllSubjects / numPerPageIndividual) + 1));
            String xpage = request.getParameter("page");
            //get currently requested page
            //if null, show first page
            if (xpage == null) {
                page = 1;
            } else {
                page = Integer.parseInt(xpage);
            }
            //pagination
            start = (page - 1) * numPerPageIndividual;
            end = Math.min(page * numPerPageIndividual, sizeOfAllSubjects);
            Vector<Subject> responseVector = daoSubject.getVectorByPage(subjectListForIndividual, start, end);
            request.setAttribute("numPerCarousel", numPerCarousel);
            request.setAttribute("numOfCarouselNew", sizeOfNewCarousel);
            request.setAttribute("numOfCarouselSale", sizeOfSaleCarousel);
            request.setAttribute("numOfCarouselFeatured", sizeOfFeaturedCarousel);
            request.setAttribute("numOfAllSubjects", numOfAllSubjects);
            request.setAttribute("dataSaleSubject", saleSubjectList);
            request.setAttribute("dataNewSubject", newSubjectList);
            request.setAttribute("dataFeaturedSubject", featuredSubjectList);
            request.setAttribute("dataAllSubject", responseVector);
            request.setAttribute("page", page);
            redirectTo = "/SubjectListForIndividual.jsp";
            dispath(request, response, redirectTo);
        }
        if (service.equals("business")) {
            Vector<Subject> subjectListForBusiness = daoSubject.getForBusiness(parentTier1, parentTier2, parentTier3, inputKey, order, level, org);
            int sizeOfAllSubjects = subjectListForBusiness.size();
            int numOfAllSubjects = (sizeOfAllSubjects % numPerPageBusiness == 0 ? (sizeOfAllSubjects / numPerPageBusiness) : ((sizeOfAllSubjects / numPerPageBusiness) + 1));
            String xpage = request.getParameter("page");
            //get currently requested page
            //if null, show first page
            if (xpage == null) {
                page = 1;
            } else {
                page = Integer.parseInt(xpage);
            }
            //pagination
            start = (page - 1) * numPerPageBusiness;
            end = Math.min(page * numPerPageBusiness, sizeOfAllSubjects);
            Vector<Subject> responseVector = daoSubject.getVectorByPage(subjectListForBusiness, start, end);
            Vector<Package> plansList = daoSubject.getPlans();
            request.setAttribute("dataFeaturedSubject", featuredSubjectList);
            request.setAttribute("numOfAllSubjects", numOfAllSubjects);
            request.setAttribute("dataAllSubject", responseVector);
            request.setAttribute("plans", plansList);
            request.setAttribute("page", page);
            redirectTo = "/SubjectListForBusiness.jsp";
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

    private String sendFilter(int[] parentTier1, int[] parentTier2,
            int[] parentTier3, int[] level, int[] org) {
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
        if (org != null) {
            for (i = 0; i < org.length; i++) {
                url += "org=" + org[i] + "&";
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
