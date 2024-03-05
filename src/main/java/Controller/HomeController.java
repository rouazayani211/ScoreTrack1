package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import entities.Utilisateur;
import service.ServiceUtilisateur;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import utils.Session;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class HomeController {

    @FXML
    private AnchorPane anchorPane;
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
    private DatePicker date_naiss;

    ServiceUtilisateur SU = new ServiceUtilisateur();

    @FXML
    private void initialize() {
        Utilisateur user = Session.getInstance().getUser();

        NomTF.setText(user.getNom());
        PrenomTF.setText(user.getPrenom());
        EmailTF.setText(user.getEmail());
        date_naiss.getValue();
        MdpTF.setText(user.getMdp());
        RoleTF.setText(user.getRole());



    }

    @FXML
    private void OnModification(ActionEvent event) {
        Utilisateur selectedUtilisateur = Session.getInstance().getUser();
        if (selectedUtilisateur != null) {
            try {
                int id = selectedUtilisateur.getId();
                Utilisateur updatedUtilisateur = getUpdatedUtilisateur(selectedUtilisateur);
                updatedUtilisateur.setId(id);
                SU.Modifier(updatedUtilisateur);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Aucun élément sélectionné");
        }
    }

    @FXML
    private void OnDisconnect(ActionEvent event) {
        Session.getInstance().logout();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        try {
            Parent root = loader.load();
            PrenomTF.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private Utilisateur getUpdatedUtilisateur(Utilisateur utilisateur) {
        String updatedNom = NomTF.getText().trim();
        String updatedPrenom = PrenomTF.getText().trim();
        String updatedEmail = EmailTF.getText().trim();
        String updatedPassword = MdpTF.getText().trim(); // Updated password
        String updatedRole = RoleTF.getText().trim();
        // Updated date of birth
        Date updatedDateOfBirth = Date.valueOf(date_naiss.getValue());

        if (!updatedNom.isEmpty()) {
            utilisateur.setNom(updatedNom);
        }
        if (!updatedPrenom.isEmpty()) {
            utilisateur.setPrenom(updatedPrenom);
        }
        if (!updatedEmail.isEmpty() && updatedEmail.contains("@")) {
            utilisateur.setEmail(updatedEmail);
        }
        if (!updatedPassword.isEmpty()) {
            utilisateur.setMdp(updatedPassword);
        }
        if (updatedDateOfBirth != null) {
            utilisateur.setDate_naiss(updatedDateOfBirth.toString());
        }
        if (!updatedRole.isEmpty()) {
            utilisateur.setRole(updatedRole);
        }
        return utilisateur;
    }


}
