package com.company;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class EncodeScreen extends JFrame {
    public static String filePath;
    public static String fileName;
    public static String fullFilePath;

    public static String imgPath;
    public static String imgName;
    public static String fullImgPath;

    public static String msg;
    public static JLabel encImg;
    public static JLabel img2;
    private boolean isImg = false;
    public static boolean isEncImg = false;

    public EncodeScreen(String title) {
        super(title);

        // set layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        final String fileDefault = "<File Name>";
        final String messageDefault = "<Message>";

        // fonts
        Font titFont = new Font("Times New Roman",Font.PLAIN,50);
        Font selFont = new Font("Times New Roman", Font.PLAIN, 25);

        //// First row /////////////////////////////////////////////
        // create swing components
        JLabel uploadLabel = new JLabel("Upload Base Image");
        uploadLabel.setFont(titFont);

        JButton upload = new JButton("UPLOAD");

        JLabel select = new JLabel("Select Encoding Method");
        select.setFont(selFont);
        select.setHorizontalAlignment(JLabel.CENTER);

        gc.weightx = 1;
        gc.weighty = 1;

        gc.anchor = GridBagConstraints.PAGE_START;
        gc.gridx = 1;
        gc.gridy = 0;
        add(uploadLabel, gc);

        gc.anchor = GridBagConstraints.PAGE_END;
        gc.gridx = 1;
        gc.gridy = 0;
        gc.ipadx = 75;
        gc.ipady = 25;
        add(upload, gc);

        gc.anchor = GridBagConstraints.PAGE_START;
        gc.gridx = 1;
        gc.gridy = 1;
        add(select, gc);

        //// Encode Message /////////////////////////////////////////////////
        JRadioButton imageRadio = new JRadioButton("Enocde Image");
        JRadioButton textRadio = new JRadioButton("Encode Text");
        ButtonGroup radios = new ButtonGroup();
        radios.add(imageRadio);
        radios.add(textRadio);

        gc.anchor = GridBagConstraints.LINE_START;
        gc.gridx = 1;
        gc.gridy = 1;
        add(textRadio, gc);
        textRadio.setSelected(true); // starts the screen of with textRadio selected

        gc.anchor = GridBagConstraints.LINE_END;
        gc.gridx = 1;
        gc.gridy = 1;
        add(imageRadio, gc);

        // Error label (starts invisible)
        JLabel error = new JLabel("ERROR");
        error.setFont(selFont);
        error.setForeground(Color.RED);
        error.setVisible(false);
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.gridx = 1;
        gc.gridy = 2;
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
        gc.anchor = GridBagConstraints.PAGE_END;
        gc.gridx = 1;
        gc.gridy = 1;
        add(scrollPane, gc);

        gc.fill = GridBagConstraints.NONE;

        // Image upload button
        JButton upl = new JButton("UPLOAD");

        // Cancel button
        JButton cancel = new JButton("CANCEL");
        gc.anchor = GridBagConstraints.LINE_START;
        gc.gridx = 1;
        gc.gridy = 2;
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

        Log.LOGGER.info("Encoding screen created.");

        //// Add behaviors ///////////////////////////////////////////////////
        // If upload button pressed
        upload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Log.LOGGER.info("Base image upload button pressed.");
                JFileChooser my = new JFileChooser();
                my.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter correct = new FileNameExtensionFilter("png",  "png");
                my.addChoosableFileFilter(correct);
                my.setCurrentDirectory(new java.io.File("."));
                my.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                my.setAcceptAllFileFilterUsed(false);

                if(my.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
                {
                    Log.LOGGER.info("Base image selected.");
                    // Store the path to the file to be used for the encoder
                    filePath =my.getCurrentDirectory().toString();
                    fileName =  my.getSelectedFile().getName();
                    fullFilePath = filePath + "/" + fileName;

                    ImageIcon baseImg = new ImageIcon(filePath + "/" + fileName);
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
                    Log.LOGGER.info("Base image displayed on screen.");
                }
                revalidate();
                Log.LOGGER.info("Frame revalidated.");
            }
        });

        // If encode image upload button is pressed
        upl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Log.LOGGER.info("Encode image upload button pressed.");
                JFileChooser my = new JFileChooser();
                my.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter correct = new FileNameExtensionFilter("png or jpg", "jpg", "png");
                my.addChoosableFileFilter(correct);
                my.setCurrentDirectory(new java.io.File("."));
                my.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                my.setAcceptAllFileFilterUsed(false);

                if(my.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
                {
                    Log.LOGGER.info("Image to be encoded selected.");
                    // Store the path to the file to be used for the encoder
                    imgPath =my.getCurrentDirectory().toString();
                    imgName =  my.getName(my.getSelectedFile().getAbsoluteFile());
                    fullImgPath = imgPath + "/" + imgName;
                    System.out.println(fullImgPath);

                    ImageIcon baseImg = new ImageIcon(fullImgPath);
                    Image image = baseImg.getImage(); // transform it
                    Image newImg = image.getScaledInstance(300, 300,  Image.SCALE_SMOOTH); // scale it the smooth way
                    baseImg = new ImageIcon(newImg);  // transform it back
                    img2 = new JLabel(baseImg);

                    Dimension size = new Dimension(300,300);
                    img2.setMinimumSize(size);
                    img2.setPreferredSize(size);
                    img2.setMaximumSize(size);

                    gc.anchor = GridBagConstraints.PAGE_END;
                    gc.gridx = 1;
                    gc.gridy = 1;
                    add(img2, gc);
                    isEncImg = true;
                    Log.LOGGER.info("Image to be encoded displayed on screen.");
                }
                revalidate();
                Log.LOGGER.info("Frame revalidated.");
            }
        });

        // if cancel button is pressed
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Log.LOGGER.info("Cancel button pressed");
                // if text radio selected, set message to default, else remove the image
                if(textRadio.isSelected()) {
                    Log.LOGGER.info("Text radio button is selected and the cancel button was pressed, " +
                            "so setting message text to default");
                    message.setText(messageDefault);
                } else if(imageRadio.isSelected() && isEncImg){
                    Log.LOGGER.info("Image radio button is selected and the cancel button was pressed, " +
                            "so removing the image to be encoded.");
                    remove(img2);
                    isEncImg = false;
                }
                // set error to invible
                Log.LOGGER.info("Removing error label.");
                error.setVisible(false);
                revalidate();
                Log.LOGGER.info("Frame was revalidated");
            }
        });


        // If back button pressed
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Log.LOGGER.info("Back button pressed.");
                // Open welcome screen
                Log.LOGGER.info("Opening welcome screen.");
                JFrame welcome = new WelcomeFrame("ImageEncoder");
                welcome.setExtendedState(JFrame.MAXIMIZED_BOTH);
                welcome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                welcome.setVisible(true);

                // Remove encode screen
                setVisible(false);
                dispose();
                Log.LOGGER.info("Encode screen removed.");
            }
        });

        // If reset button is pressed
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Log.LOGGER.info("Reset button pressed.");
                // remove base image
                if(isImg) {
                    remove(encImg);
                    isImg = false;
                }

                // if text radio selected, set message to default, else remove the image
                if(textRadio.isSelected()) {
                    Log.LOGGER.info("Reset button was pressed and text radio button is selected, " +
                            "so setting message text to default.");
                    message.setText(messageDefault);
                } else if(imageRadio.isSelected() && isEncImg){
                    Log.LOGGER.info("Reset button was pressed and text radio button is selected, " +
                            "so removing image to be encoded.");
                    remove(img2);
                    isEncImg = false;
                }

                // set error label to invisible
                error.setVisible(false);
                revalidate();
                Log.LOGGER.info("Frame revalidated.");
            }
        });

        // If start button pressed
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Log.LOGGER.info("Start button pressed.");
                if(!isImg) { // if there is no base image uploaded
                    Log.LOGGER.info("Error starting encoding: no base image uploaded.");
                    Log.LOGGER.info("Revalidating frame and and showing error message.");
                    error.setText("ERROR: NO BASE IMAGE SELECTED!");
                    error.setVisible(true);
                    revalidate();

                    return;
                } else if (imageRadio.isSelected() && !isEncImg) { // if the image radio button is selected but there is no img
                    Log.LOGGER.info("Error starting encoding: image radio button selected, but no image uploaded.");
                    Log.LOGGER.info("Revalidating frame and and showing error message.");
                    error.setText("ERROR: NO IMAGE SELECTED!");
                    error.setVisible(true);
                    revalidate();
                    return;
                } else if (textRadio.isSelected() && message.getText().equals(messageDefault)) { // if text radio is selected and message is the default
                    Log.LOGGER.info("Error starting encoding: text radio button is selected, but message is default.");
                    Log.LOGGER.info("Revalidating frame and and showing error message.");
                    error.setText("ERROR: NO MESSAGE ENTERED!");
                    error.setVisible(true);
                    revalidate();
                    return;
                }

                // if the textRadio is selected, store the message for encoding
                if(textRadio.isSelected()) {
                    msg = message.getText() + ">q<";
                    // if the length of the message is greater than 500, print an error
                    if(msg.length() > 500) {
                        Log.LOGGER.info("Error starting encoding: no base image uploaded.");
                        Log.LOGGER.info("Revalidating frame and and showing error message.");
                        error.setText("ERROR: MESSAGE OVER 500 CHARACTERS!");
                        error.setVisible(true);
                        revalidate();
                        return;
                    } else if(msg.isEmpty()) { // if the message is empty, print an error
                        Log.LOGGER.info("Error starting encoding: no base image uploaded.");
                        Log.LOGGER.info("Revalidating frame and and showing error message.");
                        error.setText("ERROR: MESSAGE EMPTY!");
                        error.setVisible(true);
                        revalidate();
                        return;
                    }
                }

                // backend for encoding a message or an image
                if(textRadio.isSelected()) { // if a message is requested to be encoded
                    try {
                        Log.LOGGER.info("Encoding message to base image.");
                        EncodeMessage.encodeText(fullFilePath, msg);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    // if imageRadioSelected then go through ImageEncode backend
                    try {
                        Log.LOGGER.info("Encoding image to base image");
                        EncodeImage.encodeImage(fullFilePath, fullImgPath);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

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
                Log.LOGGER.info("Image radio button selected.");
                // set error visible to false
                error.setVisible(false);

                // reset message to default
                message.setText(messageDefault);

                // remove message items
                remove(scrollPane);

                // add image items
                gc.fill = GridBagConstraints.NONE;
                gc.anchor = GridBagConstraints.PAGE_START;
                gc.gridx = 1;
                gc.gridy = 2;
                add(upl, gc);
                revalidate();

                Log.LOGGER.info("Text items hidden, image upload button presented to screen.");
                Log.LOGGER.info("Revalidated screen.");
            }
        });

        // if textRadio selected, remove image items, add message items
        textRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Log.LOGGER.info("Text radio button selected.");
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
                gc.anchor = GridBagConstraints.PAGE_END;
                gc.gridx = 1;
                gc.gridy = 1;
                add(scrollPane, gc);
                revalidate();

                Log.LOGGER.info("Image items hidden, message box presented to screen.");
                Log.LOGGER.info("Revalidated screen.");
            }
        });
    }
}
