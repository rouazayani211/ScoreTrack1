package controllers;

import entities.equipe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceEquipe;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterEquipeController {
    @FXML
    private Button ajouter_button_eq;

    @FXML
    private TextField classementeq;

    @FXML
    private TextField ligueeq;

    @FXML
    private TextField nomeq;
    private final ServiceEquipe SE=new ServiceEquipe();

    @FXML
    private TextField regioneq;



    @FXML
    void afficherbtn(ActionEvent event) {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/ShowEquipe1.fxml"));
        try{
            Parent root=loader.load();

            classementeq.getScene().setRoot(root);



        }catch (IOException e){System.out.println(e.getMessage());}

    }

    @FXML
    void ajoutereq(ActionEvent event) {
        if (nomeq.getText().isEmpty() || regioneq.getText().isEmpty() || ligueeq.getText().isEmpty() || classementeq.getText().isEmpty()) {
            // Display an error message or handle the empty fields accordingly
            // For example, showing an alert:
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("remplir tous les champs!");
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
                alert.setContentText("Le classement doit être de 20 ou moins!");
                alert.showAndWait();
                return; // Exit the method if classement is invalid
            }
        } catch (NumberFormatException e) {
            // Handle the case where classement is not a valid integer
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Classement doit être entier!");
            alert.showAndWait();
            return; // Exit the method if classement is not a valid integer
        }
        try {
            SE.ajouter(new equipe(nomeq.getText(), regioneq.getText(), ligueeq.getText(), Integer.parseInt(classementeq.getText())));
            // Clear the text fields after successfully adding the equipe
            nomeq.clear();
            regioneq.clear();
            ligueeq.clear();
            classementeq.clear();

            // Display a success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Equipe ajoutée avec succès!");
            alert.showAndWait();
        }catch (SQLException e){throw new RuntimeException(e);}
    }
}