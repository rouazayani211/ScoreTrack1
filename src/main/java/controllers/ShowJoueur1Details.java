package controllers;

import entities.joueur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import services.ServiceJoueur;
import test.MainFX;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class ShowJoueur1Details {

    @FXML
    private Label agejLabel;

    @FXML
    private Label ideqjLabel;

    @FXML
    private Label nomjLabel;

    @FXML
    private Label numerojLabel;

    @FXML
    private Label prenomjLabel;

    @FXML
    private Label positionjLabel;

    private ServiceJoueur service=new ServiceJoueur();

    private joueur joueurs;

    public void displayJoueurDetails(joueur joueur)
    {
        joueurs= joueur;
        nomjLabel.setText("nom :"+joueur.getNom());
        prenomjLabel.setText("prenom :"+joueur.getPrenom());
        agejLabel.setText("age :"+joueur.getAge());
        numerojLabel.setText("numero :"+joueur.getNumero());
        positionjLabel.setText("position :"+joueur.getPosition());
        ideqjLabel.setText("id_equipe :"+joueur.getId_equipe());
    }

    public void handleEdit(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("/modifierJoueur.fxml"));
            Parent root = fxmlLoader.load();
            ModifierJoueurController modifierJoueurController = fxmlLoader.getController();
            modifierJoueurController.setjoueur(joueurs);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit joueur");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleDelete(ActionEvent actionEvent) throws SQLException {
       /* service.supprimer(joueurs);
        // Display a success message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Joueur deleted successfully!");
        alert.showAndWait();
        Stage stage = (Stage) nomjLabel.getScene().getWindow();
        stage.close();*/
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to delete this joueur?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User clicked OK, proceed with deletion
            service.supprimer(joueurs);

            // Display a success message
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Joueur supprimé avec succès!");
            successAlert.showAndWait();

            // Close the window
            Stage stage = (Stage) nomjLabel.getScene().getWindow();
            stage.close();
        } else {
            // User clicked Cancel or closed the dialog, do nothing
        }
    }

    public void handleinfo(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Nextgames.fxml"));
            Parent root = loader.load();

            Nextgames nextgamesController = loader.getController();
            String playerName = nomjLabel.getText().split(":")[1].trim(); // Adjust based on your label text format
            nextgamesController.fetchPlayerSummary(playerName);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Player Information");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
