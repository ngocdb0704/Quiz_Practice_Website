package app.dal;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.http.HttpSession;
import app.entity.Slide;
import app.entity.User;

public class DAOSlide extends DBContext {
    
    private List<Slide> hardCoded;

    public DAOSlide() {
        hardCoded = new ArrayList<>();
        hardCoded.add(new Slide(0, "Test Slide 1", "public/images/placeHolderSlideImg.png", "https://duckduckgo.com/?q=placeholder&t=vivaldi&ia=web", "Active"));
        hardCoded.add(new Slide(0, "Test Slide 2", "public/images/placeHolderSlideImg2.png", "https://dictionary.cambridge.org/dictionary/english/placeholder", "Active"));
        hardCoded.add(new Slide(0, "Test Slide 3", "public/images/placeHolderSlideImg3.png", "https://www.google.com/search?q=placeholder", "Active"));
        hardCoded.add(new Slide(0, "Test Slide 4", "public/images/placeHolderSlideImg4.png", "https://www.bing.com/search?q=placeholder", "Active"));
    }
    
    public List<Slide> getSliderList() {
        return hardCoded;
    }


    // Method to get active sliders for non-marketing users
    public List<Slide> getActiveSlidesForNonMarketing() {
        List<Slide> slides = new ArrayList<>();

        String sql = "SELECT * FROM [dbo].[Slide] WHERE Status = 1"; // Assuming 1 means active, adjust as per your database schema

        try (PreparedStatement pstmt = connection.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int sliderId = rs.getInt("SliderId");
                String title = rs.getString("Title");
                String imageRef = rs.getString("ImageRef"); // Assuming you have a column for image reference
                String backlink = rs.getString("Backlink");
                String status = rs.getBoolean("Status") ? "active" : "inactive"; // Convert boolean to string
                int userId = rs.getInt("UserId");

                Slide slide = new Slide(sliderId, title, imageRef, backlink, status, userId);
                slides.add(slide);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DAOSlide.class.getName()).log(Level.SEVERE, "Error fetching active slides for non-marketing users", ex);
        }

        return slides;
    }

    // Method to get all sliders for marketing users
    public List<Slide> getAllSlidesForMarketing() {
        List<Slide> slides = new ArrayList<>();

        String sql = "SELECT * FROM [dbo].[Slide]"; // Get all sliders

        try (PreparedStatement pstmt = connection.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int sliderId = rs.getInt("SliderId");
                String title = rs.getString("Title");
                String imageRef = rs.getString("ImageRef"); // Assuming you have a column for image reference
                String backlink = rs.getString("Backlink");
                String status = rs.getBoolean("Status") ? "active" : "inactive"; // Convert boolean to string
                int userId = rs.getInt("UserId");

                Slide slide = new Slide(sliderId, title, imageRef, backlink, status, userId);
                slides.add(slide);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DAOSlide.class.getName()).log(Level.SEVERE, "Error fetching all slides for marketing users", ex);
        }

        return slides;
    }

    // Method to update slide status
    public boolean updateSlideStatus(int sliderId, boolean newStatus) {
        String sql = "UPDATE [dbo].[Slide] SET Status = ? WHERE SliderId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setBoolean(1, newStatus);
            pstmt.setInt(2, sliderId);
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DAOSlide.class.getName()).log(Level.SEVERE, "Error updating slide status", ex);
        }
        return false;
    }

    // Method to update slide image
    public boolean updateSlideImage(int sliderId, InputStream newImage) {
        String sql = "UPDATE [dbo].[Slide] SET Image = ? WHERE SliderId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setBlob(1, newImage);
            pstmt.setInt(2, sliderId);
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DAOSlide.class.getName()).log(Level.SEVERE, "Error updating slide image", ex);
        }
        return false;
    }
    
    // Method to get a slide by its ID
    public Slide getSlideById(int sliderId) {
        Slide slide = null;
        String sql = "SELECT * FROM [dbo].[Slide] WHERE SliderId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, sliderId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String title = rs.getString("Title");
                    String imageRef = rs.getString("ImageRef"); // Assuming you have a column for image reference
                    String backlink = rs.getString("Backlink");
                    String status = rs.getBoolean("Status") ? "active" : "inactive"; // Convert boolean to string
                    int userId = rs.getInt("UserId");

                    slide = new Slide(sliderId, title, imageRef, backlink, status, userId);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOSlide.class.getName()).log(Level.SEVERE, "Error fetching slide by ID", ex);
        }
        return slide;
    }
}