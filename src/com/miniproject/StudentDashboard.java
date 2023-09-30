package com.miniproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDashboard extends JFrame implements ActionListener {
    JMenuBar menuBar;
    JLabel userLabel;
    JMenuItem home, scores, attendance, credits, logOut;
    JMenu user;

    StudentDashboard() {
        menuBar = new JMenuBar();
        add(menuBar);

//        home = new JMenuItem("Home");
//        home.addActionListener(this);
//        menuBar.add(home);

        scores = new JMenuItem("Scores");
        scores.addActionListener(this);
        menuBar.add(scores);

        attendance = new JMenuItem("Attendance");
        attendance.addActionListener(this);
        menuBar.add(attendance);

        user = new JMenu("User");
        menuBar.add(user);

        credits = new JMenuItem("Credits");
        credits.addActionListener(this);
        user.add(credits);

        logOut = new JMenuItem("Log Out");
        logOut.addActionListener(this);
        user.add(logOut);

        menuBar.setBounds(0, 0, 180, 30);

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

        fetchUsn();

        JLabel userType = new JLabel("STUDENT");
        userType.setBounds(1410, 140, 100, 30);
        userType.setFont(new Font("Tahoma", Font.PLAIN, 14));
        userType.setForeground(Color.BLUE);
        imageLabel.add(userType);

        JLabel userName = new JLabel(Common.personName);
        userName.setBounds(1365, 155, 150, 30);
        userName.setHorizontalAlignment(SwingConstants.CENTER);
        userName.setFont(new Font("Tahoma", Font.PLAIN, 11));
        userName.setForeground(Color.BLUE);
        imageLabel.add(userName);

        JLabel userIdentifier = new JLabel(Common.usn);
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
        /* setUndecorated(true); Removes Window Navigation Buttons
        setBounds(0,0,1910, 1000); Used if Extended State is not used */
        setVisible(true);
    }

    void fetchUsn() {
        String query = "SELECT USN, NAME FROM STUDENT WHERE USERID = '" + Common.userId + "'";
        Conn conn = new Conn();
        try {
            ResultSet resultSet = conn.statement.executeQuery(query);
            if (resultSet.next()) {
                Common.usn = resultSet.getString(1);
                Common.personName = resultSet.getString(2);
            }
            System.out.println(Common.usn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Scores")) {
            new StudentScoreInfo().setVisible(true);
        } else if (e.getActionCommand().equals("Attendance")) {
            new StudentAttendanceInfo().setVisible(true);
        } else if (e.getActionCommand().equals("Credits")) {
            new Credits();
        } else if (e.getActionCommand().equals("Log Out")) {
            new Login().setVisible(true);
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new StudentDashboard();
    }
}
