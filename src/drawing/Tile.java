package drawing;

import java.awt.*;

class Tile {

    private SubTile _fillTile;
    private E_TileType _type;

    Tile(float posx, float posy, float size, E_TileType type, Color startColor) {
        _fillTile = new SubTile(posx, posy, size, startColor);
        _type = type;
    }

    void setTranslation(int x, int y, float zoom) {
        _fillTile.setTranslation(x, y, zoom);
    }

    SubTile getFillSubTile() {
        return _fillTile;
    }

    void setType(E_TileType type, Color color) {
        _type = type;
        _fillTile.setColor(color);
    }

    double sigmoid(double x) {
        return (2/( 1 + Math.pow(Math.E,(-1*x))) - 1);
    }

    float getPosX() {
        return _fillTile.getPosX();
    }

    float getPosY() {
        return _fillTile.getPosY();
    }

    E_TileType getType() {
        return _type;
    }

}