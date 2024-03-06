package Controller;
import java.sql.SQLException;
import java.util.Random;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import Models.Commande;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;

import javafx.scene.control.TextArea;
import org.json.JSONException;
import org.json.JSONObject;
import service.ServiceCommande;
import tn.esprit.football.Main;

public class OrderCheckOutController {

    @FXML
    private ComboBox<String> paymentOptionsList;
    @FXML
    private ComboBox<String> shippingOptionsList;
    @FXML
    private TextArea shippingAddress;

    private final ServiceCommande service = new ServiceCommande();

    private Commande commande;

    public void setData(Commande c){
        commande = c;
    }

    public void initialize() {
        ObservableList<String> shippmentList = FXCollections.observableArrayList(
                "Standard Shipping",
                "Express Shipping",
                "Next Day Shipping"
        );
        ObservableList<String> paymentList = FXCollections.observableArrayList(
                "Credit Card",
                "PayPal",
                "Bank Transfer"
        );
        paymentOptionsList.setItems(paymentList);
        shippingOptionsList.setItems(shippmentList);
        paymentOptionsList.getSelectionModel().selectFirst();
        shippingOptionsList.getSelectionModel().selectFirst();
    }

    @FXML
    private void OnPayAction(ActionEvent event) throws IOException {
        try {
            URL url = new URL("https://developers.flouci.com/api/generate_payment");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            String payload = "{\n"
                    + "    \"app_token\": \"b9e12676-1459-41a1-b608-48d7c2cedcb0\",\n"
                    + "    \"app_secret\": \"9947737a-ee91-4119-b773-fc3a190d13af\",\n"
                    + "    \"accept_card\": \"true\",\n"
                    + "    \"amount\": \"" + calculateOrdersPrice() + "\",\n"
                    + "    \"success_link\": \"http://localhost/success.html\",\n"
                    + "    \"fail_link\": \"http://localhost/fail.html\",\n"
                    + "    \"session_timeout_secs\": 1200,\n"
                    + "    \"developer_tracking_id\": \"<your_internal_tracking_id>\"\n"
                    + "}";
            System.out.println(payload);
            OutputStream os = conn.getOutputStream();
            os.write(payload.getBytes());
            os.flush();
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String link = null;
            String paymentId = null;
            String output;
            while ((output = br.readLine()) != null) {
                JSONObject jsonResponse = new JSONObject(output);
                JSONObject result = jsonResponse.getJSONObject("result");
                link = result.getString("link");
                paymentId = result.getString("payment_id");
                Desktop.getDesktop().browse(new URI(link));
            }
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("PaymentVerification.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            PaymentVerificationController controller = loader.getController();
            controller.setup(this, paymentId);
            stage.setScene(scene);
            stage.show();
            conn.disconnect();
        } catch (IOException e) {
            System.out.println(e.getCause());
        } catch (URISyntaxException ex) {
            Logger.getLogger(OrderCheckOutController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public int calculateOrdersPrice() {
        Random random = new Random();
        int randomValue = random.nextInt(1000);
        return randomValue * 1000;
    }

    public void successfulPayment() {
        try {
            service.ajouter(commande);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
