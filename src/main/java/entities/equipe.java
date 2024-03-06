package entities;

public class equipe {
    int id_equipe;
    String nom;
    String region;
    String ligue;
    int classement;

    public int getId_equipe() {
        return id_equipe;
    }

    public String getNom() {
        return nom;
    }

    public String getRegion() {
        return region;
    }

    public String getLigue() {
        return ligue;
    }

    public int getClassement() {
        return classement;
    }

    public void setId_equipe(int id_equipe) {
        this.id_equipe = id_equipe;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setLigue(String ligue) {
        this.ligue = ligue;
    }

    public void setClassement(int classement) {
        this.classement = classement;
    }

    @Override
    public String toString() {
        return "equipe{" +
                "id_equipe=" + id_equipe +
                ", nom='" + nom + '\'' +
                ", region='" + region + '\'' +
                ", ligue='" + ligue + '\'' +
                ", classement=" + classement +
                '}';
    }
    public equipe(){}

    public equipe(int id_equipe, String nom, String region, String ligue, int classement) {
        this.id_equipe = id_equipe;
        this.nom = nom;
        this.region = region;
        this.ligue = ligue;
        this.classement = classement;
    }

    public equipe (String nom, String region, String ligue, int classement) {
        this.nom = nom;
        this.region = region;
        this.ligue = ligue;
        this.classement = classement;
    }

}
