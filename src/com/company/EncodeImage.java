package com.company;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EncodeImage {
    // embed secret image into a "cover image"
    public static BufferedImage encodeImage(String imgCPath, String imgSPath) throws IOException {
        int bitMask = 0x00000001;	// define the mask bit used to get the digit
        int x = 0;					// define the starting pixel x
        int y = 0;					// define the starting pixel y
        BufferedImage imageC = ImageIO.read(new File(imgCPath));
        BufferedImage imageS = ImageIO.read(new File(imgSPath));
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
            String imageName = "encodedImg-" + EncodeScreen.fileName;
            String imagePath = EncodeScreen.filePath + "/" + imageName;
            File outputfile = new File(imagePath);
            ImageIO.write(imageC, "png", outputfile);
            Log.LOGGER.info("Encoded image and wrote encoded image to new file: " + imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageC;
    }


}
