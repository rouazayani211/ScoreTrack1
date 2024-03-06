package Controller;

import Models.Produit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import service.ServiceProduit;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.esprit.football.Main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ShowProduitController {
    public TextField rechercheField;
    @FXML
    private FlowPane productContainer;

    private ServiceProduit serviceProduit = new ServiceProduit();

    public void initialize() throws SQLException {
        List<Produit> products = serviceProduit.afficher();
        displayProducts(products);
    }

    private void displayProducts(List<Produit> products) {
        for (Produit product : products) {
            VBox card = createProductCard(product);
            productContainer.getChildren().add(card);
        }
    }

    private VBox createProductCard(Produit product) {
        VBox card = new VBox();
        card.getStyleClass().add("product-card");
        Label nameLabel = new Label(product.getNom_produit());
        Label priceLabel = new Label("Price: $" + product.getPrix());
        Label descriptionLabel = new Label(product.getDescription());
        Label stockLabel = new Label("Stock: " + product.getStock_disponible());
        card.getChildren().addAll(nameLabel, priceLabel, descriptionLabel, stockLabel);
        card.setOnMouseClicked(event -> openProductDetailsPage(product));
        card.setCursor(Cursor.HAND);
        return card;
    }

    private void openProductDetailsPage(Produit product) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ShowProduitDetails.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            ShowProduitDetails controller = fxmlLoader.getController();
            controller.displayProductDetails(product);
            stage.setTitle("Product Details");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void OnRefresh(ActionEvent actionEvent) throws SQLException {
        List<Produit> products = serviceProduit.afficher();
        productContainer.getChildren().clear();
        displayProducts(products);
    }

    public void OnAjouter(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ajoutProduit.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) productContainer.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Ajouter Produit");
        stage.show();
    }

    public void OnCommande(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ShowCommande.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) productContainer.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Commandes");
        stage.show();
    }

    @FXML
    private void OnRecherche(ActionEvent actionEvent) throws SQLException {
        String searchTerm = rechercheField.getText().toLowerCase().trim();

        if (searchTerm.isEmpty()) {
            List<Produit> products = serviceProduit.afficher();
            productContainer.getChildren().clear();
            displayProducts(products);
        } else {
            List<Produit> filteredProducts = serviceProduit.afficher().stream()
                    .filter(product -> product.getNom_produit().toLowerCase().contains(searchTerm))
                    .collect(Collectors.toList());

            productContainer.getChildren().clear();
            displayProducts(filteredProducts);

            if (!filteredProducts.isEmpty()) {
                openProductDetailsPage(filteredProducts.get(0));
            }
        }
    }

    @FXML
    private void OnSort(ActionEvent actionEvent) throws SQLException {
        List<Produit> products = serviceProduit.afficher();
        Collections.sort(products, Comparator.comparing(Produit::getNom_produit));
        productContainer.getChildren().clear();
        displayProducts(products);
    }
}
