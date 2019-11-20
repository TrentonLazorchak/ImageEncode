package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.awt.Image;

public class EncodeResultScreen extends JFrame {
    // default width and height for img
    private int width = 500;
    private int height = 500;
    private ImageIcon imageIcon;
    private JLabel img;

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

        imageIcon = new ImageIcon(filePath);
        Image image = imageIcon.getImage(); // transform it
        Image newImg = image.getScaledInstance(width, height,  Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon = new ImageIcon(newImg);  // transform it back
        img = new JLabel(imageIcon);

        final Dimension size = new Dimension(500,500);
        img.setMinimumSize(size);
        img.setPreferredSize(size);
        img.setMaximumSize(size);

        // buttons
        JButton save = new JButton("SAVE");
        JButton home = new JButton("HOME");
        JButton encodeAnother = new JButton("ENCODE ANOTHER");

        // Zoom in/out
        JButton zoomIn = new JButton("Zoom In");
        JButton zoomOut = new JButton("Zoom Out");

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

        gc.ipadx = 25;
        gc.ipady = 15;
        gc.anchor = GridBagConstraints.LAST_LINE_START;
        gc.gridx = 1;
        gc.gridy = 1;
        add(zoomIn, gc);

        gc.anchor = GridBagConstraints.LAST_LINE_END;
        gc.gridx = 1;
        gc.gridy = 1;
        add(zoomOut, gc);

        gc.ipadx = 50;
        gc.ipady = 25;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.gridx = 0;
        gc.gridy = 2;
        add(save, gc);

        gc.anchor = GridBagConstraints.CENTER;
        gc.gridx = 1;
        add(home, gc);

        gc.anchor = GridBagConstraints.LINE_START;
        gc.gridx = 2;
        add(encodeAnother, gc);

        //// Add Behaviors ///////////////////////////////////
        // zoom in pressed
        zoomIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // remove existing img
                remove(img);

                // adjust height and width
                height += 50;
                width += 50;

                // rescale img
                imageIcon = new ImageIcon(image.getScaledInstance(width, height,  Image.SCALE_SMOOTH));

                // create new jlabel to store img
                img = new JLabel(imageIcon);
                img.setMinimumSize(size);
                img.setPreferredSize(size);
                img.setMaximumSize(size);

                // add img to frame and revalidate
                gc.anchor = GridBagConstraints.PAGE_START;
                gc.gridx = 1;
                gc.gridy = 1;
                add(img, gc);
                revalidate();
            }
        });

        // zoom out pressed
        zoomOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // if max zoom out reached, don't zoom out
                if(width <= 500 || height <= 500) {
                    return;
                } else {
                    // adjust height and width
                    height -= 50;
                    width -= 50;
                }

                // remove existing img
                remove(img);

                // rescale img
                imageIcon = new ImageIcon(image.getScaledInstance(width, height,  Image.SCALE_SMOOTH));

                // create new jlabel to store img
                img = new JLabel(imageIcon);
                img.setMinimumSize(size);
                img.setPreferredSize(size);
                img.setMaximumSize(size);

                // add img to frame and revalidate
                gc.anchor = GridBagConstraints.PAGE_START;
                gc.gridx = 1;
                gc.gridy = 1;
                add(img, gc);
                revalidate();
            }
        });

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
