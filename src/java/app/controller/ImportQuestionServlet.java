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
import org.apache.poi.ss.usermodel.Cell;
import static org.apache.poi.ss.usermodel.CellType.BOOLEAN;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import org.apache.poi.ss.usermodel.DateUtil;
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
        if (filePart == null) {
            request.getRequestDispatcher("admin/importquestion.jsp").forward(request, response);
            return;
        }
        try {
            InputStream fileContent = filePart.getInputStream();
            Workbook workbook = new XSSFWorkbook(fileContent);
            Sheet sheet = workbook.getSheetAt(0); //sheet 1
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {//skip first row
                    continue;
                }

                String text = getCellValueAsString(row.getCell(0));
                String explanation = getCellValueAsString(row.getCell(1));
                int level = (int) row.getCell(2).getNumericCellValue();
                int subjectID = (int) row.getCell(3).getNumericCellValue();
                int lessonID = (int) row.getCell(4).getNumericCellValue();
                
                int questionID = quesDAO.addQuestion(text, explanation, level, subjectID, lessonID);
                
                //add answer 
                for(int i=5; i<row.getLastCellNum(); i+=2){
                    String ansName = getCellValueAsString(row.getCell(i));
                    int isCorrect = (int) row.getCell(i+1).getNumericCellValue();
                    System.out.println("question id: " +questionID+ "name " + ansName + "; iscorrect " + isCorrect);
                    quesDAO.addAnswer(questionID, ansName, isCorrect);
                }
            }
            request.setAttribute("notification", "File import successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("notification", "Error importing file!");
        }
        request.getRequestDispatcher("admin/importquestion.jsp").forward(request, response);
    }

    
    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        //: Changed getCellType() to getCellTypeEnum()
        switch (cell.getCellTypeEnum()) {
            //if data string => return string 
            case STRING -> {
                return cell.getStringCellValue();
            }
            // if data is number => return number
            case NUMERIC -> {
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return Double.toString(cell.getNumericCellValue());
                }
            }
            // if data is true/false => return boolean
            case BOOLEAN -> {
                return Boolean.toString(cell.getBooleanCellValue());
            }

            default -> {
                return "";
            }
        }
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
