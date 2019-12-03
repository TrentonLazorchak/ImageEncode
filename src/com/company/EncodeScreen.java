package com.company;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EncodeScreen extends JFrame {
    public static String name;
    public static String filePath;
    public static String imgPath;
    public static String msg;
    public static JLabel encImg;
    public static JLabel img2;
    private boolean isImg = false;
    private boolean isEncImg = false;

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

        JButton upload = new JButton("UPLOAD");

        gc.weightx = 1;
        gc.weighty = 0.5;

        gc.anchor = GridBagConstraints.CENTER;
        gc.gridx = 0;
        gc.gridy = 0;
        add(uploadLabel, gc);


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

        // Error label (starts invisible)
        JLabel error = new JLabel("ERROR");
        error.setFont(font);
        error.setForeground(Color.RED);
        error.setVisible(false);
        gc.anchor = GridBagConstraints.LAST_LINE_END;
        gc.gridx = 1;
        gc.gridy = 1;
        add(error, gc);

        // Text items
        // set max number of characters
        final int maxNumberOfCharacters = 500;
        JTextArea message = new JTextArea(new DefaultStyledDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if ((getLength() + str.length()) <= maxNumberOfCharacters) {
                    super.insertString(offs, str, a);
                }
                else {
                    Toolkit.getDefaultToolkit().beep();
                    error.setText("ERROR: TEXT IS AT MAX SIZE OF 500 CHARS!");
                    error.setVisible(true);
                    revalidate();
                }
            }
        });
        message.setWrapStyleWord(true);
        message.setLineWrap(true);
        message.setText(messageDefault);

        JScrollPane scrollPane = new JScrollPane(message);
        Dimension scrollSize = new Dimension(500, 100);
        scrollPane.setMinimumSize(scrollSize);
        scrollPane.setPreferredSize(scrollSize);
        scrollPane.setMaximumSize(scrollSize);

        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.CENTER;
        gc.gridx = 1;
        gc.gridy = 1;
        add(scrollPane, gc);

        gc.fill = GridBagConstraints.NONE;

        // Image upload button
        JButton upl = new JButton("UPLOAD");

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

        gc.anchor = GridBagConstraints.LAST_LINE_END;
        gc.gridx = 1;
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

                    // Store the path to the file to be used for the encoder
                    filePath =my.getSelectedFile().getAbsolutePath();
                    ImageIcon baseImg = new ImageIcon(filePath);
                    Image image = baseImg.getImage(); // transform it
                    Image newImg = image.getScaledInstance(200, 200,  Image.SCALE_SMOOTH); // scale it the smooth way
                    baseImg = new ImageIcon(newImg);  // transform it back
                    encImg = new JLabel(baseImg);

                    Dimension size = new Dimension(200,200);
                    encImg.setMinimumSize(size);
                    encImg.setPreferredSize(size);
                    encImg.setMaximumSize(size);

                    gc.anchor = GridBagConstraints.CENTER;
                    gc.gridx = 1;
                    gc.gridy = 0;
                    add(encImg, gc);
                    isImg = true;
                }
                revalidate();
            }
        });

        // If encode image upload button is pressed
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
                    // Store the path to the file to be used for the encoder
                    imgPath =my.getSelectedFile().getAbsolutePath();

                    ImageIcon baseImg = new ImageIcon(imgPath);
                    Image image = baseImg.getImage(); // transform it
                    Image newImg = image.getScaledInstance(300, 300,  Image.SCALE_SMOOTH); // scale it the smooth way
                    baseImg = new ImageIcon(newImg);  // transform it back
                    img2 = new JLabel(baseImg);

                    Dimension size = new Dimension(300,300);
                    img2.setMinimumSize(size);
                    img2.setPreferredSize(size);
                    img2.setMaximumSize(size);

                    gc.anchor = GridBagConstraints.LINE_START;
                    gc.gridx = 1;
                    gc.gridy = 1;
                    add(img2, gc);
                    isEncImg = true;
                }
                revalidate();
            }
        });

        // if cancel button is pressed
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // if text radio selected, set message to default, else remove the image
                if(textRadio.isSelected()) {
                    message.setText(messageDefault);
                } else if(imageRadio.isSelected() && isEncImg){
                    remove(img2);
                    isEncImg = false;
                }
                // set error to invible
                error.setVisible(false);
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

        // If reset button is pressed
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // remove base image
                if(isImg) {
                    remove(encImg);
                    isImg = false;
                }

                // if text radio selected, set message to default, else remove the image
                if(textRadio.isSelected()) {
                    message.setText(messageDefault);
                } else if(imageRadio.isSelected() && isEncImg){
                    remove(img2);
                    isEncImg = false;
                }

                // set error label to invisible
                error.setVisible(false);
                revalidate();
            }
        });

        // If start button pressed
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: display a JLabel representing the error
                if(!isImg) { // if there is no base image uploaded
                    System.out.println("ERROR: NO BASE IMAGE SELECTED!");
                    error.setText("ERROR: NO BASE IMAGE SELECTED!");
                    error.setVisible(true);
                    revalidate();
                    return;
                } else if (imageRadio.isSelected() && !isEncImg) { // if the image radio button is selected but there is no img
                    System.out.println("ERROR: NO IMAGE SELECTED!");
                    error.setText("ERROR: NO IMAGE SELECTED!");
                    error.setVisible(true);
                    revalidate();
                    return;
                } else if (textRadio.isSelected() && message.getText().equals(messageDefault)) { // if text radio is selected and message is the default
                    System.out.println("ERROR: NO MESSAGE ENTERED!");
                    error.setText("ERROR: NO MESSAGE ENTERED!");
                    error.setVisible(true);
                    revalidate();
                    return;
                }

                // if the textRadio is selected, store the message for encoding
                if(textRadio.isSelected()) {
                    msg = message.getText();
                    // if the length of the message is greater than 500, print an error
                    if(msg.length() > 500) {
                        System.out.println("ERROR: MESSAGE OVER 500 CHARACTERS!");
                        error.setText("ERROR: MESSAGE OVER 500 CHARACTERS!");
                        error.setVisible(true);
                        revalidate();
                        return;
                    } else if(msg.isEmpty()) { // if the message is empty, print an error
                        System.out.println("ERROR: MESSAGE EMPTY!");
                        error.setText("ERROR: MESSAGE EMPTY!");
                        error.setVisible(true);
                        revalidate();
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


                // Close encode screen
                setVisible(false);
                dispose();
            }
        });

        // if imageRadio selected, remove message, add image items
        imageRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // set error visible to false
                error.setVisible(false);

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
                revalidate();
            }
        });

        // if textRadio selected, remove image items, add message items
        textRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // error to invisible
                error.setVisible(false);

                // reset img path and name to default
                imgPath = fileDefault;

                // remove image items
                remove(upl);
                if(isEncImg) {
                    remove(img2);
                    isEncImg = false;
                }

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
