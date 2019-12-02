package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeFrame extends JFrame {
    public WelcomeFrame(String title) {
        super(title);

        // set layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        // fonts
        Font titleFont = new Font("Times New Roman", Font.PLAIN, 200);
        Font btnFont = new Font("Times New Roman", Font.PLAIN, 50);
        Font creditFont = new Font("Times New Roman", Font.PLAIN, 12);

        // sizes
        //Dimension titleSize = new Dimension(2000,250);

        // create Swing components
        JLabel imageEncoder = new JLabel("<html><center>Image<p>Encoder</center></html>");
        imageEncoder.setFont(titleFont);

        JButton encode = new JButton("Encode");
        encode.setFont(btnFont);

        JButton decode = new JButton("Decode");
        decode.setFont(btnFont);

        JButton replay = new JButton("Replay");
        replay.setFont(btnFont);

        JLabel credit = new JLabel("<html><center>Created by Trenton Lazorchak, Jake Howell, and Olanrewaju Arbisala.</center></html>");
        credit.setFont(creditFont);

        // add Swing components to the gui
        //// Title /////////////////////////////////////////
        gc.anchor = GridBagConstraints.PAGE_END;
        gc.weighty = 1.0;
        gc.weightx = 1.0;

        gc.gridx = 1;
        gc.gridy = 0;
        add(imageEncoder, gc);

        //// Buttons ///////////////////////////////////////
        gc.ipadx = 200;
        gc.ipady = 150;
        gc.weightx = 0;

        gc.anchor = GridBagConstraints.LAST_LINE_END;
        gc.gridx = 0;
        gc.gridy = 1;
        add(encode, gc);

        gc.anchor = GridBagConstraints.PAGE_END;
        gc.gridx = 1;
        gc.gridy = 1;
        add(decode, gc);

        gc.anchor = GridBagConstraints.LAST_LINE_START;
        gc.gridx = 2;
        gc.gridy = 1;
        add(replay, gc);

        //// Credits ////////////////////////////////////////
        gc.anchor = GridBagConstraints.PAGE_END;
        gc.gridx = 1;
        gc.gridy = 2;
        add(credit, gc);

        // Add behaviors
        // Encode button pressed
        encode.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // Create Encode Screen
                JFrame encodeScreen = new EncodeScreen("Encode");
                encodeScreen.setResizable(false);
                encodeScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
                encodeScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                encodeScreen.setVisible(true);

                // Close welcome screen
                setVisible(false);
                dispose();
            }
        });

        // Decode button pressed
        decode.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // Create decode screen
                JFrame decodeScreen = new DecodeScreen("Decode");
                decodeScreen.setResizable(false);
                decodeScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
                decodeScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                decodeScreen.setVisible(true);

                // Close welcome screen
                setVisible(false);
                dispose();
            }
        });

        // Replay button pressed
        replay.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // Create replay screen
                JFrame replayScreen = new ReplayScreen("Replay");
                replayScreen.setResizable(false);
                replayScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
                replayScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                replayScreen.setVisible(true);

                // Close welcome screen
                setVisible(false);
                dispose();
            }
        });
    }
}
