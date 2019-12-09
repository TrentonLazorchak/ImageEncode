package com.company;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

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

        BufferedImage newImg = copyImage(image);

        for(int i = 0; i < text.length(); i++) {
            bit = (int) text.charAt(i);
            for(int j = 0; j < 8; j++) {
                int flag = bit & bitMask;
                if(flag == 1) {
                    if(x < newImg.getWidth()) {
                        newImg.setRGB(x, y, newImg.getRGB(x, y) | 0x00000001); // store the bit which is 1 into a pixel's last digit
                        x++;
                    }
                    else {
                        x = 0;
                        y++;
                        newImg.setRGB(x, y, newImg.getRGB(x, y) | 0x00000001); 	// store the bit which is 1 into a pixel's last digit
                    }
                }
                else {
                    if(x < newImg.getWidth()) {
                        newImg.setRGB(x, y, newImg.getRGB(x, y) & 0xFFFFFFFE);	// store the bit which is 0 into a pixel's last digit
                        x++;
                    }
                    else {
                        x = 0;
                        y++;
                        newImg.setRGB(x, y, newImg.getRGB(x, y) & 0xFFFFFFFE);	// store the bit which is 0 into a pixel's last digit
                    }
                }
                bit = bit >> 1;			// get the next digit from the character
            }
        }

        try {
            String imageName = "encodedMsg-" + EncodeScreen.fileName;
            String imagePath = EncodeScreen.filePath + "/" + imageName;
            File outputfile = new File(imagePath);
            ImageIO.write(newImg, "png", outputfile);
            Log.LOGGER.info("Encoded message to image and wrote encoded image to new file: " + imagePath);
        } catch (IOException e) {
            e.printStackTrace();
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

    public static void ArrayList_String(ArrayList<Character> message) {
        StringBuilder sb = new StringBuilder();

        for (char s : message) {
            sb.append(s);
            //sb.append("");
        }
        decMsg = sb.toString();
        decMsg = decMsg.substring(0,decMsg.length()-3);
        System.out.println(decMsg);
    }

    public static BufferedImage copyImage(BufferedImage source){
        BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics g = b.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return b;
    }
}
