package com.miniproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame implements ActionListener {
    JLabel label1, label2, forgotPassword;
    JTextField textField;
    JPasswordField passwordField;
    JButton loginBtn, cancelBtn;

    Login() {
        label1 = new JLabel("Username");
        label1.setBounds(40, 20, 100, 30);
        add(label1);

        label2 = new JLabel("Password");
        label2.setBounds(40, 70, 100, 30);
        add(label2);

        textField = new JTextField();
        textField.setBounds(150, 20, 150, 30);
        add(textField);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 70, 150, 30);
        add(passwordField);

        loginBtn = new JButton("Login");
        loginBtn.setBackground(Color.BLACK);
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setBounds(40, 120, 120, 30);
        loginBtn.addActionListener(this);
        add(loginBtn);

        cancelBtn = new JButton("Cancel");
        cancelBtn.setBackground(Color.BLACK);
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setBounds(180, 120, 120, 30);
        cancelBtn.addActionListener(this);
        add(cancelBtn);

        forgotPassword = new JLabel("FORGOT PASSWORD?");
        forgotPassword.setForeground(Color.BLUE.darker());
        forgotPassword.setBounds(170, 160, 150, 20);
        forgotPassword.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        forgotPassword.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                resetPrompt();
            }
        });
        add(forgotPassword);

        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("com/miniproject/icons/login.png"));
        Image image = icon.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        ImageIcon scaledIcon = new ImageIcon(image);
        JLabel label3 = new JLabel(scaledIcon);
        label3.setBounds(350, 5, 200, 200);
        add(label3);

        getContentPane().setBackground(Color.WHITE);

        setLayout(null);
        setBounds(500, 300, 600, 250);
        setIconImage(Common.frameIcon.getImage());
        setVisible(true);
    }

    void resetPrompt() {
        int confirmation = JOptionPane.showConfirmDialog(this, "ONLY ADMIN CAN RESET THEIR PASSWORD\n         Are you an administrator?", "Confrmationi", JOptionPane.YES_NO_OPTION);
        System.out.println(confirmation);
        if (confirmation == 0) {
            new ResetAdminPassword();
        } else {
            JOptionPane.showMessageDialog(null, "To reset you password contact your administrator");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginBtn) {
            String username = textField.getText();
            String password = String.valueOf(passwordField.getPassword());
            Conn conn = new Conn();

            String query = "select * from users where username = '" + username + "' and password = '" + password + "'";
            try {
                ResultSet resultSet = conn.statement.executeQuery(query);

                if (resultSet.next()) {
                    String userType;
                    String userTypeQuery = "SELECT USERTYPE, USERID FROM USERS WHERE USERNAME = '" + username + "'";

                    ResultSet userTypeSet = conn.statement.executeQuery(userTypeQuery);
                    userTypeSet.next();
                    userType = userTypeSet.getString(1);
                    Common.userId = userTypeSet.getString(2);
                    Common.userName = username;

                    if (userType.equals("ADMIN")) {
                        new AdminDashboard();
                        this.setVisible(false);
                    } else if (userType.equals("STUDENT")) {
                        new StudentDashboard();
                        this.setVisible(false);
                    } else if (userType.equals("FACULTY")) {
                        new FacultyDashboard();
                        this.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid User Type!");
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == cancelBtn) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
