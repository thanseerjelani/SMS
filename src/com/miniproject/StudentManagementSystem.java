package com.miniproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentManagementSystem extends JFrame implements ActionListener {

    StudentManagementSystem() {
        ImageIcon icon1 = new ImageIcon(ClassLoader.getSystemResource("com/miniproject/icons/jss.jpg"));
        JLabel label1 = new JLabel(icon1);
        label1.setBounds(0, 0, 1024, 451);
        add(label1);

        JLabel label2 = new JLabel("Student Management System");
        label2.setBounds(30, 5, 1000, 70);
        label2.setForeground(Color.BLACK);
        label2.setFont(new Font("serif", Font.PLAIN, 55));
        label1.add(label2);

        JButton button1 = new JButton("Next");
        button1.setBounds(900, 10, 100, 30);
        button1.addActionListener(this);
        label1.add(button1);

        setBounds(200, 200, 1030, 480);
        setIconImage(Common.frameIcon.getImage());
        setLayout(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new Login().setVisible(true);
        this.setVisible(false);
    }

    public static void main(String[] args) {
        new StudentManagementSystem();
    }


}
/*
# CODE FOR BLINKING TEXT AND ADDITIONAL SETTINGS FOR JAVA FRAME
        while (true){
            label2.setVisible(false);
            try {
                Thread.sleep(500);
            } catch (Exception e){
                e.printStackTrace();
            }
            label2.setVisible(true);
            try{
                Thread.sleep(500);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
# CODE FOR SETTING PARAMETERS FOR FRAME
        setSize(400, 400);
        setLocation(300, 300);

# CODE FOR GETTING TABLE MODEL FROM SQL RESULT SET
    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);

    }

# CODE FOR COLORING BUTTONS
        button1.setBackground(Color.BLACK);
        button1.setForeground(Color.WHITE);
 */