package com.miniproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboard extends JFrame implements ActionListener {
    JMenuBar menuBar;
    JMenu view, addNew, delete, assign, user;
    JLabel userLabel, jssLabel, imageLabel;
    JMenuItem home, student, faculty, subject; // For MenuBar
    JMenuItem addStudents, addFaculty, addSubject; // For AddNew Menu
    JMenuItem deleteStudent, deleteFaculty, deleteSubject; // For Edit Menu
    JMenuItem assignSubject, enrollClass, unAssign, unEnroll;
    JMenuItem resetPassword, credits, logOut; // For User Menu


    public AdminDashboard() {
        menuBar = new JMenuBar();
        add(menuBar);

//        home = new JMenuItem("Home");
//        home.addActionListener(this);
//        menuBar.add(home);

        view = new JMenu("View");
        menuBar.add(view);

        student = new JMenuItem("Student");
        student.addActionListener(this);
        view.add(student);

        faculty = new JMenuItem("Faculty");
        faculty.addActionListener(this);
        view.add(faculty);

        subject = new JMenuItem("Subject");
        subject.addActionListener(this);
        view.add(subject);

        addNew = new JMenu("Add New");
        menuBar.add(addNew);

        addStudents = new JMenuItem("Add Student");
        addStudents.addActionListener(this);
        addNew.add(addStudents);

        addFaculty = new JMenuItem("Add Faculty");
        addFaculty.addActionListener(this);
        addNew.add(addFaculty);

        addSubject = new JMenuItem("Add Subject");
        addSubject.addActionListener(this);
        addNew.add(addSubject);


        delete = new JMenu("Edit");
        menuBar.add(delete);

        deleteStudent = new JMenuItem("Edit Student");
        deleteStudent.addActionListener(this);
        delete.add(deleteStudent);

        deleteFaculty = new JMenuItem("Edit Faculty");
        deleteFaculty.addActionListener(this);
        delete.add(deleteFaculty);

        deleteSubject = new JMenuItem("Edit Subject");
        deleteSubject.addActionListener(this);
        delete.add(deleteSubject);

        assign = new JMenu("Assign");
        menuBar.add(assign);

        assignSubject = new JMenuItem("Assign Subject");
        assignSubject.addActionListener(this);
        assign.add(assignSubject);

        enrollClass = new JMenuItem("Enroll Class");
        enrollClass.addActionListener(this);
        assign.add(enrollClass);

        unAssign = new JMenuItem("Un-Assign Subject");
        unAssign.addActionListener(this);
        assign.add(unAssign);

        unEnroll = new JMenuItem("Un-Enroll Class");
        unEnroll.addActionListener(this);
        assign.add(unEnroll);

        user = new JMenu("User");
        menuBar.add(user);

        resetPassword = new JMenuItem("Reset Password");
        resetPassword.addActionListener(this);
        user.add(resetPassword);

        credits = new JMenuItem("Credits");
        credits.addActionListener(this);
        user.add(credits);

        logOut = new JMenuItem("Log Out");
        logOut.addActionListener(this);
        user.add(logOut);

        menuBar.setBounds(0, 0, 240, 30);

        ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("com/miniproject/icons/jssateb.png"));
        Image scaledImage = imageIcon.getImage().getScaledInstance(1900, 1000, Image.SCALE_DEFAULT);
        ImageIcon finalImage = new ImageIcon(scaledImage);
        imageLabel = new JLabel(finalImage);
        imageLabel.setBounds(0, 0, 1560, 800);
        add(imageLabel);

        ImageIcon jssLogo = new ImageIcon(ClassLoader.getSystemResource("com/miniproject/icons/jssate.png"));
        Image scaledLogo = jssLogo.getImage().getScaledInstance(338, 128, Image.SCALE_DEFAULT);
        ImageIcon scaledJssLogo = new ImageIcon(scaledLogo);
        jssLabel = new JLabel(scaledJssLogo);
        jssLabel.setBounds(650, 50, 338, 128);
        imageLabel.add(jssLabel);

        JLabel adminLabel = new JLabel("ADMIN");
        adminLabel.setBounds(1404, 175, 60, 30);
        adminLabel.setForeground(Color.BLUE.darker());
        adminLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        imageLabel.add(adminLabel);

        ImageIcon userIcon = new ImageIcon(ClassLoader.getSystemResource("com/miniproject/icons/admin.png"));
        Image scaledIcon = userIcon.getImage().getScaledInstance(175, 175, Image.SCALE_SMOOTH);
        ImageIcon scaledUserIcon = new ImageIcon(scaledIcon);
        userLabel = new JLabel(scaledUserIcon);
        userLabel.setBounds(1340, 30, 180, 180);
        imageLabel.add(userLabel);

        setLayout(null);
        setIconImage(Common.frameIcon.getImage());
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Used to set JFrame to full screen
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add Faculty")) {
            new AddFaculty().setVisible(true);
        } else if (e.getActionCommand().equals("Add Student")) {
            new AddStudents().setVisible(true);
        } else if (e.getActionCommand().equals("Add Subject")) {
            new AddSubject().setVisible(true);
        } else if (e.getActionCommand().equals("Student")) {
            new StudentInfo().setVisible(true);
        } else if (e.getActionCommand().equals("Faculty")) {
            new FacultyInfo().setVisible(true);
        } else if (e.getActionCommand().equals("Subject")) {
            new SubjectInfo().setVisible(true);
        } else if (e.getActionCommand().equals("Edit Student")) {
            new EditStudent().setVisible(true);
        } else if (e.getActionCommand().equals("Edit Faculty")) {
            new EditFaculty().setVisible(true);
        } else if (e.getActionCommand().equals("Edit Subject")) {
            new EditSubject().setVisible(true);
        } else if (e.getActionCommand().equals("Assign Subject")) {
            new AssignSubject().setVisible(true);
        } else if (e.getActionCommand().equals("Enroll Class")) {
            new EnrollClass().setVisible(true);
        } else if (e.getActionCommand().equals("Un-Assign Subject")) {
            new UnAssignSubject().setVisible(true);
        } else if (e.getActionCommand().equals("Un-Enroll Class")) {
            new UnEnrollClass().setVisible(true);
        } else if (e.getActionCommand().equals("Credits")) {
            new Credits();
        } else if (e.getActionCommand().equals("Reset Password")) {
            new ResetUserPassword();
        } else if (e.getActionCommand().equals("Log Out")) {
            new Login().setVisible(true);
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new AdminDashboard().setVisible(true);

    }
}

/*
# JAVA FRAME TWEAKS
* setUndecorated(true); /*Removes Window Navigation Buttons
* setBounds(0,0,1910, 1000); /*Used if Extended State is not used
*/