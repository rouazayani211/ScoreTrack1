package entities;

import java.util.Date;

public class Admin extends Utilisateur {
    public Admin(String nom, String prenom, String email, String mdp, String date_naiss, String role) {
        super(nom, prenom, email, mdp, date_naiss, role);
    }

    public Admin(int id,String nom, String prenom, String email, String mdp, String date_naiss, String role) {
        super(id,nom, prenom, email, mdp, date_naiss, role);
    }

    public Admin() {
        super();
    }
}