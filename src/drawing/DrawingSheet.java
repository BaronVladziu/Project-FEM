package drawing;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class DrawingSheet extends JPanel {

    public DrawingSheet() {
        setPreferredSize(new Dimension(800, 600));
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2d = (Graphics2D) graphics;

        Rectangle2D rect = new Rectangle2D.Float(100, 100, 200, 200);

        g2d.draw(rect);
    }

}