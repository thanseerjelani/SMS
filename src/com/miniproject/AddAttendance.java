package com.miniproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddAttendance extends JFrame implements ActionListener {
    JLabel heading, semLabel, semVal, subLabel, secLabel;
    JLabel usnLabel, nameLabel, nameValLabel, attendedLabel, totalLabel;
    JTextField attendedTF, totalTF;
    JComboBox secCB, subjectCB, usnCB;
    String subCode, sem, sec, usn, attended, total;
    JButton update, close, fetchUSNBtn;
    ArrayList usnAL, nameAL, sections, subjects;
    Boolean subFetched = false, usnFetched = false;

    AddAttendance() {
        heading = new JLabel("ENTER ATTENDANCE FOR STUDENTS");
        heading.setBounds(110, 5, 300, 30);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 17));
        heading.setForeground(Color.BLUE);
        add(heading);

        subjects = new ArrayList();
        sections = new ArrayList();
        sections.add("All");

        subLabel = new JLabel("SUBJECT: ");
        subLabel.setBounds(40, 50, 80, 20);
        subLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        add(subLabel);

        subjectCB = new JComboBox();
        subjectCB.setBounds(120, 50, 100, 20);
        subjectCB.addActionListener(this);
        add(subjectCB);

        semLabel = new JLabel("SEM: ");
        semLabel.setBounds(40, 90, 50, 20);
        semLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        add(semLabel);

        semVal = new JLabel("4");
        semVal.setFont(new Font("Tahoma", Font.PLAIN, 14));
        semVal.setBounds(90, 90, 20, 20);
        add(semVal);

        secLabel = new JLabel("SEC: ");
        secLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        secLabel.setBounds(120, 90, 50, 20);
        add(secLabel);

        secCB = new JComboBox();
        secCB.setBounds(160, 90, 50, 20);
        add(secCB);

        fetchUSNBtn = new JButton("Fetch USNs");
        fetchUSNBtn.setBounds(290, 85, 150, 30);
        fetchUSNBtn.setBackground(Color.BLACK);
        fetchUSNBtn.setForeground(Color.WHITE);
        fetchUSNBtn.addActionListener(this);
        add(fetchUSNBtn);

        usnLabel = new JLabel("USN: ");
        usnLabel.setBounds(40, 130, 50, 20);
        usnLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        add(usnLabel);

        usnCB = new JComboBox();
        usnCB.setBounds(90, 130, 120, 20);
        usnCB.addActionListener(this);
        add(usnCB);

        nameLabel = new JLabel("NAME: ");
        nameLabel.setBounds(230, 130, 90, 20);
        nameLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        add(nameLabel);

        nameValLabel = new JLabel();
        nameValLabel.setBounds(290, 130, 200, 20);
        nameValLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        add(nameValLabel);

        attendedLabel = new JLabel("ATTENDED: ");
        attendedLabel.setBounds(40, 170, 100, 30);
        attendedLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        add(attendedLabel);

        attendedTF = new JTextField();
        attendedTF.setBounds(140, 170, 100, 30);
        add(attendedTF);

        totalLabel = new JLabel("TOTAL: ");
        totalLabel.setBounds(40, 210, 100, 30);
        totalLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        add(totalLabel);

        totalTF = new JTextField();
        totalTF.setBounds(140, 210, 100, 30);
        add(totalTF);

        update = new JButton("UPDATE");
        update.setBounds(40, 260, 150, 30);
        update.setForeground(Color.WHITE);
        update.setBackground(Color.BLACK);
        update.addActionListener(this);
        add(update);

        close = new JButton("CLOSE");
        close.setBounds(295, 260, 150, 30);
        close.setForeground(Color.WHITE);
        close.setBackground(Color.BLACK);
        close.addActionListener(this);
        add(close);

        loadSubjects();

        setLayout(null);
        setIconImage(Common.frameIcon.getImage());
        getContentPane().setBackground(Color.WHITE);
        setBounds(300, 100, 500, 350);
        setVisible(true);
    }

    void loadSubjects() {
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
            subjectCB.setModel(model);
            subFetched = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (subFetched) {
            loadSection();
        }
    }

    void loadSection() {
        subCode = String.valueOf(subjectCB.getSelectedItem());
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
            secCB.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void fetchUSN() {
        if (subFetched) {
            this.sem = semVal.getText();
            this.sec = String.valueOf(secCB.getSelectedItem());
            String query = "SELECT USN, NAME FROM STUDENT WHERE SEM = '" + sem + "' AND SEC = '" + sec + "' AND  DEPARTMENT = '" + Common.dept + "'";
            Conn conn = new Conn();
            String usn, name;
            usnAL = new ArrayList<>();
            nameAL = new ArrayList<>();
            try {
                ResultSet usnRS = conn.statement.executeQuery(query);
                while (usnRS.next()) {
                    usn = usnRS.getString(1);
                    usnAL.add(usn);
                    name = usnRS.getString(2);
                    nameAL.add(name);
                }
                DefaultComboBoxModel model = new DefaultComboBoxModel(usnAL.toArray());
                usnCB.setModel(model);
                usnFetched = true;
                setName();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    void setName() {
        if (usnFetched) {
            int index = usnAL.indexOf(usnCB.getSelectedItem());
            nameValLabel.setText(String.valueOf(nameAL.get(index)));
        }
    }

    void updateAttendance() {
        if (usnFetched) {
            usn = String.valueOf(usnCB.getSelectedItem());
            attended = attendedTF.getText();
            total = totalTF.getText();

            if (!attended.equals("") && !total.equals("")) {
                String checkQuery = "SELECT * FROM ATTENDANCE WHERE USN = '" + usn + "' AND SUBCODE = '" + subCode + "'";
                Boolean notFound = true;
                Conn conn = new Conn();
                try {
                    ResultSet checkRS = conn.statement.executeQuery(checkQuery);
                    if (checkRS.next()) {
                        notFound = false;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (notFound) {
                    String insertQuery = "INSERT INTO ATTENDANCE VALUES('" + usn + "', '" + subCode + "', null, null)";
                    try {
                        conn.statement.executeUpdate(insertQuery);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                String update = "UPDATE ATTENDANCE SET TOTAL = '" + total + "', ATTENDED = '" + attended + "' WHERE USN = '" + usn + "' AND SUBCODE = '" + subCode + "'";
                try {
                    conn.statement.executeUpdate(update);
                    JOptionPane.showMessageDialog(null, "Updated Successfully!");
                    attendedTF.setText("");
                    totalTF.setText("");
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "AN Error Occurred");
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fetchUSNBtn) {
            fetchUSN();
        } else if (e.getSource() == subjectCB) {
            loadSection();
        } else if (e.getSource() == close) {
            this.setVisible(false);
        } else if (e.getSource() == usnCB) {
            setName();
        } else if (e.getSource() == update) {
            updateAttendance();
        }
    }

    public static void main(String[] args) {
        new AddAttendance();
    }
}
