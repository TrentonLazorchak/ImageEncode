import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.geom.Ellipse2D;

public class FileSaver {


    public static void main(String[] args ) {
        //Creating a test image


        BufferedImage Testimage = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);


        int r = 5;
        int g = 35;
        int b = 350;

        int col = (r << 16) | (g << 6) | b;
        for (int x = 0; x < 500; x++) {
            for (int y = 20; y < 300; y++) {
                Testimage.setRGB(x, y, col);
            }
            // Creating Graphics for the test image
            Graphics2D graph =Testimage.createGraphics();

            graph.setColor(Color.red);
            graph.fill(new Ellipse2D.Float(100,150,200,100));
            graph.dispose();
        }


        //Creating the save option
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("png", "png"));
        if(chooser.showSaveDialog(null)== JFileChooser.APPROVE_OPTION){
            File file = chooser.getSelectedFile();

            try{
                ImageIO.write((BufferedImage)Testimage, "png", new File(file.getAbsolutePath()));
            } catch (IOException e){
                System.out.println("Failed to save image");
            }
        } else {
            System.out.println("no file chossen");
        }
        String fileLoc = chooser.getSelectedFile().getAbsolutePath();       //The Location of the file
    }

}

