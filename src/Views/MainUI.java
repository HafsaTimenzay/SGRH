package Views;

import javax.swing.*;

// import Controller.SalaireCrud;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// import java.util.HashMap;

public class MainUI extends JFrame {

    private JButton gestionCongeeButton;
    private JButton gestionSalaireButton;
    private JButton gestionEmployeeButton;

    // private NonEditableTableModel tableModel;
    // private HashMap<Integer, String> employee_salaireMap = new HashMap<>();

    public MainUI() {
        setTitle("SGRH");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLayout(new BorderLayout());

        JPanel navbar = new JPanel();
        navbar.setLayout(new BoxLayout(navbar, BoxLayout.Y_AXIS));
        navbar.setBackground(Color.LIGHT_GRAY);
        navbar.setPreferredSize(new Dimension(200, 0));

        navbar.add(Box.createVerticalStrut(20));

        gestionEmployeeButton = new JButton("Gestion Employee");
        gestionSalaireButton = new JButton("Gestion Salaire");
        gestionCongeeButton = new JButton("Gestion Congee");

        gestionEmployeeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        gestionSalaireButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        gestionCongeeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        navbar.add(gestionEmployeeButton);
        navbar.add(Box.createVerticalStrut(20));
        navbar.add(gestionSalaireButton);
        navbar.add(Box.createVerticalStrut(20));
        navbar.add(gestionCongeeButton);

        JPanel cardPanel = new JPanel(new CardLayout());

        GestionEployee gestionEmployee = new GestionEployee();
        JPanel gestionEmployeePanel = gestionEmployee.createGestionEployeePanel();

        GestionSalaire gestionSalaire = new GestionSalaire();
        JPanel gestionSalairePanel = gestionSalaire.createGestionSalaire();

        GestionConje gestionConje = new GestionConje();
        JPanel gestionCongeePanel = gestionConje.createGestionConje();

        cardPanel.add(gestionEmployeePanel, "Gestion Employee");
        cardPanel.add(gestionSalairePanel, "Gestion Salaire");
        cardPanel.add(gestionCongeePanel, "Gestion Congee");

        gestionEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (cardPanel.getLayout());
                cl.show(cardPanel, "Gestion Employee");
            }
        });
        gestionSalaireButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (cardPanel.getLayout());
                cl.show(cardPanel, "Gestion Salaire");
                // GestionSalaire employee_salaire = new GestionSalaire();

            }
        });

        gestionCongeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (cardPanel.getLayout());
                cl.show(cardPanel, "Gestion Congee");
            }
        });

        JButton ajouterConjee = gestionEmployee.AjouterConje();

        ajouterConjee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (cardPanel.getLayout());
                cl.show(cardPanel, "Gestion Congee");
            }
        });

        add(navbar, BorderLayout.WEST);
        add(cardPanel, BorderLayout.CENTER);

        setVisible(true);
    }

}
