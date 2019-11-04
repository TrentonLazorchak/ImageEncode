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
        gc.weighty = 1;

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

        //// Second row /////////////////////////////////////////////
        // Create components
        JLabel selectMethod = new JLabel("Select Method:");
        // TODO: Need to put the actual methods instead of placeholder names
        String[] methods = {"Method 1", "Method 2", "Method 3"};
        JComboBox<String> methodBox = new JComboBox<String>(methods);

        gc.gridx = 0;
        gc.gridy = 1;
        add(selectMethod, gc);

        gc.gridx = 1;
        gc.gridy = 1;
        add(methodBox, gc);

        // If upload button pressed
        upload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Uplaod image and ensure it is correct name (Lanre)
                System.out.println("File Upload Pressed");
            }
        });

    }
}
