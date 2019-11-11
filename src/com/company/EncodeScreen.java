package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EncodeScreen extends JFrame {
    public EncodeScreen(String title) {
        super(title);

        // set layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        // Default font
        Font font = new Font("Times New Roman",Font.PLAIN,25);

        //// First row /////////////////////////////////////////////
        // create swing components
        JLabel uploadLabel = new JLabel("Upload Base Image:");
        uploadLabel.setFont(font);
        JTextField fileName = new JTextField("                                                   <File Name>                                                    ");
        fileName.setEditable(false);
        JButton upload = new JButton("UPLOAD");

        gc.weightx = 1;
        gc.weighty = 0.5;

        gc.anchor = GridBagConstraints.CENTER;
        gc.gridx = 0;
        gc.gridy = 0;
        add(uploadLabel, gc);

        gc.gridx = 1;
        gc.gridy = 0;
        add(fileName, gc);

        gc.gridx = 2;
        gc.gridy = 0;
        gc.ipadx = 75;
        gc.ipady = 25;
        add(upload, gc);

        //// Second row ///////////////////////////////////////////////////
        // Create components
        JLabel selectMethod = new JLabel("Select Method:");
        // TODO: Need to put the actual methods instead of placeholder names
        String[] methods = {"Method 1", "Method 2", "Method 3"};
        JComboBox<String> methodBox = new JComboBox<String>(methods);

        gc.anchor = GridBagConstraints.PAGE_END;
        gc.gridx = 0;
        gc.gridy = 0;
        add(selectMethod, gc);

        gc.anchor = GridBagConstraints.LAST_LINE_END;
        gc.gridx = 0;
        gc.gridy = 0;
        add(methodBox, gc);

        //// Encode Message /////////////////////////////////////////////////
        JRadioButton imageRadio = new JRadioButton("Enocde Image");
        JRadioButton textRadio = new JRadioButton("Encode Text");
        ButtonGroup radios = new ButtonGroup();
        radios.add(imageRadio);
        radios.add(textRadio);

        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.gridx = 1;
        gc.gridy = 1;
        add(textRadio, gc);
        textRadio.setSelected(true); // starts the screen of with textRadio selected

        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.gridx = 1;
        gc.gridy = 1;
        add(imageRadio, gc);

        // Text items
        JTextArea message = new JTextArea(20,35);
        message.setWrapStyleWord(true);
        message.setLineWrap(true);
        message.setText("<MESSAGE>");
        JScrollPane scrollPane = new JScrollPane(message);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.CENTER;
        gc.gridx = 1;
        gc.gridy = 1;
        add(scrollPane, gc);

        gc.fill = GridBagConstraints.NONE;

        // Image items
        JButton upl = new JButton("UPLOAD");
        JTextField imgName = new JTextField("                                          <File Name>                                          ");
        imgName.setEditable(false);

        // Cancel button
        JButton cancel = new JButton("CANCEL");
        gc.anchor = GridBagConstraints.LAST_LINE_START;
        gc.gridx = 1;
        gc.gridy = 1;
        add(cancel, gc);

        //// Buttom row buttons /////////////////////////////////////////////
        // Create buttons
        JButton back = new JButton("BACK");
        JButton reset = new JButton("RESET");
        JButton start = new JButton("START");

        gc.ipadx = 50;
        gc.ipady = 25;

        gc.anchor = GridBagConstraints.LAST_LINE_START;
        gc.gridx = 0;
        gc.gridy = 2;
        add(back, gc);

        gc.anchor = GridBagConstraints.PAGE_END;
        gc.gridx = 2;
        gc.gridy = 2;
        add(reset, gc);

        gc.anchor = GridBagConstraints.LAST_LINE_END;
        gc.gridx = 2;
        gc.gridy = 2;
        add(start, gc);

        //// Add behaviors ///////////////////////////////////////////////////
        // If upload button pressed
        upload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Upload image and ensure it is correct name (Lanre)
                System.out.println("File Upload Pressed");
            }
        });

        // If back button pressed
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open welcome screen
                JFrame welcome = new WelcomeFrame("ImageEncoder");
                welcome.setExtendedState(JFrame.MAXIMIZED_BOTH);
                welcome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                welcome.setVisible(true);

                // Remove encode screen
                setVisible(false);
                dispose();
            }
        });

        // If start button pressed
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: store base image

                // TODO: store message if message radio selected, else store second image

                // perform encoding backend

                // Create encode result screen
                JFrame resultScreen = new EncodeResultScreen("Encode Result");
                resultScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
                resultScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                resultScreen.setVisible(true);

                // Close welcome screen
                setVisible(false);
                dispose();
            }
        });

        // if imageRadio selected, remove message, add image items
        imageRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // remove message items
                remove(scrollPane);

                // add image items
                gc.fill = GridBagConstraints.NONE;
                gc.anchor = GridBagConstraints.LINE_END;
                gc.gridx = 1;
                gc.gridy = 1;
                add(upl, gc);
                gc.anchor = GridBagConstraints.LINE_START;
                add(imgName, gc);
                revalidate();
            }
        });

        // if textRadio selected, remove image items, add message items
        textRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // remove image items
                remove(upl);
                remove(imgName);

                // add message items
                gc.fill = GridBagConstraints.HORIZONTAL;
                gc.anchor = GridBagConstraints.CENTER;
                gc.gridx = 1;
                gc.gridy = 1;
                add(scrollPane, gc);
                revalidate();
            }
        });
    }
}
