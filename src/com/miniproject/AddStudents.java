package com.miniproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddStudents extends JFrame implements ActionListener {
    JTextField usnTF, nameTF, enrollYearTF, usernameTF;
    JPasswordField passwordTF;
    JRadioButton maleRB, femaleRB;
    JComboBox date, month, year, department, sem, sec;
    JButton addButton, cancelButton;

    AddStudents() {
        JLabel nameLabel = new JLabel("NAME");
        nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        nameLabel.setBounds(60, 30, 120, 30);
        add(nameLabel);

        nameTF = new JTextField();
        nameTF.setBounds(200, 30, 150, 30);
        add(nameTF);


        // Getting Date Element Arrays
        String dates[] = new String[31];
        for (int j = 0, d = 1; j < 31; j++) {
            dates[j] = d + "";
            d++;
        }
        String months[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        String years[] = new String[25];
        for (int j = 0, y = 1995; j < 25; j++) {
            years[j] = y + "";
            y++;
        }

        // Birth Date Form
        JLabel dateLabel = new JLabel("BIRTHDATE");
        dateLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        dateLabel.setBounds(60, 70, 120, 30);
        add(dateLabel);

        date = new JComboBox(dates);
        date.setBounds(200, 70, 40, 30);
        add(date);

        month = new JComboBox(months);
        month.setBounds(250, 70, 60, 30);
        add(month);

        year = new JComboBox(years);
        year.setBounds(320, 70, 60, 30);
        add(year);


        // USN
        JLabel usnLabel = new JLabel("USN");
        usnLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        usnLabel.setBounds(60, 110, 120, 30);
        add(usnLabel);

        usnTF = new JTextField();
        usnTF.setBounds(200, 110, 150, 30);
        add(usnTF);

        JLabel enrollYearLabel = new JLabel("ENROLL YEAR");
        enrollYearLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        enrollYearLabel.setBounds(60, 150, 120, 30);
        add(enrollYearLabel);

        enrollYearTF = new JTextField();
        enrollYearTF.setBounds(200, 150, 150, 30);
        add(enrollYearTF);

        JLabel genderLabel = new JLabel("GENDER");
        genderLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        genderLabel.setBounds(60, 190, 120, 30);
        add(genderLabel);

        maleRB = new JRadioButton("Male");
        maleRB.setBounds(200, 190, 60, 30);
        maleRB.setFont(new Font("Tahoma", Font.PLAIN, 17));
        maleRB.setBackground(Color.WHITE);
        add(maleRB);

        femaleRB = new JRadioButton("Female");
        femaleRB.setBounds(270, 190, 90, 30);
        femaleRB.setFont(new Font("Tahoma", Font.PLAIN, 17));
        femaleRB.setBackground(Color.WHITE);
        add(femaleRB);

        ButtonGroup genderButtonGrp = new ButtonGroup();
        genderButtonGrp.add(maleRB);
        genderButtonGrp.add(femaleRB);

        JLabel usernameLabel = new JLabel("USERNAME");
        usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        usernameLabel.setBounds(60, 230, 120, 30);
        add(usernameLabel);

        usernameTF = new JTextField();
        usernameTF.setBounds(200, 230, 150, 30);
        add(usernameTF);

        JLabel passwordLabel = new JLabel("PASSWORD");
        passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        passwordLabel.setBounds(60, 270, 120, 30);
        add(passwordLabel);

        passwordTF = new JPasswordField();
        passwordTF.setBounds(200, 270, 150, 30);
        add(passwordTF);

        String departments[] = {
                "Computer Science and Engineering",
                "Information Science and Engineering",
                "Electronics and Communication Engineering",
                "Instrumentation and Electrical Engineering"
        };

        JLabel departmentLabel = new JLabel("DEPARTMENT");
        departmentLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        departmentLabel.setBounds(60, 310, 120, 30);
        add(departmentLabel);

        department = new JComboBox(departments);
        department.setBounds(200, 310, 280, 30);
        add(department);

        ImageIcon studentIcon = new ImageIcon(ClassLoader.getSystemResource("com/miniproject/icons/student.png"));
        Image scaledImage = studentIcon.getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(scaledImage);
        JLabel facultyImageLabel = new JLabel(icon);
        facultyImageLabel.setBounds(500, 35, 300, 300);
        add(facultyImageLabel);

        String semesters[] = {"1", "2", "3", "4", "5", "6", "7", "8"};
        String sections[] = {"A", "B", "C", "D", "E"};

        JLabel semesterLabel = new JLabel("SEMESTER");
        semesterLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        semesterLabel.setBounds(60, 350, 120, 30);
        add(semesterLabel);

        sem = new JComboBox(semesters);
        sem.setBounds(200, 350, 50, 30);
        add(sem);

        JLabel sectionLabel = new JLabel("SECTION");
        sectionLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        sectionLabel.setBounds(60, 390, 120, 30);
        add(sectionLabel);

        sec = new JComboBox(sections);
        sec.setBounds(200, 390, 50, 30);
        add(sec);

        JLabel frameLabel = new JLabel("ENTER STUDENT DETAILS");
        frameLabel.setBounds(520, 10, 250, 30);
        frameLabel.setForeground(Color.BLUE);
        frameLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(frameLabel);

        addButton = new JButton("ADD STUDENT");
        addButton.setBounds(60, 440, 130, 30);
        addButton.setBackground(Color.BLACK);
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(this);
        add(addButton);

        cancelButton = new JButton("CANCEL");
        cancelButton.setBounds(210, 440, 100, 30);
        cancelButton.setBackground(Color.BLACK);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(this);
        add(cancelButton);

        setLayout(null);
        setIconImage(Common.frameIcon.getImage());
        getContentPane().setBackground(Color.WHITE);
        setBounds(400, 200, 800, 550);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String name = nameTF.getText();
            String dob = year.getSelectedItem() + "-" + (month.getSelectedIndex() + 1) + "-" + date.getSelectedItem();
            String usn = usnTF.getText();
            String enrollYear = enrollYearTF.getText();
            String username = usernameTF.getText();
            String password = String.valueOf(passwordTF.getPassword());
            String dept = String.valueOf(department.getSelectedItem());
            String gender = null;
            String semester = String.valueOf(sem.getSelectedItem());
            String section = String.valueOf(sec.getSelectedItem());
            if (maleRB.isSelected()) {
                gender = "Male";
            } else if (femaleRB.isSelected()) {
                gender = "Female";
            }
//            System.out.println("name: " + name + "\ndob: " + dob + "\nenrollYear: " + enrollYear);
//            System.out.println("username: " + username + "\npassword: " + password + "\ndept: " + dept + "\ngender: " + gender);


            Conn conn = new Conn();

            String userQuery = "INSERT INTO USERS(USERNAME, PASSWORD, USERTYPE) VALUES ('" + username + "', '" + password + "', 'STUDENT')";
            try {
                conn.statement.executeUpdate(userQuery);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            String userIDQuery = "SELECT USERID FROM USERS WHERE USERNAME = '" + username + "'";
            int userId = 1;
            try {
                ResultSet userIdRS = conn.statement.executeQuery(userIDQuery);
                userIdRS.next();
                String id = userIdRS.getString(1);
                userId = Integer.parseInt(id);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            String studentQuery = "INSERT INTO STUDENT VALUES('" + userId + "', '" + usn + "','" + name + "','" +
                    gender + "','" + dob + "', '" + enrollYear + "', '" + dept + "', '" + semester + "', '" + section + "')";
            try {
                conn.statement.executeUpdate(studentQuery);
                JOptionPane.showMessageDialog(null, "New Student Added");
                this.setVisible(false);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } else if (e.getSource() == cancelButton) {
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new AddStudents();
    }
}
