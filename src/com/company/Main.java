package com.company;

import javax.swing.*;
import java.util.logging.Logger;

public class Main {
    // create a logger variable
    static final  Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame welcome = new WelcomeFrame("ImageEncoder");
                welcome.setResizable(false);
                welcome.setExtendedState(JFrame.MAXIMIZED_BOTH);
                welcome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                welcome.setVisible(true);

                LOGGER.info("Image Encoder app started");
                LOGGER.info("Welcome Screen Opened");
            }
        });

    }
}
