package com.miniproject;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentAttendanceInfo extends JFrame implements ActionListener {
    JLabel subCodeLabel, subTitleLabel, ia1MarksLabel, ia2MarksLabel, seMarksLabel;
    JTable scoresTable;
    JButton saveBtn;

    StudentAttendanceInfo() {
        subCodeLabel = new JLabel("SUBCODE");
        subCodeLabel.setBounds(95, 6, 80, 30);
        add(subCodeLabel);

        subTitleLabel = new JLabel("SUBJECT TITLE");
        subTitleLabel.setBounds(275, 6, 100, 30);
        add(subTitleLabel);

        ia1MarksLabel = new JLabel("ATTENDED");
        ia1MarksLabel.setBounds(485, 6, 100, 30);
        add(ia1MarksLabel);

        ia2MarksLabel = new JLabel("TOTAL");
        ia2MarksLabel.setBounds(690, 6, 100, 30);
        add(ia2MarksLabel);


        scoresTable = new JTable();
        scoresTable.setBounds(20, 35, 800, 200);
        add(scoresTable);

        fetchAttendance();

        JLabel saveCSV = new JLabel("SAVE CSV:");
        saveCSV.setFont(new Font("Tahoma", Font.BOLD, 14));
        saveCSV.setBounds(700, 240, 80, 30);
        add(saveCSV);

        ImageIcon saveIcon = new ImageIcon(ClassLoader.getSystemResource("com/miniproject/icons/download_icon_white.png"));
        Image scaledImage = saveIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(scaledImage);

        saveBtn = new JButton(icon);
        saveBtn.setBounds(780, 240, 30, 30);
        saveBtn.setBackground(Color.BLACK);
        saveBtn.addActionListener(this);
        add(saveBtn);

        setLayout(null);
        setIconImage(Common.frameIcon.getImage());
        getContentPane().setBackground(Color.WHITE);
        setBounds(300, 100, 840, 310);
        setVisible(true);
    }

    void fetchAttendance() {
        String query = "SELECT S.SUBCODE, S.NAME, A.ATTENDED, A.TOTAL FROM SUBJECT S, ATTENDANCE A WHERE S.SUBCODE = A.SUBCODE AND A.USN = '" + Common.usn + "'";
        Common.recentQuery = query;
        Conn conn = new Conn();
        try {
            ResultSet scores = conn.statement.executeQuery(query);
            scoresTable.setModel(DbUtils.resultSetToTableModel(scores));
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
        new StudentAttendanceInfo();
    }
}
