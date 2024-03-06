package controllers;
import entities.joueur;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import services.ServiceEquipe;
import services.ServiceJoueur;
import entities.equipe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.Optional;

public class ModifierJoueurController {
    @FXML
    private TextField agej;

    @FXML
    private TextField id;

    @FXML
    private TextField id_eq_j;

    @FXML
    private TextField nomj;

    @FXML
    private TextField numeroj;

    @FXML
    private TextField positionj;

    @FXML
    private TextField prenomj;

    @FXML
    void modifierj(ActionEvent event) {
       /* // Get the values from the UI components
        //  int id = Integer.parseInt(IdTextField.getText());
        int idd=Integer.parseInt(id.getText());
        String nom=nomj.getText();
        String prenom=prenomj.getText();
        int age=Integer.parseInt(agej.getText());
        int numero=Integer.parseInt(numeroj.getText());
        String position=positionj.getText();
        int ideqj=Integer.parseInt(id_eq_j.getText());

        // Create a new Debt object with the retrieved values
        joueur joueur= new joueur(idd,numero,age,nom,prenom,position,ideqj);
        // Update the Debt object in the database
        ServiceJoueur SJ = new ServiceJoueur();
        try {
            SJ.modifier(joueur);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("joueur mise à jour avec succès!!");
            alert.show();
            // Close the ModifierDebt window
            Stage stage = (Stage) positionj.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }*/
        String nom = nomj.getText();
        String prenom = prenomj.getText();
        String position = positionj.getText();
        String ageText = agej.getText();
        String numeroText = numeroj.getText();
        String idEqText = id_eq_j.getText();

        // Check if any field is empty
        if (nom.isEmpty() || prenom.isEmpty() || position.isEmpty() || ageText.isEmpty() || numeroText.isEmpty() || idEqText.isEmpty()) {
            // Display error message if any field is empty
            showAlert("Please fill in all fields.");
            return;
        }

        // Parse age to integer
        int age;
        try {
            age = Integer.parseInt(ageText);
            // Check if age is valid (50 or less)
            if (age > 50) {
                showAlert("Age must be 50 or less.");
                return;
            }
        } catch (NumberFormatException e) {
            // Age is not a valid integer
            showAlert("Please enter a valid age.");
            return;
        }

        // Parse numero and idEq to integers
        int idd, numero, idEq;
        try {
            idd = Integer.parseInt(id.getText());
            numero = Integer.parseInt(numeroText);
            idEq = Integer.parseInt(idEqText);
        } catch (NumberFormatException e) {
            // Id, Numero, or Id_eq is not a valid integer
            showAlert("Please enter valid numbers for Id, Numero, and Id_eq.");
            return;
        }
/*
        // Create a new joueur object with the retrieved values
        joueur joueur = new joueur(idd, numero, age, nom, prenom, position, idEq);

        // Update the joueur object in the database
        ServiceJoueur SJ = new ServiceJoueur();
        try {
            SJ.modifier(joueur);
            // Show success message
            showAlert("Joueur mise à jour avec succès!!");
            // Close the ModifierJoueur window
            Stage stage = (Stage) positionj.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            // Error updating joueur
            showAlert("Error updating joueur: " + e.getMessage());
        }
        */
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to modify this joueur?");


        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User clicked OK, proceed with modification

            // Get the values from the UI components
           /* int idd = Integer.parseInt(id.getText());
            String nom = nomj.getText();
            String prenom = prenomj.getText();
            int age = Integer.parseInt(agej.getText());
            int numero = Integer.parseInt(numeroj.getText());
            String position = positionj.getText();
            int ideq = Integer.parseInt(id_eq_j.getText());*/

            // Create a new joueur object with the retrieved values
            joueur joueur =new joueur(idd, numero, age, nom, prenom, position, idEq);
             // Update the joueur object in the database
            ServiceJoueur SJ= new ServiceJoueur();
            try {
                SJ.modifier(joueur);

                // Display a success message
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("joueur updated successfully!!");
                successAlert.showAndWait();

                // Close the ModifierEquipe window
                Stage stage = (Stage) positionj.getScene().getWindow();
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

    // Method to show an alert dialog
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();

    }
    public void setjoueur(joueur joueur) {
        //IdTextField.setText(String.valueOf(debt.getIdDebt()));
        id.setText(String.valueOf(joueur.getId()));
        nomj.setText(String.valueOf(joueur.getNom()));
        prenomj.setText(String.valueOf(joueur.getPrenom()));
        agej.setText(String.valueOf(joueur.getAge()));
        numeroj.setText(String.valueOf(joueur.getNumero()));
        positionj.setText(String.valueOf(joueur.getPosition()));
        id_eq_j.setText(String.valueOf(joueur.getId_equipe()));



    }

}
