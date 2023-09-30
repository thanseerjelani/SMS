package com.miniproject;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AttendanceInfo extends JFrame implements ActionListener {
    JTable attendanceTable;
    JLabel sem, semVal, usnLabel, nameLabel, attendedLabel, totalLabel;
    JButton applyFilter, close, saveBtn;
    JComboBox subject, sec;
    ArrayList subjects, sections;
    Boolean subFetched = false;


    AttendanceInfo() {
        usnLabel = new JLabel("USN");
        usnLabel.setBounds(130, 6, 30, 30);
        add(usnLabel);

        nameLabel = new JLabel("NAME");
        nameLabel.setBounds(380, 6, 40, 30);
        add(nameLabel);

        attendedLabel = new JLabel("ATTENDED");
        attendedLabel.setBounds(615, 6, 100, 30);
        add(attendedLabel);

        totalLabel = new JLabel("TOTAL");
        totalLabel.setBounds(880, 6, 50, 30);
        add(totalLabel);

        subjects = new ArrayList();
        sections = new ArrayList();
        sections.add("All");

        attendanceTable = new JTable();
        attendanceTable.setBounds(20, 35, 1000, 490);
        add(attendanceTable);

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

        applyFilter = new JButton("Apply Filter");
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


        close = new JButton("Close");
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
        String query = "SELECT S.USN, S.NAME, A.ATTENDED, A.TOTAL FROM STUDENT S, ATTENDANCE A WHERE A.USN=S.USN AND A.SUBCODE = '" + subCode + "'";
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
            attendanceTable.setModel(DbUtils.resultSetToTableModel(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void loadSubject() {
        String subQuery = "SELECT DISTINCT(SUBCODE) FROM ASSIGNED WHERE FACTID = '" + Common.factId + "'";
//        String subQuery1 = "SELECT DISTINCT(SUBCODE) FROM ASSIGNED WHERE FACTID = '" + "ISE/001" + "'";
        Conn conn = new Conn();
        try {
            ResultSet subcodeResult = conn.statement.executeQuery(subQuery);
            while (subcodeResult.next()) {
                subjects.add(subcodeResult.getString(1));
            }
            System.out.println(subjects);
            subject = new JComboBox(subjects.toArray());
            subject.setBounds(100, 530, 280, 20);
            subject.addActionListener(this);
            add(subject);
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
//        String secQuery1 = "SELECT SEC, SEM FROM ASSIGNED WHERE FACTID='ISE/001' AND SUBCODE = '18CS51'";
//        String secQuery2 = "SELECT SEC, SEM FROM ASSIGNED WHERE FACTID='ISE/001' AND SUBCODE = '" + subCode + "'";
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
        new AttendanceInfo();
    }
}
