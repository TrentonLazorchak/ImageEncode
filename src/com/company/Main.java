package com.company;

import javax.swing.*;
import java.util.logging.Logger;
// set logger

public class Main {

    public static void main(String[] args) {

        Log.setUpLogger();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame welcome = new WelcomeFrame("ImageEncoder");
                welcome.setResizable(false);
                welcome.setExtendedState(JFrame.MAXIMIZED_BOTH);
                welcome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                welcome.setVisible(true);

                Log.LOGGER.info("Image Encoder app started");
                Log.LOGGER.info("Welcome Screen Opened");
            }
        });

    }
}
