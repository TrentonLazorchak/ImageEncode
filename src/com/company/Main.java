package com.company;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame welcome = new WelcomeFrame("ImageEncoder");
                welcome.setExtendedState(JFrame.MAXIMIZED_BOTH);
                welcome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                welcome.setVisible(true);
            }
        });

    }
}
