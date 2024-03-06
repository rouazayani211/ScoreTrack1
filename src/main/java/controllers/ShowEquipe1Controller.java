package controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import javafx.scene.control.Alert;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import entities.equipe;
import javafx.scene.control.TextField;
import entities.equipe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import services.ServiceEquipe;
import javafx.scene.control.Label;
import javafx.scene.Cursor;
import test.MainFX;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ShowEquipe1Controller {

    @FXML
    private TextField search;


    @FXML
    private FlowPane equipe1Container;

    private ServiceEquipe serviceEquipe = new ServiceEquipe();

    public void initialize() throws SQLException {
        List<equipe> equipes = serviceEquipe.afficher();
        displayEquipes(equipes);
    }

    private void displayEquipes(List<equipe> equipes) {
        for (equipe equipe : equipes) {
            VBox card = createEquipeCard(equipe);
            equipe1Container.getChildren().add(card);
        }
    }

    public VBox createEquipeCard(equipe equipe) {
        VBox card = new VBox();
        card.getStyleClass().add("equipe-card");
        Label idLabel = new Label("id_equipe :" + equipe.getId_equipe());
        Label nameLabel = new Label("Nom :" + equipe.getNom());
        Label regionLabel = new Label("region :" + equipe.getRegion());
        Label ligueLabel = new Label("ligue :" + equipe.getLigue());
        Label classementLabel = new Label("rank" + equipe.getClassement());
        card.getChildren().addAll(idLabel, nameLabel, regionLabel, ligueLabel, classementLabel);
        card.setOnMouseClicked(event -> openEquipeDetailsPage(equipe));
        card.setCursor(Cursor.HAND);
        return card;
    }

    private void openEquipeDetailsPage(equipe equipe) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("/ShowEquipeDetails1.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            ShowEquipe1Details controller = fxmlLoader.getController();
            controller.displayEquipeDetails(equipe);
            stage.setTitle("equipe Details");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void OnRefresh(ActionEvent actionEvent) throws SQLException {
        List<equipe> products = serviceEquipe.afficher();
        equipe1Container.getChildren().clear();
        displayEquipes(products);

    }

    public void OnAjouter(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("/ajoutEquipe.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) equipe1Container.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Ajouter Produit");
        stage.show();

    }

    public void joueurPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("/ShowJoueur1.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) equipe1Container.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Joueur");
        stage.show();
    }

    public void Ontrier(ActionEvent actionEvent) {
        try {
            List<equipe> equipes = serviceEquipe.afficher();
            // Sort the list of equipes based on classement
            Collections.sort(equipes, Comparator.comparingInt(equipe::getClassement));
            // Clear the existing display
            equipe1Container.getChildren().clear();
            // Display the sorted equipes
            displayEquipes(equipes);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void OnPDF(ActionEvent actionEvent) {
        try {
            List<equipe> equipes = serviceEquipe.afficher();
            createPDF(equipes);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        // Show a success message to the user
        showAlert(Alert.AlertType.INFORMATION, "PDF", "PDF Downloaded",
                "The equipe PDF file has been downloaded successfully.");

    }

    private void createPDF(List<equipe> equipes) throws IOException {
        // Specify the download directory path explicitly
        String downloadDirectory = "C:/Users/Fares/Desktop/je test - Copy/";
        String outputPath = downloadDirectory + "equipes.pdf";
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, 700);
                for (equipe equipe : equipes) {
                    contentStream.showText("ID: " + equipe.getId_equipe());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Nom: " + equipe.getNom());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Region: " + equipe.getRegion());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Ligue: " + equipe.getLigue());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Classement: " + equipe.getClassement());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.newLineAtOffset(0, -10); // space between equipes
                }
                contentStream.endText();
            }
            document.save(outputPath); // Save using the outputPath variable
        }
    }

    public void OnRechercher(ActionEvent actionEvent) {
        try {
            String equipeName = search.getText().trim();
            if (!equipeName.isEmpty()) {
                List<equipe> equipes = new ArrayList<>();
                for (equipe e : serviceEquipe.afficher()) {
                    if (e.getNom().equalsIgnoreCase(equipeName)) {
                        equipes.add(e);
                    }
                }
                if (!equipes.isEmpty()) {
                    equipe1Container.getChildren().clear();
                    displayEquipes(equipes);
                    search.clear(); // Clear the TextField after displaying search results
                } else {
                    showAlert(Alert.AlertType.INFORMATION, "Information", "No Equipe Found",
                            "No equipe with the name '" + equipeName + "' found.");
                    search.clear();
                }
            } else {
                showAlert(Alert.AlertType.WARNING, "Warning", "Empty Search Field",
                        "Please enter an equipe name to search.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void OnNotes(ActionEvent actionEvent) {
        try {
            // Get the list of equipes
            List<equipe> equipes = serviceEquipe.afficher();

            // Create a new file named "equipe_notes.txt"
            File file = new File("equipe_notes.txt");

            // Initialize a BufferedWriter to write to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                // Write equipe data to the file
                for (equipe equipe : equipes) {
                    writer.write("ID: " + equipe.getId_equipe() + "\n");
                    writer.write("Nom: " + equipe.getNom() + "\n");
                    writer.write("Region: " + equipe.getRegion() + "\n");
                    writer.write("Ligue: " + equipe.getLigue() + "\n");
                    writer.write("Classement: " + equipe.getClassement() + "\n\n");
                }
            }

            // Show a success message to the user
            showAlert(Alert.AlertType.INFORMATION, "Notes", "Notes Downloaded",
                    "The equipe notes file has been downloaded successfully.");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to Download Notes",
                    "An error occurred while downloading the equipe notes.");
        }
    }

    public void stats(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("/statsEq.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) equipe1Container.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Equipe Statistics");
    }
}
