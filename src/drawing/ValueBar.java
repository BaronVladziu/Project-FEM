package drawing;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.text.ParseException;

public class ValueBar {

    private float _posX;
    private float _posY;
    private float _sizeX;
    private float _sizeY;
    private Rectangle2D _frame;
    private final Color _frameColor = new Color(0,0,0);
    private DecimalFormat _format = new DecimalFormat("####0.00");


    ValueBar(float posX, float posY, float sizeX, float sizeY) {
        _posX = posX + 3;
        _posY = posY + 2;
        _sizeX = sizeX - 6;
        _sizeY = sizeY - 5;
        _frame = new Rectangle2D.Float(posX, posY, sizeX, sizeY);
    }

    void print(Graphics2D g2d, double minValue, double maxValue) {
        Color color = _frameColor;
        g2d.fill(_frame);
        for (int y = 1; y < _sizeY; y++) {
            color = ColorCounter.countColor(1 - 2*y/_sizeY);
            for (int x = 0; x < _sizeX; x++) {
                g2d.setColor(color);
                g2d.fillRect((int)_posX + x, (int)_posY + y, 1, 1);
            }
        }
        g2d.drawString(_format.format(maxValue), _posX + _sizeX + 4, _posY);
        g2d.drawString(_format.format(minValue), _posX + _sizeX + 4, _posY + _sizeY);
    }

}