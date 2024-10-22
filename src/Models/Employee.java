package Models;

public abstract class Employee {

    protected int id;
    protected String nom;
    protected String prenom;
    protected Double salaire;
    protected Poste poste;

    public Employee(String nom, String prenom, Double salaire, Poste poste) {
        this.nom = nom;
        this.prenom = prenom;
        this.salaire = salaire;
        this.poste = poste;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Double getSalaire() {
        return salaire;
    }

    public void setSalaire(Double salaire) {
        this.salaire = salaire;
    }

    public Poste getPoste() {
        return poste;
    }

    public void setPoste(Poste poste) {
        this.poste = poste;
    }

    public abstract double calculerSalaire();


}