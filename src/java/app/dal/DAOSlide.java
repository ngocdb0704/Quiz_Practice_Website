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

    public ArrayList<Slide> getAllSlide() {
        ArrayList<Slide> bloList = new ArrayList<>();
        try {
            String sql = "select top 4 title,img,description,backlink from slide  where [active] =1";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Slide b = new Slide(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
                bloList.add(b);
            }
        } catch (SQLException e) {
        }
        return bloList;
    }

    public int getNumberSlide() {
        int number = 0;
        try {
            String sql = "select count(*) from slide";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return number;
            }
        } catch (SQLException e) {
        }
        return 0;
    }

    public ArrayList<Slide> getAllSlideWithCondition(String status, String title, String backlink, int index) {
        ArrayList<Slide> slist = new ArrayList<>();
        try {
            String sql = "  select * from slide  where active like ?  \n"
                    + "  and title like ? and backlink like ?\n"
                    + "  order by [slide_id] asc OFFSET ? ROWS FETCH NEXT 9  ROWS ONLY";
            PreparedStatement stm = connection.prepareStatement(sql);
            System.out.println(sql);
            stm.setString(1, "%" + status + "%");
            stm.setString(2, "%" + title + "%");
            stm.setString(3, "%" + backlink + "%");
            stm.setInt(4, (index - 1) * 9);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Slide b = new Slide(rs.getInt(1), rs.getString(2), rs.getString(3), 
                        rs.getString(4), rs.getInt(5), rs.getString(6), rs.getBoolean(7));
                slist.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return slist;
    }

    public static void main(String[] args) {
        System.out.println(new DAOSlide().getAllSlideWithCondition("", "", "", 1));
    }

    public int getNumberSlideWithCondition(String status, String title, String backlink) {
        try {
            String sql = "  select COUNT(*) from slide  where active like ?  \n"
                    + "  and title like ? and backlink like ?\n";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, "%" + status + "%");
            stm.setString(2, "%" + title + "%");
            stm.setString(3, "%" + backlink + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void updateSlide(String title, String img, String backlink, int authorId, String descrip, int active, int slideid) {
        try {
            String sql = "update slide set [title]=?,  [img] = ? ,[backlink] = ? ,[author_id] = ? ,[description]  = ? ,[active] = ? where [slide_id] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, title);
            stm.setString(2, img);
            stm.setString(3, backlink);
            stm.setInt(4, authorId);
            stm.setString(5, descrip);
            stm.setInt(6, active);
            stm.setInt(7, slideid);
            stm.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public void switchStatus(int sid, int sstatus) {
        try {
            String sql = " update slide set [active] = ? where [slide_id] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, sstatus);
            stm.setInt(2, sid);
            stm.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public void deleteSlide(int sid) {
        try {
            String sql = " delete from slide where slide_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, sid);
            stm.executeUpdate();
        } catch (SQLException e) {
        }
    }

}
