package entities;

public class Utilisateur {
    int id;
    String nom,prenom,email,mdp,role;
    String date_naiss;

    public Utilisateur() {

    }


    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
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

    public  String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public  String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public  String getDate_naiss() {
        return date_naiss;
    }

    public void setDate_naiss(String date_naiss) {
        this.date_naiss = date_naiss;
    }
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", mdp='" + mdp + '\'' +
                ", date_naiss='" + date_naiss + '\'' +
                ", role='" + role + '\'' +
                '}';
    }


    public Utilisateur (int id, String nom, String prenom, String email, String mdp, String date_naiss, String role) {
        this.id = this.id;
        this.nom = this.nom;
        this.prenom = this.prenom;
        this.email = this.email;
        this.mdp = this.mdp;
        this.date_naiss = this.date_naiss;
        this.role= this.role;
    }
    public Utilisateur(int id) {
        this.id = id;
    }


    public Utilisateur(String nom, String prenom, String email,String mdp, String date_naiss,String role) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
        this.date_naiss = date_naiss;
        this.role=role;
    }
}
