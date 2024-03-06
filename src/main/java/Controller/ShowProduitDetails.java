package Controller;

import Models.Produit;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import service.ServiceProduit;
import tn.esprit.football.Main;

import java.io.IOException;
import java.sql.SQLException;

public class ShowProduitDetails {

    @FXML
    private Label productNameLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label stockLabel;

    private ServiceProduit service = new ServiceProduit();

    private Produit produits;
    public void displayProductDetails(Produit product) {
        produits = product;
        productNameLabel.setText("Product Name: " + product.getNom_produit());
        priceLabel.setText("Price: $" + product.getPrix());
        descriptionLabel.setText("Description: " + product.getDescription());
        stockLabel.setText("Stock: " + product.getStock_disponible());
    }

    @FXML
    private void handleEdit(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("editProduit.fxml"));
            Parent root = fxmlLoader.load();
            EditProduitController editProduitController = fxmlLoader.getController();
            editProduitController.setProduit(produits);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit produit");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDelete(ActionEvent event) throws SQLException {
        service.Supprimer(produits);
        Stage stage = (Stage) productNameLabel.getScene().getWindow();
        stage.close();
    }
}
