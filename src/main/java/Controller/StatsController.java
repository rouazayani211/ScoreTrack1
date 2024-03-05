package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import entities.Utilisateur;
import service.ServiceUtilisateur;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class StatsController implements Initializable {

    @FXML
    private PieChart pieChart;

    private ServiceUtilisateur serviceUtilisateur = new ServiceUtilisateur();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            List<Utilisateur> users = serviceUtilisateur.Afficher();
            int adminCount = 0;
            int normalUserCount = 0;
            for (Utilisateur user : users) {
                if (user.getRole().equalsIgnoreCase("admin")) {
                    adminCount++;
                } else {
                    normalUserCount++;
                }
            }

            pieChart.getData().addAll(
                    new PieChart.Data("Admins", adminCount),
                    new PieChart.Data("Normal Users", normalUserCount)
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
