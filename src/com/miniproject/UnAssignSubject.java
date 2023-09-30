package com.miniproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UnAssignSubject extends JFrame implements ActionListener {
    JLabel headingLabel, subCodeLabel, factIdLabel, subTitleLabel, factNameLabel, factNameVal, subTitleVal;
    JLabel semSecLabel, semVal, secVal, secLabel;
    JTextField subCodeTF, factIdTF;
    JButton fetchInfoBtn, UnAssignBtn, cancelBtn;
    JComboBox defaultCB, secCB;
    String subCode, factId, secToDelete;
    String secArray[] = {"All"};
    ArrayList secAL;
    Boolean fetched = false, factOk = false, subOk = false;

    UnAssignSubject() {
        secAL = new ArrayList();
        secAL.add("All");

        headingLabel = new JLabel("UN-ASSIGN SUBJECT TO FACULTY");
        headingLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        headingLabel.setForeground(Color.BLUE);
        headingLabel.setBounds(200, 5, 400, 30);
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

        semSecLabel = new JLabel("Semester & Sections: ");
        semSecLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        semSecLabel.setBounds(40, 210, 170, 30);
        add(semSecLabel);

        semVal = new JLabel();
        semVal.setBounds(250, 210, 30, 30);
        semVal.setFont(new Font("Tahoma", Font.PLAIN, 15));
        add(semVal);

        secVal = new JLabel();
        secVal.setBounds(280, 210, 100, 30);
        secVal.setFont(new Font("Tahoma", Font.PLAIN, 15));
        add(secVal);

        secLabel = new JLabel("Section to Un-Assign: ");
        secLabel.setBounds(40, 250, 200, 30);
        secLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(secLabel);

        defaultCB = new JComboBox(secArray);
        defaultCB.setBounds(250, 250, 45, 30);
        defaultCB.setEnabled(false);
        add(defaultCB);

        UnAssignBtn = new JButton("UN-ASSIGN");
        UnAssignBtn.setBounds(40, 300, 100, 30);
        UnAssignBtn.setBackground(Color.BLACK);
        UnAssignBtn.setForeground(Color.WHITE);
        UnAssignBtn.addActionListener(this);
        add(UnAssignBtn);

        cancelBtn = new JButton("CANCEL");
        cancelBtn.setBounds(500, 300, 100, 30);
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setBackground(Color.BLACK);
        cancelBtn.addActionListener(this);
        add(cancelBtn);

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setIconImage(Common.frameIcon.getImage());
        setBounds(400, 200, 650, 400);
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
                    String sem = "", sec = "";
                    String query = "SELECT SEM, SEC FROM ASSIGNED WHERE SUBCODE = '" + subCode + "' AND FACTID = '" + factId + "'";
                    ResultSet semSecResult = conn.statement.executeQuery(query);
                    if (semSecResult.next()) {
                        sem = semSecResult.getString(1);
                        sec = semSecResult.getString(2);
                        secAL.add(sec);
                        while (semSecResult.next()) {
                            sec += " " + semSecResult.getString(2);
                            secAL.add(semSecResult.getString(2));
                        }
                    }
                    if (!sem.equals("") && !sec.equals("")) {
                        semVal.setText(sem);
                        secVal.setText(sec);
                        defaultCB.setVisible(false);
                        secCB = new JComboBox(secAL.toArray());
                        secCB.setBounds(250, 250, 45, 30);
                        add(secCB);
                        fetched = true;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            JOptionPane.showMessageDialog(null, "Please Enter Subject code and Faculty ID to Fetch");
        }
    }

    void deleteAssignment() {
        if (fetched) {
            this.secToDelete = String.valueOf(secCB.getSelectedItem());
            if (!semVal.equals("") && !secVal.equals("") && secToDelete.equals("All")) {
                String query = "DELETE FROM ASSIGNED WHERE SUBCODE = '" + subCode + "' AND FACTID = '" + factId + "'";
                Conn conn = new Conn();
                try {
                    conn.statement.executeUpdate(query);
                    JOptionPane.showMessageDialog(null, "Subject Un-Assigned Successfully!");
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "An Error Occurred");
                }
            } else if (!semVal.equals("") && !secVal.equals("") && !secToDelete.equals("All")) {
                String query = "DELETE FROM ASSIGNED WHERE SUBCODE = '" + subCode + "' AND FACTID = '" + factId + "' AND SEC = '" + secToDelete + "'";
                Conn conn = new Conn();
                try {
                    conn.statement.executeUpdate(query);
                    JOptionPane.showMessageDialog(null, "Subject Un-Assigned Successfully!");
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "An Error Occurred");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Fetch details and verify first");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fetchInfoBtn) {
            fetchDetails();
        } else if (e.getSource() == UnAssignBtn) {
            if (fetched) {
                int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete assignment of " + this.subCode + "?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirmation == 0) {
                    deleteAssignment();
                    this.setVisible(false);
                }
            }
        } else if (e.getSource() == cancelBtn) {
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new UnAssignSubject();
    }
}
