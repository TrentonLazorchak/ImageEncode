package com.company;

import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        BufferedImage originalImageText = null;
        BufferedImage coverImageText = null;
        BufferedImage originalImage = null;
        BufferedImage coverImage = null;
        BufferedImage secretImage = null;

        // show a user interface to display the original image and the cover image with the secret information included
        try {
            //originalImageText = ImageIO.read(new File("C:\\Users\\Jake Howell\\Desktop\\curlyw.png"));
            coverImageText = ImageIO.read(new File("C:\\Users\\Jake Howell\\Desktop\\hood.jpg"));
            //originalImage = ImageIO.read(new File("C:\\Users\\Jake Howell\\Desktop\\cs226-assignment6.jpg"));
            //coverImage = ImageIO.read(new File("C:\\Users\\Jake Howell\\Desktop\\CS226\\cs226-assignment6.jpg"));
            //secretImage = ImageIO.read(new File("C:\\Users\\Jake Howell\\Desktop\\secret.jpg"));

            System.out.println("Input Message to be encoded: ");
            Scanner scan = new Scanner(System.in);

            String s = scan.nextLine();
            int width = coverImageText.getWidth();
            System.out.println(width);
            coverImageText = encodeText(coverImageText, s); // embed the secret information

            System.out.println("Would you like to decode an image? (Insert 1 for yes and 2 for no.");
            Scanner scan3 = new Scanner(System.in);
            int decode = scan.nextInt();

            if (decode == 1) {
                BufferedImage encodedMessage = ImageIO.read(new File("C:\\Users\\Jake Howell\\Desktop\\encodedmessage.jpg"));
                int encodedWidth = encodedMessage.getWidth();
                extractText(encodedMessage, s.length());		// extract the secret information
                //decodeText(encodedMessage, encodedWidth);   // extract the secret information
            }

            else { }

        } catch(IOException e) {  }
    }



    // embed secret information/TEXT into a "cover image"
    public static BufferedImage encodeText(BufferedImage image, String text) {
        int bitMask = 0x00000001;	// define the mask bit used to get the digit
        int bit;				// define an integer number to represent the ASCII number of a character
        int x = 0;				// define the starting pixel x
        int y = 0;				// define the starting pixel y
        for(int i = 0; i < text.length(); i++) {
            bit = (int) text.charAt(i);		// get the ASCII number of a character
            for(int j = 0; j < 8; j++) {
                int flag = bit & bitMask;	// get 1 digit from the character
                if(flag == 1) {
                    if(x < image.getWidth()) {
                        image.setRGB(x, y, image.getRGB(x, y) | 0x00000001); 	// store the bit which is 1 into a pixel's last digit
                        x++;
                    }
                    else {
                        x = 0;
                        y++;
                        image.setRGB(x, y, image.getRGB(x, y) | 0x00000001); 	// store the bit which is 1 into a pixel's last digit
                    }
                }
                else {
                    if(x < image.getWidth()) {
                        image.setRGB(x, y, image.getRGB(x, y) & 0xFFFFFFFE);	// store the bit which is 0 into a pixel's last digit
                        x++;
                    }
                    else {
                        x = 0;
                        y++;
                        image.setRGB(x, y, image.getRGB(x, y) & 0xFFFFFFFE);	// store the bit which is 0 into a pixel's last digit
                    }
                }
                bit = bit >> 1;				// get the next digit from the character
            }
        }

        // save the image which contains the secret information to another image file
        try {
            System.out.println("Would you like to save as .png or .jpg? (Insert 1 for .png or 2 for .jpg): ");
            Scanner scanner = new Scanner(System.in);
            int type = scanner.nextInt();
            if (type == 1) {
                File outputfile = new File("C:\\Users\\Jake Howell\\Desktop\\encodedmessage.png");
                ImageIO.write(image, "png", outputfile);
            }
            else if(type == 2) {
                File outputfile = new File("C:\\Users\\Jake Howell\\Desktop\\encodedmessage.jpg");
                ImageIO.write(image, "jpg", outputfile);
            }
            else { System.out.println("Not a valid Entry."); }

        } catch (IOException e) {

        }
        return image;
    }

    public static void extractText(BufferedImage image, int length) {
        //System.out.print("Extracting: ");
        int bitMask = 0x00000001;	// define the mask bit used to get the digit
        int x = 0;					// define the starting pixel x
        int y = 0;					// define the starting pixel y
        int flag;
        char[] c = new char[length] ;	// define a character array to store the secret information
        System.out.println("Extracted Message: ");
        for(int i = 0; i < length; i++) {
            int bit = 0;

            // 8 digits form a character
            for(int j = 0; j < 8; j++) {
                if(x < image.getWidth()) {
                    flag = image.getRGB(x, y) & bitMask;	// get the last digit of the pixel
                    x++;
                }
                else {
                    x = 0;
                    y++;
                    flag = image.getRGB(x, y) & bitMask;	// get the last digit of the pixel
                }

                // store the extracted digits into an integer as a ASCII number
                if(flag == 1) {
                    bit = bit >> 1;
                    bit = bit | 0x80;
                }
                else {
                    bit = bit >> 1;
                }
            }
            c[i] = (char) bit;	// represent the ASCII number by characters
            System.out.print(c[i]);
        }
    }
}
