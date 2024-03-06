package Models;

public class Produit {
    int id_produit,stock_disponible,prix;
    String nom_produit,description;


    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }
    public String getNom_produit() {
        return nom_produit;
    }

    public void setNom_produit(String nom_produit) {
        this.nom_produit = nom_produit;
    }
    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock_disponible() {
        return stock_disponible;
    }

    public void setStock_disponible(int stock_disponible) {
        this.stock_disponible = stock_disponible;
    }

    @Override
    public String toString() {
        return "produit{" +
                "id_produit=" + id_produit +
                ", stock_disponible=" + stock_disponible +
                ", nom_produit='" + nom_produit + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                '}';
    }

    public Produit() {
    }

    public Produit(int id_produit, String nom_produit, int prix, String description, int stock_disponible) {
        this.id_produit = id_produit;
        this.nom_produit=nom_produit;
        this.prix=prix;
        this.description=description;
        this.stock_disponible=stock_disponible;
    }

    public Produit(String nom_produit, int prix, String description, int stock_disponible) {
        this.nom_produit=nom_produit;
        this.prix=prix;
        this.description=description;
        this.stock_disponible = stock_disponible;
    }
}
