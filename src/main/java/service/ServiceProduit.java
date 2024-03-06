package service;

import Models.Produit;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceProduit implements IService<Produit> {

    private Connection connection;

    public ServiceProduit() {
        connection = MyDB.getInstance().getConnection();
    }

    @Override
    public void Ajouter(Produit produit) throws SQLException {
        String req = "insert into produit (nom_produit,prix,description,stock_disponible)" +
                "values('" + produit.getNom_produit() + "','" + produit.getPrix() + "','" + produit.getDescription() + "'," + produit.getStock_disponible() + ")";
        Statement ste = connection.createStatement();
        ste.executeUpdate(req);
    }

    @Override
    public void Modifier(Produit produit) throws SQLException {
        String req = "update produit set nom_produit=?  , prix=? ,description=? ,stock_disponible=? where id_produit=? ";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setString(1, produit.getNom_produit());
        pre.setInt(2, produit.getPrix());
        pre.setString(3, produit.getDescription());
        pre.setInt(4, produit.getStock_disponible());
        pre.setInt(5, produit.getId_produit());
        pre.executeUpdate();

    }

    @Override
    public void Supprimer(Produit produit) throws SQLException {
        String req="delete from produit where id_produit=?";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setInt(1, produit.getId_produit());
        pre.executeUpdate();

    }

    @Override
    public List<Produit> Afficher() throws SQLException {
        String req="select * from produit";
        Statement ste=connection.createStatement();
        ResultSet res= ste.executeQuery(req);
        List<Produit> list=new ArrayList<>();
        while (res.next()) {
            Produit p=new Produit();
            p.setId_produit(res.getInt(1));
            p.setNom_produit(res.getString("nom_produit"));
            p.setPrix(res.getInt(3));
            p.setDescription(res.getString("description"));
            p.setStock_disponible(res.getInt(5));
            list.add(p);
        }
        return list;




    }


}
