package Views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Controller.ConjeeCrud;

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

public class GestionConje extends JFrame {

    private JTextField DateDebutField, DateFinField;
    private JButton submitBtn, UpdateBtn, DeleteBtn;
    private JComboBox<String> typeCombox;
    private JTable table;
    private NonEditableTableModel tableModel;
    private HashMap<Integer, String> employee_conjeMap = new HashMap<>();
    private int selectedId = -1;

    public JPanel createGestionConje() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel TopPanel = new JPanel(new BorderLayout());
        TopPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));

        JLabel title = new JLabel("GESTION CONJE", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        TopPanel.add(title, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 1, 0));
        JLabel infoLabel = new JLabel("!! la form de date : DD-MM-YYYY");
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 10));
        infoPanel.add(infoLabel);
        mainPanel.add(TopPanel, BorderLayout.NORTH);
        mainPanel.add(infoPanel);

        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 5, 0));

        JLabel DateDebutLable = new JLabel("Date de début:");
        DateDebutField = new JTextField(10);
        JLabel DateFinLabel = new JLabel("Date de fin: ");
        DateFinField = new JTextField(15);
        JLabel typeLabel = new JLabel("Type de congé: ");
        String[] type = { "congé annuel", "congé maladie", "congé maternité/paternité", "congé sans solde",
                "congé d'urgence familiale" };
        typeCombox = new JComboBox<>(type);

        formPanel.add(DateDebutLable);
        formPanel.add(DateDebutField);
        formPanel.add(DateFinLabel);
        formPanel.add(DateFinField);
        formPanel.add(typeLabel);
        formPanel.add(typeCombox);

        mainPanel.add(formPanel);

        JPanel submitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        submitBtn = new JButton("Ajouter");
        submitPanel.add(submitBtn);

        mainPanel.add(submitPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 5, 10));
        UpdateBtn = new JButton("Modifier");
        DeleteBtn = new JButton("Supprimer");
        buttonPanel.add(UpdateBtn);
        buttonPanel.add(DeleteBtn);
        mainPanel.add(buttonPanel, BorderLayout.EAST);

        String[] employe_conjes = { "Employee", "Poste", "Date Debut", "Date Fin", "Type Conje" };
        tableModel = new NonEditableTableModel(employe_conjes, 0);

        table = new JTable(tableModel);
        JScrollPane scrolPane = new JScrollPane(table);
        scrolPane.setBorder(BorderFactory.createEmptyBorder(5, 20, 20, 20));
        mainPanel.add(scrolPane, BorderLayout.CENTER);

        ConjeeCrud employee_conje = new ConjeeCrud();

        // Ajouter
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String date_debut = DateDebutField.getText();
                String date_fin = DateFinField.getText();
                Object type = typeCombox.getSelectedItem();
                if (!date_debut.isEmpty() && !date_fin.isEmpty() && !String.valueOf(type).isEmpty()) {
                    int[] id_employees = new int[1];
                    int id_employee = getIdEmpl(id_employees)[0];
                    System.out.println(selectedId + " " + id_employee);
                    if (selectedId == -1) {
                        employee_conje.ajouter(date_debut, date_fin, String.valueOf(type), id_employee);
                        employee_conje.lire(tableModel, employee_conjeMap);
                    } else {
                        employee_conje.mettreAjour(selectedId, date_debut, date_debut, String.valueOf(type),
                                id_employee);
                        employee_conje.lire(tableModel, employee_conjeMap);

                        selectedId = -1;
                    }
                    DateDebutField.setText("");
                    DateFinField.setText("");
                    typeCombox.setSelectedItem("congé annuel");
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
                    employee_conje.supprimer(selectedId);
                    int row = table.getSelectedRow();
                    tableModel.removeRow(row);
                    table.clearSelection();
                    selectedId = -1;
                    System.out.println("employee_conje supprimée.");
                } else {
                    System.out.println("Erreur: employee_conje n'est pas sélectionnée");
                }
            }
        });

        // Modifier
        UpdateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = table.getSelectedRow();
                if (selectedIndex != -1) {
                    DateDebutField.setText(String.valueOf(table.getValueAt(selectedIndex, 2)));
                    DateFinField.setText(String.valueOf(table.getValueAt(selectedIndex, 3)));
                    typeCombox.setSelectedItem(table.getValueAt(selectedIndex, 4));
                } else {
                    JOptionPane.showMessageDialog(null, "Sélectionnez une employee_conje à modifier");
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
                    for (Map.Entry<Integer, String> entry : employee_conjeMap.entrySet()) {
                        if (entry.getValue().equals(selectedValue)) {
                            selectedId = entry.getKey();
                            System.out.println("selected id: " + selectedId);
                            break;
                        }
                    }
                } else {
                    selectedId = -1;
                    System.out.println("Erreur: aucune employee_conje sélectionnée");
                }
            }
        });

        employee_conje.lire(tableModel, employee_conjeMap);
        System.out.println(employee_conjeMap);
        return mainPanel;
    }

    public int[] getIdEmpl(int[] id_employee) {
        return id_employee;
    }

}
