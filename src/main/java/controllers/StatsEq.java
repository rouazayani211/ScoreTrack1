package controllers;

import entities.equipe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import test.MainFX;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class StatsEq {

    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private ServiceEquipe serviceEquipe = new ServiceEquipe();

    public void initialize() {
        // Initialize the chart
        xAxis.setLabel("Equipe Classement Statistiques");
        yAxis.setLabel("Nombre D'Equipes");

        try {
            // Get your equipe data and calculate statistics
            List<equipe> equipes = serviceEquipe.afficher();

            int promotionCount = 0;
            int noRewardsCount = 0;
            int relegationCount = 0;

            for (equipe equipe : equipes) {
                int classement = equipe.getClassement();
                if (classement >= 1 && classement <= 6) {
                    promotionCount++;
                } else if (classement >= 7 && classement <= 16) {
                    noRewardsCount++;
                } else if (classement >= 17 && classement <= 20) {
                    relegationCount++;
                }
            }

            // Create a data series for the bar chart
            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>("Promotion 1-6", promotionCount));
            series.getData().add(new XYChart.Data<>("No Rewards 7-16", noRewardsCount));
            series.getData().add(new XYChart.Data<>("Relegation 17-20", relegationCount));

            // Add the data series to the chart
            barChart.getData().add(series);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void goBack(ActionEvent actionEvent) {
        try {
            // Load the previous scene (assuming it's stored in a separate FXML file)
            Parent root = FXMLLoader.load(MainFX.class.getResource("/ShowEquipe1.fxml"));
            Stage stage = (Stage) barChart.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Equipes");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
