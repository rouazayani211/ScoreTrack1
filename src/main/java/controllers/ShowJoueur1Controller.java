package controllers;

import javafx.scene.control.TextField;

import entities.equipe;
import entities.joueur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import services.ServiceJoueur;
import test.MainFX;
import javafx.scene.Cursor;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javafx.scene.control.Label;

public class ShowJoueur1Controller {

    @FXML
    private TextField search;

    @FXML
    private FlowPane joueur1Container;

    private ServiceJoueur serviceJoueur=new ServiceJoueur();

    public void initialize()throws SQLException
    {
        List<joueur>joueurs=serviceJoueur.afficher();
        displayJoueur(joueurs);
    }

    private void displayJoueur(List<joueur>joueurs)
    {
        for(entities.joueur joueur: joueurs)
        {
            VBox card=createJoueurCard(joueur);
            joueur1Container.getChildren().add(card);
        }
    }

    public VBox createJoueurCard(joueur joueur)
    {
        VBox card=new VBox();
        card.getStyleClass().add("joueur-card");
        Label idLabel=new Label("id :"+joueur.getId());
        Label nameLabel=new Label("Nom :"+joueur.getNom());
        Label prenomLabel=new Label("Prenom :"+joueur.getPrenom());
        Label ageLabel=new Label("Age :"+joueur.getAge());
        Label numeroLabel=new Label("Numero :"+joueur.getNumero());
        Label positionLabel=new Label("Position :"+joueur.getPosition());
        Label ideqjLabel=new Label("Id_eq :"+joueur.getId_equipe());
        card.getChildren().addAll(idLabel,nameLabel,prenomLabel,ageLabel,numeroLabel,positionLabel,ideqjLabel);
        card.setOnMouseClicked(event -> openJoueurDetailsPage(joueur));
        card.setCursor(Cursor.HAND);
        return card;
    }

    private void openJoueurDetailsPage(joueur joueur)
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("/ShowJoueurDetails1.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            ShowJoueur1Details controller = fxmlLoader.getController();
            controller.displayJoueurDetails(joueur);
            stage.setTitle("joueur Details");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void OnRefresh(ActionEvent actionEvent) throws SQLException{
        List<joueur> joueurs = serviceJoueur.afficher();
        joueur1Container.getChildren().clear();
        displayJoueur(joueurs);
    }

    public void OnAjouter(ActionEvent actionEvent) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("/ajoutJoueur.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) joueur1Container.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Ajouter joueur");
        stage.show();
    }




    @FXML
    void equipePage(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("/ShowEquipe1.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) joueur1Container.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Equipe");
        stage.show();

        }

    public void Ontrier(ActionEvent actionEvent) {
        try {
            List<joueur> joueurs = serviceJoueur.afficher();
            // Sort the list of equipes based on classement
            Collections.sort(joueurs, Comparator.comparingInt(joueur::getAge));
            // Clear the existing display
            joueur1Container.getChildren().clear();
            // Display the sorted equipes
            displayJoueur(joueurs);
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
            List<joueur> joueurs = serviceJoueur.afficher();

            // Create a new file named "equipe_notes.txt"
            File file = new File("joueur_notes.txt");

            // Initialize a BufferedWriter to write to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                // Write equipe data to the file
                for (joueur joueur : joueurs) {
                    writer.write("ID: " + joueur.getId() + "\n");
                    writer.write("Nom: " + joueur.getNom() + "\n");
                    writer.write("prenom: " + joueur.getPrenom() + "\n");
                    writer.write("numero: " + joueur.getNumero() + "\n");
                    writer.write("position: " + joueur.getPosition() + "\n");
                    writer.write("id_equipe: " + joueur.getId_equipe() + "\n\n");
                }
            }

            // Show a success message to the user
            showAlert(Alert.AlertType.INFORMATION, "Notes", "Notes Downloaded",
                    "The joueur notes file has been downloaded successfully.");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to Download Notes",
                    "An error occurred while downloading the joueur notes.");
        }
    }

    public void OnPDF(ActionEvent actionEvent) {
        try {
            List<joueur> joueurs = serviceJoueur.afficher();
            createPDF(joueurs);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        // Show a success message to the user
        showAlert(Alert.AlertType.INFORMATION, "PDF", "PDF Downloaded",
                "The joueur PDF file has been downloaded successfully.");

    }

    private void createPDF(List<joueur> joueurs) throws IOException {
        // Specify the download directory path explicitly
        String downloadDirectory = "C:/Users/Fares/Desktop/je test - Copy/";
        String outputPath = downloadDirectory + "joueurs.pdf";
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, 700);
                for (joueur joueur: joueurs) {
                    contentStream.showText("ID: " + joueur.getId());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Nom: " + joueur.getNom());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("prenom: " + joueur.getPrenom());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("age: " + joueur.getAge());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("position: " + joueur.getPosition());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("id_equipe: " + joueur.getId_equipe());
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
            String joueurName = search.getText().trim();
            if (!joueurName.isEmpty()) {
                List<joueur> joueurs = new ArrayList<>();
                for (joueur j : serviceJoueur.afficher()) {
                    if (j.getNom().equalsIgnoreCase(joueurName)) {
                        joueurs.add(j);
                    }
                }
                if (!joueurs.isEmpty()) {
                    joueur1Container.getChildren().clear();
                    displayJoueur(joueurs);
                    search.clear(); // Clear the TextField after displaying search results
                } else {
                    showAlert(Alert.AlertType.INFORMATION, "Information", "No joueur Found",
                            "No joueur with the name '" + joueurName + "' found.");
                    search.clear();
                }
            } else {
                showAlert(Alert.AlertType.WARNING, "Warning", "Empty Search Field",
                        "Please enter a joueur name to search.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void stats(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("/statsJ.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) joueur1Container.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Joueur Statistics");
    }
}
