package com.company;

import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

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

            System.out.println("Would you like to encode a message or image? (Input 1 for message and 2 for image)");
            Scanner scan = new Scanner(System.in);
            int input = scan.nextInt();

            if (input == 1) {
                System.out.print("Message to be Encoded: ");
                Scanner scan2 = new Scanner(System.in);
                String s = scan2.nextLine();
                int width = coverImageText.getWidth();
                System.out.println(width);
                coverImageText = encodeText(coverImageText, s);								// embed the secret information

            }
            else if (input == 2) {
                coverImage = encodeImage(coverImage, secretImage);	// embed the secret image into a cover image
            }

            else { System.out.println("Not a valid entry."); }

            System.out.println("Would you like to decode an image? (Insert 1 for decode message, 2 for decode image, and any other key for do not decode: ");
            Scanner scan3 = new Scanner(System.in);
            int decode = scan.nextInt();

            if (decode == 1) {
                BufferedImage encodedMessage = ImageIO.read(new File("C:\\Users\\Jake Howell\\Desktop\\encodedmessage.jpg"));
                int width = encodedMessage.getWidth();
                String message = decodeText(encodedMessage, width);   // extract the secret information
                System.out.println("Encoded Message: " + message);
            }

            else if(decode == 2) {
                decodeImage(ImageIO.read(new File("encoded.jpg")), secretImage.getWidth(), secretImage.getHeight());	 // extract secret image from a cover image
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

    // extract secret information/Text from a "cover image"
    public static String decodeText(BufferedImage image, int width) {
        System.out.println("Currently Extracting");
        int bitMask = 0x00000001;	// define the mask bit used to get the digit
        int x = 0;					// define the starting pixel x
        int y = 0;					// define the starting pixel y
        int flag;
        ArrayList<Character> c = new ArrayList<Character>();
        for(int i = 0; i < 501; i++) {
            int bit = 0;
            for (int j = 0; j < 8; j++) {
                if (x < width) {
                    flag = image.getRGB(x, y) & bitMask;    // get the last digit of the pixel
                    x++;
                }
                else {
                    x = 0;
                    y++;
                    flag = image.getRGB(x, y) & bitMask;    // get the last digit of the pixel
                }

                // store the extracted digits into an integer as a ASCII number
                if (flag == 1) {
                    bit = bit >> 1;
                    bit = bit | 0x80;
                }
                else {
                    bit = bit >> 1;
                }
            }
            c.add((char) bit);
            //System.out.print(c[i]);
        }
        Object[] messageArray = new Object[c.size()];
        messageArray = c.toArray();
        String message = messageArray.toString();
        return message;
    }

    // embed secret image into a "cover image"
    public static BufferedImage encodeImage(BufferedImage imageC, BufferedImage imageS) {
        int bitMask = 0x00000001;	// define the mask bit used to get the digit
        int x = 0;					// define the starting pixel x
        int y = 0;					// define the starting pixel y
        int l = imageS.getWidth() * imageS.getHeight();	// calculate the total pixel in the secret image
        int[] array = new int[l];	// define an array to store the color numbers from the secret image

        // store the RGB from the secret to an array
        for(int i = 0; i < array.length; i++) {
            if(x < imageS.getWidth()) {
                array[i] = imageS.getRGB(x, y);
                x++;
            }
            else {
                x = 0;
                y++;
                array[i] = imageS.getRGB(x, y);
            }
        }
        x = 0;  // reset the x coordinate of the cover image
        y = 0;	// reset the y coordinate of the cover image
        for(int i = 0; i < l; i++) {
            for(int j = 0; j < 32; j++) {
                int flag = array[i] & bitMask;	// get 1 digit from the character
                if(flag == 1) {
                    if(x < imageC.getWidth()) {
                        imageC.setRGB(x, y, imageC.getRGB(x, y) | 0x00000001); 	// store the bit which is 1 into a pixel's last digit
                        x++;
                    }
                    else {
                        x = 0;
                        y++;
                        imageC.setRGB(x, y, imageC.getRGB(x, y) | 0x00000001); 	// store the bit which is 1 into a pixel's last digit
                    }
                }
                else {
                    if(x < imageC.getWidth()) {
                        imageC.setRGB(x, y, imageC.getRGB(x, y) & 0xFFFFFFFE);	// store the bit which is 0 into a pixel's last digit
                        x++;
                    }
                    else {
                        x = 0;
                        y++;
                        imageC.setRGB(x, y, imageC.getRGB(x, y) & 0xFFFFFFFE);	// store the bit which is 0 into a pixel's last digit
                    }
                }
                array[i] = array[i] >> 1;				// get the next digit from the character
            }
        }

        // save the image which contains the secret information to another image file
        try {
            File outputfile = new File("C:\\Users\\Jake Howell\\Desktop\\encodedimage.jpg");
            ImageIO.write(imageC, "jpg", outputfile);
        } catch (IOException e) {

        }
        return imageC;
    }

    // extract secret image from a "cover image"
    public static void decodeImage(BufferedImage image, int width, int height) {
        int bitMask = 0x00000001;	// define the mask bit used to get the digit
        int x = 0;					// define the starting pixel x
        int y = 0;					// define the starting pixel y
        int flag;
        BufferedImage imageStore = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int pixelNumber = width * height;
        int[] array = new int[pixelNumber] ;
        for(int i = 0; i < pixelNumber; i++) {
            int bit = 0x00000000;

            // 32 digits form a pixel
            for(int j = 0; j < 32; j++) {
                if(x < image.getWidth()) {
                    flag = image.getRGB(x, y) & bitMask;	// get the last digit of the pixel
                    x++;
                }
                else {
                    x = 0;
                    y++;
                    flag = image.getRGB(x, y) & bitMask;	// get the last digit of the pixel
                }

                // store the extracted digits into an integer
                if(flag == 1) {
                    bit = bit >> 1;
                    bit = bit | 0x80000000;
                }
                else {
                    bit = bit >> 1;
                    bit = bit & 0x7FFFFFFF;
                }
            }
            array[i] = bit;	// store the integer color in to an array
            //System.out.println(Integer.toBinaryString(array[i]));
        }


        x = 0;  // reset the x coordinate of the extracted image
        y = 0;	// reset the y coordinate of the extracted image

        // draw the extracted image
        for(int i = 0; i < array.length; i++){
            if(x < width) {
                imageStore.setRGB(x, y, array[i]);
                x++;
            }
            else {
                x = 0;
                y++;
                imageStore.setRGB(x, y, array[i]);
            }
        }

        // store the extracted image
        try {
            File outputfile = new File("C:\\Users\\Jake Howell\\Desktop\\extractedimage.jpg");
            ImageIO.write(imageStore, "jpg", outputfile);
        } catch (IOException e) {

        }


    }
}
