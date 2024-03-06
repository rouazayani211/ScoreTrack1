package services;
import entities.joueur;
import entities.equipe;
import utils.mydb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ServiceJoueur implements IService<joueur>{
    private Connection connection;
    public ServiceJoueur(){connection= mydb.getInstance().getConnection();}

    @Override
    public void ajouter(joueur joueur) throws SQLException {
        String req="insert into joueur (nom , prenom , age , numero , position , id_equipe) values('"+joueur.getNom()+"','"+joueur.getPrenom()+"','"+joueur.getAge()+"','"+joueur.getNumero()+"','"+joueur.getPosition()+"','"+joueur.getId_equipe()+"')";

      // String req="insert into joueur (nom,prenom,age,numero,position)+" +
       // "values('"+joueur.getNom()+"','"+joueur.getPrenom()+"','"+joueur.getAge()+"','"+joueur.getNumero()+"','"+joueur.getPosition()+"')";
            //   "values('"+joueur.getNumero()+"','"+joueur.getAge()+"','"+joueur.getNom()+"','"+joueur.getPrenom()+"','"+joueur.getPosition()+"')";
        Statement ste=connection.createStatement();
        ste.executeUpdate(req);
    }

    @Override
    public void modifier(joueur joueur) throws SQLException {
    String req="update joueur set nom=? , prenom=? , age=? , numero=? , position=? ,id_equipe=? where id=?";
        PreparedStatement pre=connection.prepareStatement(req);
        pre.setString(1,joueur.getNom());
        pre.setString(2,joueur.getPrenom());
        pre.setInt(3,joueur.getAge());
        pre.setInt(4,joueur.getNumero());
        pre.setString(5,joueur.getPosition());
        pre.setInt(6,joueur.getId_equipe());
        pre.setInt(7,joueur.getId());

        pre.executeUpdate();
    }

    @Override
    public void supprimer(joueur joueur) throws SQLException {
    String req="delete from joueur where id=?";
    PreparedStatement pre=connection.prepareStatement(req);
    pre.setInt(1,joueur.getId());
    pre.executeUpdate();
    }

    @Override
    public List<joueur> afficher() throws SQLException {
        String req="select * from joueur";
        Statement ste =connection.createStatement();
        ResultSet res =ste.executeQuery(req);
        List<joueur> list=new ArrayList<>();
        while (res.next())
        {
            joueur j=new joueur();
            j.setId(res.getInt("id"));
            j.setNom(res.getString("nom"));
            j.setPrenom(res.getString("prenom"));
            j.setAge(res.getInt("age"));
            j.setNumero(res.getInt("numero"));
            j.setPosition(res.getString("position"));
            j.setId_equipe(res.getInt("id_equipe"));

            list.add(j);

        }
        return list;
    }
}
