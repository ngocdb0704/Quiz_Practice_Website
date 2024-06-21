/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dal;

/**
 *
 * @author OwO
 */
import app.entity.Dimension;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAODimension extends DBContext {

    public List<Dimension> listAllDimensions(int subjectId) throws SQLException {
        List<Dimension> listDimension = new ArrayList<>();

        String sql = "SELECT * FROM [Dimension] WHERE SubjectId = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, subjectId);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int dimensionId = resultSet.getInt("DimensionId");
            String dimensionType = resultSet.getString("DimensionType");
            String dimensionName = resultSet.getString("DimensionName");
            String description = resultSet.getString("DimensionDescription");
            int subId = resultSet.getInt("SubjectId");

            Dimension dimension = new Dimension(dimensionId, dimensionType, dimensionName, description, subId);
            listDimension.add(dimension);
        }

        resultSet.close();
        return listDimension;
    }

    public void insertDimension(Dimension dimension) throws SQLException {
        String sql = "INSERT INTO [Dimension] (SubjectId, DimensionType, DimensionName, DimensionDescription) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, dimension.getSubjectId());
            statement.setString(2, dimension.getDimensionType());
            statement.setString(3, dimension.getDimensionName());
            statement.setString(4, dimension.getDescription());

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAODimension.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Dimension getDimension(int subjectId) throws SQLException {
        String sql = "SELECT * FROM [Dimension] WHERE SubjectId = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, subjectId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String dimensionType = resultSet.getString("DimensionType");
            String dimensionName = resultSet.getString("DimensionName");
            String description = resultSet.getString("DimensionDescription");
            int dimensionId = resultSet.getInt("dimensionId");
            Dimension dimension = new Dimension(dimensionId, dimensionType, dimensionName, description, subjectId);
            return dimension;
        }
        resultSet.close();
        return null;
    }

    public void updateDimension(Dimension dimension) throws SQLException {
        String sql = "UPDATE [Dimension] SET DimensionType = ?, DimensionName = ?, DimensionDescription = ? WHERE DimensionId = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, dimension.getDimensionType());
        statement.setString(2, dimension.getDimensionName());
        statement.setString(3, dimension.getDescription());
        statement.setInt(4, dimension.getDimensionId());
        statement.executeUpdate();
    }
    
    public void deleteDimension(int dimensionId) throws SQLException {
        String sql = "DELETE FROM [Dimension] WHERE DimensionId = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, dimensionId);
            statement.executeUpdate();
        }
    }
}
