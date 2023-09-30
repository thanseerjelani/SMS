package com.miniproject;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ScoresInfo extends JFrame implements ActionListener {
    JTable scoresTable;
    JLabel sem, semVal, usnLabel, nameLabel, ia1MarksLabel, ia2MarksLabel, ia3MarksLabel;
    JLabel finalIAMarksLabel, seMarksLabel;
    JButton applyFilter, close, saveBtn;
    JComboBox subject, sec;
    ArrayList subjects, sections;
    Boolean subFetched = false;

    ScoresInfo() {

        usnLabel = new JLabel("USN");
        usnLabel.setBounds(80, 6, 30, 30);
        add(usnLabel);

        nameLabel = new JLabel("NAME");
        nameLabel.setBounds(215, 6, 40, 30);
        add(nameLabel);

        ia1MarksLabel = new JLabel("IA1 MARKS");
        ia1MarksLabel.setBounds(345, 6, 100, 30);
        add(ia1MarksLabel);

        ia2MarksLabel = new JLabel("IA2 MARKS");
        ia2MarksLabel.setBounds(490, 6, 100, 30);
        add(ia2MarksLabel);

        ia3MarksLabel = new JLabel("IA3 MARKS");
        ia3MarksLabel.setBounds(630, 6, 100, 30);
        add(ia3MarksLabel);

        finalIAMarksLabel = new JLabel("FINAL IA MARKS");
        finalIAMarksLabel.setBounds(760, 6, 100, 30);
        add(finalIAMarksLabel);

        seMarksLabel = new JLabel("SE MARKS");
        seMarksLabel.setBounds(918, 6, 100, 30);
        add(seMarksLabel);

        subjects = new ArrayList();
        sections = new ArrayList();
        sections.add("All");

        subject = new JComboBox();
        subject.setBounds(100, 530, 280, 20);
        subject.addActionListener(this);
        add(subject);

        scoresTable = new JTable();
        scoresTable.setBounds(20, 35, 1000, 490);
        add(scoresTable);

        sem = new JLabel("SEM: ");
        sem.setBounds(400, 530, 50, 20);
        sem.setFont(new Font("Tahoma", Font.BOLD, 14));
        add(sem);

        semVal = new JLabel();
        semVal.setFont(new Font("Tahoma", Font.PLAIN, 14));
        semVal.setBounds(450, 530, 50, 20);
        add(semVal);

        sec = new JComboBox();
        sec.setBounds(500, 530, 50, 20);
        add(sec);

        applyFilter = new JButton("APPLY FILTER");
        applyFilter.setBounds(100, 560, 150, 30);
        applyFilter.setForeground(Color.WHITE);
        applyFilter.setBackground(Color.BLACK);
        applyFilter.addActionListener(this);
        add(applyFilter);

        JLabel saveCSV = new JLabel("SAVE CSV:");
        saveCSV.setFont(new Font("Tahoma", Font.BOLD, 14));
        saveCSV.setBounds(430, 560, 80, 30);
        add(saveCSV);

        ImageIcon saveIcon = new ImageIcon(ClassLoader.getSystemResource("com/miniproject/icons/download_icon_white.png"));
        Image scaledImage = saveIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(scaledImage);

        saveBtn = new JButton(icon);
        saveBtn.setBounds(510, 560, 30, 30);
        saveBtn.setBackground(Color.BLACK);
        saveBtn.addActionListener(this);
        add(saveBtn);

        close = new JButton("CLOSE");
        close.setBounds(800, 560, 150, 30);
        close.setForeground(Color.WHITE);
        close.setBackground(Color.BLACK);
        close.addActionListener(this);
        add(close);

        loadSubject();

        setLayout(null);
        setIconImage(Common.frameIcon.getImage());
        getContentPane().setBackground(Color.WHITE);
        setBounds(300, 100, 1055, 640);
        setVisible(true);

        loadStudents(getQuery());
    }

    String getQuery() {
        String subCode = String.valueOf(subject.getSelectedItem());
        String query = "SELECT S.USN, S.NAME, M.IA1MARKS, M.IA2MARKS, M.IA3MARKS, M.FINALIAMARKS, M.SEMARKS FROM STUDENT S, MARKS M WHERE S.USN=M.USN AND M.SUBCODE='" + subCode + "'";
        String orderBy = "ORDER BY S.USN";
        String sect = String.valueOf(sec.getSelectedItem());
        if (!sect.equals("All")) {
            query += " AND S.SEC = '" + sect + "'";
        }
        query += orderBy;
        return query;
    }

    void loadStudents(String query) {
        Common.recentQuery = query;
        Conn conn = new Conn();
        try {
            ResultSet resultSet = conn.statement.executeQuery(query);
            scoresTable.setModel(DbUtils.resultSetToTableModel(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void loadSubject() {
        String subQuery = "SELECT DISTINCT(SUBCODE) FROM ASSIGNED WHERE FACTID = '" + Common.factId + "'";
        String subQuery1 = "SELECT DISTINCT(SUBCODE) FROM ASSIGNED WHERE FACTID = '" + "ISE/001" + "'";
        Conn conn = new Conn();
        try {
            ResultSet subcodeResult = conn.statement.executeQuery(subQuery);
            while (subcodeResult.next()) {
                subjects.add(subcodeResult.getString(1));
            }
            System.out.println(subjects);
            DefaultComboBoxModel model = new DefaultComboBoxModel(subjects.toArray());
            subject.setModel(model);
            subFetched = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (subFetched) {
            loadSection();
        }
    }

    void loadSection() {
        String subCode = String.valueOf(subject.getSelectedItem());
        String secQuery = "SELECT SEC, SEM FROM ASSIGNED WHERE FACTID='" + Common.factId + "' AND SUBCODE = '" + subCode + "'";
        String secQuery1 = "SELECT SEC, SEM FROM ASSIGNED WHERE FACTID='ISE/001' AND SUBCODE = '18CS51'";
        String secQuery2 = "SELECT SEC, SEM FROM ASSIGNED WHERE FACTID='ISE/001' AND SUBCODE = '" + subCode + "'";
        ArrayList sectionsAL = new ArrayList();
        Conn conn = new Conn();
        try {
            ResultSet subcodeResult = conn.statement.executeQuery(secQuery);
            while (subcodeResult.next()) {
                sectionsAL.add(subcodeResult.getString(1));
                semVal.setText(subcodeResult.getString(2));
            }
            System.out.println(sectionsAL);
            DefaultComboBoxModel model = new DefaultComboBoxModel(sectionsAL.toArray());
            sec.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == applyFilter) {
            String newQuery = getQuery();
            loadStudents(newQuery);
        } else if (e.getSource() == subject) {
            System.out.println("Clicked");
            loadSection();
        } else if (e.getSource() == close) {
            this.setVisible(false);
        } else if (e.getSource() == saveBtn) {
            new SaveCSV();
        }
    }

    public static void main(String[] args) {
        new ScoresInfo();
    }
}
