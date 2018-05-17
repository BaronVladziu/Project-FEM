package drawing;

import java.awt.*;

class Tile {

    private SubTile _fillTile;
    private SubTile[][] _valueTilesTable;
    private E_TileType _type;
    private int _split;
    private float _subSize;

    Tile(float posx, float posy, float size, E_TileType type, Color startColor) {
        _fillTile = new SubTile(posx, posy, size, startColor);
        _type = type;
    }

    void setTranslation(int x, int y, float zoom) {
        _fillTile.setTranslation(x, y, zoom);
        if (_valueTilesTable != null) {
            for (int i = 0; i < _split; i++) {
                for (int j = 0; j < _split; j++) {
                    _valueTilesTable[i][j].setTranslation(x, y, zoom);
                }
            }
        }
    }

    SubTile getFillSubTile() {
        return _fillTile;
    }

    SubTile getValueSubTile(int x, int y) {
        return _valueTilesTable[x][y];
    }

    void setType(E_TileType type, Color color) {
        _type = type;
        _fillTile.setColor(color);
    }

    void createValueTable(int split) {
        _split = split;
        _valueTilesTable = new SubTile[_split][_split];
        _subSize = _fillTile.getSize()/_split;
        for (int i = 0; i < _split; i++) {
            for (int j = 0; j < _split; j++) {
                _valueTilesTable[i][j] = new SubTile(_fillTile.getPosX() + i*_subSize,
                        _fillTile.getPosY() + j*_subSize, _subSize, _fillTile.getColor());
            }
        }
    }

    int getSplit() {
        return _split;
    }

    void setColor(int x, int y, double nw, double ne, double sw, double se) {
        _valueTilesTable[x][y].setColor(nw, ne, sw, se);
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