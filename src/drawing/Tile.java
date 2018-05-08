package drawing;

import java.awt.*;
import java.awt.geom.Rectangle2D;

class Tile extends Rectangle2D.Float {

    private Color _fillColor;
    private float _posx;
    private float _posy;
    private float _size;

    Tile(float posx, float posy, float size, Color startColor) {
        super(posx, posy, size, size);
        _posx = posx;
        _posy = posy;
        _size = size;
        _fillColor = startColor;
    }

    void setTranslation(int x, int y, float zoom) {
        super.x = (_posx * zoom) + x;
        super.y = (_posy * zoom) + y;
        super.width = _size * zoom;
        super.height = _size * zoom;
    }

    Color getColor() {
        return _fillColor;
    }

    void setColor(Color color) {
        _fillColor = color;
    }

    float getPosX() {
        return _posx;
    }

    float getPosY() {
        return _posy;
    }

}