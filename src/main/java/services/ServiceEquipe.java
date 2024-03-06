package services;
import entities.equipe;
import entities.joueur;
import utils.mydb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceEquipe implements IService<equipe>{
    private Connection connection;
    public ServiceEquipe(){connection= mydb.getInstance().getConnection();}


    @Override
    public void ajouter(equipe equipe) throws SQLException {
        String req="insert into equipe (nom,region,ligue,classement) values('"+equipe.getNom()+"','"+equipe.getRegion()+"','"+equipe.getLigue()+"','"+equipe.getClassement()+"')";
        Statement ste=connection.createStatement();
        ste.executeUpdate(req);
    }

    @Override
    public void modifier(equipe equipe) throws SQLException {
        String req="update equipe set nom=? , region=? , ligue=? , classement=?  where id_equipe=?";
        PreparedStatement pre=connection.prepareStatement(req);
      //  pre.setInt(1,equipe.getId_equipe());
        pre.setString(1,equipe.getNom());
        pre.setString(2,equipe.getRegion());
        pre.setString(3,equipe.getLigue());
        pre.setInt(4,equipe.getClassement());
        pre.setInt(5,equipe.getId_equipe());


        pre.executeUpdate();
    }

    @Override
    public void supprimer(equipe equipe) throws SQLException {
        String req="delete from equipe where id_equipe=?";
        PreparedStatement pre=connection.prepareStatement(req);
        pre.setInt(1,equipe.getId_equipe());
        pre.executeUpdate();
    }

    @Override
    public List<equipe> afficher() throws SQLException {
        String req="select * from equipe";
        Statement ste =connection.createStatement();
        ResultSet res =ste.executeQuery(req);
        List<equipe> list=new ArrayList<>();
        while (res.next())
        {
            equipe e=new equipe();
            e.setId_equipe(res.getInt("id_equipe"));
            e.setNom(res.getString("nom"));
            e.setRegion(res.getString("region"));
            e.setLigue(res.getString("ligue"));
            e.setClassement(res.getInt("classement"));

            list.add(e);

        }
        return list;
    }
}
