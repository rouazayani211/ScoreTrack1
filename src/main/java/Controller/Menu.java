package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.io.IOException;

public class Menu {
    @FXML
    private Label lbMenu;
    @FXML
    void Inscrire(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        try {
            Parent root = loader.load();
            lbMenu.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());      }
    }

    }


