package solver;

import java.util.Random;

public class Matrix {

    private final int _sizeX;
    private final int _sizeY;
    private final Random _random = new Random();
    private double _table[][];

    Matrix(int sizeX, int sizeY) {
        _sizeX = sizeX;
        _sizeY = sizeY;
        _table = new double[sizeX][sizeY];
    }

    double get(int x, int y) {
        return _table[x][y];
    }

    void set(int x, int y, double value) {
        _table[x][y] = value;
    }

    void print() {
        System.out.println('[');
        for (int j = 0; j < _sizeY; j++) {
            for (int i = 0; i < _sizeX; i++) {
                System.out.print(_table[i][j]);
                System.out.print('\t');
            }
            System.out.print('\n');
        }
        System.out.println("]");
    }

    void randomize() {
        for (int j = 0; j < _sizeY; j++) {
            for (int i = 0; i < _sizeX; i++) {
                _table[i][j] = _random.nextDouble();
            }
        }
    }

    void solve() throws MatrixException {
        //Gaussian method
        if (!isEasilySolvable()) {
            throw new MatrixException("Matrix not easily solvable!");
        }
        for (int j = 0; j < _sizeY; j++) {
            scaleX(j, 1/_table[j][j]);
            for (int i = j+1; i < _sizeY; i++) {
                subtractX(i, j, _table[j][i]);
            }
        }
        for (int j = _sizeY - 1; j >= 0; j--) {
            scaleX(j, 1/_table[j][j]);
            for (int i = j-1; i >= 0; i--) {
                subtractX(i, j, _table[j][i]);
            }
        }
    }

    boolean isEasilySolvable() {
        if (_sizeY + 1 != _sizeX) {
            return false;
        }
        for (int i = 0; i < _sizeY; i++) {
            if (_table[i][i] == 0) {
                return false;
            }
        }
        return true;
    }

    private void subtractX(int fromY, int posY, double factor) {
        if (factor != 0) {
            for (int i = 0; i < _sizeX; i++) {
                _table[i][fromY] -= factor * _table[i][posY];
            }
        }
    }

    private void scaleX(int posY, double factor) {
        if (factor != 0) {
            for (int i = 0; i < _sizeX; i++) {
                _table[i][posY] *= factor;
            }
        }
    }

    private void scaleY(int posX, double factor) {
        for (int j = 0; j < _sizeY; j++) {
            _table[posX][j] *= factor;
        }
    }

    int getSizeX() {
        return _sizeX;
    }

    int getSizeY() {
        return _sizeY;
    }

}