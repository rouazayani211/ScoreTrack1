package Controller;

import Models.Produit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.ServiceProduit;
import tn.esprit.football.Main;

import java.io.IOException;
import java.sql.SQLException;

public class EditProduitController {

    @FXML
    private TextField tfnom_produit;

    @FXML
    private TextField tfprix;

    @FXML
    private TextField tfdescription;

    @FXML
    private TextField tfstock_disponible;
    private Produit produit;
    private ServiceProduit serviceProduit = new ServiceProduit();

    public void setProduit(Produit produit) {
        this.produit = produit;
        tfnom_produit.setText(produit.getNom_produit());
        tfprix.setText(String.valueOf(produit.getPrix()));
        tfdescription.setText(produit.getDescription());
        tfstock_disponible.setText(String.valueOf(produit.getStock_disponible()));
    }

    public void updateBtn(ActionEvent actionEvent) throws SQLException {
        if (tfnom_produit.getText().equals("") ||  tfprix.getText().equals("")|| tfdescription.getText().equals("")|| tfstock_disponible.getText().equals("") ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("veuillez remplir ces champs");
            alert.showAndWait();
            return;
        }
        produit.setNom_produit(tfnom_produit.getText());
        produit.setPrix(Integer.parseInt(tfprix.getText()));
        produit.setDescription(tfdescription.getText());
        produit.setStock_disponible(Integer.parseInt(tfstock_disponible.getText()));
        serviceProduit.Modifier(produit);
        Stage window= (Stage) tfdescription.getScene().getWindow();
        window.close();
    }

    public void afficherBtn(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("ShowProduit.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage window= (Stage) tfdescription.getScene().getWindow();
        window.setTitle("Liste Produits");
        window.setScene(scene);
        window.show();
    }
}
