package com.miniproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditFaculty extends JFrame implements ActionListener {
    JLabel factIdLabel, nameLabel, nameVal, deptLabel, deptVal;
    JButton fetchInfoBtn, cancelBtn, deleteBtn;
    JTextField factIdTF;
    String factId;

    EditFaculty() {
        factIdLabel = new JLabel("ENTER FACULTY ID: ");
        factIdLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        factIdLabel.setBounds(40, 10, 200, 30);
        add(factIdLabel);

        factIdTF = new JTextField();
        factIdTF.setBounds(250, 10, 150, 30);
        add(factIdTF);

        fetchInfoBtn = new JButton("Fetch Info");
        fetchInfoBtn.setBackground(Color.BLACK);
        fetchInfoBtn.setForeground(Color.WHITE);
        fetchInfoBtn.setBounds(450, 10, 150, 30);
        fetchInfoBtn.addActionListener(this);
        add(fetchInfoBtn);

        nameLabel = new JLabel("NAME: ");
        nameLabel.setBounds(40, 50, 100, 30);
        nameLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(nameLabel);

        nameVal = new JLabel();
        nameVal.setBounds(250, 50, 200, 30);
        nameVal.setFont(new Font("Tahoma", Font.PLAIN, 15));
        add(nameVal);

        deptLabel = new JLabel("DEPARTMENT: ");
        deptLabel.setBounds(40, 90, 150, 30);
        deptLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(deptLabel);

        deptVal = new JLabel();
        deptVal.setBounds(250, 90, 300, 30);
        deptVal.setFont(new Font("Tahoma", Font.PLAIN, 15));
        add(deptVal);

        deleteBtn = new JButton("DELETE");
        deleteBtn.setBounds(40, 150, 150, 30);
        deleteBtn.setBackground(Color.BLACK);
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.addActionListener(this);
        add(deleteBtn);

        cancelBtn = new JButton("CANCEL");
        cancelBtn.setBounds(450, 150, 150, 30);
        cancelBtn.setBackground(Color.BLACK);
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.addActionListener(this);
        add(cancelBtn);


        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setIconImage(Common.frameIcon.getImage());
        setBounds(400, 200, 650, 240);
        setVisible(true);
    }

    void fetchDetails() {
        this.factId = factIdTF.getText();
        if (!factId.equals("")) {
            String query = "SELECT * FROM FACULTY WHERE FACTID = '" + factId + "'";
            Conn conn = new Conn();
            try {
                ResultSet resultSet = conn.statement.executeQuery(query);
                if (resultSet.next()) {
                    String name = resultSet.getString(2);
                    String dept = resultSet.getString(4);

                    nameVal.setText(name);
                    deptVal.setText(dept);
                } else {
                    JOptionPane.showMessageDialog(null, "Faculty Not Found");

                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "SQL Error Occurred");
            }
        }
    }

    void deleteFaculty() {

        String userIdQuery = "SELECT USERID FROM FACULTY WHERE FACTID = '" + this.factId + "'";
        Conn conn = new Conn();
        try {
            String userId;
            ResultSet rs = conn.statement.executeQuery(userIdQuery);
            rs.next();
            userId = rs.getString(1);
            String query = "DELETE FROM USERS WHERE USERID = " + userId + "";
            conn.statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Faculty Deleted Successfully");
            this.setVisible(false);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "SQL Error Occurred");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fetchInfoBtn) {
            fetchDetails();
        } else if (e.getSource() == deleteBtn) {
            int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete details of " + this.factId + "?", "Confirmation", JOptionPane.YES_NO_OPTION);
            System.out.println(confirmation);
            if (confirmation == 0) {
                deleteFaculty();
            }
        } else if (e.getSource() == cancelBtn) {
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new EditFaculty();
    }
}
