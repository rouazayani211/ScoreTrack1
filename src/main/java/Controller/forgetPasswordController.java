package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import service.ServiceUtilisateur;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class forgetPasswordController {
    public TextField passwordField;
    public TextField passwordField1;
    public Button ConfirmPasswordBtn;
    public ImageView resetImageView;

    @FXML
    private TextField emailField;
    private ServiceUtilisateur serviceForgetPassword = new ServiceUtilisateur();

    public void submitForgetPassword(ActionEvent actionEvent) {
        String email = emailField.getText();

        try {
            if (serviceForgetPassword.doesEmailExist(email)) {
                String verificationCode = generateVerificationCode();
                serviceForgetPassword.sendEmail(email,verificationCode,"Your verifcatrion cod e it ");
                System.out.println(verificationCode);
                boolean isCodeCorrect = showVerificationPrompt(verificationCode);

                if (isCodeCorrect) {
                    resetImageView.setVisible(true);
                    passwordField.setVisible(true);
                    passwordField1.setVisible(true);
                    ConfirmPasswordBtn.setVisible(true);
                } else {
                    showAlert("Incorrect Code", "The verification code entered is incorrect.");
                }
            } else {
                showAlert("Email Not Found", "The provided email does not exist.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void goBackToLogin(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        try {
            Parent root = loader.load();
            emailField.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private String generateVerificationCode() {
        return String.valueOf((int) (Math.random() * 900000 + 100000));
    }

    private boolean showVerificationPrompt(String verificationCode) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Verification Code");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the verification code sent to your email:");

        Optional<String> result = dialog.showAndWait();
        return result.isPresent() && result.get().equals(verificationCode);
    }

    public void ConfirmPassword(ActionEvent actionEvent) {
        String newPassword = passwordField.getText();
        String confirmPassword = passwordField1.getText();
        if (!newPassword.equals(confirmPassword)) {
            showAlert("Password Mismatch", "The entered passwords do not match.");
            return;
        }
        String email = emailField.getText();
        try {
            serviceForgetPassword.changePassword(email, newPassword);
            showAlert("Password Changed", "Your password has been successfully changed.");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            try {
                Parent root = loader.load();
                emailField.getScene().setRoot(root);
            } catch (IOException e) {
                System.out.println(e.getMessage());      }
        } catch (SQLException e) {
            showAlert("Error", "Failed to change password. Please try again.");
            e.printStackTrace();
        }
    }
}
