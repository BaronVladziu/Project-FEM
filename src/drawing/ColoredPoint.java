package drawing;

import java.awt.*;

public class ColoredPoint {

    private int _x;
    private int _y;
    private Color _color;

    ColoredPoint(float x, float y, Color color) {
        setPosition(x, y);
        setColor(color);
    }

    int getX() {
        return _x;
    }

    int getY() {
        return _y;
    }

    Color getColor() {
        return _color;
    }

    void setPosition(float x, float y) {
        _x = (int)x;
        _y = (int)y;
    }

    void setColor(Color color) {
        _color = color;
    }

}