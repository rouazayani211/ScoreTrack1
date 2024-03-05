package test;

import entities.Admin;
import entities.Utilisateur;
import service.ServiceUtilisateur;
import service.Serviceadmin;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args){
        /*MyDB db1 =MyDB.getInstance();
        MyDB db2 =MyDB.getInstance();

        System.out.println(db1.hashCode());
        System.out.println(db2.hashCode());*/
       // Utilisateur u1 = new Utilisateur("nouhe","hamdi","azertyu","test","29/07/2001","admin");
        //Utilisateur u2 = new Utilisateur("syrine","syrine","dfghjkl","test","9/06/2002","admin");
        //Admin a1= new Admin("nounou","hamdi","fghjklm","9/07/2001","xxxxx","admin");
       // Utilisateur u= new Utilisateur ("khadija","cccc","fghjklm","ccccc","9/07/2001","admin");
        ServiceUtilisateur service = new ServiceUtilisateur();
        Serviceadmin serviceadmin= new Serviceadmin();
        // ajouter les utilisateurs
       /*try {

           System.out.println("user added successfully.");
           service.Ajouter(u);
        } catch (SQLException e) {
          System.out.println(e.getMessage());
        }*/

        // ajouter les admins
        /*try {
            System.out.println("Admin added successfully.");
            serviceadmin.Ajouter(a1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/


        // afficher les utilisateurs
        /*try {
            System.out.println(service.Afficher());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/

        // afficher les admins
        /*try {
            System.out.println(serviceadmin.Afficher());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
       /* // modification les utilisateurs
        try {
            service.Modifier(u2);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/


        // modification les admins
       /* try {
            serviceadmin.Modifier(a1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/


        //supprimer les utilisateur
       /* try {
            service.Supprimer(u);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/

        //supprimer les admins
      /*  try {
            serviceadmin.Supprimer();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/

    }
}
