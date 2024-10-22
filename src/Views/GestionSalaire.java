package Views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Controller.SalaireCrud;

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

public class GestionSalaire extends JFrame {

    private JTextField bonusField, salairField;
    private JButton submitBtn;
    private JTable table;
    private NonEditableTableModel tableModel;
    private HashMap<Integer, String> employee_salaireMap = new HashMap<>();
    private int selectedId = -1;

    public JPanel createGestionSalaire() {

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel TopPanel = new JPanel(new BorderLayout());
        TopPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));

        JLabel title = new JLabel("GESTION SLAIRE", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        TopPanel.add(title, BorderLayout.CENTER);

        mainPanel.add(TopPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 5, 0));

        JLabel salaireLabel = new JLabel("Salaire: ");
        salairField = new JTextField(15);
        JLabel bonusLabel = new JLabel("Bonus: ");
        bonusField = new JTextField(15);

        formPanel.add(salaireLabel);
        formPanel.add(salairField);
        formPanel.add(bonusLabel);
        formPanel.add(bonusField);

        mainPanel.add(formPanel);

        JPanel submitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        submitBtn = new JButton("Modifier");
        submitPanel.add(submitBtn);
        mainPanel.add(submitPanel);

        String[] employee = { "employee", "Poste", "Salaire", "Bonus" };
        tableModel = new NonEditableTableModel(employee, 0);

        table = new JTable(tableModel);
        JScrollPane scrolPane = new JScrollPane(table);
        scrolPane.setBorder(BorderFactory.createEmptyBorder(5, 20, 20, 20));
        mainPanel.add(scrolPane, BorderLayout.CENTER);

        SalaireCrud employee_salaire = new SalaireCrud();

        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String salaire = salairField.getText();
                String bonus = bonusField.getText();
                if (!bonus.isEmpty() && !salaire.isEmpty()) {
                    // String[] poste = new String[1];
                    // employee_salaire.lirePoste(14, poste);
                    // String poste_emp = poste[0];
                    // if (poste_emp.equalsIgnoreCase("Manager")) {
                    employee_salaire.mettreAjour(selectedId, Double.parseDouble(salaire), Double.parseDouble(bonus));
                    employee_salaire.lire(tableModel, employee_salaireMap);
                    salairField.setText("");
                    bonusField.setText("");
                    // }else{
                    // JOptionPane.showMessageDialog(scrolPane, "employee faut etre manager");
                    // }

                } else {
                    JOptionPane.showMessageDialog(scrolPane, "Tous les champs sont requis!");
                }
            }
        });

        // Selection
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedIndex = table.getSelectedRow();
                if (selectedIndex != -1) {
                    salairField.setText(String.valueOf(table.getValueAt(selectedIndex, 2)));
                    bonusField.setText(String.valueOf(table.getValueAt(selectedIndex, 3)));
                } else {
                    JOptionPane.showMessageDialog(null, "Sélectionnez une employee_salaire à modifier");
                }
                int row = table.getSelectedRow();
                String[] rowData = new String[4];
                for (int i = 0; i <= 3; i++) {
                    rowData[i] = String.valueOf(table.getValueAt(row, i));
                }
                String selectedValue = Arrays.toString(rowData);
                System.out.println("selectedValue : " + selectedValue);
                if (selectedValue != null) {
                    for (Map.Entry<Integer, String> entry : employee_salaireMap.entrySet()) {
                        System.out.println(entry.getValue() + selectedValue);
                        if (entry.getValue().equals(selectedValue)) {
                            selectedId = entry.getKey();
                            System.out.println("selected id: " + selectedId);
                            break;
                        }
                    }
                } else {
                    selectedId = -1;
                    System.out.println("Erreur: aucune employee_salaire sélectionnée");
                }
            }
        });

        employee_salaire.lire(tableModel, employee_salaireMap);

        return mainPanel;
    }

}
