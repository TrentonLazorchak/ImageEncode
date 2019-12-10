package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReplayScreen extends JFrame {
    public static String fullReplayPath;
    public static boolean isImg = false;

    public ReplayScreen(String title) {
        super(title);

        // set layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();


        final File folder = new File(System.getProperty("user.dir"));
        System.out.println(System.getProperty("user.dir"));

        List<String> result = new ArrayList<>();
        String[] fileList = new String[1000];

        search( ".*.png", folder, result);

        int i = 0;
        for (String s : result) {
            if(s.contains("encodedImg-") || s.contains("encodedMsg-")) {
                fileList[i] = s;
                i++;
            }
        }

        //Making the J frame
        JList<String> list = new JList<>(fileList);

        JButton start = new JButton("START");
        JButton back = new JButton("BACK");

        JPanel p = new JPanel();
        Dimension pSize= new Dimension(1000, 1000);
        p.setMinimumSize(pSize);
        p.setPreferredSize(pSize);
        p.setMaximumSize(pSize);


        JLabel l = new JLabel("Select the file you want to view");

        //Making the scrollbarr
        JScrollPane sc = new JScrollPane(list);
        sc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        Dimension scSize = new Dimension(350, 350);
        sc.setMinimumSize(scSize);
        sc.setPreferredSize(scSize);
        sc.setMaximumSize(scSize);

        p.add(l);
        p.add(sc);
        p.add(back);
        p.add(start);

        gc.anchor = GridBagConstraints.CENTER;
        gc.gridx = 1;
        gc.gridy = 1;
        add(p, gc);

        // Add ActionListeners
        MouseListener mouselis = new MouseAdapter() {
            @Override
            public void  mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    fullReplayPath = list.getSelectedValue();
                    isImg = true;
                }
            }

        };
        list.addMouseListener(mouselis);

        // start button pressed
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Log.LOGGER.info("Start button pressed.");
                // verify image is correct, is image there, is it encoded
                if(!isImg) {
                    // TODO: error.setText("ERROR: MUST UPLOAD AN ENCODED IMAGE");
                    //TODO: error.setVisible(true);
                    //revalidate();
                    Log.LOGGER.info("ERROR: Must select an image in order to replay.");
                    return;
                }

                // check file name contains encodeImg or encodeMsg
                boolean isEncImg = fullReplayPath.contains("encodedImg-");
                boolean isEncMsg = fullReplayPath.contains("encodedMsg-");

                if(isEncMsg) { // if img is encoded with a message
                    try {
                        EncodeMessage.decodeText(fullReplayPath);
                        Log.LOGGER.info("Message decoded from image.");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    // transition to replay result screen
                    // Create replay result screen
                    JFrame resultScreen = new ReplayResultScreen("Replay Result");
                    resultScreen.setResizable(false);
                    resultScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    resultScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    resultScreen.setVisible(true);
                    Log.LOGGER.info("Replay result screen created.");

                    // Close replay screen
                    setVisible(false);
                    dispose();
                    Log.LOGGER.info("Replay screen disposed of.");
                } else if(isEncImg) { // if img is encoded with a image
                    try {
                        //EncodeImage.decodeImage(fullDecPath); //TODO: Replace decode message with decode image
                        EncodeMessage.decodeText(fullReplayPath);
                        Log.LOGGER.info("Image decoded from image.");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    // transition to replay result screen
                    // Create replay result screen
                    JFrame resultScreen = new ReplayResultScreen("Replay Result");
                    resultScreen.setResizable(false);
                    resultScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    resultScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    resultScreen.setVisible(true);
                    Log.LOGGER.info("Replay result screen created.");

                    // Close replay screen
                    setVisible(false);
                    dispose();
                    Log.LOGGER.info("Replay screen disposed of.");
                } else {
//                    error.setText("ERROR: UPLOADED IMAGE IS NOT ENCODED!");
//                    error.setVisible(true);
                    //revalidate();
                    Log.LOGGER.info("ERROR: Uploaded image is not encoded.");
                }
            }
        });

        // back button pressed
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // transition to home screen
                // Create home screen
                JFrame welcome = new WelcomeFrame("Image Encoder");
                welcome.setResizable(false);
                welcome.setExtendedState(JFrame.MAXIMIZED_BOTH);
                welcome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                welcome.setVisible(true);
                Log.LOGGER.info("Welcome screen created.");

                // Close replay screen
                setVisible(false);
                dispose();
                Log.LOGGER.info("Replay screen disposed of.");
            }
        });
    }

    private static void search(final String pattern1, final File folder, List<String> result) {
        for (final File f : Objects.requireNonNull(folder.listFiles())) {

            if (f.isDirectory()) {
                search( pattern1, f, result);
            }

            if (f.isFile()) {
                if (f.getName().matches(pattern1)) {
                    result.add(f.getAbsolutePath());
                }
            }

        }
    }
}
