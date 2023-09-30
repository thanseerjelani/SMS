package com.miniproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditStudent extends JFrame implements ActionListener {
    JLabel usnLabel, nameLabel, genderLabel, departmentLabel, semLabel, dobLabel, enrollYearLabel;
    JLabel nameVal, genderVal, deptVal, semVal, dobVal, enrollYearVal;
    JButton fetchInfoBtn, editBtn, deleteBtn, cancelBtn;
    JTextField usnTF;
    String usnVal;

    EditStudent() {
        usnLabel = new JLabel("ENTER USN: ");
        usnLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        usnLabel.setBounds(60, 10, 100, 30);
        add(usnLabel);

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

        departmentLabel = new JLabel("DEPARTMENT: ");
        departmentLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        departmentLabel.setBounds(60, 90, 150, 30);
        add(departmentLabel);
        deptVal = new JLabel();
        deptVal.setBounds(200, 90, 250, 30);
        deptVal.setFont(new Font("Tahoma", Font.PLAIN, 15));
        add(deptVal);

        genderLabel = new JLabel("GENDER: ");
        genderLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        genderLabel.setBounds(60, 130, 100, 30);
        add(genderLabel);
        genderVal = new JLabel();
        genderVal.setBounds(200, 130, 150, 30);
        genderVal.setFont(new Font("Tahoma", Font.PLAIN, 15));
        add(genderVal);

        semLabel = new JLabel("SEM-SEC: ");
        semLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        semLabel.setBounds(60, 170, 100, 30);
        add(semLabel);
        semVal = new JLabel();
        semVal.setBounds(200, 170, 150, 30);
        semVal.setFont(new Font("Tahoma", Font.PLAIN, 15));
        add(semVal);

        dobLabel = new JLabel("DATE OF BIRTH: ");
        dobLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        dobLabel.setBounds(60, 210, 150, 30);
        add(dobLabel);
        dobVal = new JLabel();
        dobVal.setBounds(200, 210, 150, 30);
        dobVal.setFont(new Font("Tahoma", Font.PLAIN, 15));
        add(dobVal);

        enrollYearLabel = new JLabel("ENROLL YEAR: ");
        enrollYearLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        enrollYearLabel.setBounds(60, 250, 150, 30);
        add(enrollYearLabel);
        enrollYearVal = new JLabel();
        enrollYearVal.setBounds(200, 250, 150, 30);
        enrollYearVal.setFont(new Font("Tahoma", Font.PLAIN, 15));
        add(enrollYearVal);

        editBtn = new JButton("EDIT");
        editBtn.setBounds(60, 300, 150, 30);
        editBtn.setForeground(Color.WHITE);
        editBtn.setBackground(Color.BLACK);
        editBtn.addActionListener(this);
        add(editBtn);

        deleteBtn = new JButton("DELETE");
        deleteBtn.setBounds(235, 300, 150, 30);
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setBackground(Color.BLACK);
        deleteBtn.addActionListener(this);
        add(deleteBtn);

        cancelBtn = new JButton("CANCEL");
        cancelBtn.setBounds(410, 300, 150, 30);
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setBackground(Color.BLACK);
        cancelBtn.addActionListener(this);
        add(cancelBtn);

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setIconImage(Common.frameIcon.getImage());
        setBounds(400, 200, 630, 400);
        setVisible(true);

    }

    void fetchDetails() {
        String name, dept, gender, dob, semSec, enrollYear;
        Conn conn = new Conn();
        String usn = usnTF.getText();
        this.usnVal = usn;
        if (!usn.equals("")) {
            String query = "SELECT NAME, DEPARTMENT, GENDER, SEM, SEC, DATEOFBIRTH, ENROLLYEAR FROM STUDENT WHERE USN = '" + usn + "'";
            try {
                ResultSet rs = conn.statement.executeQuery(query);
                if (rs.next()) {
                    name = rs.getString(1);
                    dept = rs.getString(2);
                    gender = rs.getString(3);
                    semSec = rs.getString(4) + " - " + rs.getString(5);
                    dob = rs.getString(6);
                    enrollYear = rs.getString(7);
                    System.out.println(name + " " + " " + dept + " " + gender + " " + semSec + " " + dob + " " + enrollYear);

                    nameVal.setText(name);
                    deptVal.setText(dept);
                    genderVal.setText(gender);
                    semVal.setText(semSec);
                    dobVal.setText(dob);
                    enrollYearVal.setText(enrollYear);
                } else {
                    JOptionPane.showMessageDialog(null, "Student Not Found");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "SQL Error Occurred");
            }
        }
    }

    public static void main(String[] args) {
        new EditStudent();
//        new updateStudent("1JS19IS088");
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fetchInfoBtn) {
            fetchDetails();
        } else if (e.getSource() == editBtn) {
            new updateStudent(this.usnVal);
        } else if (e.getSource() == deleteBtn) {
            int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete details of " + nameVal.getText() + "?", "Confirmation", JOptionPane.YES_NO_OPTION);
            System.out.println(confirmation);
            if (confirmation == 0) {
                deleteUser();
            }
        } else if (e.getSource() == cancelBtn) {
            this.setVisible(false);
        }
    }

    private void deleteUser() {
        try {
            String userIDQuery = "SELECT USERID FROM STUDENT WHERE USN = '" + this.usnVal + "'";
            Conn conn = new Conn();
            String userId;
            ResultSet userIDRS = conn.statement.executeQuery(userIDQuery);
            userIDRS.next();
            userId = userIDRS.getString(1);
            String query = "DELETE FROM USERS WHERE USERID = " + userId + "";
            conn.statement.executeUpdate(query);
            JOptionPane.showMessageDialog(this, "Deleted Successfully");
            this.setVisible(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class updateStudent extends JFrame implements ActionListener {
    String usn;
    JLabel heading, instruction, nameLabel, semSecLabel, dobLabel;
    JTextField nameTF;
    JComboBox sem, sec, date, month, year;
    JButton update, cancel;

    updateStudent(String usn) {
        this.usn = usn;
        heading = new JLabel("UPDATE STUDENT DETAILS");
        heading.setBounds(100, 10, 300, 30);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 17));
        heading.setForeground(Color.BLUE);
        add(heading);

        nameLabel = new JLabel("NAME");
        nameLabel.setBounds(40, 50, 60, 30);
        nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        add(nameLabel);

        nameTF = new JTextField();
        nameTF.setBounds(200, 50, 180, 30);
        add(nameTF);

        semSecLabel = new JLabel("DATE OF BIRTH");
        semSecLabel.setBounds(40, 90, 150, 30);
        semSecLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        add(semSecLabel);

        // Getting Date Element Arrays
        String dates[] = new String[32];
        dates[0] = "--";
        for (int j = 1, d = 1; j < 32; j++) {
            dates[j] = d + "";
            d++;
        }
        String months[] = {"---", "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        String years[] = new String[26];
        years[0] = "----";
        for (int j = 1, y = 1995; j < 25; j++) {
            years[j] = y + "";
            y++;
        }

        // Birth Date Form
        date = new JComboBox(dates);
        date.setBounds(200, 90, 40, 30);
        add(date);

        month = new JComboBox(months);
        month.setBounds(250, 90, 60, 30);
        add(month);

        year = new JComboBox(years);
        year.setBounds(320, 90, 60, 30);
        add(year);

        dobLabel = new JLabel("SEM AND SEC");
        dobLabel.setBounds(40, 130, 150, 30);
        dobLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        add(dobLabel);

        String semesters[] = {"-", "1", "2", "3", "4", "5", "6", "7", "8"};
        String sections[] = {"-", "A", "B", "C", "D", "E"};

        sem = new JComboBox(semesters);
        sem.setBounds(200, 130, 40, 30);
        add(sem);

        sec = new JComboBox(sections);
        sec.setBounds(250, 130, 40, 30);
        add(sec);

        instruction = new JLabel("Only fill the fields that are needed to be updated");
        instruction.setFont(new Font("Tahoma", Font.PLAIN, 13));
        instruction.setForeground(Color.RED);
        instruction.setBounds(40, 165, 500, 30);
        add(instruction);

        update = new JButton("UPDATE");
        update.setBackground(Color.BLACK);
        update.setForeground(Color.WHITE);
        update.setBounds(40, 200, 100, 30);
        update.addActionListener(this);
        add(update);

        cancel = new JButton("CANCEL");
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.setBounds(280, 200, 100, 30);
        cancel.addActionListener(this);
        add(cancel);

        setLayout(null);
        setIconImage(Common.frameIcon.getImage());
        getContentPane().setBackground(Color.WHITE);
        setBounds(400, 200, 420, 280);
        setVisible(true);
    }

    String fetchQuery() {
        String query = "UPDATE STUDENT SET";
        String condition = " WHERE USN = '" + usn + "'";
        String name = nameTF.getText();
        String semester = String.valueOf(sem.getSelectedItem());
        String section = String.valueOf(sec.getSelectedItem());
        String dd = String.valueOf(date.getSelectedItem());
        String mm = String.valueOf(month.getSelectedIndex());
        String yyyy = String.valueOf(year.getSelectedItem());
        Boolean updated = false;

        if (!name.equals("")) {
            query += " NAME = '" + name + "'";
            updated = true;
        }
        if (!semester.equals("-") && !section.equals("-")) {
            if (updated) {
                query += ", ";
            }
            query += " SEM = " + semester + ", SEC = '" + section + "'";
            updated = true;
        }
        if (!dd.equals("--") && !mm.equals("---") && !yyyy.equals("----")) {
            if (updated) {
                query += ", ";
            }
            query += " DATEOFBIRTH = '" + yyyy + "-" + mm + "-" + dd + "'";
            updated = true;
        }
        query += condition;
        if (updated) {
            return query;
        } else {
            return null;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == update) {
            System.out.println(fetchQuery());
            Conn conn = new Conn();
            try {
                conn.statement.executeUpdate(fetchQuery());
                JOptionPane.showMessageDialog(null, "Updated Successfully");
                this.setVisible(false);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "SQL Error Occurred");
                this.setVisible(false);
                ex.printStackTrace();
            }

        } else if (e.getSource() == cancel) {
            this.setVisible(false);
        }
    }
}

