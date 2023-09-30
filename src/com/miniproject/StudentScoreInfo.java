package com.miniproject;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentScoreInfo extends JFrame implements ActionListener {
    JLabel subCodeLabel, subTitleLabel, ia1MarksLabel, ia2MarksLabel, ia3MarksLabel;
    JLabel finalIAMarksLabel, seMarksLabel;
    JTable scoresTable;
    JButton saveBtn;

    StudentScoreInfo() {

        subCodeLabel = new JLabel("SUBCODE");
        subCodeLabel.setBounds(55, 6, 80, 30);
        add(subCodeLabel);

        subTitleLabel = new JLabel("SUBJECT TITLE");
        subTitleLabel.setBounds(210, 6, 100, 30);
        add(subTitleLabel);

        ia1MarksLabel = new JLabel("IA1 MARKS");
        ia1MarksLabel.setBounds(395, 6, 100, 30);
        add(ia1MarksLabel);

        ia2MarksLabel = new JLabel("IA2 MARKS");
        ia2MarksLabel.setBounds(525, 6, 100, 30);
        add(ia2MarksLabel);

        ia3MarksLabel = new JLabel("IA3 MARKS");
        ia3MarksLabel.setBounds(655, 6, 100, 30);
        add(ia3MarksLabel);

        finalIAMarksLabel = new JLabel("FINAL IA MARKS");
        finalIAMarksLabel.setBounds(772, 6, 100, 30);
        add(finalIAMarksLabel);

        seMarksLabel = new JLabel("SE MARKS");
        seMarksLabel.setBounds(920, 6, 100, 30);
        add(seMarksLabel);

        scoresTable = new JTable();
        scoresTable.setBounds(20, 35, 1000, 200);
        add(scoresTable);

        loadScores();

        JLabel saveCSV = new JLabel("SAVE CSV:");
        saveCSV.setFont(new Font("Tahoma", Font.BOLD, 14));
        saveCSV.setBounds(920, 240, 80, 30);
        add(saveCSV);

        ImageIcon saveIcon = new ImageIcon(ClassLoader.getSystemResource("com/miniproject/icons/download_icon_white.png"));
        Image scaledImage = saveIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(scaledImage);

        saveBtn = new JButton(icon);
        saveBtn.setBounds(1000, 240, 30, 30);
        saveBtn.setBackground(Color.BLACK);
        saveBtn.addActionListener(this);
        add(saveBtn);

        setLayout(null);
        setIconImage(Common.frameIcon.getImage());
        getContentPane().setBackground(Color.WHITE);
        setBounds(300, 210, 1055, 310);
        setVisible(true);
    }

    void loadScores() {
        String query = "SELECT S.SUBCODE, S.NAME, M.IA1MARKS, M.IA2MARKS, M.IA3MARKS, M.FINALIAMARKS, M.SEMARKS FROM SUBJECT S, MARKS M WHERE S.SUBCODE = M.SUBCODE AND M.USN = '" + Common.usn + "'";
        Common.recentQuery = query;
        Conn conn = new Conn();
        try {
            ResultSet scores = conn.statement.executeQuery(query);
            scoresTable.setModel(DbUtils.resultSetToTableModel(scores));
            scoresTable.getColumnModel().getColumn(1).setPreferredWidth(150);
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
        new StudentScoreInfo();
    }


}
