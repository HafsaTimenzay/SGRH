package Models;

import java.util.List;

public class Manager extends Employee {

    private Double bonus;
    private List<Technicien> techniciens;

    public Manager(String nom, String prenom, Double salaire, Poste poste, List<Technicien> techniciens, Double bonus) {
        super(nom, prenom, salaire, poste);
        this.techniciens = techniciens;
        this.bonus = bonus;
    }

    public Double getbonus() {
        return bonus;
    }

    public void setbonus(Double bonus) {
        this.bonus = bonus;
    }

    public List<Technicien> getTechniciens() {
        return techniciens;
    }

    public void setTechniciens(List<Technicien> techniciens) {
        this.techniciens = techniciens;
    }

    @Override
    public double calculerSalaire() {
        return salaire + bonus;
    }

}
