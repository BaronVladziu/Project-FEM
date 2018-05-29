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
                _points[x][y] = new ColoredPoint(x*_tileSize, y*_tileSize, countColor(matrix.get(x, y)));
            }
        }
    }

    private Color countColor(double value) {
        if (value < -1 || value > 1) {
            throw new IllegalValueError("Color value not between -1 and 1!");
        }
        int valueRange = 2;
        value *= valueRange;
        int rest = (int)(255*((value + valueRange) - ((int)(value + valueRange))));
        System.out.println(Double.toString(value) + " --> " + Double.toString((value + valueRange) - ((int)(value + valueRange))));

        if (value > 0) {
            if (value > 1) {
                return new Color(255, 255-rest, 0);
            } else {
                return new Color(rest, 255, 0);
            }
        } else {
            if (value > -1) {
                return new Color(0, 255, 255-rest);
            } else {
                return new Color(0, rest, 255);
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
        r += colors[0].getRed()*colorFadeFunction(sizeX, sizeY, x, y);
        r += colors[1].getRed()*colorFadeFunction(sizeX, sizeY, sizeX - x, y);
        r += colors[2].getRed()*colorFadeFunction(sizeX, sizeY, x, sizeY - y);
        r += colors[3].getRed()*colorFadeFunction(sizeX, sizeY, sizeX - x, sizeY - y);
        g += colors[0].getGreen()*colorFadeFunction(sizeX, sizeY, x, y);
        g += colors[1].getGreen()*colorFadeFunction(sizeX, sizeY, sizeX - x, y);
        g += colors[2].getGreen()*colorFadeFunction(sizeX, sizeY, x, sizeY - y);
        g += colors[3].getGreen()*colorFadeFunction(sizeX, sizeY, sizeX - x, sizeY - y);
        b += colors[0].getBlue()*colorFadeFunction(sizeX, sizeY, x, y);
        b += colors[1].getBlue()*colorFadeFunction(sizeX, sizeY, sizeX - x, y);
        b += colors[2].getBlue()*colorFadeFunction(sizeX, sizeY, x, sizeY - y);
        b += colors[3].getBlue()*colorFadeFunction(sizeX, sizeY, sizeX - x, sizeY - y);
        return new Color(r, g, b);
    }

    private float colorFadeFunction(int sizeX, int sizeY, int x, int y) {
        return linFun((float)x / (float)sizeX)*linFun((float)y / (float)sizeY);
    }

    private float linFun(float x) {
        return 1.f - x;
    }

}