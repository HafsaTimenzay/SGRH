package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Arrays;
import javax.swing.table.DefaultTableModel;

import Models.DbConnection;

public class employeeCrud {

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

                String[] employees = { nom, prenom, poste, salaire };
                tableModel.addRow(employees);
                employeeMap.put(id, Arrays.toString(employees));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void lireColumn(int id, String[] employee) {
        String query = "SELECT * FROM employee WHERE id = ?";
        try (
                Connection connection = DbConnection.getConnection();
                PreparedStatement preparedStm = connection.prepareStatement(query);) {
            preparedStm.setInt(1, id);

            try (ResultSet resultSet = preparedStm.executeQuery()) {
                if (resultSet.next()) {
                    String nom = resultSet.getString("nom");
                    String prenom = resultSet.getString("prenom");
                    String poste = resultSet.getString("poste");
                    String salaire = resultSet.getString("salaire");
                    String bonus = resultSet.getString("bonus");

                    employee[0] = nom;
                    employee[1] = prenom;
                    employee[2] = poste;
                    employee[3] = salaire;
                    employee[4] = bonus;

                } else {
                    System.out.println("Aucun employé trouvé avec l'ID: " + id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ajouter(String nom, String prenom, String poste, Double salaire, Double bonus) {
        String query = "INSERT INTO employee(nom, prenom, poste, salaire, bonus) VALUES(?,?,?,?,?)";
        try (
                Connection connection = DbConnection.getConnection();
                PreparedStatement preparedStm = connection.prepareStatement(query);) {
            preparedStm.setString(1, nom);
            preparedStm.setString(2, prenom);
            preparedStm.setString(3, poste);
            preparedStm.setDouble(4, salaire);
            preparedStm.setDouble(5, bonus);
            preparedStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void mettreAjour(int id, String nom, String prenom, String poste, Double salaire, Double bonus) {
        String query = "UPDATE employee SET nom = ?, prenom = ?, poste = ?, salaire = ?, bonus = ? WHERE id = ?";
        try (
                Connection connection = DbConnection.getConnection();
                PreparedStatement preparedStm = connection.prepareStatement(query);) {
            preparedStm.setString(1, nom);
            preparedStm.setString(2, prenom);
            preparedStm.setString(3, poste);
            preparedStm.setDouble(4, salaire);
            preparedStm.setDouble(5, bonus);
            preparedStm.setInt(6, id);
            int rowUpd = preparedStm.executeUpdate();
            if (rowUpd > 0) {
                System.out.println("Employé mis à jour avec succès");
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
