package controllers;

import entities.equipe;
import entities.joueur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import services.ServiceEquipe;
import services.ServiceJoueur;
import test.MainFX;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class StatsJ {
    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private ServiceJoueur serviceJoueur = new ServiceJoueur();

    public void initialize() {
        // Initialize the chart
        xAxis.setLabel("Joueur Age Statistiques");
        yAxis.setLabel("Nombre De Joueurs");

        try {
            // Get your joueur data and calculate statistics
            List<joueur> joueurs = serviceJoueur.afficher();

            int youngCount = 0;
            int midCount = 0;
            int oldCount = 0;

            for (joueur joueur : joueurs) {
                int age = joueur.getAge();
                if (age >= 0 && age <= 22) {
                    youngCount++;
                } else if (age >= 23 && age <= 28) {
                    midCount++;
                } else if (age >= 29 && age <= 50) {
                    oldCount++;
                }
            }

            // Create a data series for the bar chart
            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>("Young -22", youngCount));
            series.getData().add(new XYChart.Data<>("Middle Age 23-28", midCount));
            series.getData().add(new XYChart.Data<>("Old +29", oldCount));

            // Add the data series to the chart
            barChart.getData().add(series);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void goBack(ActionEvent actionEvent) {
        try {
            // Load the previous scene (assuming it's stored in a separate FXML file)
            Parent root = FXMLLoader.load(MainFX.class.getResource("/ShowJoueur1.fxml"));
            Stage stage = (Stage) barChart.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Joueurs");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
