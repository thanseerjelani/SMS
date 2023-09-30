package com.miniproject;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectInfo extends JFrame implements ActionListener {
    JTable subjectTable;
    JButton saveBtn;

    SubjectInfo() {

        subjectTable = new JTable();
        subjectTable.setBounds(20, 35, 1000, 460);
        add(subjectTable);

        JLabel subCodeLabel = new JLabel("SUBJECT CODE");
        subCodeLabel.setBounds(140, 6, 150, 30);
        add(subCodeLabel);

        JLabel nameLabel = new JLabel("TITLE");
        nameLabel.setBounds(500, 6, 60, 30);
        add(nameLabel);

        JLabel creditLabel = new JLabel("CREDITS");
        creditLabel.setBounds(830, 6, 60, 30);
        add(creditLabel);

        JLabel saveCSV = new JLabel("SAVE CSV:");
        saveCSV.setFont(new Font("Tahoma", Font.BOLD, 14));
        saveCSV.setBounds(920, 525, 80, 30);
        add(saveCSV);

        ImageIcon saveIcon = new ImageIcon(ClassLoader.getSystemResource("com/miniproject/icons/download_icon_white.png"));
        Image scaledImage = saveIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(scaledImage);

        saveBtn = new JButton(icon);
        saveBtn.setBounds(1000, 525, 30, 30);
        saveBtn.setBackground(Color.BLACK);
        saveBtn.addActionListener(this);
        add(saveBtn);

        setLayout(null);
        setIconImage(Common.frameIcon.getImage());
        getContentPane().setBackground(Color.WHITE);
        setBounds(450, 200, 1055, 600);
        setVisible(true);

        String query = getQuery();
        loadStudents(query);

    }

    String getQuery() {
        String query = "SELECT * FROM SUBJECT";
        // if filter is added, add selection query generation logic here
        return query;
    }

    void loadStudents(String query) {
        Common.recentQuery = query;
        Conn conn = new Conn();
        try {
            ResultSet resultSet = conn.statement.executeQuery(query);
            subjectTable.setModel(DbUtils.resultSetToTableModel(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveBtn) {
            new SaveCSV();
        }
    }

    public static void main(String[] args) {
        new SubjectInfo();
    }
}
