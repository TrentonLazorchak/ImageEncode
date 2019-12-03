package com.company;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DecodeScreen extends JFrame {
    private static boolean isImg = false;
    private static JLabel decImg;

    public DecodeScreen(String title) {
        super(title);

        // set layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        // fonts
        Font titleFont = new Font("Times New Roman",Font.PLAIN,100);

        // create swing components
        JLabel tit = new JLabel("Upload Image To Decode");
        tit.setFont(titleFont);
        JButton upload = new JButton("UPLOAD");
        JButton back = new JButton("BACK");
        JButton reset = new JButton("RESET");
        JButton start = new JButton("START");

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
                    String filePath =my.getSelectedFile().getAbsolutePath();
                    ImageIcon baseImg = new ImageIcon(filePath);
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
                }
                revalidate();
            }
        });

        // if back button is pressed
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open welcome screen
                JFrame welcome = new WelcomeFrame("ImageEncoder");
                welcome.setExtendedState(JFrame.MAXIMIZED_BOTH);
                welcome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                welcome.setVisible(true);

                // Remove decode screen
                setVisible(false);
                dispose();
            }
        });

        // if reset button is pressed
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // if an image has been uploaded
                if(isImg) {
                    // remove the decoded image from the screen
                    remove(decImg);
                    isImg = false;
                }

                // TODO: set error to invisible

                revalidate();
            }
        });

        // if start button pressed
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: verify image is correct, is image there, is it encoded

                // TODO: if not there or not encoded, print an error message

                // TODO: if image is encoded, send image to decoding backend

                // transition to decode result screen
                // Create decode result screen
                JFrame resultScreen = new DecodeResultScreen("Decode Result");
                resultScreen.setResizable(false);
                resultScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
                resultScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                resultScreen.setVisible(true);


                // Close encode screen
                setVisible(false);
                dispose();
            }
        });
    }
}
