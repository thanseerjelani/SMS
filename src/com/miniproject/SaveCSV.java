package com.miniproject;

import com.opencsv.CSVWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SaveCSV extends JFrame implements ActionListener {
    JLabel instructionLabel;
    JTextField fileNameTF;
    JButton saveBtn;

    SaveCSV() {
        instructionLabel = new JLabel("Insert Filename to save as CSV:");
        instructionLabel.setBounds(25, 20, 280, 30);
        instructionLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        add(instructionLabel);

        fileNameTF = new JTextField();
        fileNameTF.setBounds(50, 50, 180, 30);
        add(fileNameTF);

        saveBtn = new JButton("Save CSV");
        saveBtn.setBounds(65, 90, 150, 30);
        saveBtn.addActionListener(this);
        add(saveBtn);

        setLayout(null);
        setIconImage(Common.frameIcon.getImage());
        getContentPane().setBackground(Color.WHITE);
        setBounds(600, 200, 300, 180);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveBtn) {
            String fileName = fileNameTF.getText();
            if (!fileName.equals("")) {
                saveCSV(fileName);
            } else {
                JOptionPane.showMessageDialog(null, "Please Enter a Filename!");
            }
        }
    }

    public void saveCSV(String fileName) {
        String path = "D:\\";
        String file = fileName + ".csv";
        String pathToFile = path + file;
        Boolean includeHeaders = true;
        Conn conn = new Conn();
        String query = Common.recentQuery;
        if (!query.equals("")) {
            try {
                FileWriter filewriter = new FileWriter(pathToFile);
                CSVWriter writer = new CSVWriter(filewriter);
                ResultSet rs = conn.statement.executeQuery(query);
                writer.writeAll(rs, includeHeaders);
                filewriter.close();
                JOptionPane.showMessageDialog(null, "CSV Saved Successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Common.recentQuery = "SELECT * FROM STUDENT";
        new SaveCSV();
    }
}
