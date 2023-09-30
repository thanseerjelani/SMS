package com.miniproject;

import com.opencsv.CSVWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.sql.SQLException;


// TO BE DELETED: Temporary Class for Testing and Debugging throughout Development
public class ProgramTest {

    public static void main(String[] args) {
        Common.recentQuery = "SELECT * FROM STUDENT";
        new TestingFrame();
    }

    private static void executeQuery() {
        Conn conn = new Conn();
        String query = "SELECT * FROM STUDENT";

        try {
            ResultSet rs = conn.statement.executeQuery(query);
            rs.next();
            rs.next();
            String val = rs.getString(3);
            System.out.println("Value: " + val);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class TestingFrame extends JFrame implements ActionListener {
    JButton saveBtn;

    TestingFrame() {
        ImageIcon saveIcon = new ImageIcon(ClassLoader.getSystemResource("com/miniproject/icons/download_icon.png"));
        Image scaledImage = saveIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(scaledImage);

        saveBtn = new JButton(icon);
        saveBtn.setBounds(35, 35, 30, 30);
        saveBtn.addActionListener(this);
        add(saveBtn);

        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        setBounds(400, 200, 300, 200);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveBtn) {
            System.out.println("Button Clicked");
            downloadCSV();
        }
    }

    public void downloadCSV() {
        Conn conn = new Conn();
        String query = Common.recentQuery;
        Boolean includeHeaders = true;
        try {
            FileWriter filewriter = new FileWriter("database.csv");
            CSVWriter writer = new CSVWriter(filewriter);
            ResultSet rs = conn.statement.executeQuery(query);
            writer.writeAll(rs, includeHeaders);
            filewriter.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}