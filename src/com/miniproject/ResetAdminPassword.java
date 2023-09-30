package com.miniproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResetAdminPassword extends JFrame implements ActionListener {
    JLabel heading, userNameLabel, phoneLabel, backupCodeLabel, passwordLabel;
    JTextField usernameTF, phoneTF, backupCodeTF, passwordTF;
    JButton reset, cancel;
    String username, phone, backupCode, password;

    ResetAdminPassword() {
        heading = new JLabel("RESET ADMIN PASSWORD");
        heading.setBounds(100, 5, 300, 30);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 17));
        heading.setForeground(Color.BLUE);
        add(heading);

        userNameLabel = new JLabel("USERNAME: ");
        userNameLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        userNameLabel.setBounds(50, 50, 100, 30);
        add(userNameLabel);

        usernameTF = new JTextField();
        usernameTF.setBounds(180, 50, 150, 30);
        add(usernameTF);

        phoneLabel = new JLabel("PHONE: ");
        phoneLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        phoneLabel.setBounds(50, 90, 100, 30);
        add(phoneLabel);

        phoneTF = new JTextField();
        phoneTF.setBounds(180, 90, 150, 30);
        add(phoneTF);

        backupCodeLabel = new JLabel("BACKUP CODE: ");
        backupCodeLabel.setBounds(50, 130, 150, 30);
        backupCodeLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        add(backupCodeLabel);

        backupCodeTF = new JTextField();
        backupCodeTF.setBounds(180, 130, 150, 30);
        add(backupCodeTF);

        passwordLabel = new JLabel("PASSWORD: ");
        passwordLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        passwordLabel.setBounds(50, 170, 100, 30);
        add(passwordLabel);

        passwordTF = new JTextField();
        passwordTF.setBounds(180, 170, 150, 30);
        add(passwordTF);

        reset = new JButton("RESET");
        reset.setBounds(40, 220, 120, 30);
        reset.setForeground(Color.WHITE);
        reset.setBackground(Color.BLACK);
        reset.addActionListener(this);
        add(reset);

        cancel = new JButton("CANCEL");
        cancel.setBounds(225, 220, 120, 30);
        cancel.setForeground(Color.WHITE);
        cancel.setBackground(Color.BLACK);
        cancel.addActionListener(this);
        add(cancel);

        setLayout(null);
        setIconImage(Common.frameIcon.getImage());
        getContentPane().setBackground(Color.WHITE);
        setBounds(300, 100, 400, 300);
        setVisible(true);
    }

    void resetPassword() {
        String userId = "", phoneDB = "", backupDB = "";
        username = usernameTF.getText();
        phone = phoneTF.getText();
        backupCode = backupCodeTF.getText();
        Boolean userNameFound = false, verified = false;
        if (!username.equals("")) {
            String checkUserName = "SELECT USERID FROM USERS WHERE USERNAME = '" + username + "' AND USERTYPE = 'ADMIN'";
            Conn conn = new Conn();
            try {
                ResultSet userIdRs = conn.statement.executeQuery(checkUserName);
                if (userIdRs.next()) {
                    userId = userIdRs.getString(1);
                    userNameFound = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (userNameFound) {
                String verificationQuery = "SELECT PHONE, BACKUPCODE FROM ADMINISTRATOR WHERE USERID = '" + userId + "'";
                try {
                    ResultSet verificationRS = conn.statement.executeQuery(verificationQuery);
                    if (verificationRS.next()) {
                        phoneDB = verificationRS.getString(1);
                        backupDB = verificationRS.getString(2);
                        if (phoneDB.equals(phone) && backupDB.equals(backupCode)) {
                            verified = true;
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (verified) {
                password = passwordTF.getText();
                if (!password.equals("")) {
                    String updateQuery = "UPDATE USERS SET PASSWORD = '" + password + "' WHERE USERNAME = '" + username + "'";
                    try {
                        conn.statement.executeUpdate(updateQuery);
                        JOptionPane.showMessageDialog(null, "Updated Successfully");
                    } catch (SQLException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, "An Error Occurred");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "User Not Found!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please Enter username!");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == reset) {
            resetPassword();
            this.setVisible(false);
        } else if (e.getSource() == cancel) {
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new ResetAdminPassword();
    }
}
