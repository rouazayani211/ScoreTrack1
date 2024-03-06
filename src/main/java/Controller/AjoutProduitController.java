package Controller;

import Models.Produit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.ServiceProduit;

import java.sql.SQLException;

public class AjoutProduitController {

    @FXML
    private TextField tfdescription;

    @FXML
    private TextField tfnom_produit;

    @FXML
    private TextField tfprix;

    public ServiceProduit prod = new ServiceProduit();

    @FXML
    private TextField tfstock_disponible;

    @FXML
    void afficherBtn(ActionEvent event)throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowProduit.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage window= (Stage) ((Node)event.getSource()).getScene().getWindow() ;
        window.setTitle("Liste Produits");
        window.setScene(scene);
        window.show();
    }

    @FXML
    void ajouterBtn(ActionEvent event) throws SQLException {
        if (tfnom_produit.getText().equals("") ||  tfprix.getText().equals("")|| tfdescription.getText().equals("")|| tfstock_disponible.getText().equals("") ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("veuillez remplir ces champs");
            alert.showAndWait();
            return;
        }
        Produit pro = new Produit(tfnom_produit.getText(),Integer.parseInt(tfprix.getText()),tfdescription.getText(),Integer.parseInt(tfstock_disponible.getText()));
        prod.Ajouter(pro);
    }
}
