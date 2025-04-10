//test page
package com.assemblers.app.UI;
import javax.swing.*;

import com.assemblers.app.APIController.EmployeeInfo;
import com.assemblers.app.APIController.UserLogin;
import com.assemblers.app.Models.Employee;
import com.assemblers.app.Models.User;

import java.awt.*;
import java.awt.event.*;

public class LoginForm extends JFrame implements ActionListener{

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel messageLabel;
    private CardLayout cardLayout;
    private JPanel mainPanel, panel;

    public LoginForm() {
        setTitle("Login Form");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();

        mainPanel = new JPanel(cardLayout);

        panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image backgroundImage = new ImageIcon("../background.png").getImage();
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } catch (Exception e) {
                    e.printStackTrace(); // Handle any exceptions related to image loading
                }
            }
        };

        panel.setLayout(null);

        JLabel welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setFont(new Font("Monospaced", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBounds(150, 180, 250, 30);
        panel.add(welcomeLabel);

        JLabel instructionLabel = new JLabel("Sign in to continue");
        instructionLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
        instructionLabel.setForeground(Color.WHITE);
        instructionLabel.setBounds(120, 210, 200, 20);
        panel.add(instructionLabel);

        usernameField = new JTextField();
        usernameField.setBounds(50, 320, 300, 40);
        usernameField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        usernameField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        usernameField.setBackground(new Color(0, 0, 0, 120));  // Semi-transparent dark background
        usernameField.setForeground(Color.WHITE);  // White text color
        usernameField.setCaretColor(Color.WHITE);  // White caret
        panel.add(usernameField);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(50, 295, 100, 20);
        usernameLabel.setForeground(Color.WHITE);
        panel.add(usernameLabel);

        // Create a glowing effect for the password field
        passwordField = new JPasswordField();
        passwordField.setBounds(50, 390, 300, 40);
        passwordField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        passwordField.setBackground(new Color(0, 0, 0, 120));  // Semi-transparent dark background
        passwordField.setForeground(Color.WHITE);  // White text color
        passwordField.setCaretColor(Color.WHITE);  // White caret
        panel.add(passwordField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(50, 365, 100, 20);
        passwordLabel.setForeground(Color.WHITE);
        panel.add(passwordLabel);

        loginButton = new JButton("Login");
        loginButton.setBounds(160, 460, 80, 40);
        loginButton.setBackground(new Color(70, 130, 180)); // Steel blue color
        loginButton.setForeground(Color.BLACK);
        loginButton.setFont(new Font("Monospaced", Font.PLAIN, 12));
        panel.add(loginButton);
        
        messageLabel = new JLabel("", JLabel.CENTER);
        messageLabel.setFont(new Font("Monospaced", Font.PLAIN, 9));
        messageLabel.setForeground(Color.RED); // Red color for error messages
        messageLabel.setBounds(-30, 420, 300, 30);
        panel.add(messageLabel);


        JLabel forgotPasswordLabel = new JLabel("Forgot Password?");
        forgotPasswordLabel.setBounds(240, 435, 120, 20);
        forgotPasswordLabel.setForeground(Color.WHITE);
        forgotPasswordLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forgotPasswordLabel.setFont(new Font("Monospaced", Font.PLAIN, 12));
        panel.add(forgotPasswordLabel);

        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.setBounds(130, 520, 140, 30);
        createAccountButton.setForeground(Color.BLACK);
        createAccountButton.setBackground(new Color(70, 130, 180));
        createAccountButton.setFocusPainted(false);
        createAccountButton.setFont(new Font("Monospaced", Font.PLAIN, 12));
        panel.add(createAccountButton);

        EmployeePage workerPage = new EmployeePage(this);
        mainPanel.add(panel, "Login Screen");
        mainPanel.add(workerPage, "Employee Page");

        add(mainPanel);
        setVisible(true);
        // Login button action
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginAction();
            }
        });
    }
    
    private void loginAction() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        
        User user = UserLogin.login(username, password);

        if (user != null) {
            if (user.getRole() == 1) {
                dispose();
                openAdminPage();  
            } else if (user.getRole() == 0) {
                dispose();
                cardLayout.show(mainPanel, "Employee Page");
                openEmployeePage(EmployeeInfo.viewEmployeeInfoById(user.getEmpid()));
            }
        } else {
            messageLabel.setText("Invalid username or password.");
        }
        
    }

    private void openAdminPage() {
        // For now, just show a message (you can later expand it)
        JOptionPane.showMessageDialog(this, "Welcome Admin! This page is under construction.");
    }

    private void openEmployeePage(Employee employee) {
        StringBuilder details = new StringBuilder();
        details.append("Employee ID: ").append(employee.getEmpid()).append("\n");
        details.append("Name: ").append(employee.getFname()).append(" ").append(employee.getLname()).append("\n");
        details.append("Job Title: ").append(employee.getJobTitle()).append("\n");
        details.append("Pay Date: ").append(employee.getPayDate() != null ? employee.getPayDate().toString() : "N/A").append("\n");
        details.append("Earnings: ").append(employee.getEarnings()).append("\n");
        details.append("Federal Tax: ").append(employee.getFedTax()).append("\n");
        details.append("Federal Medicare: ").append(employee.getFedMed()).append("\n");
        details.append("Federal Social Security: ").append(employee.getFedSS()).append("\n");
        details.append("State Tax: ").append(employee.getStateTax()).append("\n");
        details.append("401K Retirement: ").append(employee.getRetire401K()).append("\n");
        details.append("Health Care: ").append(employee.getHealthCare()).append("\n");
    
        JOptionPane.showMessageDialog(this, details.toString(), "Employee Details", JOptionPane.INFORMATION_MESSAGE);

        // Switch to employee page
        cardLayout.show(panel, "Employee Page");
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        // if (e.getSource() == loginButton){
        //     System.out.println("Page Switch");
        //     cardLayout.show(panel, "Employee Page");
        // }
    }
    
}
