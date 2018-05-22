package drawing;

import java.awt.*;

public class ColoredPoint {

    private float _x;
    private float _y;
    private Color _color;

    ColoredPoint(float x, float y, Color color) {
        setPosition(x, y);
        setColor(color);
    }

    float getX() {
        return _x;
    }

    float getY() {
        return _y;
    }

    Color getColor() {
        return _color;
    }

    void setPosition(float x, float y) {
        _x = x;
        _y = y;
    }

    void setColor(Color color) {
        _color = color;
    }

}