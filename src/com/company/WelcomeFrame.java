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

        // create Swing components
        JLabel imageEncoder = new JLabel("Image Encoder");
        imageEncoder.setFont(new Font("Times New Roman",Font.PLAIN,100));
        JButton encode = new JButton("Encode");
        JButton decode = new JButton("Decode");
        JButton replay = new JButton("Replay");
        JLabel credit = new JLabel("                                Created by Trenton Lazorchak, Jake Howell, and Olanrewaju Arbisala.");
        credit.setFont(new Font("Times New Roman", Font.PLAIN, 12));

        // add Swing components to the gui
        //// Title /////////////////////////////////////////
        gc.anchor = GridBagConstraints.PAGE_END;
        gc.weighty = 1.0;
        gc.weightx = 1.0;

        gc.gridx = 1;
        gc.gridy = 0;
        add(imageEncoder, gc);

        //// Buttons ///////////////////////////////////////
        gc.ipadx = 100;
        gc.ipady = 50;

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
        gc.gridy = 3;
        add(credit, gc);

        // Add behaviors
        // Encode button pressed
        encode.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // Set welcome screen to not visible
                setVisible(false);
                dispose();

                // Create Encode Screen
                JFrame encodeScreen = new EncodeScreen("Encode");
                encodeScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
                encodeScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                encodeScreen.setVisible(true);
            }
        });

        // Decode button pressed
        decode.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // Set welcome screen to not visible
                setVisible(false);
                dispose();

                // Create decode screen
                JFrame decodeScreen = new DecodeScreen("Decode");
                decodeScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
                decodeScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                decodeScreen.setVisible(true);
            }
        });

        // Replay button pressed
        replay.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // Set welcome screen to not visible
                setVisible(false);
                dispose();

                // Create replay screen
                JFrame replayScreen = new ReplayScreen("Encode");
                replayScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
                replayScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                replayScreen.setVisible(true);
            }
        });
    }
}
