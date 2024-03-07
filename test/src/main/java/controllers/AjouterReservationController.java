package controllers;

import entities.Reservation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import services.ServiceReservation;

import java.sql.SQLException;

public class AjouterReservationController {

    ServiceReservation sr = new ServiceReservation();
    @FXML
    private TextField tfdater;

    @FXML
    private TextField tfiduser;

    @FXML
    private TextField tfnbrt;

    @FXML
    private TextField tfst;

    @FXML
    void ajouerReservation(ActionEvent event) {
        try {
            sr.ajouter(new Reservation(Integer.parseInt(tfiduser.getText()),tfdater.getText(),Integer.parseInt(tfnbrt.getText()),tfst.getText()));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
