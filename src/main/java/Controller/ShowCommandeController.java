package Controller;

import com.itextpdf.text.pdf.PdfPTable;
import Models.Commande;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import service.ServiceCommande;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.esprit.football.Main;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ShowCommandeController {
    @FXML
    private FlowPane commandeContainer;

    private ServiceCommande serviceCommande = new ServiceCommande();

    public void initialize() throws SQLException {
        List<Commande> commandes = serviceCommande.afficher();
        displayCommandes(commandes);
    }

    private void displayCommandes(List<Commande> commandes) {
        for (Commande commande : commandes) {
            VBox card = createCommandeCard(commande);
            commandeContainer.getChildren().add(card);
        }
    }

    private VBox createCommandeCard(Commande commande) {
        VBox card = new VBox();
        card.getStyleClass().add("commande-card");
        Label idCommandeLabel = new Label("ID Commande: " + commande.getId_commande());
        Label idProduitLabel = new Label("ID Produit: " + commande.getId_produit());
        Label dateCommandeLabel = new Label("Date Commande: " + commande.getDate_commande());
        card.getChildren().addAll(idCommandeLabel, idProduitLabel, dateCommandeLabel);
        card.setOnMouseClicked(event -> openProductDetailsPage(commande));
        return card;
    }

    private void openProductDetailsPage(Commande commande) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ShowCommandeDetails.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            ShowCommandeDetailController controller = fxmlLoader.getController();
            controller.displayCommandeDetails(commande);
            stage.setTitle("Commande Details");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onRefresh(ActionEvent event) throws SQLException {
        commandeContainer.getChildren().clear();
        List<Commande> commandes = serviceCommande.afficher();
        displayCommandes(commandes);
    }

    public void onBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ShowProduit.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) commandeContainer.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Produits");
        stage.show();
    }

    public void onAjouter(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ajouterCommande.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) commandeContainer.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Produits");
        stage.show();
    }

    public void onSort(ActionEvent actionEvent) {
        try {
            List<Commande> commandes = serviceCommande.afficher();
            Collections.sort(commandes, Comparator.comparing(Commande::getDate_commande));
            commandeContainer.getChildren().clear();
            displayCommandes(commandes);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onStats(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/CommandeStatistics.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) commandeContainer.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Stats");
        stage.show();
    }

    public void onPDF(ActionEvent actionEvent) {
        try {
            List<Commande> commandes = serviceCommande.afficher();
            Collections.sort(commandes, Comparator.comparing(Commande::getDate_commande));

            Document document = new Document();
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File file = fileChooser.showSaveDialog(commandeContainer.getScene().getWindow());

            if (file != null) {
                PdfWriter.getInstance(document, new FileOutputStream(file));
                document.open();
                addTableToPDF(document, commandes);
                document.close();
            }
        } catch (SQLException | DocumentException | IOException e) {
            e.printStackTrace();
            showAlert("Error", "Error exporting to PDF", Alert.AlertType.ERROR);
        }
    }

    private void addTableToPDF(Document document, List<Commande> commandes) throws DocumentException {
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);

        table.addCell("ID Commande");
        table.addCell("ID Produit");
        table.addCell("Date Commande");

        for (Commande commande : commandes) {
            table.addCell(String.valueOf(commande.getId_commande()));
            table.addCell(String.valueOf(commande.getId_produit()));
            table.addCell(String.valueOf(commande.getDate_commande()));
        }

        document.add(table);
    }

    public void onExcel(ActionEvent actionEvent) {
        try {
            List<Commande> commandes = serviceCommande.afficher();
            Collections.sort(commandes, Comparator.comparing(Commande::getDate_commande));

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Commandes");

            addHeaderToExcel(sheet);

            int rowNumber = 1;
            for (Commande commande : commandes) {
                Row row = sheet.createRow(rowNumber++);
                row.createCell(0).setCellValue(commande.getId_commande());
                row.createCell(1).setCellValue(commande.getId_produit());
                row.createCell(2).setCellValue(commande.getDate_commande().toString());
            }

            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
            File file = fileChooser.showSaveDialog(commandeContainer.getScene().getWindow());

            if (file != null) {
                try (FileOutputStream outputStream = new FileOutputStream(file)) {
                    workbook.write(outputStream);
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            showAlert("Error", "Error exporting to Excel", Alert.AlertType.ERROR);
        }
    }

    private void addHeaderToExcel(Sheet sheet) {
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID Commande");
        header.createCell(1).setCellValue("ID Produit");
        header.createCell(2).setCellValue("Date Commande");
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType, content, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
