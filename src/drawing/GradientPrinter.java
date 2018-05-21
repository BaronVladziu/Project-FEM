package drawing;

import solver.Matrix;

import java.awt.*;

public class GradientPrinter {

    private int _matrixSize;
    private ColoredPoint _points[][];
    private float _tileSize;

    GradientPrinter() {};

    void initialize(Matrix matrix, float tileSize) {
        _tileSize = tileSize;
        _matrixSize = matrix.getSizeY();
        _points = new ColoredPoint[_matrixSize][_matrixSize];
        for (int x = 0; x < _matrixSize; x++) {
            for (int y = 0; y < _matrixSize; y++) {
                _points[x][y] = new ColoredPoint(x*_tileSize, y*_tileSize, countColor(matrix.get(x, y)));
            }
        }
    }

    private Color countColor(double value) {
        int mean = (int)(255*sigmoid(value));
        if (mean > 0) {
            return new Color(mean, 255-mean, 0);
        } else {
            mean = Math.abs(mean);
            return new Color(0, 255-mean, mean);
        }
    }

    private double sigmoid(double x) {
        return (2/( 1 + Math.pow(Math.E,(-1*x))) - 1);
    }

    void print(Graphics2D g2d) {
        for (int x = 0; x < _matrixSize - 1; x++) {
            for (int y = 0; y < _matrixSize - 1; y++) {
                int posX = (int)(_points[x][y].getX());
                int posY = (int)(_points[x][y].getY());
                int sizeX = (int)(_points[x+1][y].getX() - _points[x][y].getX());
                int sizeY = (int)(_points[x][y+1].getY() - _points[x][y].getY());
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
        return (1.f - ((float)x / (float)sizeX))*(1.f - ((float)y / (float)sizeY));
    }

}