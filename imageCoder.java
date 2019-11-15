//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class imageCoder {
    BufferedImage img = null;

    public imageCoder(String add) {
        try {
            this.img = ImageIO.read(new File(add));
        } catch (Exception var3) {
        }

    }

    public void setText(String text) {
        this.write(text);
    }

    private void write(String text) {
        int place = 0;
        int count = 3;
        int maxX = this.img.getWidth();
        int maxY = this.img.getHeight();
        this.clean();

        for(int y = 0; y < maxY; ++y) {
            for(int x = 0; x < maxX; ++x) {
                int pix = this.img.getRGB(x, y);
                int blue = pix & 255;
                int green = (pix & '\uff00') >> 8;
                int red = (pix & 16711680) >> 16;
                blue -= blue % 4;
                red -= red % 4;
                green -= green % 4;
                char let = text.charAt(place);
                red += (let >> count * 2) % 4;
                if (count-- < 0) {
                    count = 3;
                    ++place;
                    if (place >= text.length()) {
                        this.img.setRGB(x, y, (red << 16) + (green << 8) + blue);
                        return;
                    }

                    let = text.charAt(place);
                }

                green += (let >> count * 2) % 4;
                if (count-- < 0) {
                    count = 3;
                    ++place;
                    if (place >= text.length()) {
                        this.img.setRGB(x, y, (red << 16) + (green << 8) + blue);
                        return;
                    }

                    let = text.charAt(place);
                }

                blue += (let >> count * 2) % 4;
                if (count-- < 0) {
                    count = 3;
                    ++place;
                    if (place >= text.length()) {
                        this.img.setRGB(x, y, (red << 16) + (green << 8) + blue);
                        return;
                    }
                }

                this.img.setRGB(x, y, (red << 16) + (green << 8) + blue);
            }
        }

    }

    public String getText() {
        return this.read();
    }

    private String read() {
        String text = "";
        int count = 3;
        char let = 0;
        int maxX = this.img.getWidth();
        int maxY = this.img.getHeight();

        for(int y = 0; y < maxY; ++y) {
            for(int x = 0; x < maxX; ++x) {
                int pix = this.img.getRGB(x, y);
                int blue = pix & 255;
                int green = (pix & '\uff00') >> 8;
                int red = (pix & 16711680) >> 16;
                red %= 4;
                let = (char)(let + (red << count * 2));
                if (count-- < 0) {
                    if (let == 0) {
                        return text;
                    }

                    text = text + let;
                    let = 0;
                    count = 3;
                }

                green %= 4;
                let = (char)(let + (green << count * 2));
                if (count-- < 0) {
                    if (let == 0) {
                        return text;
                    }

                    text = text + let;
                    let = 0;
                    count = 3;
                }

                blue %= 4;
                let = (char)(let + (blue << count * 2));
                if (count-- < 0) {
                    if (let == 0) {
                        return text;
                    }

                    text = text + let;
                    let = 0;
                    count = 3;
                }
            }
        }

        return text;
    }

    public void redify() {
        int maxX = this.img.getWidth();
        int maxY = this.img.getHeight();

        for(int y = 0; y < maxY; ++y) {
            for(int x = 0; x < maxX; ++x) {
                this.img.setRGB(x, y, 16711680);
            }
        }

    }

    public void clean() {
        int maxX = this.img.getWidth();
        int maxY = this.img.getHeight();

        for(int y = 0; y < maxY; ++y) {
            for(int x = 0; x < maxX; ++x) {
                int pix = this.img.getRGB(x, y);
                int blue = pix & 255;
                int green = (pix & '\uff00') >> 8;
                int red = (pix & 16711680) >> 16;
                blue -= blue % 4;
                red -= red % 4;
                green -= green % 4;
                this.img.setRGB(x, y, (red << 16) + (green << 8) + blue);
            }
        }

    }
}
