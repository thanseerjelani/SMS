package com.miniproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AssignSubject extends JFrame implements ActionListener {
    JLabel headingLabel, subCodeLabel, factIdLabel, subTitleLabel, factNameLabel, factNameVal, subTitleVal, semSecLabel;
    JTextField subCodeTF, factIdTF;
    JButton fetchInfoBtn, assignBtn, cancelBtn;
    JComboBox semCB, secCB;
    String subCode, factId;
    Boolean fetched = false, factOk = false, subOk = false;

    AssignSubject() {
        headingLabel = new JLabel("ASSIGN SUBJECT TO FACULTY");
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

        factIdLabel = new JLabel("Faculty ID: ");
        factIdLabel.setBounds(40, 90, 200, 30);
        factIdLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(factIdLabel);

        factIdTF = new JTextField();
        factIdTF.setBounds(250, 90, 150, 30);
        add(factIdTF);

        fetchInfoBtn = new JButton("Fetch Info");
        fetchInfoBtn.setBackground(Color.BLACK);
        fetchInfoBtn.setForeground(Color.WHITE);
        fetchInfoBtn.setBounds(450, 70, 150, 30);
        fetchInfoBtn.addActionListener(this);
        add(fetchInfoBtn);

        subTitleLabel = new JLabel("Subject Title: ");
        subTitleLabel.setBounds(40, 130, 150, 30);
        subTitleLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(subTitleLabel);

        subTitleVal = new JLabel();
        subTitleVal.setBounds(250, 130, 350, 30);
        subTitleVal.setFont(new Font("Tahoma", Font.PLAIN, 15));
        add(subTitleVal);

        factNameLabel = new JLabel("Faculty Name: ");
        factNameLabel.setBounds(40, 170, 150, 30);
        factNameLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(factNameLabel);

        factNameVal = new JLabel();
        factNameVal.setBounds(250, 170, 250, 30);
        factNameVal.setFont(new Font("Tahoma", Font.PLAIN, 15));
        add(factNameVal);

        String semesters[] = {"-", "1", "2", "3", "4", "5", "6", "7", "8"};
        String sections[] = {"-", "A", "B", "C", "D", "E"};

        semSecLabel = new JLabel("Semester-Section: ");
        semSecLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        semSecLabel.setBounds(40, 210, 150, 30);
        add(semSecLabel);

        semCB = new JComboBox(semesters);
        semCB.setBounds(250, 210, 50, 30);
        add(semCB);

        secCB = new JComboBox(sections);
        secCB.setBounds(320, 210, 50, 30);
        add(secCB);

        assignBtn = new JButton("ASSIGN");
        assignBtn.setBounds(40, 270, 100, 30);
        assignBtn.setBackground(Color.BLACK);
        assignBtn.setForeground(Color.WHITE);
        assignBtn.addActionListener(this);
        add(assignBtn);

        cancelBtn = new JButton("CANCEL");
        cancelBtn.setBounds(500, 270, 100, 30);
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setBackground(Color.BLACK);
        cancelBtn.addActionListener(this);
        add(cancelBtn);

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setIconImage(Common.frameIcon.getImage());
        setBounds(400, 200, 650, 370);
        setVisible(true);
    }

    void fetchDetails() {
        this.subCode = subCodeTF.getText();
        this.factId = factIdTF.getText();
        if (!subCode.equals("") && !factId.equals("")) {
            String subjectQuery = "SELECT NAME FROM SUBJECT WHERE SUBCODE = '" + subCode + "'";
            String facultyQuery = "SELECT NAME FROM FACULTY WHERE FACTID = '" + factId + "'";
            Conn conn = new Conn();
            try {
                ResultSet subResult = conn.statement.executeQuery(subjectQuery);
                if (subResult.next()) {
                    subTitleVal.setText(subResult.getString(1));
                    subOk = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Subject Not Found");
                    subTitleVal.setText("");
                }
                ResultSet factResult = conn.statement.executeQuery(facultyQuery);
                if (factResult.next()) {
                    factNameVal.setText(factResult.getString(1));
                    factOk = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Faculty Not Found");
                    factNameVal.setText("");
                }
                if (factOk && subOk) {
                    fetched = true;
                }
                System.out.println(fetched);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            JOptionPane.showMessageDialog(null, "Please Enter Subject code and Faculty ID to Fetch");
        }
    }

    void assignSubject() {
        String sem = String.valueOf(semCB.getSelectedItem());
        String sec = String.valueOf(secCB.getSelectedItem());

        if (fetched && !sem.equals("-") && !sec.equals("-")) {
            String query = "INSERT INTO ASSIGNED VALUES('" + factId + "','" + subCode + "','" + sem + "','" + sec + "')";
            Conn conn = new Conn();
            try {
                conn.statement.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Subject Assigned Successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "An Error Occurred");
            }
        } else if (!fetched) {
            JOptionPane.showMessageDialog(null, "Please Fetch Details of Subject and Faculty then Verify to assign");
        } else {
            JOptionPane.showMessageDialog(null, "Choose a valid semester and section");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fetchInfoBtn) {
            fetchDetails();
        } else if (e.getSource() == assignBtn) {
            assignSubject();
        } else if (e.getSource() == cancelBtn) {
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new AssignSubject();
    }
}
