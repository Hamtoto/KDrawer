package kdr.fig;

import java.awt.*;
import java.io.*;
import java.awt.image.*;

public class KImage extends KBox implements Serializable {

    transient private BufferedImage image;
    int imageWidth;
    int imageHeight;
    int imageType;

    public KImage(BufferedImage img, int x, int y) {
        super(Color.black, x, y, x + img.getWidth(), y + img.getHeight());
        imageWidth = img.getWidth();
        imageHeight = img.getHeight();
        imageType = img.getType();

        image = new BufferedImage(imageWidth, imageHeight, imageType);
        Color white = new Color(255, 255, 255);
        for (int i = 0; i < imageWidth; i++) {
            for (int j = 0; j < imageHeight; j++) {
                image.setRGB(i, j, white.getRGB());
            }
        }
        for (int i = 0; i < imageWidth; i++) {
            for (int j = 0; j < imageHeight; j++) {
                if (img.getRGB(i, j) != 0)
                    image.setRGB(i, j, img.getRGB(i, j));
            }
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        for (int i = 0; i < imageWidth; i++) {
            for (int j = 0; j < imageHeight; j++) {
                out.writeInt(image.getRGB(i, j));
            }
        }
    }

    public void preparePopup() {
    }

    private void readObject(ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        image = new BufferedImage(imageWidth, imageHeight, imageType);
        for (int i = 0; i < imageWidth; i++) {
            for (int j = 0; j < imageHeight; j++) {
                image.setRGB(i, j, in.readInt());
            }
        }
    }

    protected void drawEx(Graphics g) {
        if (dotFlag == true) drawDotEx(g);

        if (image != null) {
            g.drawImage(image, x1, y1, x2 - x1, y2 - y1, null);
        }
    }

    public KFigure copy() {
        KImage newImage = new KImage(image, x1, y1);
        newImage.popup = popup;
        return newImage;
    }
}
