package Views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Controller.employeeCrud;

import java.awt.event.*;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class NonEditableTableModel extends DefaultTableModel {
    public NonEditableTableModel(Object[] columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}

public class GestionEployee extends JFrame {

    private JTextField nomField, prenomField, posteField, salairField;
    private JButton submitBtn, UpdateBtn, DeleteBtn, AddConje;
    private JTable table;
    private NonEditableTableModel tableModel;
    private HashMap<Integer, String> employeeMap = new HashMap<>();
    private int selectedId = -1;

    public JPanel createGestionEployeePanel() {

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel TopPanel = new JPanel(new BorderLayout());
        TopPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));

        JLabel title = new JLabel("GESTION EMPLOYEE", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        TopPanel.add(title, BorderLayout.CENTER);

        mainPanel.add(TopPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 5, 0));

        JLabel nomLabel = new JLabel("Nom:");
        nomField = new JTextField(10);
        JLabel prenomLabel = new JLabel("Prenom: ");
        prenomField = new JTextField(15);
        JLabel posteLabel = new JLabel("poste: ");
        posteField = new JTextField(15);
        JLabel salaireLabel = new JLabel("Salaire: ");
        salairField = new JTextField(15);

        formPanel.add(nomLabel);
        formPanel.add(nomField);
        formPanel.add(prenomLabel);
        formPanel.add(prenomField);
        formPanel.add(posteLabel);
        formPanel.add(posteField);
        formPanel.add(salaireLabel);
        formPanel.add(salairField);

        mainPanel.add(formPanel);

        JPanel submitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        submitBtn = new JButton("Ajouter");
        submitPanel.add(submitBtn);
        mainPanel.add(submitPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel conjePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        conjePanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 5, 210));
        AddConje = new JButton("Ajouter conje");
        conjePanel.add(AddConje);
        buttonPanel.add(conjePanel);

        JPanel crudPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        crudPanel.setBorder(BorderFactory.createEmptyBorder(30, 210, 5, 10));
        UpdateBtn = new JButton("Modifier");
        DeleteBtn = new JButton("Supprimer");
        crudPanel.add(UpdateBtn);
        crudPanel.add(DeleteBtn);
        buttonPanel.add(crudPanel);
        mainPanel.add(buttonPanel, BorderLayout.EAST);

        String[] voitrues = { "Nom", "Prenom", "Poste", "Salaire" };
        tableModel = new NonEditableTableModel(voitrues, 0); // Use custom model

        table = new JTable(tableModel);
        JScrollPane scrolPane = new JScrollPane(table);
        scrolPane.setBorder(BorderFactory.createEmptyBorder(5, 20, 20, 20));
        mainPanel.add(scrolPane, BorderLayout.CENTER);

        employeeCrud employee = new employeeCrud();

        AddConje.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GestionConje gestionConje = new GestionConje();
                int[] id_employee = new int[1];
                id_employee[0] = selectedId;

                gestionConje.getIdEmpl(id_employee);
            }
        });

        // Ajouter
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = nomField.getText();
                String prenom = prenomField.getText();
                String poste = posteField.getText();
                String salaire = salairField.getText();
                String bonus = "0";

                if (!nom.isEmpty() && !prenom.isEmpty() && !poste.isEmpty()
                        && !salaire.isEmpty()) {

                    if (selectedId == -1) {
                        System.out.println(selectedId);
                        employee.ajouter(nom, prenom, poste, Double.parseDouble(salaire), Double.parseDouble(bonus));
                        employee.lire(tableModel, employeeMap);
                    } else {
                        employee.mettreAjour(selectedId, nom, prenom, poste, Double.parseDouble(salaire),
                                Double.parseDouble(bonus));
                        employee.lire(tableModel, employeeMap);
                        selectedId = -1;
                    }
                    nomField.setText("");
                    prenomField.setText("");
                    posteField.setText("");
                    salairField.setText("");

                } else {
                    JOptionPane.showMessageDialog(scrolPane, "Tous les champs sont requis!");
                }
            }
        });

        // Supprimer
        DeleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedId != -1) {
                    employee.supprimer(selectedId);
                    int row = table.getSelectedRow();
                    tableModel.removeRow(row);
                    table.clearSelection();
                    selectedId = -1;
                    System.out.println("employee supprimée.");
                } else {
                    System.out.println("Erreur: employee n'est pas sélectionnée");
                }
            }
        });

        // Modifier
        UpdateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = table.getSelectedRow();
                System.out.println(selectedIndex + " hhh");
                if (selectedIndex != -1) {
                    nomField.setText(String.valueOf(table.getValueAt(selectedIndex, 0)));
                    prenomField.setText(String.valueOf(table.getValueAt(selectedIndex, 1)));
                    posteField.setText(String.valueOf(table.getValueAt(selectedIndex, 2)));
                    salairField.setText(String.valueOf(table.getValueAt(selectedIndex, 3)));
                } else {
                    JOptionPane.showMessageDialog(null, "Sélectionnez une employee à modifier");
                }
            }
        });

        // Selection
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                String[] rowData = new String[4];
                for (int i = 0; i <= 3; i++) {
                    rowData[i] = String.valueOf(table.getValueAt(row, i));
                }
                String selectedValue = Arrays.toString(rowData);
                System.out.println("selectedValue : " + selectedValue);
                if (selectedValue != null) {
                    for (Map.Entry<Integer, String> entry : employeeMap.entrySet()) {
                        System.out.println(entry.getValue() + (selectedValue));
                        if (entry.getValue().equals(selectedValue)) {
                            selectedId = entry.getKey();
                            System.out.println("selected id: " + selectedId);
                            break;
                        }
                    }
                } else {
                    selectedId = -1;
                    System.out.println("Erreur: aucune employee sélectionnée");
                }
            }
        });

        employee.lire(tableModel, employeeMap);

        return mainPanel;
    }

    public JButton AjouterConje() {
        return AddConje;
    }

}
