package drawing;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Tile extends Rectangle2D.Float {

    private Color _fillColor;

    public Tile(float posx, float posy, float size) {
        super(posx, posy, size, size);
        _fillColor = new Color(255, 0, 0);
    }

    Color getColor() {
        return _fillColor;
    }

}