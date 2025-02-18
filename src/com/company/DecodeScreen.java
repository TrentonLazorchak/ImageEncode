package com.company;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class DecodeScreen extends JFrame {
    private static boolean isImg = false;
    public static String decPath;
    public static String decName;
    public static String fullDecPath;
    private static JLabel decImg;

    public DecodeScreen(String title) {
        super(title);

        // set layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        // fonts
        Font titleFont = new Font("Times New Roman",Font.PLAIN,100);
        Font errorFont = new Font("Times New Roman", Font.PLAIN, 25);

        // create swing components
        JLabel tit = new JLabel("Upload Image To Decode");
        tit.setFont(titleFont);
        JButton upload = new JButton("UPLOAD");
        JButton back = new JButton("BACK");
        JButton reset = new JButton("RESET");
        JButton start = new JButton("START");

        JLabel error = new JLabel("ERROR");
        error.setHorizontalAlignment(JLabel.CENTER);
        error.setFont(errorFont);
        error.setForeground(Color.RED);
        error.setVisible(false);

        //// Title and Upload //////////////////////////////////////////////////////////
        gc.weightx = 1;
        gc.weighty = 1;

        gc.anchor = GridBagConstraints.PAGE_END;
        gc.gridx = 1;
        gc.gridy = 0;
        add(tit, gc);

        gc.anchor = GridBagConstraints.PAGE_START;
        gc.ipadx = 75;
        gc.ipady = 25;
        gc.gridx = 1;
        gc.gridy = 2;
        add(upload, gc);

        gc.anchor = GridBagConstraints.CENTER;
        gc.gridx = 1;
        gc.gridy = 2;
        add(error, gc);

        //// Bottom Row Buttons /////////////////////////////////////////////////////////
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

        //// Add behaviors ///////////////////////////////////////////////////////////////
        // if upload button is pressed
        upload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Log.LOGGER.info("Upload button pressed.");
                JFileChooser my = new JFileChooser();
                my.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter correct = new FileNameExtensionFilter("png or jpg", "jpg", "png");
                my.addChoosableFileFilter(correct);
                my.setCurrentDirectory(new java.io.File("."));
                my.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                my.setAcceptAllFileFilterUsed(false);

                if(my.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
                {

                    // Store the path to the file to be used for the decoder
                    decPath =my.getCurrentDirectory().toString();
                    decName =  my.getSelectedFile().getName();
                    fullDecPath = decPath + "/" + decName;

                    ImageIcon baseImg = new ImageIcon(fullDecPath);
                    Image image = baseImg.getImage(); // transform it
                    Image newImg = image.getScaledInstance(200, 200,  Image.SCALE_SMOOTH); // scale it the smooth way
                    baseImg = new ImageIcon(newImg);  // transform it back
                    decImg = new JLabel(baseImg);

                    Dimension size = new Dimension(200,200);
                    decImg.setMinimumSize(size);
                    decImg.setPreferredSize(size);
                    decImg.setMaximumSize(size);

                    gc.anchor = GridBagConstraints.CENTER;
                    gc.gridx = 1;
                    gc.gridy = 1;
                    add(decImg, gc);
                    isImg = true;
                    Log.LOGGER.info("Image selected and presented to the screen.");
                }
                revalidate();
                Log.LOGGER.info("Revalidated screen.");
            }
        });

        // if back button is pressed
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Log.LOGGER.info("Back button pressed.");
                // Open welcome screen
                JFrame welcome = new WelcomeFrame("ImageEncoder");
                welcome.setExtendedState(JFrame.MAXIMIZED_BOTH);
                welcome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                welcome.setVisible(true);
                Log.LOGGER.info("Welcome screen created.");

                // Remove decode screen
                setVisible(false);
                dispose();
                Log.LOGGER.info("Disposed of the decode screen.");
            }
        });

        // if reset button is pressed
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Log.LOGGER.info("Reset button pressed.");
                // if an image has been uploaded
                if(isImg) {
                    // remove the decoded image from the screen
                    remove(decImg);
                    isImg = false;
                    Log.LOGGER.info("Image removed from the screen.");
                } else {
                    Log.LOGGER.info("No image was on the screen when reset was pressed.");
                }

                // set error to invisible
                error.setVisible(false);

                revalidate();
                Log.LOGGER.info("Error removed and screen revalidated.");
            }
        });

        // if start button pressed
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Log.LOGGER.info("Start button pressed.");
                // verify image is correct, is image there, is it encoded
                if(!isImg) {
                    error.setText("ERROR: MUST UPLOAD AN ENCODED IMAGE");
                    error.setVisible(true);
                    revalidate();
                    Log.LOGGER.info("ERROR: Must upload an encoded image in order to decode.");
                    return;
                }

                // check file name contains encodeImg or encodeMsg
                boolean isEncImg = fullDecPath.contains("encodedImg-");
                boolean isEncMsg = fullDecPath.contains("encodedMsg-");
                if(isEncMsg) { // if img is encoded with a message
                    try {
                        EncodeMessage.decodeText(fullDecPath);
                        Log.LOGGER.info("Message decoded from image.");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    // transition to decode result screen
                    // Create decode result screen
                    JFrame resultScreen = new DecodeResultScreen("Decode Result");
                    resultScreen.setResizable(false);
                    resultScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    resultScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    resultScreen.setVisible(true);
                    Log.LOGGER.info("Decode result screen created.");

                    // Close encode screen
                    setVisible(false);
                    dispose();
                    Log.LOGGER.info("Encode screen disposed of.");
               } else if(isEncImg) { // if img is encoded with a image
                    try {
                        //EncodeImage.decodeImage(fullDecPath); //TODO: Replace decode message with decode image
                        EncodeMessage.decodeText(fullDecPath);
                        Log.LOGGER.info("Image decoded from image.");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    // transition to decode result screen
                    // Create decode result screen
                    JFrame resultScreen = new DecodeResultScreen("Decode Result");
                    resultScreen.setResizable(false);
                    resultScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    resultScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    resultScreen.setVisible(true);
                    Log.LOGGER.info("Decode result screen created.");

                    // Close encode screen
                    setVisible(false);
                    dispose();
                    Log.LOGGER.info("Encode screen disposed of.");
                } else {
                    error.setText("ERROR: UPLOADED IMAGE IS NOT ENCODED!");
                    error.setVisible(true);
                    revalidate();
                    Log.LOGGER.info("ERROR: Uploaded image is not encoded.");
                }
            }
        });
    }
}
