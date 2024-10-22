package Models;

import java.util.List;

public class Departement {

    private int id;
    private String nom_departement;
    private List<Employee> employees;

    public Departement(String nom_departement, List<Employee> employees) {
        this.nom_departement = nom_departement;
        this.employees = employees;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_departement() {
        return nom_departement;
    }

    public void setNom_departement(String nom_departement) {
        this.nom_departement = nom_departement;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

}
