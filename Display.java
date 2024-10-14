package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.example.APIConnection.getChatbotResponse;

public class Display extends JFrame implements ActionListener {
    private String userEmail; // Store user's email
    JTextArea area = new JTextArea();
    JTextField question = new JTextField();
    JButton upload = new JButton(new ImageIcon("C:/Users/HP/IdeaProjects/CHATBOTT_GRADLE/src/main/java/org/example/send.png"));

    public Display(String userEmail) { // Constructor accepting email
        this.userEmail = userEmail;

        this.setSize(1200, 800);
        this.setTitle("ChatBot");
        this.setResizable(true);
        this.setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);


        this.getContentPane().setBackground(new Color(0, 0, 0));

        area.setBackground(new Color(128, 128, 128));
        area.setBounds(20, 20, 1160, 650);
        area.setLineWrap(true);          // Enables line wrapping
        area.setWrapStyleWord(true);
        area.setEditable(false);  // Make JTextArea non-editable

        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setBounds(20, 20, 1160, 650);  // Set bounds for the JScrollPane
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Only allow vertical scrolling
        this.add(scrollPane);

        question.setBounds(20, 680, 1040, 40);
        question.setBackground(new Color(255, 255, 255));

        upload.setBounds(1060, 680, 120, 40);  // Adjusted size and position
        upload.setVisible(true);
        upload.addActionListener(this);

        this.add(question);
        this.add(upload);
        this.setVisible(true);
        this.setLocationRelativeTo(null); // Center the window
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == upload) {
            String userInput = question.getText().trim();
            if (!userInput.isEmpty()) {
                area.append("You: " + userInput + "\n");
                String response;
                try {

                    response = getChatbotResponse(userInput);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    response = "Error fetching response.";
                }
                area.append("Chatbot: " + response + "\n");
                question.setText("");

                // Save conversation history
                saveConversation(userEmail, userInput, "user"); // Log user message
                saveConversation(userEmail, response, "bot");   // Log bot response
            }
        }
    }




    // Method to save conversation history
    private void saveConversation(String userEmail, String message, String sender) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try {
                String query = "INSERT INTO Conversation_History (User_id, Message, Sender) " +
                        "VALUES ((SELECT User_id FROM Users WHERE User_email = ?), ?, ?)";
                try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                    pstmt.setString(1, userEmail);
                    pstmt.setString(2, message);
                    pstmt.setString(3, sender);  // 'user' or 'bot'
                    pstmt.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error saving conversation: " + e.getMessage());
            }
        }
    }
}




