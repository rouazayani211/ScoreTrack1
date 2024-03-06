package Controller;

import Models.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import service.ServiceUtilisateur;
import utils.Session;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Random;

public class Login {
    public TextField captchaInput;
    public Label captchaLabel;

    @FXML
    private TextField Email;

    @FXML
    private TextField mdp;
    private String captchaChallenge;

    private ServiceUtilisateur serviceUtilisateur = new ServiceUtilisateur();

    @FXML
    public void initialize() {
        captchaChallenge = generateCaptcha();
        captchaLabel.setText(captchaChallenge);
    }

    // Method to generate a simple CAPTCHA challenge
    private String generateCaptcha() {
        int length = 6; // Length of the CAPTCHA challenge
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder captcha = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            captcha.append(chars.charAt(random.nextInt(chars.length())));
        }
        return captcha.toString();
    }

    @FXML
    void LoginBT(ActionEvent event) {
        String userInput = captchaInput.getText().trim();
        if (userInput.equals(captchaChallenge)) {
            login();
        } else {
            showAlert("Login Failed", "Invalid CAPTCHA. Please try again.");
        }
    }

    private void login() {
        String email = Email.getText();
        String password = mdp.getText();

        try {
            Utilisateur user = serviceUtilisateur.login(email, password);
            if (user != null) {
                Session.getInstance().setUser(user);
                if(Objects.equals(user.getRole(), "admin")){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherUtilisateurController.fxml"));
                    try {
                        Parent root = loader.load();
                        mdp.getScene().setRoot(root);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }else{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
                    try {
                        Parent root = loader.load();
                        mdp.getScene().setRoot(root);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            } else {
                showAlert("Login Failed", "Invalid credentials. Please check your email and password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred during login.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void creeCompte(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterUtilisateurContoller.fxml"));
        try {
            Parent root = loader.load();
            captchaInput.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void OnForget(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ForgetPassword.fxml"));
        try {
            Parent root = loader.load();
            captchaInput.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
