package com.company;

import javax.swing.*;
import java.awt.*;

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
        JLabel orgImgLbl = new JLabel("Original Image: ");
        orgImgLbl.setFont(lblFont);
        JLabel decodedLbl = new JLabel("Decoded msg/img: "); // TODO: the text of this label changes based on whether image or msg is displayed
        decodedLbl.setFont(lblFont);
        // TODO: the paths to the images need to be found using the decoding backend and replaced for these variables
        String imgPath = "/Users/trentonlazorchak/Desktop/PrototypeUIEncoder/sampleImg.jpg";
        String decPath = "/Users/trentonlazorchak/Desktop/PrototypeUIEncoder/sampleImg.jpg";
        // TODO: there should be an if/else for whether to display an img as decoded or msg
        // base image
        ImageIcon baseIcon = new ImageIcon(imgPath);
        Image image = baseIcon.getImage(); // transform it
        Image newImg = image.getScaledInstance(200, 200,  Image.SCALE_SMOOTH); // scale it the smooth way
        baseIcon = new ImageIcon(newImg);  // transform it back
        JLabel baseImg = new JLabel(baseIcon);

        final Dimension size = new Dimension(200,200);
        baseImg.setMinimumSize(size);
        baseImg.setPreferredSize(size);
        baseImg.setMaximumSize(size);
        // decoded image
        ImageIcon decIcon = new ImageIcon(imgPath);
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

        // buttons
        JButton decAnother = new JButton("DECODE ANOTHER");
        JButton home = new JButton("HOME");

        //// Add components to frame //////////////////////////////////////////////////////////////
        gc.weightx = 1.0;
        gc.weighty = 1.0;

        gc.anchor = GridBagConstraints.CENTER;
        gc.gridx = 1;
        gc.gridy = 0;
        add(head, gc);

        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.gridx = 0;
        gc.gridy = 1;
        add(orgImgLbl, gc);

        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.gridx = 1;
        gc.gridy = 1;
        add(baseImg, gc);

        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.gridx = 1;
        gc.gridy = 1;
        add(decodedLbl, gc);

        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.gridx = 2;
        gc.gridy = 1;
        add(decImg, gc);

        //// Buttons //////////////////////////////////////////////////////////////////////////////
        gc.ipadx = 25;
        gc.ipady = 15;

        gc.anchor = GridBagConstraints.LINE_START;
        gc.gridx = 1;
        gc.gridy = 2;
        add(decAnother, gc);

        gc.anchor = GridBagConstraints.LINE_END;
        gc.gridx = 1;
        gc.gridy = 2;
        add(home, gc);
    }
}
