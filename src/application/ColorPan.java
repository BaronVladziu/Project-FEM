package application;

import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class ColorPan extends JComponent {
    BufferedImage image;

    public void initialize() {
        int width = getSize().width;
        int height = getSize().height;
        int[] data = new int[width * height];
        int index = 0;
        for (int i = 0; i < height; i++) {
            int red = (i * 255) / (height - 1);
            for (int j = 0; j < width; j++) {
                int green = (j * 255) / (width - 1);
                int blue = 128;
                data[index++] = (red << 16) | (green << 8) | blue;
            }
        }

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, width, height, data, 0, width);
    }

    public void paint(Graphics g) {
        if (image == null)
            initialize();
        g.drawImage(image, 0, 0, this);
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("ColorPan");
        f.getContentPane().add(new ColorPan());
        f.setSize(300, 300);
        f.setLocation(100, 100);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        f.setVisible(true);
    }
}