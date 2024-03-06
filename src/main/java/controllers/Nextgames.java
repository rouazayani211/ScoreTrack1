package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import org.json.JSONObject; // Make sure to include the JSON processing library
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class Nextgames {

    @FXML
    private TextArea summaryTextArea; // Reference to the TextArea in the FXML
    @FXML
    private ImageView playerImageView;
    // Method to fetch player summary

    public void displayImage(String imageUrl) {
        Image image = new Image(imageUrl);
        playerImageView.setImage(image);
    }
    public void fetchPlayerSummary(String playerName) {

        String baseUrl = "https://en.wikipedia.org/api/rest_v1/page/summary/";
        String formattedName = playerName.replace(" ", "_");

        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL) // Ensure redirects are followed
                .build();
        String finalUrl = baseUrl + formattedName;
        System.out.println("Requesting URL: " + finalUrl); // Debug print

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(finalUrl))
                .build();

        new Thread(() -> {
            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    // Success
                    JSONObject jsonResponse = new JSONObject(response.body());
                    String extract = jsonResponse.getString("extract");
                    javafx.application.Platform.runLater(() -> summaryTextArea.setText(extract));
                } else {
                    // Handle redirect or error
                    System.out.println("Received HTTP error code: " + response.statusCode());
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            } catch (org.json.JSONException e) {
                System.out.println("Failed to parse JSON response.");
                e.printStackTrace();
            }
        }).start();
    }



}




/*package controllers;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Nextgames {

    @FXML
    private WebView webview;

    public void searchPlayer(String playerName) {
        try {
            String baseUrl = "https://dbpedia.org/page/";
            String encodedPlayerName = URLEncoder.encode(playerName, StandardCharsets.UTF_8.toString());
            String fullUrl = baseUrl + encodedPlayerName;

            webview.getEngine().load(fullUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
*/
/*package controllers;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
public class Nextgames {
    @FXML
    private WebView webview;

    // This method is intended to fetch the competition standings and find the team's standing
    public void loadTeamStanding(String teamName, String apiKey) {
        HttpClient client = HttpClient.newHttpClient();
        // Use a valid competition ID; for example, "2021" for the Premier League
        String competitionId = "2021"; // Adjust as needed
        String apiUrl = "https://api.football-data.org/v2/competitions/" + competitionId + "/standings";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("X-Auth-Token", apiKey)
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(body -> {
                    // Here, you need logic to parse the JSON and find the team's standing
                    // For now, let's directly use the body to simulate this process
                    String htmlContent = formatStandingDataAsHtml(body, teamName); // Adjusted to use teamName
                    javafx.application.Platform.runLater(() -> webview.getEngine().loadContent(htmlContent));
                })
                .join(); // Ensure this operation doesn't freeze the UI; consider using CompletableFuture properly
    }

    // Combine the functionality of both formatStandingDataAsHtml methods for clarity

    private String formatStandingDataAsHtml(String standingsData, String teamName) {
        JsonObject jsonObj = JsonParser.parseString(standingsData).getAsJsonObject();
        JsonArray standingsArray = jsonObj.getAsJsonArray("standings");

        for (JsonElement standing : standingsArray) {
            JsonObject standingObj = standing.getAsJsonObject();
            if ("TOTAL".equals(standingObj.get("type").getAsString())) { // Check if it's the total standings
                JsonArray table = standingObj.getAsJsonArray("table");
                for (JsonElement teamElement : table) {
                    JsonObject teamObj = teamElement.getAsJsonObject();
                    JsonObject team = teamObj.getAsJsonObject("team");
                    if (teamName.equalsIgnoreCase(team.get("name").getAsString())) {
                        int position = teamObj.get("position").getAsInt();
                        // You can extract more information here as needed
                        return "<html><body>Position: " + position + " - " + teamName + "</body></html>";
                    }
                }
            }
        }
        return "<html><body>Team not found in standings.</body></html>";
    }
}
*/