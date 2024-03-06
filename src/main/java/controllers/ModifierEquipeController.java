package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Optional;

import entities.equipe;
import javafx.stage.Stage;
import services.ServiceEquipe;

public class ModifierEquipeController {
    @FXML
    private TextField classementeq;

    @FXML
    private TextField ideq;

    @FXML
    private TextField ligueeq;

    @FXML
    private Button modifier_button_eq;

    @FXML
    private TextField nomeq;

    @FXML
    private TextField regioneq;

    @FXML
    void modifierreq(ActionEvent event) {
     /*   // Get the values from the UI components
      //  int id = Integer.parseInt(IdTextField.getText());
        int id=Integer.parseInt(ideq.getText());
        String nom=nomeq.getText();
        String region=regioneq.getText();
        String ligue=ligueeq.getText();
        int classement=Integer.parseInt(classementeq.getText());

        // Create a new Debt object with the retrieved values
        equipe equipe = new equipe(id,nom,region,ligue,classement);
        // Update the Debt object in the database
        ServiceEquipe SE = new ServiceEquipe();
        try {
            SE.modifier(equipe);
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("equipe mise à jour avec succès!!");
            alert.show();
            // Close the ModifierDebt window
            Stage stage = (Stage) regioneq.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }*/
       /* // Check if any of the text fields are empty
        if (ideq.getText().isEmpty() || nomeq.getText().isEmpty() || regioneq.getText().isEmpty() || ligueeq.getText().isEmpty() || classementeq.getText().isEmpty()) {
            // Display an error message or handle the empty fields accordingly
            // For example, showing an alert:
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields!");
            alert.showAndWait();
            return; // Exit the method if any field is empty
        }

        // Check if classement is within the valid range (20 or less)
        int classementValue;
        try {
            classementValue = Integer.parseInt(classementeq.getText());
            if (classementValue > 20) {
                // Display an error message if classement is greater than 20
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Classement doit etre egale à 20 ou moins!");
                alert.showAndWait();
                return; // Exit the method if classement is invalid
            }
        } catch (NumberFormatException e) {
            // Handle the case where classement is not a valid integer
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Classement doit etre entier!");
            alert.showAndWait();
            return; // Exit the method if classement is not a valid integer
        }

        // All fields are filled and classement is valid, proceed with modifying the team
        // Get the values from the UI components
        int id = Integer.parseInt(ideq.getText());
        String nom = nomeq.getText();
        String region = regioneq.getText();
        String ligue = ligueeq.getText();
        int classement = Integer.parseInt(classementeq.getText());

        // Create a new equipe object with the retrieved values
        equipe equipe = new equipe(id, nom, region, ligue, classement);
        // Update the equipe object in the database
        ServiceEquipe SE = new ServiceEquipe();
        try {
            SE.modifier(equipe);

            // Display a success message
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Equipe modifiée avec succès!");
            successAlert.showAndWait();

            // Close the ModifierEquipe window
            Stage stage = (Stage) regioneq.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            // Display an error message if an SQL exception occurs
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
        }*/
        // Check if any of the text fields are empty
        if (ideq.getText().isEmpty() || nomeq.getText().isEmpty() || regioneq.getText().isEmpty() || ligueeq.getText().isEmpty() || classementeq.getText().isEmpty()) {
            // Display an error message or handle the empty fields accordingly
            // For example, showing an alert:
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields!");
            alert.showAndWait();
            return; // Exit the method if any field is empty
        }

        // Check if classement is within the valid range (20 or less)
        int classementValue;
        try {
            classementValue = Integer.parseInt(classementeq.getText());
            if (classementValue > 20) {
                // Display an error message if classement is greater than 20
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Classement must be 20 or less!");
                alert.showAndWait();
                return; // Exit the method if classement is invalid
            }
        } catch (NumberFormatException e) {
            // Handle the case where classement is not a valid integer
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Classement must be a valid integer!");
            alert.showAndWait();
            return; // Exit the method if classement is not a valid integer
        }

        // Display a confirmation dialog
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to modify this equipe?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User clicked OK, proceed with modification

            // Get the values from the UI components
            int id = Integer.parseInt(ideq.getText());
            String nom = nomeq.getText();
            String region = regioneq.getText();
            String ligue = ligueeq.getText();
            int classement = Integer.parseInt(classementeq.getText());

            // Create a new equipe object with the retrieved values
            equipe equipe = new equipe(id, nom, region, ligue, classement);
            // Update the equipe object in the database
            ServiceEquipe SE = new ServiceEquipe();
            try {
                SE.modifier(equipe);

                // Display a success message
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Equipe updated successfully!!");
                successAlert.showAndWait();

                // Close the ModifierEquipe window
                Stage stage = (Stage) regioneq.getScene().getWindow();
                stage.close();
            } catch (SQLException e) {
                // Display an error message if an SQL exception occurs
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText(e.getMessage());
                errorAlert.showAndWait();
            }
        } else {
            // User clicked Cancel or closed the dialog, do nothing
        }
    }
    public void setequipe(equipe equipe) {
        //IdTextField.setText(String.valueOf(debt.getIdDebt()));
        ideq.setText(String.valueOf(equipe.getId_equipe()));
         nomeq.setText(String.valueOf(equipe.getNom()));
        regioneq.setText(String.valueOf(equipe.getRegion()));
        ligueeq.setText(String.valueOf(equipe.getLigue()));
        classementeq.setText(String.valueOf(equipe.getClassement()));
    }

    @FXML
    void initialize() {
    }

}
