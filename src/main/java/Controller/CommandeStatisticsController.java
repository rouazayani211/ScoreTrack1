package Controller;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import Models.Commande;
import service.ServiceCommande;

public class CommandeStatisticsController {

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private ServiceCommande serviceCommande = new ServiceCommande();

    public void initialize() throws SQLException {
        loadStatisticsData();
    }

    private void loadStatisticsData() throws SQLException {
        List<Commande> commandes = serviceCommande.Afficher();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        commandes.forEach(commande -> {
            Date date_commande = commande.getDate_commande();
            String month = getMonthFromDate(date_commande);
            series.getData().add(new XYChart.Data<>(month, commande.getMontant_total()));
        });

        barChart.getData().add(series);
    }

    private String getMonthFromDate(Date date) {
        return date.toLocalDate().getMonth().name().substring(0, 3);
    }
}
