package Controller;

import Models.Commande;
import Models.Produit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.ServiceProduit;
import tn.esprit.football.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.io.IOException;
import java.sql.Date;

public class AjouterCommandeController {

    @FXML
    private ComboBox<Produit> cbIdProduit;
    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField tfMontantTotal;

    @FXML
    private TextField tfModePaiement;

    @FXML
    private TextField tfAdresseLivraison;

    private final ServiceProduit prod = new ServiceProduit();

    public void initialize() {
        try {
            ObservableList<Produit> productIds = FXCollections.observableArrayList(prod.Afficher());
            cbIdProduit.setItems(productIds);
            System.out.println(productIds);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erreur lors de la récupération des ID de produit: " + e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    void ajouterBtn(ActionEvent event) throws SQLException, IOException {
        if (cbIdProduit.getValue() == null ||
                datePicker.getValue() == null || tfMontantTotal.getText().isEmpty() ||
                tfModePaiement.getText().isEmpty() || tfAdresseLivraison.getText().isEmpty()) {
            System.out.println("Please fill in all fields.");
            return;
        }
        int idProduit = cbIdProduit.getValue().getId_produit();
        String dateCommande = datePicker.getValue().toString();
        String modePaiement = tfModePaiement.getText();
        String adresseLivraison = tfAdresseLivraison.getText();
        int montantTotal = Integer.parseInt(tfMontantTotal.getText());
        Commande c = new Commande(idProduit, Date.valueOf(dateCommande), montantTotal, modePaiement, adresseLivraison);
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("OrderCheckOut.fxml"));
        Parent root = loader.load();
        OrderCheckOutController controller = loader.getController();
        controller.setData(c);
        Scene scene = new Scene(root);
        Stage window = (Stage) tfMontantTotal.getScene().getWindow();
        window.setTitle("Payments");
        window.setScene(scene);
        window.show();
    }

    private void clearFields() {
        datePicker.getEditor().clear();
        tfMontantTotal.clear();
        tfModePaiement.clear();
        tfAdresseLivraison.clear();
    }

    public void afficherBtn(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("ShowCommande.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage window = (Stage) tfMontantTotal.getScene().getWindow();
        window.setTitle("Liste Commandes");
        window.setScene(scene);
        window.show();
    }
}
