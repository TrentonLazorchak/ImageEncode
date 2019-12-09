package com.company;

import java.awt.image.*;
import javax.imageio.*;
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
        BufferedImage encodedMessage = null;



        // show a user interface to display the original image and the cover image with the secret information included
        try {
            System.out.println("HI");
            //originalImageText = ImageIO.read(new File("C:\\Users\\Jake Howell\\Desktop\\curlyw.png"));
            coverImageText = ImageIO.read(new File("C:\\Users\\Jake Howell\\Desktop\\hood.jpg"));
            originalImage = ImageIO.read(new File("C:\\Users\\Jake Howell\\Desktop\\hood.jpg"));
            //coverImage = ImageIO.read(new File("C:\\Users\\Jake Howell\\Desktop\\CS226\\cs226-assignment6.jpg"));
            //secretImage = ImageIO.read(new File("C:\\Users\\Jake Howell\\Desktop\\secret.jpg"));
            //encodedMessage = ImageIO.read(new File("C:\\Users\\Jake Howell\\Desktop\\encodedmessage.jpg"));

            System.out.println("Input Message to be encoded: ");
            Scanner scan = new Scanner(System.in);

            String s = scan.nextLine();
            encodedMessage = encodeText(coverImageText, s); // embed the secret information

            System.out.println("Would you like to decode an image? (Insert 1 for yes and 2 for no): ");
            Scanner scan3 = new Scanner(System.in);
            int decode = scan.nextInt();

            if (decode == 1) {
                //extractText(coverImageText, s.length());
                extractText(encodedMessage, s.length());// extract the secret information
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
            bit = (int) text.charAt(i);
            //System.out.println("Inputed Bit: " + bit);
            //System.out.println("Bit: " + bit); // get the ASCII number of a character
            for(int j = 0; j < 8; j++) {
                int flag = bit & bitMask;
                //System.out.println("Flag: " + flag); // get 1 digit from the character
                if(flag == 1) {
                    if(x < image.getWidth()) {
                        //System.out.println("RGB of " + x + "," + y + ": " + (image.getRGB(x,y)));
                        image.setRGB(x, y, image.getRGB(x, y) | 0x00000001); // store the bit which is 1 into a pixel's last digit
                        //System.out.println("New RGB of " + x + "," + y + ": " + (image.getRGB(x,y)));
                        x++;
                    }
                    else {
                        x = 0;
                        y++;
                        //System.out.println("RBG of " + x + "," + y + ": " + (image.getRGB(x,y)));
                        image.setRGB(x, y, image.getRGB(x, y) | 0x00000001); 	// store the bit which is 1 into a pixel's last digit
                        //System.out.println("New RGB of " + x + "," + y + ": " + (image.getRGB(x,y)));
                    }
                }
                else {
                    if(x < image.getWidth()) {
                        //System.out.println("RGB of " + x + "," + y + ": " + (image.getRGB(x,y)));
                        image.setRGB(x, y, image.getRGB(x, y) & 0xFFFFFFFE);	// store the bit which is 0 into a pixel's last digit
                        //System.out.println("New RGB of " + x + "," + y + ": " + (image.getRGB(x,y)));
                        x++;
                    }
                    else {
                        x = 0;
                        y++;
                        //System.out.println("RGB of " + x + "," + y + ": " + (image.getRGB(x,y)));
                        image.setRGB(x, y, image.getRGB(x, y) & 0xFFFFFFFE);	// store the bit which is 0 into a pixel's last digit
                        //System.out.println("New RGB of " + x + "," + y + ": " + (image.getRGB(x,y)));
                    }
                }
                //System.out.println("Bit Before: " + bit);
                bit = bit >> 1;			// get the next digit from the character
                //System.out.println("Bit After: " + bit);
            }
        }
        return image;
        }

    public static void extractText(BufferedImage image, int length) {
        //System.out.print("Extracting: ");
        int bitMask = 0x00000001;	// define the mask bit used to get the digit
        int x = 0;					// define the starting pixel x
        int y = 0;					// define the starting pixel y
        int flag;
        int bit = 0;
        ArrayList<Character> c = new ArrayList<Character>();	// define a character array to store the secret information
        System.out.println("Extracted Message: ");
        for(int i = 0; i < length; i++) {
            // 8 digits form a character
            for(int j = 0; j < 8; j++) {
                if(x < image.getWidth()) {
                    //System.out.println("image.getRBG(x,y): " + image.getRGB(x, y));
                    flag = image.getRGB(x, y) & bitMask; // get the last digit of the pixel
                    //System.out.println("Flag of " + bit + ": " + flag);
                    x++;
                }
                else {
                    x = 0;
                    y++;
                    flag = image.getRGB(x, y) & bitMask;	// get the last digit of the pixel
                    //System.out.println("Flag of " + bit + ": " + flag);
                }

                // store the extracted digits into an integer as an ASCII number
                if(flag == 1) {
                    //System.out.println("Bit Before (DEC): " + bit);
                    bit = bit >> 1;
                    //System.out.println("Bit after >> 1 (DEC): " + bit);
                    bit = bit | 0x00000080;
                    //System.out.println("Bit AFTER 0x80 (DEC): " + bit);
                }
                else {
                    //System.out.println("Bit Before (DEC): " + bit);
                    bit = bit >> 1;
                    //System.out.println("Bit After >> 1 (DEC): " + bit);
                }
            }
            c.add((char) bit);
        }
        ArrayList_String(c);
    }

    public static void ArrayList_String(ArrayList<Character> message) {
        StringBuffer sb = new StringBuffer();

        for (char s : message) {
            sb.append(s);
            //sb.append("");
        }
        String str = sb.toString();
        System.out.println(str);

    }
}
