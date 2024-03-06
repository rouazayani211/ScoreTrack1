package Models;

import java.sql.Date;

public class Commande {
    int id_commande, id_produit,montant_total;
    String  mode_paiement,adresse_livraison;
    Date date_commande ;


    public int getId_commande() {
        return id_commande;
    }

    public void setId_commande(int id_commande) {
        this.id_commande = id_commande;
    }

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    public Date getDate_commande() {
        return date_commande;
    }

    public void setDate_commande(Date date_commande) {
        this.date_commande = date_commande;
    }

    public int getMontant_total() {
        return montant_total;
    }

    public void setMontant_total(int montant_total) {
        this.montant_total = montant_total;
    }

    public String getMode_paiement() {
        return mode_paiement;
    }

    public void setMode_paiement(String mode_paiement) {
        this.mode_paiement = mode_paiement;
    }

    public String getAdresse_livraison() {
        return adresse_livraison;
    }

    public void setAdresse_livraison(String adresse_livraison) {
        this.adresse_livraison = adresse_livraison;
    }

    @Override
    public String toString() {
        return "commande{" +
                "id_commande=" + id_commande +
                ", id_produit=" + id_produit +
                ", date_commande=" + date_commande +
                ", montant_total=" + montant_total +
                ", mode_paiement='" + mode_paiement + '\'' +
                ", adresse_livraison='" + adresse_livraison + '\'' +
                '}';
    }

    public Commande() {
    }

    public Commande(int id_commande,int id_produit, Date date_commande,int montant_total,String mode_paiement,String adresse_livraison) {
        this.id_commande =id_commande;
        this.id_produit=id_produit;
        this.date_commande=date_commande;
        this. montant_total=montant_total;
        this.mode_paiement=mode_paiement;
        this.adresse_livraison=adresse_livraison;
    }

    public Commande(int id_produit, Date date_commande,int montant_total,String mode_paiement,String adresse_livraison) {
        this.id_produit=id_produit;
        this.date_commande=date_commande;
        this. montant_total=montant_total;
        this.mode_paiement=mode_paiement;
        this.adresse_livraison=adresse_livraison;
    }
}

