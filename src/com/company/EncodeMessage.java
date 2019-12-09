package com.company;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class EncodeMessage {
    public static String decMsg;

    // embed secret information/TEXT into a "cover image"
    public static void encodeText(String imgPath, String text) throws IOException {
        int bitMask = 0x00000001;	// define the mask bit used to get the digit
        int bit;				// define an integer number to represent the ASCII number of a character
        int x = 0;				// define the starting pixel x
        int y = 0;				// define the starting pixel y

        // save the image which contains the secret information to another image file
        BufferedImage image = ImageIO.read(new File(imgPath));
        File outputfile = null;
        try {
            String imageName = "encodedMsg-" + EncodeScreen.fileName;
            String imagePath = EncodeScreen.filePath + "/" + imageName;
            outputfile = new File(imagePath);
            ImageIO.write(image, "jpg", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedImage newImg = ImageIO.read(outputfile);

        for(int i = 0; i < text.length(); i++) {
            bit = (int) text.charAt(i);
            //System.out.println("Inputed Bit: " + bit);
            //System.out.println("Bit: " + bit); // get the ASCII number of a character
            for(int j = 0; j < 8; j++) {
                int flag = bit & bitMask;
                //System.out.println("Flag: " + flag); // get 1 digit from the character
                if(flag == 1) {
                    if(x < newImg.getWidth()) {
                        //System.out.println("RGB of " + x + "," + y + ": " + (image.getRGB(x,y)));
                        newImg.setRGB(x, y, newImg.getRGB(x, y) | 0x00000001); // store the bit which is 1 into a pixel's last digit
                        //System.out.println("New RGB of " + x + "," + y + ": " + (image.getRGB(x,y)));
                        x++;
                    }
                    else {
                        x = 0;
                        y++;
                        //System.out.println("RBG of " + x + "," + y + ": " + (image.getRGB(x,y)));
                        newImg.setRGB(x, y, newImg.getRGB(x, y) | 0x00000001); 	// store the bit which is 1 into a pixel's last digit
                        //System.out.println("New RGB of " + x + "," + y + ": " + (image.getRGB(x,y)));
                    }
                }
                else {
                    if(x < newImg.getWidth()) {
                        //System.out.println("RGB of " + x + "," + y + ": " + (image.getRGB(x,y)));
                        newImg.setRGB(x, y, newImg.getRGB(x, y) & 0xFFFFFFFE);	// store the bit which is 0 into a pixel's last digit
                        //System.out.println("New RGB of " + x + "," + y + ": " + (image.getRGB(x,y)));
                        x++;
                    }
                    else {
                        x = 0;
                        y++;
                        //System.out.println("RGB of " + x + "," + y + ": " + (image.getRGB(x,y)));
                        newImg.setRGB(x, y, newImg.getRGB(x, y) & 0xFFFFFFFE);	// store the bit which is 0 into a pixel's last digit
                        //System.out.println("New RGB of " + x + "," + y + ": " + (image.getRGB(x,y)));
                    }
                }
                //System.out.println("Bit Before: " + bit);
                bit = bit >> 1;			// get the next digit from the character
                //System.out.println("Bit After: " + bit);
            }
        }

    }



    // extract secret information/Text from a "cover image"
    public static void decodeText(String imgPath) throws IOException {
        //System.out.print("Extracting: ");
        int bitMask = 0x00000001;	// define the mask bit used to get the digit
        int x = 0;					// define the starting pixel x
        int y = 0;					// define the starting pixel y
        int flag;
        int bit = 0;
        BufferedImage image = ImageIO.read(new File(imgPath));
        ArrayList<Character> c = new ArrayList<>();	// define a character array to store the secret information
        System.out.println("Extracted Message: ");
        while(!(c.contains('>') && c.contains('q') && c.contains('<'))) {
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



    public static String ArrayList_String(ArrayList<Character> message) {
        StringBuffer sb = new StringBuffer();

        for (char s : message) {
            sb.append(s);
            //sb.append("");
        }
        String str = sb.toString();
        return str;
    }
}
