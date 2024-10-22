package Models;

public class Technicien extends Employee {

    private int id;
    private int heures;

    public Technicien(String nom, String prenom, Double salaire, Poste poste, int heures) {
        super(nom, prenom, salaire, poste);
        this.heures = heures;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public double calculerSalaire() {
        return salaire + (heures * 10);
    }

}
