package controllers;
import entities.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.ServiceJoueur;
import java.io.IOException;
import java.sql.SQLException;
public class AjouterJoueurController {

    @FXML
    private TextField agej;

    @FXML
    private Button ajouter_button_j;

    @FXML
    private TextField id_j_eq;

    @FXML
    private TextField nomj;

    @FXML
    private TextField numeroj;

    @FXML
    private TextField positionj;

    @FXML
    private TextField prenomj;
    private final ServiceJoueur SJ=new ServiceJoueur();

    @FXML
    void afficherjbtn(ActionEvent event) {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/ShowJoueur1.fxml"));
        try{
            Parent root=loader.load();

            numeroj.getScene().setRoot(root);


        }catch (IOException e){System.out.println(e.getMessage());}
    }

    @FXML
    void ajouterj(ActionEvent event) {
        /*try {
            SJ.ajouter(new joueur(Integer.parseInt(numeroj.getText()),Integer.parseInt(agej.getText()),nomj.getText(),prenomj.getText(),positionj.getText(),Integer.parseInt(id_j_eq.getText())));
        }catch (SQLException e){throw new RuntimeException(e);}
*/
        String nom = nomj.getText();
        String prenom = prenomj.getText();
        String position = positionj.getText();
        String ageText = agej.getText();
        String numeroText = numeroj.getText();
        String idEqText = id_j_eq.getText();

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
        int numero, idEq;
        try {
            numero = Integer.parseInt(numeroText);
            idEq = Integer.parseInt(idEqText);
        } catch (NumberFormatException e) {
            // Numero or Id_eq is not a valid integer
            showAlert("Please enter valid numbers for Numero and Id_eq.");
            return;
        }

        try {
            // Attempt to add joueur
            SJ.ajouter(new joueur(numero, age, nom, prenom, position, idEq));
            // Clear text fields after successful addition
            nomj.clear();
            prenomj.clear();
            positionj.clear();
            agej.clear();
            numeroj.clear();
            id_j_eq.clear();
            // Show success message
            showAlert("Joueur added successfully.");
        } catch (SQLException e) {
            // Error adding joueur
            showAlert("Error adding joueur: " + e.getMessage());
        }
    }

    // Method to show an alert dialog
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
