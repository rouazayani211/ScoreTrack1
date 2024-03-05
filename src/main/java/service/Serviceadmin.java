package service;

import entities.Admin;
import entities.Utilisateur;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Serviceadmin implements IService<Admin>{
    private Connection connection;
    public Serviceadmin() {
        connection = MyDB.getInstance().getConnection();
    }

    @Override
    public void Ajouter(Admin admin) throws SQLException {


            String req = "insert into utilisateur (nom, prenom, date_naiss, email,mdp, role)" +
                    " VALUES (?, ?, ?, ?, ?,?)";

            try (PreparedStatement pre = connection.prepareStatement(req)) {
                pre.setString(1, admin.getNom());
                pre.setString(2, admin.getPrenom());
                pre.setString(3, String.valueOf(admin.getDate_naiss()));
                pre.setString(4, admin.getEmail());
                pre.setString(5, admin.getMdp());
                pre.setString(6, "admin");

                pre.executeUpdate();
            }

    }

    @Override
    public void Modifier(Admin admin) throws SQLException {

        String req = "update utilisateur set nom=?, prenom=?, date_naiss=?, email=?, mdp=?, role=? where id=?";

        try (PreparedStatement pre = connection.prepareStatement(req)) {
            pre.setString(1, admin.getNom());
            pre.setString(2, admin.getPrenom());
            pre.setString(3, admin.getDate_naiss());
            pre.setString(4, admin.getEmail());
            pre.setString(5, admin.getEmail());
            pre.setString(6, admin.getRole());
            pre.setInt(7, admin.getId());

            pre.executeUpdate();
        }
    }

    @Override
    public void Supprimer(Admin admin) throws SQLException {


        String req = "delete from utilisateur where id=?";

        try (PreparedStatement pre = connection.prepareStatement(req)) {
            pre.setInt(1, admin.getId());
            pre.executeUpdate();
        }
    }

    @Override
    public List<Admin> Afficher() throws SQLException {

        String req = "select * from utilisateur";

        try (Statement ste = connection.createStatement();
             ResultSet res = ste.executeQuery(req)) {

            List<Admin> list = new ArrayList<>();
            while (res.next()) {
                Admin admin = new Admin();
                admin.setId(res.getInt(1));
                admin.setNom(res.getString(2));
                admin.setPrenom(res.getString(3));
                admin.setDate_naiss(res.getString(4));
                admin.setEmail(res.getString(5));
                admin.setMdp(res.getString(6));
                admin.setRole(res.getString(7));
                list.add(admin);
            }
            return list;
        }
    }


}


