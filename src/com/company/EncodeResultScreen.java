package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class EncodeResultScreen extends JFrame {
    public EncodeResultScreen(String title) {
        super(title);

        // set layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        // default font
        Font font = new Font("Times New Roman",Font.PLAIN,50);

        // swing components
        // title
        JLabel encodedResultLbl = new JLabel("Encoded Result");
        encodedResultLbl.setFont(font);

        // image
        // TODO: Take the stored value of the encoded image and substitute below for filePath
        // TODO: this String needs to be public and static in encoding backend or have accessor method
        String filePath = "/Users/trentonlazorchak/Desktop/PrototypeUIEncoder/sampleImg.jpg";
        ImageIcon image = new ImageIcon(filePath);
        JLabel img = new JLabel(image);
        Dimension size = new Dimension(500,500);
        img.setMinimumSize(size);
        img.setPreferredSize(size);
        img.setMaximumSize(size);

        // buttons
        JButton save = new JButton("SAVE");
        JButton home = new JButton("HOME");
        JButton encodeAnother = new JButton("ENCODE ANOTHER");

        //// Add components to frame //////////////////////////////////////////////////
        gc.weightx = 0;
        gc.weighty = 0.25;

        gc.anchor = GridBagConstraints.CENTER;
        gc.gridx = 1;
        gc.gridy = 0;
        add(encodedResultLbl, gc);

        gc.anchor = GridBagConstraints.PAGE_START;
        gc.gridx = 1;
        gc.gridy = 1;
        add(img, gc);

        gc.ipadx = 50;
        gc.ipady = 25;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.gridx = 0;
        gc.gridy = 2;
        add(save, gc);

        gc.anchor = GridBagConstraints.PAGE_START;
        gc.gridx = 1;
        add(home, gc);

        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.gridx = 2;
        add(encodeAnother, gc);

        //// Add Behaviors ///////////////////////////////////
        // save button pressed
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: open a file select screen so the user can select where to save the file to

                // TODO: also give option to save image so it can be replayed
            }
        });

        // home button pressed
        home.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // open welcome screen
                JFrame welcome = new WelcomeFrame("ImageEncoder");
                welcome.setResizable(false);
                welcome.setExtendedState(JFrame.MAXIMIZED_BOTH);
                welcome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                welcome.setVisible(true);

                // close encoded result screen
                setVisible(false);
                dispose();

                // TODO: set the value of the encoded image variable to null
            }
        });

        // encode another button pressed
        encodeAnother.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // open encode screen
                JFrame encodeScreen = new EncodeScreen("Encode");
                encodeScreen.setResizable(false);
                encodeScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
                encodeScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                encodeScreen.setVisible(true);

                // close encoded result screen
                setVisible(false);
                dispose();

                // TODO: set the value of the encoded image variable to null
            }
        });
    }

}
