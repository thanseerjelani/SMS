package com.miniproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ResetUserPassword extends JFrame implements ActionListener {
    JLabel heading, userNameLabel, passwordLabel, userTypeLabel;
    JTextField usernameTF, passwordTF;
    JButton reset, cancel;
    JComboBox userTypeCB;
    String username, userType, password;
    String userTypes[] = {"FACULTY", "STUDENT"};

    ResetUserPassword() {
        heading = new JLabel("RESET USER PASSWORD");
        heading.setBounds(100, 5, 300, 30);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 17));
        heading.setForeground(Color.BLUE);
        add(heading);

        userTypeLabel = new JLabel("USERTYPE: ");
        userTypeLabel.setBounds(50, 50, 100, 30);
        userTypeLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        add(userTypeLabel);

        userTypeCB = new JComboBox(userTypes);
        userTypeCB.setBounds(180, 50, 100, 30);
        add(userTypeCB);

        userNameLabel = new JLabel("USERNAME: ");
        userNameLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        userNameLabel.setBounds(50, 90, 100, 30);
        add(userNameLabel);

        usernameTF = new JTextField();
        usernameTF.setBounds(180, 90, 150, 30);
        add(usernameTF);

        passwordLabel = new JLabel("PASSWORD: ");
        passwordLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        passwordLabel.setBounds(50, 130, 100, 30);
        add(passwordLabel);

        passwordTF = new JTextField();
        passwordTF.setBounds(180, 130, 150, 30);
        add(passwordTF);

        reset = new JButton("RESET");
        reset.setBounds(40, 180, 120, 30);
        reset.setForeground(Color.WHITE);
        reset.setBackground(Color.BLACK);
        reset.addActionListener(this);
        add(reset);

        cancel = new JButton("CANCEL");
        cancel.setBounds(225, 180, 120, 30);
        cancel.setForeground(Color.WHITE);
        cancel.setBackground(Color.BLACK);
        cancel.addActionListener(this);
        add(cancel);

        setLayout(null);
        setIconImage(Common.frameIcon.getImage());
        getContentPane().setBackground(Color.WHITE);
        setBounds(300, 100, 400, 270);
        setVisible(true);
    }

    void resetPassword() {
        userType = String.valueOf(userTypeCB.getSelectedItem());
        username = usernameTF.getText();
        password = passwordTF.getText();
        String updateQuery = "UPDATE USERS SET PASSWORD ='" + password + "' WHERE USERNAME = '" + username + "' AND USERTYPE = '" + userType + "'";
        Conn conn = new Conn();
        try {
            conn.statement.executeUpdate(updateQuery);
            JOptionPane.showMessageDialog(null, "Password Updated Successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An Error Occurred");
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == reset) {
            resetPassword();
        } else if (e.getSource() == cancel) {
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new ResetUserPassword();
    }
}
