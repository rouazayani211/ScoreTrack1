package controllers;

import entities.equipe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import services.ServiceEquipe;
import test.MainFX;

import java.sql.SQLException;
import java.io.IOException;
import java.util.Optional;

public class ShowEquipe1Details {
    @FXML
    private Label classementeqLabel;

    @FXML
    private Label ligueeqLabel;

    @FXML
    private Label nomeqLabel;

    @FXML
    private Label regioneqLabel;

    private ServiceEquipe service=new ServiceEquipe();

    private equipe equipes;

    public void displayEquipeDetails(equipe equipe)
    {
        equipes=equipe;
        nomeqLabel.setText("nom :"+equipe.getNom());
        regioneqLabel.setText("region :"+equipe.getRegion());
        ligueeqLabel.setText("ligue :"+equipe.getLigue());
        classementeqLabel.setText("classement :"+equipe.getClassement());

    }
    public void handleEdit(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("/modifieEquipe.fxml"));
            Parent root = fxmlLoader.load();
            ModifierEquipeController modifierEquipeController = fxmlLoader.getController();
            modifierEquipeController.setequipe(equipes);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit equipe");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void handleDelete(ActionEvent actionEvent)throws SQLException {
        // Display a confirmation dialog
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to delete this equipe?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User clicked OK, proceed with deletion
            service.supprimer(equipes);

            // Display a success message
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Equipe deleted successfully!");
            successAlert.showAndWait();

            // Close the window
            Stage stage = (Stage) nomeqLabel.getScene().getWindow();
            stage.close();
        } else {
            // User clicked Cancel or closed the dialog, do nothing
        }
    }

    public void handlematch(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("/video.fxml"));
            Parent root = fxmlLoader.load();
            Video videoController = fxmlLoader.getController();

            // Directly passing nomeqLabel's text to setTeamNameAndLoadVideos method
            String displayedName = nomeqLabel.getText().substring(nomeqLabel.getText().indexOf(":") + 1).trim();
            videoController.setTeamNameAndLoadVideos(displayedName);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Match Videos for " + displayedName);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
