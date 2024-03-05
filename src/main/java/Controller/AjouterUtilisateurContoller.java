package Controller;

import entities.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import service.ServiceUtilisateur;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class AjouterUtilisateurContoller {

    @FXML
    private TextField EmailTF;

    @FXML
    private TextField MdpTF;

    @FXML
    private TextField NomTF;

    @FXML
    private TextField PrenomTF;

    @FXML
    private TextField RoleTF;

    @FXML
    private Label lbnom;

    @FXML
    private DatePicker date_naiss;

    private ServiceUtilisateur uti = new ServiceUtilisateur();

    @FXML
    void AjouterUtilisateur(ActionEvent event) {
        // Vérifier si les champs obligatoires sont remplis
        if (NomTF.getText().isEmpty() || PrenomTF.getText().isEmpty() || EmailTF.getText().isEmpty() ||
                MdpTF.getText().isEmpty() || date_naiss.getValue() == null || RoleTF.getText().isEmpty()) {
            // Afficher un message d'erreur ou faire quelque chose en cas de champs manquants
            System.out.println("Veuillez remplir tous les champs obligatoires.");
            return;
        }

        // Vérifier si l'email est valide
        if (!EmailTF.getText().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            System.out.println("Veuillez saisir une adresse email valide (doit contenir '@' et un point après le domaine).");
            return;
        }

        try {
            // Appeler la méthode Ajouter uniquement si les contrôles de saisie sont passés avec succès
            uti.Ajouter(new Utilisateur(NomTF.getText(), PrenomTF.getText(), EmailTF.getText(), MdpTF.getText(), date_naiss.getValue().toString(), RoleTF.getText()));
            System.out.println("Utilisateur ajouté avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'utilisateur : " + e.getMessage());
        }
    }

    @FXML
    void afficherutilisateur(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        try {
            Parent root = loader.load();
            lbnom.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
