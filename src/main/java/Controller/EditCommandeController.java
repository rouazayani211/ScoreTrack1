package Controller;

import Models.Produit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import service.ServiceCommande;
import Models.Commande;
import service.ServiceProduit;

import java.sql.Date;
import java.sql.SQLException;

public class EditCommandeController {

    @FXML
    private ComboBox<Produit> cbIdProduit;
    @FXML
    private TextField tfDateCommande;
    @FXML
    private TextField tfMontantTotal;
    @FXML
    private TextField tfModePaiement;
    @FXML
    private TextField tfAdresseLivraison;
    private ServiceCommande serviceCommande = new ServiceCommande();
    private final ServiceProduit prod = new ServiceProduit();

    private Commande commandeToUpdate;

    public void initialize(Commande commande) {
        this.commandeToUpdate = commande;
        try {
            ObservableList<Produit> productIds = FXCollections.observableArrayList(prod.Afficher());
            cbIdProduit.setItems(productIds);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erreur lors de la récupération des ID de produit: " + e.getMessage());
            alert.showAndWait();
        }
        tfDateCommande.setText(commande.getDate_commande().toString());
        tfMontantTotal.setText(String.valueOf(commande.getMontant_total()));
        tfModePaiement.setText(commande.getMode_paiement());
        tfAdresseLivraison.setText(commande.getAdresse_livraison());
    }

    @FXML
    void updateBtn(ActionEvent event) {
        if (cbIdProduit.getValue().equals(null) ||tfMontantTotal.getText().isEmpty() ||
                tfModePaiement.getText().isEmpty() || tfAdresseLivraison.getText().isEmpty()) {
            System.out.println("Please fill in all fields.");
            return;
        }

        int idProduit = cbIdProduit.getValue().getId_produit();
        Date dateCommande = Date.valueOf(tfDateCommande.getText());
        int montantTotal = Integer.parseInt(tfMontantTotal.getText());
        String modePaiement = tfModePaiement.getText();
        String adresseLivraison = tfAdresseLivraison.getText();

        commandeToUpdate.setId_commande(commandeToUpdate.getId_commande());
        commandeToUpdate.setId_produit(idProduit);
        commandeToUpdate.setDate_commande(dateCommande);
        commandeToUpdate.setMontant_total(montantTotal);
        commandeToUpdate.setMode_paiement(modePaiement);
        commandeToUpdate.setAdresse_livraison(adresseLivraison);

        try {
            // Update the Commande in the database
            serviceCommande.modifier(commandeToUpdate);
            // Close the window after updating
            tfModePaiement.getScene().getWindow().hide();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void afficherBtn(ActionEvent actionEvent) {
    }
}
