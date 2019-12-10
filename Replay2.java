import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Replay2 extends JFrame {


    public static void main(String[] args) {

        new  Replay2();

        //System.out.println(ss);
    }




    public  Replay2() {


        final File folder = new File("/Users/lanre/Documents");

        List<String> result = new ArrayList<>();
        String fileList[] = new String[100];

        search( ".*.png", folder, result);

        int i = 0;
        for (
                String s : result) {
            fileList[i] = s;
            i++;


        }


        //Making the J frame
        JFrame f = new JFrame("Replay system");

        JList b = new JList(fileList);


        JPanel p = new JPanel();
        Dimension pSize= new Dimension(1000, 1000);
        p.setMinimumSize(pSize);
        p.setPreferredSize(pSize);
        p.setMaximumSize(pSize);







        JLabel l = new JLabel("Select the file you want to view");








        //Making the scrollbarr
        JScrollPane sc = new JScrollPane(b);
        sc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
        Dimension scSize = new Dimension(350, 350);
        sc.setMinimumSize(scSize);
        sc.setPreferredSize(scSize);
        sc.setMaximumSize(scSize);




        p.add(l);
        p.add(sc);
        f.add(p);
        f.setExtendedState(MAXIMIZED_BOTH);
        f.setVisible(true);
        MouseListener mouselis = new MouseAdapter() {
            @Override
            public void  mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    String ss=(String)b.getSelectedValue();
                    System.out.println(ss);
                    try { BufferedImage img = ImageIO.read(new File(ss));
                    ImageIcon icon=new ImageIcon(img);
                    JFrame frame=new JFrame();
                    frame.setLayout(new FlowLayout());
                    frame.setSize(500,500);
                    JLabel lbl=new JLabel();
                    lbl.setIcon(icon);
                    frame.add(lbl);
                    frame.setVisible(true);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }

                     catch (IOException e1) {
                        e1.printStackTrace();
                    }
                   // File ff = new File(ss);


                }

            }


        };
        b.addMouseListener(mouselis);


}



    public static void search(final  String pattern1, final File folder, List<String> result) {
        for (final File f : folder.listFiles()) {

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