package test;
import entities.joueur;
import services.ServiceJoueur;
import entities.equipe;
import services.ServiceEquipe;
import utils.mydb;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
       /* mydb db1=mydb.getInstance();
        mydb db2=mydb.getInstance();*/

       /* System.out.println(db1.hashCode());
        System.out.println(db2.hashCode());*/

       joueur j1 = new joueur( 15,7, 30, "kylian", "mbappe", "ST",9);
     //   joueur j2 = new joueur(2,10,36,"messi","leo","CF");

        equipe e1= new equipe(77,"psggg","italie","ligue 1",1);

        ServiceJoueur services = new ServiceJoueur();
        ServiceEquipe servicesE =new ServiceEquipe();

////////////////////ajouter equipe///////////////////////////
        try {
            servicesE.ajouter(e1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
////////////////////////////////////////////////////////////



///////////////////afficher equipe////////////////////////////////
   /*    try {
            System.out.println(servicesE.afficher());
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }*/
////////////////////////////////////////////////////////////////


///////////////////modifier equipe///////////////////////////////////
      /*  try {
            servicesE.modifier(e1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
//////////////////////////////////////////////////////////////////


//////////////////////supprimer equipe/////////////////////////////////
       /* try {
            servicesE.supprimer(e1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
//////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

////////////////////ajouter joueur///////////////////////////
    /* try {
            services.ajouter(j1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }*/
////////////////////////////////////////////////////////////



///////////////////afficher joueur////////////////////////////////
   /* try {
            System.out.println(services.afficher());
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }*/
///////////////////modifier joueur////////////////////////////////////////

      /*  try {
            services.modifier(j1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
/////////////////////////////////////////////////////////////////////////////////

//////////////////////supprimer joueur///////////////////////////////////////////////////////////
     /* try {
            services.supprimer(j1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
/////////////////////////////////////////////////////////////////////////////////




    }
}
