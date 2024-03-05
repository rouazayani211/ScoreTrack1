package utils;

import entities.Utilisateur;

public class Session {
    private static Session instance;
    private Utilisateur currentUser;

    private Session() {
    }

    public static Session getInstance() {
        if (instance == null) {
            if (instance == null) {
                instance = new Session();
            }
        }
        return instance;
    }

    public void setUser(Utilisateur user) {
        this.currentUser = user;
    }
    public Utilisateur getUser() {
        return currentUser;
    }

    public void logout(){
        this.currentUser = null;
    }
}
