/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package app.controller;

import app.dal.DAOPackage;
import app.dal.DAORegistration;
import app.dal.DAOSubject;
import app.entity.Customer;
import app.entity.Registration;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Vector;
import app.entity.Package;
import app.entity.SubjectCategory;
import app.entity.Transaction;
import app.model.DAOCustomer;

/**
 *
 * @author admin
 */
public class RegistrationController extends HttpServlet {

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
        DAORegistration daoRegistration = new DAORegistration();
        DAOSubject daoSubject = new DAOSubject();
        DAOCustomer daoCustomer = new DAOCustomer();
        HttpSession session = request.getSession();
        String service = request.getParameter("service");
        String redirectTo;
        String userEmail = "";
        String listAll = "listAll";
        int page;
        int numPerPage = 4;
        int start;
        int end;
        int i;
        String controller = "RegistrationController";
        //list of checked categories from form on jsp
        String[] parentTier1Raw = request.getParameterValues("idTier1");
        String[] parentTier2Raw = request.getParameterValues("idTier2");
        String[] parentTier3Raw = request.getParameterValues("idTier3");
        String[] statusRaw = request.getParameterValues("idStatus");
        String inputKey = request.getParameter("key");
        int[] parentTier1 = null;
        int[] parentTier2 = null;
        int[] parentTier3 = null;
        int[] status = null;
        parentTier1 = cookRawIngredient(parentTier1Raw, parentTier1);
        parentTier2 = cookRawIngredient(parentTier2Raw, parentTier2);
        parentTier3 = cookRawIngredient(parentTier3Raw, parentTier3);
        status = cookRawIngredient(statusRaw, status);
        Vector<SubjectCategory> listOfCategory = daoSubject.getFilterList();
        Vector<Integer> listOfStatus = daoRegistration.getStatusId();
        boolean[] checkId = new boolean[listOfCategory.size()];
        boolean[] checkStatusId = new boolean[listOfStatus.size()];
        //check which category check box is ticked 
        for (i = 0; i < checkId.length; i++) {
            checkId[i] = isCheck(listOfCategory.get(i).getCateId(), parentTier1)
                    || isCheck(listOfCategory.get(i).getCateId(), parentTier2)
                    || isCheck(listOfCategory.get(i).getCateId(), parentTier3);
        }
        for (i = 0; i < checkStatusId.length; i++) {
            checkStatusId[i] = isCheck(listOfStatus.get(i), status);
        }
        //check session's attribute for email
        if (session.getAttribute("userEmail") != null) {
            userEmail = session.getAttribute("userEmail").toString();
        } else {
            //redirect to home redirectTo
            redirectTo = "/index.jsp";
            dispath(request, response, redirectTo);
        }
        // check service's value
        if (service == null) {
            service = listAll;
        }
        // check service's value
        if (service.equals("listAll")) {
            Vector<Registration> registrationVector = daoRegistration.getById(
                    userEmail, parentTier1, parentTier2, parentTier3,
                    status, inputKey);
            Customer cus = daoCustomer.searchbyEmail(userEmail).get(0);
            int size = registrationVector.size();
            //number of pages for pagination, each page has 4 cards of registration
            int num = (size % 4 == 0 ? (size / 4) : ((size / 4) + 1));
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
            end = Math.min(page * numPerPage, size);
            Vector<Registration> responseVector = daoRegistration.getVectorByPage(registrationVector, start, end);
            Vector<Transaction> history = daoRegistration.getTransactionHistory(userEmail);
            String sendFilter = sendFilter(parentTier1, parentTier2, parentTier3);
            float totalCost = daoRegistration.getTotalCost(userEmail);
            request.setAttribute("data", responseVector);
            request.setAttribute("page", page);
            request.setAttribute("num", num);
            request.setAttribute("total", totalCost);
            request.setAttribute("key", inputKey);
            request.setAttribute("list", listOfCategory);
            request.setAttribute("listOfStatus", listOfStatus);
            request.setAttribute("check", checkId);
            request.setAttribute("history", history);
            request.setAttribute("checkStatus", checkStatusId);
            request.setAttribute("sendFilter", sendFilter);
            request.setAttribute("userId", cus.getUserId());
            redirectTo = "/MyRegistration.jsp";
            dispath(request, response, redirectTo);
        }
        // check service's value, cancellation
        if (service.equals("cancel")) {
            String removeKey = request.getParameter("cancelId");
            int removeId = Integer.parseInt(removeKey);
            int n = daoRegistration.removeRegistration(removeId);
            service = listAll;
            response.sendRedirect(controller);
        }
        // check service's value, update status
        if (service.equals("paid")) {
            String paidKey = request.getParameter("paidId");
            String code = request.getParameter("code");
            String acc = request.getParameter("acc");
            int paidId = Integer.parseInt(paidKey);
            int n = daoRegistration.updateRegistrationStatus(paidId, code, acc);
            service = listAll;
            response.sendRedirect(controller);
        }
        // check service's value
        if (service.equals("update")) {
            service = listAll;
            response.sendRedirect(controller);
        }
    }

    public void dispath(HttpServletRequest request, HttpServletResponse response, String url)
            throws ServletException, IOException {
        RequestDispatcher dispth = request.getRequestDispatcher(url);
        //run, show
        dispth.forward(request, response);
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

    private String sendFilter(int[] parentTier1, int[] parentTier2, int[] parentTier3) {
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
