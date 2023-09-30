package com.miniproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnrollClass extends JFrame implements ActionListener {
    JLabel headingLabel, subCodeLabel, subTitleLabel, subTitleVal, semLabel;
    JButton fetchInfoBtn, assignBtn, cancelBtn;
    JTextField subCodeTF;
    JComboBox semCB;
    Boolean fetched = false;
    String subCode, sem;

    EnrollClass() {

        headingLabel = new JLabel("ENROLL CLASS FOR SUBJECT");
        headingLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        headingLabel.setForeground(Color.BLUE);
        headingLabel.setBounds(215, 5, 400, 30);
        add(headingLabel);

        subCodeLabel = new JLabel("Subject Code: ");
        subCodeLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        subCodeLabel.setBounds(40, 50, 200, 30);
        add(subCodeLabel);

        subCodeTF = new JTextField();
        subCodeTF.setBounds(250, 50, 150, 30);
        add(subCodeTF);

        fetchInfoBtn = new JButton("Fetch Info");
        fetchInfoBtn.setBackground(Color.BLACK);
        fetchInfoBtn.setForeground(Color.WHITE);
        fetchInfoBtn.setBounds(450, 50, 150, 30);
        fetchInfoBtn.addActionListener(this);
        add(fetchInfoBtn);

        subTitleLabel = new JLabel("Subject Title: ");
        subTitleLabel.setBounds(40, 90, 150, 30);
        subTitleLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(subTitleLabel);

        subTitleVal = new JLabel();
        subTitleVal.setBounds(250, 90, 350, 30);
        subTitleVal.setFont(new Font("Tahoma", Font.PLAIN, 15));
        add(subTitleVal);

        String semesters[] = {"-", "1", "2", "3", "4", "5", "6", "7", "8"};

        semLabel = new JLabel("Semester: ");
        semLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        semLabel.setBounds(40, 130, 150, 30);
        add(semLabel);

        semCB = new JComboBox(semesters);
        semCB.setBounds(250, 130, 50, 30);
        add(semCB);

        assignBtn = new JButton("ASSIGN");
        assignBtn.setBounds(40, 200, 100, 30);
        assignBtn.setBackground(Color.BLACK);
        assignBtn.setForeground(Color.WHITE);
        assignBtn.addActionListener(this);
        add(assignBtn);

        cancelBtn = new JButton("CANCEL");
        cancelBtn.setBounds(500, 200, 100, 30);
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setBackground(Color.BLACK);
        cancelBtn.addActionListener(this);
        add(cancelBtn);

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setIconImage(Common.frameIcon.getImage());
        setBounds(400, 200, 650, 300);
        setVisible(true);
    }

    void fetchDetails() {
        this.subCode = subCodeTF.getText();
        if (!subCode.equals("")) {
            String query = "SELECT NAME FROM SUBJECT WHERE SUBCODE = '" + subCode + "'";
            Conn conn = new Conn();
            try {
                ResultSet resultSet = conn.statement.executeQuery(query);
                if (resultSet.next()) {
                    subTitleVal.setText(resultSet.getString(1));
                    fetched = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Subject Not Found!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "An Error Occurred");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please Enter Subject Code");
        }
    }

    void enrollClass() {
        this.sem = String.valueOf(semCB.getSelectedItem());
        if (fetched && !sem.equals("-")) {
            String query = "INSERT INTO ENROLLED VALUES('" + subCode + "','" + sem + "')";
            Conn conn = new Conn();
            try {
                conn.statement.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Class Enrolled Successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "An Error Occurred");
            }
        } else if (!fetched) {
            JOptionPane.showMessageDialog(null, "Please Fetch Details of Subject and Verify");
        } else if (sem.equals("-")) {
            JOptionPane.showMessageDialog(null, "Please Select a semester");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fetchInfoBtn) {
            fetchDetails();
        } else if (e.getSource() == assignBtn) {
            enrollClass();
        } else if (e.getSource() == cancelBtn) {
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new EnrollClass();
    }
}
