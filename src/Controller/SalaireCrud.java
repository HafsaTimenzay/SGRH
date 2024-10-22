package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Arrays;
import javax.swing.table.DefaultTableModel;

import Models.DbConnection;

public class SalaireCrud {
    public void lire(DefaultTableModel tableModel, HashMap<Integer, String> employeeMap) {
        String query = "SELECT * FROM employee";
        try (
                Connection connection = DbConnection.getConnection();
                PreparedStatement preparedStm = connection.prepareStatement(query);
                ResultSet resultSet = preparedStm.executeQuery();) {
            tableModel.setRowCount(0);
            employeeMap.clear();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String poste = resultSet.getString("poste");
                String salaire = resultSet.getString("salaire");
                String bonus = resultSet.getString("bonus");

                String[] employees = { nom + prenom, poste, salaire, bonus };
                tableModel.addRow(employees);
                employeeMap.put(id, Arrays.toString(employees));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void lirePoste(int id, String[] poste_employee) {
        String query = "SELECT poste FROM employee WHERE id = ?";
        try (
                Connection connection = DbConnection.getConnection();
                PreparedStatement preparedStm = connection.prepareStatement(query);) {
            preparedStm.setInt(1, id);

            try (ResultSet resultSet = preparedStm.executeQuery()) {
                if (resultSet.next()) {
                    String poste = resultSet.getString("poste");
                    poste_employee[0] = poste;

                } else {
                    System.out.println("Aucun employé trouvé avec l'ID: " + id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void mettreAjour(int id, Double salaire, Double bonus) {
        String query = "UPDATE employee SET salaire = ?, bonus = ? WHERE id = ?";
        try (
                Connection connection = DbConnection.getConnection();
                PreparedStatement preparedStm = connection.prepareStatement(query);) {
            preparedStm.setDouble(1, salaire);
            preparedStm.setDouble(2, bonus);
            preparedStm.setInt(3, id);

            int rowUpd = preparedStm.executeUpdate();
            if (rowUpd > 0) {
                System.out.println("salaire d'Employé mis à jour avec succès");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Aucune mise à jour");
        }
    }

    public void supprimer(int id) {
        String query = "DELETE FROM employee WHERE id=?";
        try (
                Connection connection = DbConnection.getConnection();
                PreparedStatement preparedStm = connection.prepareStatement(query);) {
            preparedStm.setInt(1, id);
            int rowDelet = preparedStm.executeUpdate();
            if (rowDelet > 0) {
                System.out.println("Employé supprimé avec succès");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
