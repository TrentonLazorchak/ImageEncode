package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReplayResultScreen extends JFrame {
    public ReplayResultScreen(String title) {
        super(title);

        // set layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        // fonts
        Font titleFont = new Font("Times New Roman",Font.PLAIN,100);
        Font lblFont = new Font("Times New Roman", Font.PLAIN, 30);

        // create swing components
        JLabel head = new JLabel("Replay Result");
            head.setFont(titleFont);
        //head.setHorizontalAlignment(JLabel.CENTER);
        JLabel orgImgLbl = new JLabel("Original Image");
            orgImgLbl.setFont(lblFont);

        JLabel decodedLbl = new JLabel();
        boolean isEncImg = ReplayScreen.fullReplayPath.contains("encodedImg-");
        boolean isEncMsg = ReplayScreen.fullReplayPath.contains("encodedMsg-");
        // set label based on whether encoded by image or message
        if(isEncImg) {
            decodedLbl.setText("Decoded image");
        } else if(isEncMsg) {
            decodedLbl.setText("Decoded message");
        } else {
            decodedLbl.setText("Decoded msg/img");
        }
        decodedLbl.setFont(lblFont);

        // base image
        ImageIcon baseIcon = new ImageIcon(ReplayScreen.fullReplayPath);
        Image image = baseIcon.getImage(); // transform it
        Image newImg = image.getScaledInstance(200, 200,  Image.SCALE_SMOOTH); // scale it the smooth way
        baseIcon = new ImageIcon(newImg);  // transform it back
        JLabel baseImg = new JLabel(baseIcon);

        final Dimension size = new Dimension(200,200);
        baseImg.setMinimumSize(size);
        baseImg.setPreferredSize(size);
        baseImg.setMaximumSize(size);
        // decoded image
        ImageIcon decIcon = new ImageIcon(ReplayScreen.fullReplayPath); //TODO: Change this to EncodeImage.blah
        Image decImage = decIcon.getImage(); // transform it
        Image newerImg = decImage.getScaledInstance(200, 200,  Image.SCALE_SMOOTH); // scale it the smooth way
        decIcon = new ImageIcon(newerImg);  // transform it back
        JLabel decImg = new JLabel(decIcon);

        decImg.setMinimumSize(size);
        decImg.setPreferredSize(size);
        decImg.setMaximumSize(size);

        // decoded message
        JTextArea decMsg = new JTextArea();
        decMsg.setEditable(false);
        Dimension msgSize = new Dimension(550, 200);
        decMsg.setMinimumSize(msgSize);
        decMsg.setPreferredSize(msgSize);
        decMsg.setMaximumSize(msgSize);
        decMsg.setWrapStyleWord(true);
        decMsg.setLineWrap(true);
        decMsg.setText(EncodeMessage.decMsg);
        JScrollPane msgScroll = new JScrollPane(decMsg);
        Dimension scrollSize = new Dimension(550, 200);
        msgScroll.setMinimumSize(scrollSize);
        msgScroll.setPreferredSize(scrollSize);
        msgScroll.setMaximumSize(scrollSize);


        // buttons
        JButton replayAnother = new JButton("REPLAY ANOTHER");
        JButton home = new JButton("HOME");

        //// Add components to frame //////////////////////////////////////////////////////////////
        gc.weightx = 1;
        gc.weighty = 1.0;

        gc.anchor = GridBagConstraints.CENTER;
        gc.gridx = 1;
        gc.gridy = 0;
        add(head, gc);

        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.gridx = 0;
        gc.gridy = 1;
        add(orgImgLbl, gc);

        gc.anchor = GridBagConstraints.LINE_END;
        gc.gridx = 0;
        gc.gridy = 1;
        add(baseImg, gc);

        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.gridx = 2;
        gc.gridy = 1;
        add(decodedLbl, gc);

        gc.anchor = GridBagConstraints.LINE_START;
        gc.gridx = 2;
        gc.gridy = 1;
        // display decImg if image was encoded with an image, vice versa for decMsg
        if(isEncImg) {
            add(decImg, gc);
        } else if(isEncMsg) {
            add(msgScroll, gc);
        }

        //// Buttons //////////////////////////////////////////////////////////////////////////////
        gc.ipadx = 50;
        gc.ipady = 25;

        gc.anchor = GridBagConstraints.LINE_START;
        gc.gridx = 1;
        gc.gridy = 2;
        add(replayAnother, gc);

        gc.anchor = GridBagConstraints.LINE_END;
        gc.gridx = 1;
        gc.gridy = 2;
        add(home, gc);

        //// Add Behaviors /////////////////////////////////////////////////////////////////////////
        // if decAnother button is pressed
            replayAnother.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Log.LOGGER.info("Decode Another button pressed.");
                // open replay screen
                JFrame replay = new ReplayScreen("Replay");
                replay.setResizable(false);
                replay.setExtendedState(JFrame.MAXIMIZED_BOTH);
                replay.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                replay.setVisible(true);
                Log.LOGGER.info("Replay screen opened.");

                // close replay result screen
                setVisible(false);
                dispose();
                Log.LOGGER.info("Disposed of replay result screen.");
            }
        });

        // if home button pressed
            home.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Log.LOGGER.info("Home button pressed.");
                // open welcome screen
                JFrame welcome = new WelcomeFrame("ImageEncoder");
                welcome.setResizable(false);
                welcome.setExtendedState(JFrame.MAXIMIZED_BOTH);
                welcome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                welcome.setVisible(true);
                Log.LOGGER.info("Welcome screen opened.");

                // close replayresult screen
                setVisible(false);
                dispose();
                Log.LOGGER.info("Disposed of the replay result screen.");
            }
        });
    }
}


