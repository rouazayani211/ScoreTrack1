package Controller;

import entities.Utilisateur;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import service.ServiceUtilisateur;
import utils.Session;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
public class AfficherUtilisateurController {
    public TextField RechercheField;
    @FXML
    private ListView<Utilisateur> labelfromdb;
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField dateNaissField;
    @FXML
    private TextField emailField;
    @FXML
    public TextField roleField;
    @FXML
    public TextField mdpField;

    ServiceUtilisateur SU = new ServiceUtilisateur();

    public void initialize() {
        try {
            labelfromdb.setItems(FXCollections.observableArrayList(SU.Afficher()));
            labelfromdb.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            labelfromdb.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    updateFields(newValue);
                }
            });
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    private void updateFields(Utilisateur utilisateur) {
        nomField.setText(utilisateur.getNom());
        prenomField.setText(utilisateur.getPrenom());
        dateNaissField.setText(utilisateur.getDate_naiss());
        emailField.setText(utilisateur.getEmail());
        roleField.setText(utilisateur.getRole());
        mdpField.setText(utilisateur.getMdp());
    }

    @FXML
    void AfficherDB(ActionEvent event) {
        try {
            labelfromdb.setItems(FXCollections.observableArrayList(SU.Afficher()));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void SupprimerUtilisateur(ActionEvent event) {
        Utilisateur selectedUtilisateur = labelfromdb.getSelectionModel().getSelectedItem();
        if (selectedUtilisateur != null) {
            int id = selectedUtilisateur.getId();

            try {
                SU.Supprimer(new Utilisateur(id));
                AfficherDB(event);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Aucun élément sélectionné.");
        }
    }

    public void OnLogout(ActionEvent actionEvent) {
        Session.getInstance().logout();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        try {
            Parent root = loader.load();
            labelfromdb.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void UpdateUtilisateur(ActionEvent event) {
        Utilisateur selectedUtilisateur = labelfromdb.getSelectionModel().getSelectedItem();
        if (selectedUtilisateur != null) {
            try {
                int id = selectedUtilisateur.getId();
                Utilisateur updatedUtilisateur = getUpdatedUtilisateur(selectedUtilisateur);
                updatedUtilisateur.setId(id);
                SU.Modifier(updatedUtilisateur);
                AfficherDB(event);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Aucun élément sélectionné");
        }
    }
    private Utilisateur getUpdatedUtilisateur(Utilisateur utilisateur) {
        String updatedNom = nomField.getText().trim();
        String updatedPrenom = prenomField.getText().trim();
        String updatedDateNaiss = dateNaissField.getText().trim();
        String updatedEmail = emailField.getText().trim();
        String updatedRole = roleField.getText().trim();
        String updatedMdp = mdpField.getText().trim();

        if (!updatedNom.isEmpty()) {
            utilisateur.setNom(updatedNom);
        }
        if (!updatedPrenom.isEmpty()) {
            utilisateur.setPrenom(updatedPrenom);
        }
        if (!updatedDateNaiss.isEmpty()) {
            utilisateur.setDate_naiss(updatedDateNaiss);
        }
        if (!updatedEmail.isEmpty() && updatedEmail.contains("@")) {
            utilisateur.setEmail(updatedEmail);
        } else {
            System.out.println("email non valide ");
        }
        if (!updatedRole.isEmpty()) {
            utilisateur.setRole(updatedRole);
        }
        if (!updatedMdp.isEmpty()) {
            utilisateur.setMdp(updatedMdp);
        }
        return utilisateur;
    }

    public void OnSort(ActionEvent actionEvent) {
        FXCollections.sort(labelfromdb.getItems(), Comparator.comparing(Utilisateur::getNom));
    }

    @FXML
    void OnRecherche(ActionEvent actionEvent) {
        String searchKeyword = RechercheField.getText();
        if (!searchKeyword.isEmpty()) {
            List<Utilisateur> filteredList = labelfromdb.getItems().stream()
                    .filter(utilisateur -> utilisateur.getNom().contains(searchKeyword))
                    .collect(Collectors.toList());
            labelfromdb.setItems(FXCollections.observableArrayList(filteredList));
        } else {
            refreshList();
        }

        MultipleSelectionModel<Utilisateur> selectionModel = labelfromdb.getSelectionModel();
        if (!labelfromdb.getItems().isEmpty()) {
            selectionModel.selectFirst();
        }
    }
    private void refreshList() {
        try {
            labelfromdb.setItems(FXCollections.observableArrayList(SU.Afficher()));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void OnStats(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Stats.fxml"));
        try {
            Parent root = loader.load();
            labelfromdb.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public void OnExport(ActionEvent actionEvent) {
        // Get data from ListView
        List<Utilisateur> utilisateurs = labelfromdb.getItems();

        // Create a new Excel workbook
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Utilisateurs");

            // Create headers
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Nom");
            headerRow.createCell(2).setCellValue("Prénom");
            headerRow.createCell(3).setCellValue("Date de Naissance");
            headerRow.createCell(4).setCellValue("Email");
            headerRow.createCell(5).setCellValue("Role");

            // Fill data rows
            int rowNum = 1;
            for (Utilisateur utilisateur : utilisateurs) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(utilisateur.getId());
                row.createCell(1).setCellValue(utilisateur.getNom());
                row.createCell(2).setCellValue(utilisateur.getPrenom());
                row.createCell(3).setCellValue(utilisateur.getDate_naiss());
                row.createCell(4).setCellValue(utilisateur.getEmail());
                row.createCell(5).setCellValue(utilisateur.getRole());
            }

            try (FileOutputStream fileOut = new FileOutputStream("utilisateurs.xlsx")) {
                workbook.write(fileOut);
                System.out.println("Exported successfully to utilisateurs.xlsx");
            }
        } catch (IOException e) {
            System.out.println("Error exporting to Excel: " + e.getMessage());
        }
    }
}
