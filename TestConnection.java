package org.example;

public class TestConnection {
    public static void main(String[] args) {
        try {
            // Make sure APIConnection is set up correctly to use the Gemini API
            String testInput = "Hello!";
            String testResponse = APIConnection.getChatbotResponse(testInput);
            System.out.println("Test Response: " + testResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
