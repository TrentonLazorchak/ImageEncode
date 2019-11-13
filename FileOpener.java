package com;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.*;
import java.awt.event.*;

public class FileOpener{



    public static void main (String[] args)
    {

        JFrame f = new JFrame("Ecnode Screen");
        JButton b= new JButton("Upload");
        b.setBounds(50,100,95,30);
        b.addActionListener(new ActionListener() {

            public  void actionPerformed(ActionEvent e) {

                JFileChooser my = new JFileChooser();
                my.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter correct = new FileNameExtensionFilter("png or jpg", "jpg", "png");
                my.addChoosableFileFilter(correct);
                my.setCurrentDirectory(new java.io.File("."));
                my.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                my.setAcceptAllFileFilterUsed(false);

                if(my.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
                {

                    System.out.println(my.getName(my.getSelectedFile()));
                }
                String path =my.getSelectedFile().getAbsolutePath();
                System.out.println(path);


            }

        });

        f.add(b);
        f.setSize(400, 400);
        f.setLayout(null);
        f.setVisible(true);
    }


}

