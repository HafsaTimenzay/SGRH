package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Arrays;
import javax.swing.table.DefaultTableModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import Models.DbConnection;

public class ConjeeCrud {
    public void lire(DefaultTableModel tableModel, HashMap<Integer, String> employeeMap) {
        String query = "SELECT e.id, e.nom, e.prenom, e.poste, c.dateDebut, c.dateFin, c.type " +
                "FROM employee e INNER JOIN conje c ON e.id = c.employee_id;";
        try (
                Connection connection = DbConnection.getConnection();
                PreparedStatement preparedStm = connection.prepareStatement(query);
                ResultSet resultSet = preparedStm.executeQuery();) {
            tableModel.setRowCount(0);
            while (resultSet.next()) {
                int id = resultSet.getInt("e.id");
                String nom = resultSet.getString("e.nom");
                String prenom = resultSet.getString("e.prenom");
                String poste = resultSet.getString("e.poste");
                java.sql.Date dateDebut = resultSet.getDate("c.dateDebut");
                java.sql.Date dateFin = resultSet.getDate("c.dateFin");
                String type = resultSet.getString("c.type");

                Object[] conge = { nom + " " + prenom, poste, dateDebut, dateFin, type };
                tableModel.addRow(conge);
                employeeMap.put(id, Arrays.toString(conge));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ajouter(String dateDebut, String dateFin, String type, int employeeId) {
        String query = "INSERT INTO conje (dateDebut, dateFin, type) VALUES (?, ?, ?)";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try (
                Connection connection = DbConnection.getConnection();
                PreparedStatement preparedStm = connection.prepareStatement(query);) {
            // Parse the String into java.util.Date
            java.util.Date parsedDateDebut = dateFormat.parse(dateDebut);
            java.util.Date parsedDateFin = dateFormat.parse(dateFin);

            // Convert java.util.Date to java.sql.Date
            java.sql.Date sqlDateDebut = new java.sql.Date(parsedDateDebut.getTime());
            java.sql.Date sqlDateFin = new java.sql.Date(parsedDateFin.getTime());

            // Set the values in the PreparedStatement
            preparedStm.setDate(1, sqlDateDebut);
            preparedStm.setDate(2, sqlDateFin);
            preparedStm.setString(3, type);

            preparedStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getMessage());
        }
    }

    public void mettreAjour(int id, String dateDebut, String dateFin, String type, int employeeId) {
        String query = "UPDATE conje SET dateDebut = ?, dateFin = ?, type = ? WHERE id = ?";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try (
                Connection connection = DbConnection.getConnection();
                PreparedStatement preparedStm = connection.prepareStatement(query);) {

            java.util.Date parsedDateDebut = dateFormat.parse(dateDebut);
            java.util.Date parsedDateFin = dateFormat.parse(dateFin);

            java.sql.Date sqlDateDebut = new java.sql.Date(parsedDateDebut.getTime());
            java.sql.Date sqlDateFin = new java.sql.Date(parsedDateFin.getTime());

            preparedStm.setDate(1, sqlDateDebut);
            preparedStm.setDate(2, sqlDateFin);
            preparedStm.setString(3, type);
            preparedStm.setInt(5, id);

            int rowUpd = preparedStm.executeUpdate();
            if (rowUpd > 0) {
                System.out.println("Congé mis à jour avec succès");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getMessage());
        }
    }

    public void supprimer(int id) {
        String query = "DELETE FROM conje WHERE id = ?";
        try (
                Connection connection = DbConnection.getConnection();
                PreparedStatement preparedStm = connection.prepareStatement(query);) {
            preparedStm.setInt(1, id);
            int rowDelet = preparedStm.executeUpdate();
            if (rowDelet > 0) {
                System.out.println("Congé supprimé avec succès");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
