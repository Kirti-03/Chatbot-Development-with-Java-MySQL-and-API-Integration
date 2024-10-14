package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
public class Home extends JFrame implements ActionListener {

    JTextField username = new JTextField("username");
    JLabel vieew = new JLabel();
    JTextField user_email = new JTextField("user email");
    JButton submit = new JButton("Submit");

    Home() {
        // Frame settings
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600); // Adjusted size for better view
        this.setTitle("Registration window");

        // Set layout to null to manually position components
        this.setLayout(null);
        JPanel panel = new JPanel();
        panel.setLayout(null); // Use absolute layout
        panel.setBackground(new Color(200, 230, 255)); // Panel background color
        panel.setBounds(100, 100, 600, 400);

        // Label (Title or View)
        vieew.setBounds(100, 20, 200, 40);// Adjusted size and position
        vieew.setHorizontalAlignment(SwingConstants.CENTER); // Align text to center
        vieew.setFont(new Font("Arial", Font.BOLD, 36)); // Make the title text larger
        vieew.setText("Login Page");
        vieew.setForeground(Color.DARK_GRAY);
        // Username field
        username.setBounds(100, 100, 300, 40);  // Adjusted for better spacing
        username.setBackground(new Color(255, 255, 255));

        // Email field
        user_email.setBounds(100, 160, 300, 40);  // Positioned below username
        user_email.setBackground(new Color(255, 255, 255));

        // Submit button
        submit.setBounds(150, 220, 200, 40);  // Centered relative to fields
        submit.setBackground(new Color(76, 175, 80));
        submit.addActionListener(this);

        panel.add(vieew);
        panel.add(username);
        panel.add(user_email);
        panel.add(submit);

        // Add panel to the frame
        this.add(panel);

        // Background color for the entire frame
        this.getContentPane().setBackground(new Color(245, 222, 179));

        // Set icon image
        ImageIcon icon = new ImageIcon("C:/Users/HP/IdeaProjects/CHATBOTT_GRADLE/src/main/java/org/example/CHATBOT.jpg");
        this.setIconImage(icon.getImage());

        // Display the frame
        this.setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            String userName = username.getText().trim();
            String userEmail = user_email.getText().trim();
            try {
                // Check if the user already exists
                if (userServices.isUserRegistered(userEmail)) {
                    // If user exists, redirect to the chat window
                    JOptionPane.showMessageDialog(this, "User already registered. Redirecting to chat.");
                    this.dispose();
                    Display chatWindow = new Display(userEmail);
                    chatWindow.setVisible(true);
                } else {
                    // If user doesn't exist, register them
                    userServices.registerUser(userName, userEmail);
                    JOptionPane.showMessageDialog(this, "User registered successfully.");
                    this.dispose();
                    Display chatWindow = new Display(userEmail);
                    chatWindow.setVisible(true);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error registering user: " + ex.getMessage());
            }
        }
    }
}


