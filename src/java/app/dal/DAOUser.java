package app.dal;

import java.util.ArrayList;
import java.util.List;
import app.entity.User;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class DAOUser extends DBContext {

    public boolean updatePasswordById(int id, String password) {
        try {
            PreparedStatement stmt = connection.prepareStatement("update [User] set [Password] = ? where [UserId] = ?");
            stmt.setString(1, password);
            stmt.setInt(2, id);
            stmt.executeUpdate();

            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    //==================================
    public void updatePassByUser(String user, String pass) {
        String sql = "UPDATE [User]\n"
                + "   SET Password = ?\n"
                + " WHERE Email = ?";
        PreparedStatement pre;
        try {
            pre = connection.prepareStatement(sql);
            pre.setString(1, pass);
            pre.setString(2, user);
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addUser(User user) {
        String sql = "INSERT INTO [dbo].[User] "
                + "([Email], [Password], [RoleId], [FullName], [GenderId], [Mobile], [isActive]) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, user.getEmail());
            pre.setString(2, user.getPassword());
            pre.setInt(3, user.getRoleId());
            pre.setString(4, user.getFullName());
            pre.setInt(5, user.getGenderId());
            pre.setString(6, user.getMobile());
            pre.setBoolean(7, true);
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean isEmailRegistered(String email) {
        boolean isRegistered = false;
        String sql = "SELECT COUNT(*) FROM [dbo].[User] WHERE email = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, email);
            ResultSet resultSet = pre.executeQuery();
            if (resultSet.next()) {
                isRegistered = resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isRegistered;
    }



    private Vector<User> getFull(String sql) {
        Vector<User> Out = new Vector<User>();
        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                Out.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5),
                        rs.getInt(6), rs.getString(7), rs.getBoolean(8)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return Out;
    }

    public Vector<User> getAll() {
        return getFull("select * from [User]");
    }

    public User getUserById(int id) {
        String sql = "SELECT * FROM [User] where UserId = '" + id + "';";
        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            if (rs.next()) {
                return new User(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getInt(4), rs.getString(5),
                        rs.getInt(6), rs.getString(7), rs.getBoolean(8));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM [User] where Email = ?";
        try {
            PreparedStatement preStat = connection.prepareStatement(sql);
            preStat.setString(1, email);
            ResultSet rs = preStat.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5),
                        rs.getInt(6), rs.getString(7), rs.getBoolean(8));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public byte[] getProfileImage(int id) {
        String sql = "SELECT * FROM ProfilePicture where UserId = '" + id + "';";
        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            if (rs.next()) {
                return rs.getBytes(2);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public int insertProfileImage(int id, byte[] image) {
        String sql = "INSERT INTO ProfilePicture (UserId, Image)\n"
                + "     VALUES (?,?)";
        try {
            PreparedStatement preStat = connection.prepareStatement(sql);
            preStat.setInt(1, id);
            preStat.setBytes(2, image);
            return preStat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int updateProfileImage(int id, byte[] image) {
        if (getProfileImage(id) == null) {
            return insertProfileImage(id, image);
        } else {
            String sql = "UPDATE ProfilePicture\n"
                    + " SET Image = ?"
                    + " WHERE UserId = ?;";
            try {
                PreparedStatement preStat = connection.prepareStatement(sql);
                preStat.setInt(2, id);
                preStat.setBytes(1, image);
                return preStat.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    public int updateUserProfile(int id, String fullName, int genderId, String mobile) {
        String sql = "UPDATE [User]\n"
                + "   SET FullName = ?\n"
                + "      ,GenderId = ?\n"
                + "      ,Mobile = ?\n"
                + " WHERE UserId = ?";
        try {
            PreparedStatement preStat = connection.prepareStatement(sql);
            preStat.setInt(4, id);
            preStat.setString(1, fullName);
            preStat.setInt(2, genderId);
            preStat.setString(3, mobile);
            return preStat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // to be replaced with better ways immediately after showcase
    public String idToName(int id) {
        String sql = "SELECT FullName FROM [User] where UserId = '" + id + "';";
        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<User> extractResults(ResultSet rs) throws SQLException {
        List<User> result = new ArrayList<>();
        while (rs.next()) {
            User user = new User();
            user.setUserId(rs.getInt("UserId"));
            user.setEmail(rs.getString("Email"));
            user.setPassword(rs.getString("Password"));
            user.setFullName(rs.getString("FullName"));
            user.setGenderId(rs.getInt("GenderId"));
            user.setMobile(rs.getString("Mobile"));
            user.setRoleId(rs.getInt("RoleId"));
            user.setIsActive(rs.getBoolean("IsActive"));
            result.add(user);
        }
        return result;
    }

    public ConcurrentHashMap<Integer, String> idArrayToNameMap(int[] ids) {
        ConcurrentHashMap<Integer, String> Out = new ConcurrentHashMap<>();
        String sql = "SELECT UserId, FullName FROM [User] where UserId in ("
                + Arrays.stream(ids).mapToObj(Integer::toString).collect(Collectors.joining(","))
                + ");";
        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                Out.put(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Out;
    }

    public static void main(String[] args) {
        
    }
}
