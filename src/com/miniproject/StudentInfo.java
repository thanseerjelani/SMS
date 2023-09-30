package com.miniproject;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentInfo extends JFrame implements ActionListener {
    JTable studentTable;
    JButton applyFilter, close, saveBtn;
    JComboBox department, sem, sec;
    JCheckBox useFilter;

    String departments[] = {
            "Computer Science and Engineering",
            "Information Science and Engineering",
            "Electronics and Communication Engineering",
            "Instrumentation and Electrical Engineering"
    };
    String semesters[] = {"All", "1", "2", "3", "4", "5", "6", "7", "8"};
    String sections[] = {"All", "A", "B", "C", "D", "E"};

    StudentInfo() {

        studentTable = new JTable();
        studentTable.setBounds(20, 35, 1000, 490);
        add(studentTable);

        JLabel usnLabel = new JLabel("USN");
        usnLabel.setBounds(100, 6, 30, 30);
        add(usnLabel);

        JLabel nameLabel = new JLabel("NAME");
        nameLabel.setBounds(300, 6, 40, 30);
        add(nameLabel);

        JLabel genderLabel = new JLabel("GENDER");
        genderLabel.setBounds(495, 6, 55, 30);
        add(genderLabel);

        JLabel dobLabel = new JLabel("DATE OF BIRTH");
        dobLabel.setBounds(680, 6, 100, 30);
        add(dobLabel);

        JLabel deptLabel = new JLabel("DEPARTMENT");
        deptLabel.setBounds(870, 6, 100, 30);
        add(deptLabel);

        useFilter = new JCheckBox("Use Filter");
        useFilter.setBounds(100, 520, 80, 40);
        useFilter.setBackground(Color.WHITE);
        useFilter.addActionListener(this);
        add(useFilter);

        department = new JComboBox(departments);
        department.setBounds(200, 530, 280, 20);
        add(department);

        sem = new JComboBox(semesters);
        sem.setBounds(500, 530, 50, 20);
        add(sem);

        sec = new JComboBox(sections);
        sec.setBounds(570, 530, 50, 20);
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

        department.setEnabled(false);
        sem.setEnabled(false);
        sec.setEnabled(false);

        setLayout(null);
        setIconImage(Common.frameIcon.getImage());
        getContentPane().setBackground(Color.WHITE);
        setBounds(300, 100, 1055, 640);
        setVisible(true);

        String query = getQuery();
        loadStudents(query);

    }

    String getQuery() {
        String query = "SELECT USN, NAME, GENDER, DATEOFBIRTH, DEPARTMENT FROM STUDENT";
        String dept = String.valueOf(department.getSelectedItem());
        String semester = String.valueOf(sem.getSelectedItem());
        String section = String.valueOf(sec.getSelectedItem());
        String queryCondition = " WHERE DEPARTMENT = '" + dept + "'";
        if (useFilter.isSelected()) {
            if (semester != "All") {
                queryCondition += " AND SEM = " + semester;
            }
            if (section != "All") {
                queryCondition += " AND SEC = '" + section + "'";
            }
            query += queryCondition;
        }
        return query;
    }

    void loadStudents(String query) {
        Common.recentQuery = query;
        Conn conn = new Conn();
        try {
            ResultSet resultSet = conn.statement.executeQuery(query);
            studentTable.setModel(DbUtils.resultSetToTableModel(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == useFilter) {
            if (useFilter.isSelected()) {
                department.setEnabled(true);
                sem.setEnabled(true);
                sec.setEnabled(true);
            } else {
                department.setEnabled(false);
                sem.setEnabled(false);
                sec.setEnabled(false);
            }
        } else if (e.getSource() == applyFilter) {
            String newQuery = getQuery();
            loadStudents(newQuery);
        } else if (e.getSource() == close) {
            this.setVisible(false);
        } else if (e.getSource() == saveBtn) {
            new SaveCSV();
        }
    }

    public static void main(String[] args) {
        new StudentInfo();
    }
}
