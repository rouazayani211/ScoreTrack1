package Controller;

import javafx.event.ActionEvent;
import Models.Commande;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import service.ServiceCommande;
import tn.esprit.football.Main;

import java.io.IOException;
import java.sql.SQLException;

public class ShowCommandeDetailController {
    @FXML
    private Label idCommandeLabel;

    @FXML
    private Label idProduitLabel;

    @FXML
    private Label dateCommandeLabel;

    @FXML
    private Label montantTotalLabel;

    @FXML
    private Label modePaiementLabel;

    @FXML
    private Label adresseLivraisonLabel;

    private Commande commande;

    private ServiceCommande serviceCommande = new ServiceCommande();

    public void displayCommandeDetails(Commande commande) {
        this.commande = commande;
        idCommandeLabel.setText("ID Commande: " + commande.getId_commande());
        idProduitLabel.setText("ID Produit: " + commande.getId_produit());
        dateCommandeLabel.setText("Date Commande: " + commande.getDate_commande());
        montantTotalLabel.setText("Montant Total: " + commande.getMontant_total());
        modePaiementLabel.setText("Mode de Paiement: " + commande.getMode_paiement());
        adresseLivraisonLabel.setText("Adresse de Livraison: " + commande.getAdresse_livraison());
    }

    @FXML
    private void handleEdit(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("editCommande.fxml"));
            Parent root = fxmlLoader.load();
            EditCommandeController controller = fxmlLoader.getController();
            controller.initialize(commande);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit Commande");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        try {
            serviceCommande.Supprimer(commande);
            // Close the window after deleting the command
            idCommandeLabel.getScene().getWindow().hide();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception accordingly
        }
    }
}
