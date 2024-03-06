package controllers;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Video {

    @FXML
    private WebView webview;

    private static final String API_KEY = "AIzaSyDxDaCWxRtl6uANa4qyDNgJO0BWF8-waKE"; // Place your API key here
    private static final String YOUTUBE_SEARCH_URL = "https://www.googleapis.com/youtube/v3/search";

    public void initialize() {
        // Initialization logic, if necessary
    }

    public void setTeamNameAndLoadVideos(String equipeNom) {
        try {
            String videoEmbedCode = fetchFirstVideoEmbedCode(equipeNom + " highlights");
            if (videoEmbedCode != null) {
                loadVideoInWebView(videoEmbedCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception (e.g., show an error message)
        }
    }

    private String fetchFirstVideoEmbedCode(String searchQuery) throws Exception {
        String encodedQuery = URLEncoder.encode(searchQuery, StandardCharsets.UTF_8);
        String requestUrl = YOUTUBE_SEARCH_URL + "?part=snippet&maxResults=1&q=" + encodedQuery + "&key=" + API_KEY;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestUrl))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Here, you should parse the JSON response to extract the video ID
        // For simplicity, this example won't cover JSON parsing
        String videoId = parseVideoIdFromResponse(response.body()); // You need to implement this method

        if (videoId != null) {
            return constructEmbedUrl(videoId);
        }
        return null;
    }

    private String constructEmbedUrl(String videoId) {
        return "<html><body><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + videoId + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";
    }

    private void loadVideoInWebView(String htmlContent) {
        webview.getEngine().loadContent(htmlContent);
    }

    // Example placeholder - implement JSON parsing to extract video ID
    private String parseVideoIdFromResponse(String jsonResponse) {
       /*  TODO: Parse the JSON response to extract the first video's ID
        return "VIDEO_ID_HERE";  Replace this with actual parsing logic*/
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode itemsNode = rootNode.path("items");
            if (!itemsNode.isEmpty()) {
                JsonNode firstItem = itemsNode.get(0);
                JsonNode videoIdNode = firstItem.path("id").path("videoId");
                return videoIdNode.asText();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}



//////////////////////////webview metier lmao
/*package controllers;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Video {

    @FXML
    private WebView webview;

    public void initialize() {
        // Initialization logic, if necessary
    }

    public void setTeamNameAndLoadVideos(String equipeNom) {
        String searchURL = constructYouTubeSearchURL(equipeNom);
        loadVideoInWebView(searchURL);
    }

    private String constructYouTubeSearchURL(String equipeNom) {
        String searchQuery = equipeNom + " latest games";
        String encodedQuery = URLEncoder.encode(searchQuery, StandardCharsets.UTF_8);
        return "https://www.youtube.com/results?search_query=" + encodedQuery;
    }

    private void loadVideoInWebView(String url) {
        webview.getEngine().load(url);
    }
}
*/