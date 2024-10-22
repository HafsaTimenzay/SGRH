package Models;

public class Poste {

    private int id;
    private String nom_post;

    public Poste(int id, String nom_post) {
        this.id = id;
        this.nom_post = nom_post;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_post() {
        return nom_post;
    }

    public void setNom_post(String nom_post) {
        this.nom_post = nom_post;
    }

}
