package com.miniproject;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FacultyInfo extends JFrame implements ActionListener {

    JTable facultyTable;
    JButton applyFilter, back, saveBtn;
    JComboBox department;
    String departments[] = {
            "All",
            "Computer Science and Engineering",
            "Information Science and Engineering",
            "Electronics and Communication Engineering",
            "Instrumentation and Electrical Engineering"
    };

    FacultyInfo() {

        facultyTable = new JTable();
        facultyTable.setBounds(20, 30, 700, 480);
        add(facultyTable);

        JLabel nameLabel = new JLabel("NAME");
        nameLabel.setBounds(120, 5, 50, 30);
        add(nameLabel);

        JLabel factIDLabel = new JLabel("FACULTY ID");
        factIDLabel.setBounds(340, 5, 100, 30);
        add(factIDLabel);

        JLabel deptLabel = new JLabel("DEPARTMENT");
        deptLabel.setBounds(550, 5, 100, 30);
        add(deptLabel);

        department = new JComboBox(departments);
        department.setBounds(40, 520, 280, 30);
        add(department);

        applyFilter = new JButton("Apply Filter");
        applyFilter.setBounds(340, 520, 120, 30);
        applyFilter.setForeground(Color.WHITE);
        applyFilter.setBackground(Color.BLACK);
        applyFilter.addActionListener(this);
        add(applyFilter);

        back = new JButton("Back");
        back.setBounds(480, 520, 120, 30);
        back.setForeground(Color.WHITE);
        back.setBackground(Color.BLACK);
        back.addActionListener(this);
        add(back);

        JLabel saveCSV = new JLabel("SAVE CSV:");
        saveCSV.setFont(new Font("Tahoma", Font.BOLD, 14));
        saveCSV.setBounds(610, 520, 80, 30);
        add(saveCSV);

        ImageIcon saveIcon = new ImageIcon(ClassLoader.getSystemResource("com/miniproject/icons/download_icon_white.png"));
        Image scaledImage = saveIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(scaledImage);

        saveBtn = new JButton(icon);
        saveBtn.setBounds(690, 520, 30, 30);
        saveBtn.setBackground(Color.BLACK);
        saveBtn.addActionListener(this);
        add(saveBtn);

        setLayout(null);
        setIconImage(Common.frameIcon.getImage());
        getContentPane().setBackground(Color.WHITE);
        setBounds(400, 150, 750, 600);
        setVisible(true);

        String query = getQuery();
        loadFaculty(query);
    }

    String getQuery() {
        String query = "SELECT NAME, FACTID, DEPARTMENT FROM FACULTY";
        String dept = String.valueOf(department.getSelectedItem());
        if (dept != "All") {
            query += " WHERE DEPARTMENT = '" + dept + "'";
        }
        return query;
    }

    void loadFaculty(String query) {
        Common.recentQuery = query;
        Conn conn = new Conn();
        try {
            ResultSet resultSet = conn.statement.executeQuery(query);
            facultyTable.setModel(DbUtils.resultSetToTableModel(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == applyFilter) {
            String filterQuery = getQuery();
            loadFaculty(filterQuery);
        } else if (e.getSource() == back) {
            this.setVisible(false);
        } else if (e.getSource() == saveBtn) {
            new SaveCSV();
        }
    }

    public static void main(String[] args) {
        new FacultyInfo();
    }

}
