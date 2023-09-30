package com.miniproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FacultyDashboard extends JFrame implements ActionListener {
    JMenuBar menuBar;
    JLabel userLabel;
    JMenuItem home, viewScores, viewAttendance, addScores, addAttendance, credits, logOut;
    JMenu userMenu, viewMenu, addMenu;

    FacultyDashboard() {

        menuBar = new JMenuBar();
        add(menuBar);

//        home = new JMenuItem("Home");
//        home.addActionListener(this);
//        menuBar.add(home);

        viewMenu = new JMenu("View");
        menuBar.add(viewMenu);

        addMenu = new JMenu("Add");
        menuBar.add(addMenu);

        viewScores = new JMenuItem("View Scores");
        viewScores.addActionListener(this);
        viewMenu.add(viewScores);

        viewAttendance = new JMenuItem("View Attendance");
        viewAttendance.addActionListener(this);
        viewMenu.add(viewAttendance);

        addScores = new JMenuItem("Add Scores");
        addScores.addActionListener(this);
        addMenu.add(addScores);

        addAttendance = new JMenuItem("Add Attendance");
        addAttendance.addActionListener(this);
        addMenu.add(addAttendance);

        userMenu = new JMenu("User");
        menuBar.add(userMenu);

        credits = new JMenuItem("Credits");
        credits.addActionListener(this);
        userMenu.add(credits);

        logOut = new JMenuItem("Log Out");
        logOut.addActionListener(this);
        userMenu.add(logOut);

        menuBar.setBounds(0, 0, 120, 30);

        ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("com/miniproject/icons/jssateb.png"));
        Image scaledImage = imageIcon.getImage().getScaledInstance(1900, 1000, Image.SCALE_DEFAULT);
        ImageIcon finalImage = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(finalImage);
        imageLabel.setBounds(0, 0, 1560, 800);
        add(imageLabel);

//        JLabel welcomeLabel = new JLabel("JSS Academy of Technical Education Welcomes You");
//        welcomeLabel.setBounds(400, 50, 800, 50);
//        welcomeLabel.setForeground(Color.BLACK);
//        welcomeLabel.setFont(new Font("Book Antiqua", Font.PLAIN, 30));
//        imageLabel.add(welcomeLabel);

        ImageIcon jssLogo = new ImageIcon(ClassLoader.getSystemResource("com/miniproject/icons/jssate.png"));
        Image scaledLogo = jssLogo.getImage().getScaledInstance(338, 128, Image.SCALE_DEFAULT);
        ImageIcon scaledJssLogo = new ImageIcon(scaledLogo);
        JLabel jssLabel = new JLabel(scaledJssLogo);
        jssLabel.setBounds(650, 50, 338, 128);
        imageLabel.add(jssLabel);

        fetchFactId();

        JLabel userType = new JLabel("FACULTY");
        userType.setBounds(1411, 140, 100, 30);
        userType.setFont(new Font("Tahoma", Font.PLAIN, 14));
        userType.setForeground(Color.BLUE);
        imageLabel.add(userType);

        JLabel userName = new JLabel(Common.personName);
        userName.setBounds(1365, 155, 150, 30);
        userName.setHorizontalAlignment(SwingConstants.CENTER);
        userName.setFont(new Font("Tahoma", Font.PLAIN, 11));
        userName.setForeground(Color.BLUE);
        imageLabel.add(userName);

        JLabel userIdentifier = new JLabel(Common.factId);
        userIdentifier.setBounds(1365, 170, 150, 30);
        userIdentifier.setHorizontalAlignment(SwingConstants.CENTER);
        userIdentifier.setFont(new Font("Tahoma", Font.PLAIN, 11));
        userIdentifier.setForeground(Color.BLUE);
        imageLabel.add(userIdentifier);

        ImageIcon userIcon = new ImageIcon(ClassLoader.getSystemResource("com/miniproject/icons/user.png"));
        Image scaledIcon = userIcon.getImage().getScaledInstance(175, 175, Image.SCALE_SMOOTH);
        ImageIcon scaledUserIcon = new ImageIcon(scaledIcon);
        userLabel = new JLabel(scaledUserIcon);
        userLabel.setBounds(1350, 30, 180, 180);
        imageLabel.add(userLabel);


        setLayout(null);
        setIconImage(Common.frameIcon.getImage());
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Used to set JFrame to full screen
        // setUndecorated(true); /*Removes Window Navigation Buttons*/
        // setBounds(0,0,1910, 1000); /*Used if Extended State is not used */
        setVisible(true);
    }

    void fetchFactId() {
        String query = "SELECT FACTID, DEPARTMENT, NAME FROM FACULTY WHERE USERID = '" + Common.userId + "'";
        Conn conn = new Conn();
        try {
            ResultSet resultSet = conn.statement.executeQuery(query);
            if (resultSet.next()) {
                Common.factId = resultSet.getString(1);
                Common.dept = resultSet.getString(2);
                Common.personName = resultSet.getString(3);
            }
            System.out.println(Common.factId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add Faculty")) {
            new AddFaculty().setVisible(true);
        } else if (e.getActionCommand().equals("Add Student")) {
            new AddStudents().setVisible(true);
        } else if (e.getActionCommand().equals("View Scores")) {
            new ScoresInfo().setVisible(true);
        } else if (e.getActionCommand().equals("View Attendance")) {
            new AttendanceInfo().setVisible(true);
        } else if (e.getActionCommand().equals("Add Scores")) {
            new AddMarks().setVisible(true);
        } else if (e.getActionCommand().equals("Add Attendance")) {
            new AddAttendance().setVisible(true);
        } else if (e.getActionCommand().equals("Credits")) {
            new Credits();
        } else if (e.getActionCommand().equals("Log Out")) {
            new Login().setVisible(true);
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new FacultyDashboard();
    }
}
