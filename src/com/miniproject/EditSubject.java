package com.miniproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditSubject extends JFrame implements ActionListener {
    JLabel subCodeLabel, titleLabel, creditsLabel, titleVal, creditsVal;
    JTextField subCodeTF;
    JButton fetchInfoBtn, deleteBtn, cancelBtn;
    String subCode;

    EditSubject() {
        subCodeLabel = new JLabel("ENTER SUBJECT CODE: ");
        subCodeLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        subCodeLabel.setBounds(40, 10, 200, 30);
        add(subCodeLabel);

        subCodeTF = new JTextField();
        subCodeTF.setBounds(250, 10, 150, 30);
        add(subCodeTF);

        fetchInfoBtn = new JButton("Fetch Info");
        fetchInfoBtn.setBackground(Color.BLACK);
        fetchInfoBtn.setForeground(Color.WHITE);
        fetchInfoBtn.setBounds(450, 10, 150, 30);
        fetchInfoBtn.addActionListener(this);
        add(fetchInfoBtn);

        titleLabel = new JLabel("TITLE: ");
        titleLabel.setBounds(40, 50, 100, 30);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(titleLabel);

        titleVal = new JLabel();
        titleVal.setBounds(250, 50, 300, 30);
        titleVal.setFont(new Font("Tahoma", Font.PLAIN, 15));
        add(titleVal);

        creditsLabel = new JLabel("CREDITS: ");
        creditsLabel.setBounds(40, 90, 100, 30);
        creditsLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(creditsLabel);

        creditsVal = new JLabel();
        creditsVal.setBounds(250, 90, 50, 30);
        creditsVal.setFont(new Font("Tahoma", Font.PLAIN, 15));
        add(creditsVal);

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
        this.subCode = subCodeTF.getText();
        if (!subCode.equals("")) {
            String query = "SELECT * FROM SUBJECT WHERE SUBCODE = '" + subCodeTF.getText() + "'";
            Conn conn = new Conn();

            try {
                ResultSet resultSet = conn.statement.executeQuery(query);
                if (resultSet.next()) {
                    String title = resultSet.getString(2);
                    String credits = resultSet.getString(3);

                    titleVal.setText(title);
                    creditsVal.setText(credits);
                } else {
                    JOptionPane.showMessageDialog(null, "Subject Not Found");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "SQL Error Occurred");
            }
        }
    }

    void deleteSubject() {
        Conn conn = new Conn();
        String deleteQuery = "DELETE FROM SUBJECT WHERE SUBCODE = '" + subCode + "'";
        try {
            conn.statement.executeUpdate(deleteQuery);
            JOptionPane.showMessageDialog(null, "Subject Deleted Successfully");
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
            int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete details of " + this.subCode + "?", "Confirmation", JOptionPane.YES_NO_OPTION);
            System.out.println(confirmation);
            if (confirmation == 0) {
                deleteSubject();
            }
        } else if (e.getSource() == cancelBtn) {
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new EditSubject();
    }
}
