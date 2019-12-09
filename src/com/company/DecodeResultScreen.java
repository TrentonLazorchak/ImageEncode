package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DecodeResultScreen extends JFrame {
    public DecodeResultScreen(String title) {
        super(title);

        // set layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        // fonts
        Font titleFont = new Font("Times New Roman",Font.PLAIN,100);
        Font lblFont = new Font("Times New Roman", Font.PLAIN, 30);

        // create swing components
        JLabel head = new JLabel("Decoded Result");
        head.setFont(titleFont);
        //head.setHorizontalAlignment(JLabel.CENTER);
        JLabel orgImgLbl = new JLabel("Original Image");
        orgImgLbl.setFont(lblFont);

        JLabel decodedLbl = new JLabel();
        boolean isEncImg = DecodeScreen.fullDecPath.contains("encodedImg-");
        boolean isEncMsg = DecodeScreen.fullDecPath.contains("encodedMsg-");
        // set label based on whether encoded by image or message
        if(isEncImg) {
            decodedLbl.setText("Decoded image");
        } else if(isEncMsg) {
            decodedLbl.setText("Decoded message");
        } else {
            decodedLbl.setText("Decoded msg/img");
        }
        decodedLbl.setFont(lblFont);

        // TODO: there should be an if/else for whether to display an img as decoded or msg
        // base image
        ImageIcon baseIcon = new ImageIcon(DecodeScreen.fullDecPath);
        Image image = baseIcon.getImage(); // transform it
        Image newImg = image.getScaledInstance(200, 200,  Image.SCALE_SMOOTH); // scale it the smooth way
        baseIcon = new ImageIcon(newImg);  // transform it back
        JLabel baseImg = new JLabel(baseIcon);

        final Dimension size = new Dimension(200,200);
        baseImg.setMinimumSize(size);
        baseImg.setPreferredSize(size);
        baseImg.setMaximumSize(size);
        // decoded image
        ImageIcon decIcon = new ImageIcon(DecodeScreen.fullDecPath);
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
        Dimension msgSize = new Dimension(500, 100);
        decMsg.setMinimumSize(msgSize);
        decMsg.setPreferredSize(msgSize);
        decMsg.setMaximumSize(msgSize);
        decMsg.setText(EncodeMessage.decMsg);
        JScrollPane msgScroll = new JScrollPane(decMsg);
        Dimension scrollSize = new Dimension(400, 250);
        msgScroll.setMinimumSize(scrollSize);
        msgScroll.setPreferredSize(scrollSize);
        msgScroll.setMaximumSize(scrollSize);


        // buttons
        JButton decAnother = new JButton("DECODE ANOTHER");
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
        add(decAnother, gc);

        gc.anchor = GridBagConstraints.LINE_END;
        gc.gridx = 1;
        gc.gridy = 2;
        add(home, gc);

        //// Add Behaviors /////////////////////////////////////////////////////////////////////////
        // if decAnother button is pressed
        decAnother.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Log.LOGGER.info("Decode Another button pressed.");
                // open decode screen
                JFrame decode = new DecodeScreen("Decode");
                decode.setResizable(false);
                decode.setExtendedState(JFrame.MAXIMIZED_BOTH);
                decode.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                decode.setVisible(true);
                Log.LOGGER.info("Decode screen opened.");

                // close encoded result screen
                setVisible(false);
                dispose();
                Log.LOGGER.info("Disposed of encoded result screen.");
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

                // close decode result screen
                setVisible(false);
                dispose();
                Log.LOGGER.info("Disposed of the decode result screen.");
            }
        });
    }
}
