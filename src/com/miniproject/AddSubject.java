package com.miniproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AddSubject extends JFrame implements ActionListener {
    JTextField nameTF, subCodeTF, creditsTF;
    JButton addButton, cancelButton;

    AddSubject() {
        JLabel nameLabel = new JLabel("SUBJECT NAME");
        nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        nameLabel.setBounds(60, 30, 120, 30);
        add(nameLabel);

        nameTF = new JTextField();
        nameTF.setBounds(200, 30, 150, 30);
        add(nameTF);

        JLabel subCodeLabel = new JLabel("SUBJECT CODE");
        subCodeLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        subCodeLabel.setBounds(60, 70, 120, 30);
        add(subCodeLabel);

        subCodeTF = new JTextField();
        subCodeTF.setBounds(200, 70, 150, 30);
        add(subCodeTF);

        JLabel creditsLabel = new JLabel("CREDITS");
        creditsLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        creditsLabel.setBounds(60, 110, 120, 30);
        add(creditsLabel);

        creditsTF = new JTextField();
        creditsTF.setBounds(200, 110, 150, 30);
        add(creditsTF);

        ImageIcon subjectIcon = new ImageIcon(ClassLoader.getSystemResource("com/miniproject/icons/subjects.png"));
        JLabel subjectImageLabel = new JLabel(subjectIcon);
        subjectImageLabel.setBounds(400, 40, 200, 200);
        add(subjectImageLabel);

        JLabel frameLabel = new JLabel("ENTER SUBJECT DETAILS");
        frameLabel.setBounds(395, 5, 250, 30);
        frameLabel.setForeground(Color.BLUE);
        frameLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(frameLabel);

        addButton = new JButton("ADD SUBJECT");
        addButton.setBounds(60, 170, 130, 30);
        addButton.setBackground(Color.BLACK);
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(this);
        add(addButton);

        cancelButton = new JButton("CANCEL");
        cancelButton.setBounds(210, 170, 100, 30);
        cancelButton.setBackground(Color.BLACK);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(this);
        add(cancelButton);

        setLayout(null);
        setIconImage(Common.frameIcon.getImage());
        getContentPane().setBackground(Color.WHITE);
        setBounds(400, 200, 650, 300);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String name = nameTF.getText();
            String subCode = subCodeTF.getText();
            String credits = creditsTF.getText();

            Conn conn = new Conn();

            String subjectQuery = "INSERT INTO SUBJECT VALUES('" + subCode + "', '" + name + "'," + credits + ")";
            try {
                conn.statement.executeUpdate(subjectQuery);
                JOptionPane.showMessageDialog(null, "New Subject Added");
                this.setVisible(false);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == cancelButton) {
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new AddSubject();
    }
}
