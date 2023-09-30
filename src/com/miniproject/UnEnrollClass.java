package com.miniproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UnEnrollClass extends JFrame implements ActionListener {
    JLabel headingLabel, subCodeLabel, semLabel, semVal;
    JTextField subCodeTF;
    JButton fetchInfoBtn, removeBtn, cancelBtn;
    String subCode;
    Boolean fetched = false;

    UnEnrollClass() {
        headingLabel = new JLabel("UN-ENROLL CLASS FOR SUBJECT");
        headingLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        headingLabel.setForeground(Color.BLUE);
        headingLabel.setBounds(160, 5, 300, 30);
        add(headingLabel);

        subCodeLabel = new JLabel("Subject Code: ");
        subCodeLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        subCodeLabel.setBounds(40, 50, 200, 30);
        add(subCodeLabel);

        subCodeTF = new JTextField();
        subCodeTF.setBounds(180, 50, 150, 30);
        add(subCodeTF);

        fetchInfoBtn = new JButton("Fetch Info");
        fetchInfoBtn.setBackground(Color.BLACK);
        fetchInfoBtn.setForeground(Color.WHITE);
        fetchInfoBtn.setBounds(370, 50, 150, 30);
        fetchInfoBtn.addActionListener(this);
        add(fetchInfoBtn);

        semLabel = new JLabel("Semester: ");
        semLabel.setBounds(40, 90, 150, 30);
        semLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(semLabel);

        semVal = new JLabel();
        semVal.setBounds(180, 90, 350, 30);
        semVal.setFont(new Font("Tahoma", Font.PLAIN, 15));
        add(semVal);

        removeBtn = new JButton("REMOVE");
        removeBtn.setBounds(40, 150, 100, 30);
        removeBtn.setBackground(Color.BLACK);
        removeBtn.setForeground(Color.WHITE);
        removeBtn.addActionListener(this);
        add(removeBtn);

        cancelBtn = new JButton("CANCEL");
        cancelBtn.setBounds(420, 150, 100, 30);
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setBackground(Color.BLACK);
        cancelBtn.addActionListener(this);
        add(cancelBtn);

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setIconImage(Common.frameIcon.getImage());
        setBounds(400, 200, 570, 250);
        setVisible(true);
    }

    void fetchDetails() {
        this.subCode = subCodeTF.getText();
        if (!subCode.equals("")) {
            String query = "SELECT SEM FROM ENROLLED WHERE SUBCODE = '" + subCode + "'";
            Conn conn = new Conn();
            try {
                ResultSet resultSet = conn.statement.executeQuery(query);
                if (resultSet.next()) {
                    semVal.setText(resultSet.getString(1));
                    fetched = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "An Error Occurred");
            }
        }
    }

    void deleteEnrollment() {
        if (fetched) {
            String query = "DELETE FROM ENROLLED WHERE SUBCODE = '" + subCode + "'";
            Conn conn = new Conn();
            try {
                conn.statement.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Un-Enrolled Successfully");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "An Error Occurred");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please fetch and verify first");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fetchInfoBtn) {
            fetchDetails();
        } else if (e.getSource() == removeBtn) {
            if (fetched) {
                int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete enrollment of " + this.subCode + "?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirmation == 0) {
                    deleteEnrollment();
                    this.setVisible(false);
                }
            }
        } else if (e.getSource() == cancelBtn) {
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new UnEnrollClass();
    }
}
