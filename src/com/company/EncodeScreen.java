package com.company;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class EncodeScreen extends JFrame {
    public static String name;
    public static String filePath;
    public static String imgPath;
    public static String msg;

    public EncodeScreen(String title) {
        super(title);

        // set layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        final String fileDefault = "<File Name>";
        final String messageDefault = "<Message>";

        // Default font
        Font font = new Font("Times New Roman",Font.PLAIN,25);

        //// First row /////////////////////////////////////////////
        // create swing components
        JLabel uploadLabel = new JLabel("Upload Base Image:");
        uploadLabel.setFont(font);

        JTextField fileName = new JTextField(fileDefault);
        Dimension dim = new Dimension(1000,50);
        fileName.setMinimumSize(dim);
        fileName.setPreferredSize(dim);
        fileName.setMaximumSize(dim);
        fileName.setEditable(false);
        fileName.setHorizontalAlignment(JTextField.CENTER);

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

        gc.anchor = GridBagConstraints.LAST_LINE_START;
        gc.gridx = 1;
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
        message.setText(messageDefault);
        JScrollPane scrollPane = new JScrollPane(message);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.CENTER;
        gc.gridx = 1;
        gc.gridy = 1;
        add(scrollPane, gc);

        gc.fill = GridBagConstraints.NONE;

        // Image items
        JButton upl = new JButton("UPLOAD");
        String file2 = fileDefault;
        JTextField imgName = new JTextField(file2);
        Dimension dim2 = new Dimension(750, 40);
        imgName.setMinimumSize(dim2);
        imgName.setPreferredSize(dim2);
        imgName.setMaximumSize(dim2);
        imgName.setHorizontalAlignment(JTextField.CENTER);
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

        gc.anchor = GridBagConstraints.LAST_LINE_START;
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
                JFileChooser my = new JFileChooser();
                my.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter correct = new FileNameExtensionFilter("png or jpg", "jpg", "png");
                my.addChoosableFileFilter(correct);
                my.setCurrentDirectory(new java.io.File("."));
                my.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                my.setAcceptAllFileFilterUsed(false);

                if(my.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
                {
                    // Display the name of the file
                    name = my.getName(my.getSelectedFile());
                    fileName.setText(name);

                    // Store the path to the file to be used for the encoder
                    filePath =my.getSelectedFile().getAbsolutePath();
                }
                revalidate();
            }
        });

        // If encode message upload button is pressed
        upl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser my = new JFileChooser();
                my.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter correct = new FileNameExtensionFilter("png or jpg", "jpg", "png");
                my.addChoosableFileFilter(correct);
                my.setCurrentDirectory(new java.io.File("."));
                my.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                my.setAcceptAllFileFilterUsed(false);

                if(my.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
                {
                    // Display the name of the file
                    name = my.getName(my.getSelectedFile());
                    imgName.setText(name);

                    // Store the path to the file to be used for the encoder
                    imgPath =my.getSelectedFile().getAbsolutePath();
                }
                revalidate();
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
                // TODO: display a JLabel representing the error
                if(filePath.equals(fileDefault)) {
                    System.out.println("ERROR: NO BASE IMAGE SELECTED!");
                    return;
                } else if (imageRadio.isSelected() && imgPath.equals(fileDefault)) {
                    System.out.println("ERROR: NO IMAGE SELECTED!");
                    return;
                } else if (textRadio.isSelected() && message.getText().equals(messageDefault)) {
                    System.out.println("ERROR: NO MESSAGE ENTERED!");
                    return;
                }

                // if the textRadio is selected, store the message for encoding, TODO: display JLabel for error
                if(textRadio.isSelected()) {
                    msg = message.getText();
                    if(msg.length() > 500) {
                        System.out.println("ERROR: MESSAGE OVER 500 CHARACTERS!");
                        return;
                    }
                    if(msg.isEmpty()) {
                        System.out.println("ERROR: MESSAGE EMPTY!");
                        return;
                    }
                }

                // TODO: perform encoding backend (Jake)

                // Create encode result screen
                JFrame resultScreen = new EncodeResultScreen("Encode Result");
                resultScreen.setResizable(false);
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
                // reset message to default
                message.setText(messageDefault);

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
                // reset img path and name to default
                imgName.setText(fileDefault);
                imgPath = fileDefault;

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
