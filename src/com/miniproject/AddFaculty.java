package com.miniproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddFaculty extends JFrame implements ActionListener {

    JTextField nameTF, factIdTF, usernameTF;
    JPasswordField passwordTF;
    JComboBox departmentCB;
    JButton addButton, cancelButton;

    AddFaculty() {
        JLabel nameLabel = new JLabel("NAME");
        nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        nameLabel.setBounds(60, 30, 120, 30);
        add(nameLabel);

        nameTF = new JTextField();
        nameTF.setBounds(200, 30, 150, 30);
        add(nameTF);

        JLabel factIdLabel = new JLabel("FACULTY ID");
        factIdLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        factIdLabel.setBounds(60, 70, 120, 30);
        add(factIdLabel);

        factIdTF = new JTextField();
        factIdTF.setBounds(200, 70, 150, 30);
        add(factIdTF);

        String departments[] = {
                "Computer Science and Engineering",
                "Information Science and Engineering",
                "Electronics and Communication Engineering",
                "Instrumentation and Electrical Engineering"
        };

        JLabel departmentLabel = new JLabel("DEPARTMENT");
        departmentLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        departmentLabel.setBounds(60, 110, 120, 30);
        add(departmentLabel);

        departmentCB = new JComboBox<>(departments);
        departmentCB.setBounds(200, 110, 280, 30);
        add(departmentCB);

        JLabel usernameLabel = new JLabel("USERNAME");
        usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        usernameLabel.setBounds(60, 150, 120, 30);
        add(usernameLabel);

        usernameTF = new JTextField();
        usernameTF.setBounds(200, 150, 150, 30);
        add(usernameTF);

        JLabel passwordLabel = new JLabel("PASSWORD");
        passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        passwordLabel.setBounds(60, 190, 120, 30);
        add(passwordLabel);

        passwordTF = new JPasswordField();
        passwordTF.setBounds(200, 190, 150, 30);
        add(passwordTF);

        ImageIcon facultyIcon = new ImageIcon(ClassLoader.getSystemResource("com/miniproject/icons/faculty.png"));
        JLabel facultyImageLabel = new JLabel(facultyIcon);
        facultyImageLabel.setBounds(500, 35, 200, 200);
        add(facultyImageLabel);

        JLabel frameLabel = new JLabel("ENTER FACULTY DETAILS");
        frameLabel.setBounds(495, 10, 250, 30);
        frameLabel.setForeground(Color.BLUE);
        frameLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(frameLabel);

        addButton = new JButton("ADD FACULTY");
        addButton.setBounds(60, 250, 130, 30);
        addButton.setBackground(Color.BLACK);
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(this);
        add(addButton);

        cancelButton = new JButton("CANCEL");
        cancelButton.setBounds(210, 250, 100, 30);
        cancelButton.setBackground(Color.BLACK);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(this);
        add(cancelButton);

        setLayout(null);
        setIconImage(Common.frameIcon.getImage());
        getContentPane().setBackground(Color.WHITE);
        setBounds(400, 200, 750, 400);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String name = nameTF.getText();
            String factId = factIdTF.getText();
            String username = usernameTF.getText();
            String password = String.valueOf(passwordTF.getPassword());
            String department = String.valueOf(departmentCB.getSelectedItem());

            Conn conn = new Conn();

            String userQuery = "INSERT INTO USERS(USERNAME, PASSWORD, USERTYPE) VALUES ('" + username + "', '" + password + "', 'FACULTY')";
            try {
                conn.statement.executeUpdate(userQuery);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            String userIDQuery = "SELECT USERID FROM USERS WHERE USERNAME = '" + username + "'";
            int userId = 1;
            try {
                ResultSet userIdRS = conn.statement.executeQuery(userIDQuery);
                userIdRS.next();
                String id = userIdRS.getString(1);
                userId = Integer.parseInt(id);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            String facultyQuery = "INSERT INTO FACULTY VALUES('" + userId + "', '" + name + "', '" + factId + "', '" + department + "')";
            try {
                conn.statement.executeUpdate(facultyQuery);
                JOptionPane.showMessageDialog(null, "New Faculty Added");
                this.setVisible(false);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } else if (e.getSource() == cancelButton) {
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new AddFaculty();
    }

}
