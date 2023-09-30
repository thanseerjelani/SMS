package com.miniproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateMarks extends JFrame implements ActionListener {
    JLabel usn, nameLabel, nameVal;
    JTextField usnTF;
    JButton fetchInfoBtn;
    String usnVal;

    UpdateMarks() {
        usn = new JLabel("ENTER USN:");
        usn.setFont(new Font("Tahoma", Font.BOLD, 15));
        usn.setBounds(60, 10, 100, 30);
        add(usn);

        usnTF = new JTextField();
        usnTF.setBounds(200, 10, 150, 30);
        add(usnTF);

        fetchInfoBtn = new JButton("Fetch Info");
        fetchInfoBtn.setBackground(Color.BLACK);
        fetchInfoBtn.setForeground(Color.WHITE);
        fetchInfoBtn.setBounds(400, 10, 150, 30);
        fetchInfoBtn.addActionListener(this);
        add(fetchInfoBtn);

        nameLabel = new JLabel("NAME: ");
        nameLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        nameLabel.setBounds(60, 50, 100, 30);
        add(nameLabel);
        nameVal = new JLabel();
        nameVal.setBounds(200, 50, 150, 30);
        nameVal.setFont(new Font("Tahoma", Font.PLAIN, 15));
        add(nameVal);

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setIconImage(Common.frameIcon.getImage());
        setBounds(400, 200, 630, 400);
        setVisible(true);
    }

    void fetchDetails() {
        String name;
        Conn conn = new Conn();
        String usn = usnTF.getText();
        this.usnVal = usn;
        if (!usn.equals("")) {
            String query = "SELECT NAME FROM STUDENT WHERE USN = '" + usn + "'";
            try {
                ResultSet rs = conn.statement.executeQuery(query);
                if (rs.next()) {
                    name = rs.getString(1);
                    nameVal.setText(name);
                } else {
                    JOptionPane.showMessageDialog(null, "Student Not Found");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "SQL Error Occurred");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fetchInfoBtn) {
            fetchDetails();
        }
    }

    public static void main(String[] args) {
        new UpdateMarks();
    }
}
