package drawing;

import solver.Matrix;

import java.awt.*;

public class GradientPrinter {

    private int _matrixSizeX;
    private int _matrixSizeY;
    private ColoredPoint _points[][];
    private float _tileSize;

    GradientPrinter() {};

    void initialize(Matrix matrix, float tileSize) {
        _tileSize = tileSize;
        _matrixSizeX = matrix.getSizeX();
        _matrixSizeY = matrix.getSizeY();
        _points = new ColoredPoint[_matrixSizeX][_matrixSizeY];
        for (int x = 0; x < _matrixSizeX; x++) {
            for (int y = 0; y < _matrixSizeY; y++) {
                _points[x][y] = new ColoredPoint(x*_tileSize, y*_tileSize,
                        ColorCounter.countColor(matrix.get(x, y)));
            }
        }
    }

    void print(Graphics2D g2d, int transX, int transY, float zoom) {
        for (int x = 0; x < _matrixSizeX - 1; x++) {
            for (int y = 0; y < _matrixSizeY - 1; y++) {
                int posX = (int)(_points[x][y].getX()*zoom + transX);
                int posY = (int)(_points[x][y].getY()*zoom + transY);
                int posNextX = (int)(_points[x+1][y].getX()*zoom + transX);
                int posNextY = (int)(_points[x][y+1].getY()*zoom + transY);
                int sizeX = posNextX - posX;
                int sizeY = posNextY - posY;
                Color[] colors = {_points[x][y].getColor(), _points[x+1][y].getColor(),
                        _points[x][y+1].getColor(), _points[x+1][y+1].getColor()};
                for (int v1 = 0; v1 < sizeX; v1++) {
                    for (int v2 = 0; v2 < sizeY; v2++) {
                        g2d.setColor(mixColors(colors, v1, v2, sizeX, sizeY));
                        g2d.fillRect(posX + v1, posY + v2, 1, 1);
                    }
                }
            }
        }
    }

    private Color mixColors(Color[] colors, int x, int y, int sizeX, int sizeY) {
        int r = 0;
        int g = 0;
        int b = 0;
        r += colors[0].getRed()*ColorCounter.colorFadeFunction(sizeX, sizeY, x, y);
        r += colors[1].getRed()*ColorCounter.colorFadeFunction(sizeX, sizeY, sizeX - x, y);
        r += colors[2].getRed()*ColorCounter.colorFadeFunction(sizeX, sizeY, x, sizeY - y);
        r += colors[3].getRed()*ColorCounter.colorFadeFunction(sizeX, sizeY, sizeX - x, sizeY - y);
        g += colors[0].getGreen()*ColorCounter.colorFadeFunction(sizeX, sizeY, x, y);
        g += colors[1].getGreen()*ColorCounter.colorFadeFunction(sizeX, sizeY, sizeX - x, y);
        g += colors[2].getGreen()*ColorCounter.colorFadeFunction(sizeX, sizeY, x, sizeY - y);
        g += colors[3].getGreen()*ColorCounter.colorFadeFunction(sizeX, sizeY, sizeX - x, sizeY - y);
        b += colors[0].getBlue()*ColorCounter.colorFadeFunction(sizeX, sizeY, x, y);
        b += colors[1].getBlue()*ColorCounter.colorFadeFunction(sizeX, sizeY, sizeX - x, y);
        b += colors[2].getBlue()*ColorCounter.colorFadeFunction(sizeX, sizeY, x, sizeY - y);
        b += colors[3].getBlue()*ColorCounter.colorFadeFunction(sizeX, sizeY, sizeX - x, sizeY - y);
        return new Color(r, g, b);
    }

}