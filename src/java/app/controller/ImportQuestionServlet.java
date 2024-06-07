package app.controller;

import app.dal.QuestionDAO;
import java.io.IOException;
import java.io.InputStream;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author hoapmhe173343
 */
@WebServlet(name = "ImportQuestionServlet", urlPatterns = {"/importquestion"})
@MultipartConfig
public class ImportQuestionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("admin/importquestion.jsp").forward(request, response);
    }

    QuestionDAO quesDAO = new QuestionDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Part filePart = request.getPart("file");
        System.out.println("1");
        if (filePart == null) {
            request.getRequestDispatcher("admin/importquestion.jsp").forward(request, response);
            return;
        }
        System.out.println("2");
        try {
            InputStream fileContent = filePart.getInputStream();
            Workbook workbook = new XSSFWorkbook(fileContent);
            System.out.println("2.1");
            Sheet sheet = workbook.getSheetAt(0); //sheet 1
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {//skip first row
                    continue;
                }

                String text = row.getCell(0).getStringCellValue();
                String explanation = row.getCell(1).getStringCellValue();
                int level = (int) row.getCell(2).getNumericCellValue();
                int subjectID = (int) row.getCell(3).getNumericCellValue();
                int lessonID = (int) row.getCell(4).getNumericCellValue();
                
                int questionID = quesDAO.addQuestion(text, explanation, level, subjectID, lessonID);
                
                //add answer 
                for(int i=5; i<row.getLastCellNum(); i+=2){
                    String ansName = row.getCell(i).getStringCellValue();
                    int isCorrect = (int) row.getCell(i+1).getNumericCellValue();
                    
                    quesDAO.addAnswer(questionID, ansName, isCorrect);
                }
            }
            System.out.println("2.2");
            request.setAttribute("notification", "File import successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("notification", "Error importing file!");
        }
        System.out.println("3");
        request.getRequestDispatcher("admin/importquestion.jsp").forward(request, response);
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
