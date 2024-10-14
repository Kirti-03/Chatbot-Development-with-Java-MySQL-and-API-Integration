package org.example;
import okhttp3.*;
import java.io.IOException;
public class APIConnection {
    private static final String API_KEY = "your api key";  // Replace with your actual API key
    private static final String API_URL = "api_url";

    public static String getChatbotResponse(String userInput) throws IOException {
        OkHttpClient client = new OkHttpClient();

        // Create the JSON payload for the POST request
        String jsonPayload = "{ \"contents\": [{\"role\": \"user\", \"parts\": [{\"text\": \"" + userInput + "\"}]}]}";

        // Create the request body
        RequestBody body = RequestBody.create(jsonPayload, MediaType.parse("application/json"));

        // Build the request
        Request request = new Request.Builder()
                .url(API_URL + "?key=" + API_KEY)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        // Execute the request and get the response
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response + ". Response body: " + response.body().string());
            }

            // Ensure response body is not null
            if (response.body() != null) {
                String responseBody = response.body().string();
                return extractText(responseBody); // Returns the API's response as a string
            } else {
                throw new IOException("Response body is null");
            }
        }
    }
    private static String extractText(String responseBody) {
        // Simple string operations to extract the text
        // This approach is fragile and assumes the response format is consistent
        String startMarker = "\"text\": \"";
        String endMarker = "\"";

        int startIndex = responseBody.indexOf(startMarker);
        if (startIndex == -1) return "Text not found";
        startIndex += startMarker.length();

        int endIndex = responseBody.indexOf(endMarker, startIndex);
        if (endIndex == -1) return "Text not found";

        return responseBody.substring(startIndex, endIndex);
    }
}



