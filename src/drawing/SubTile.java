package drawing;

import java.awt.*;
import java.awt.geom.Rectangle2D;

class SubTile extends Rectangle2D.Float {

    private Color[] _colors = new Color[4];
    private float _posx;
    private float _posy;
    private float _size;

    SubTile(float posx, float posy, float size, Color startColor) {
        super(posx, posy, size, size);
        _posx = posx;
        _posy = posy;
        _size = size;
        for (int i = 0; i < 4; i++) {
            _colors[i] = startColor;
        }
    }

    void setTranslation(int x, int y, float zoom) {
        super.x = (_posx * zoom) + x;
        super.y = (_posy * zoom) + y;
        super.width = _size * zoom;
        super.height = _size * zoom;
    }

    Color getColor(int nr) {
        return _colors[nr];
    }

    Color[] getColors() {
        return _colors;
    }

    void setColor(Color color) {
        for (int i = 0; i < 4; i++) {
            _colors[i] = color;
        }
    }

//    void setColor(double nw, double ne, double sw, double se) {
//        setColor(0, nw);
//        setColor(1, ne);
//        setColor(2, sw);
//        setColor(3, se);
//    }
//
//    private void setColor(int nr, double value) {
//        int mean = (int)(255*sigmoid(value));
//        if (mean > 0) {
//            _colors[nr] = new Color(mean, 255-mean, 0);
//        } else {
//            mean = Math.abs(mean);
//            _colors[nr] = new Color(0, 255-mean, mean);
//        }
//    }
//
//    double sigmoid(double x) {
//        return (2/( 1 + Math.pow(Math.E,(-1*x))) - 1);
//    }

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