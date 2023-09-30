package com.miniproject;

import javax.swing.*;

public class Credits extends JFrame {
    static String subCode = "";
    static String subject = "\nFULL STACK WEB DEVELOPMENT PROJECT";
    static String title = "\n     STUDENT MANAGEMENT SYSTEM";
    static String madeBy = "\n                           MADE BY:\n       1JS20CS171 - Thanseer Jelani";

    Credits() {
        setIconImage(Common.frameIcon.getImage());
        JOptionPane.showMessageDialog(null, subCode + subject + title + madeBy);
    }
}
