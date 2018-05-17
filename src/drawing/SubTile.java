package drawing;

import java.awt.*;
import java.awt.geom.Rectangle2D;

class SubTile extends Rectangle2D.Float {

    private Color _fillColor;
    private float _posx;
    private float _posy;
    private float _size;

    SubTile(float posx, float posy, float size, Color startColor) {
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

    void setColor(double nw, double ne, double sw, double se) {
        int mean = (int)(255*sigmoid(nw + ne + sw + se));
        if (mean > 0) {
            _fillColor = new Color(mean, 255-mean, 0);
        } else {
            mean = Math.abs(mean);
            _fillColor = new Color(0, 255-mean, mean);
        }
    }

    double sigmoid(double x) {
        return (2/( 1 + Math.pow(Math.E,(-1*x))) - 1);
    }

    float getPosX() {
        return _posx;
    }

    float getPosY() {
        return _posy;
    }

    float getSize() {
        return _size;
    }

}