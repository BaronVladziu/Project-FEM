package solver;

import application.ConsolePanel;
import drawing.*;

import static java.lang.Math.sqrt;

public class Solver {

    private final DrawingSheet _drawingSheet;
    private final ConsolePanel _console;

    private Matrix _matrix;
    private Matrix _valueMatrix;
    private int _n;
    private double _c;
    private double _w;
    private double _k;
    private double _mapD;
    private int _r;
    private double _d;
    private double _alpha;
    private double _g;
    private double _z;
    private double _minValue;
    private double _maxValue;

    public Solver(DrawingSheet drawingSheet, ConsolePanel console) {
        _drawingSheet = drawingSheet;
        _console = console;
    }

    public void solve(SimulationSettings settings) {
        _console.display("Solving...");
        updateConstants(settings);
        createMatrix();
        //_matrix.print();
        try {
            _matrix.solve();
        } catch (MatrixException e) {
            _console.display(e.getMessage());
        }
        _console.display("Solved");
        //_matrix.print();
        updateValueMatrix(settings.getDrawValueType());
    }

    public void updateValueMatrix(E_DrawValueType valueType) {
        _console.display("Printing...");
        createValueMatrix(valueType);
        scaleValues();
        setValueColors();
        _console.display("Printed");
    }

    private void  updateConstants(SimulationSettings settings) {
        _c = settings.getC();
        _g = settings.getG();
        _z = _c*_g;
        _w = settings.getF() * 2 * Math.PI;
        _k = _w/_c;
        _mapD = settings.getD();
        double d = _c/(6*settings.getF());
        _r = (int)(_mapD / d) + 1;
        _d = _mapD / _r;
        _alpha = _d*_d*_k*_k - 4;
    }

    private void createMatrix() {
        _n = (_drawingSheet.getNOTilesX()*_r + 1) * (_drawingSheet.getNOTilesY()*_r + 1);
        _matrix = new Matrix(2*_n + 1, 2*_n);
        E_TileType ne, se, sw, nw;
        int counters[] = new int[3]; //SOURCES, WALLS, VOIDS
        for (int i = 0; i <= _drawingSheet.getNOTilesX()*_r; i++) {
            se = _drawingSheet.getTileType(i/_r, -1);
            if (i == 0) {
                sw = _drawingSheet.getTileType(-1, -1);
            } else {
                sw = _drawingSheet.getTileType((i - 1) / _r, -1);
            }
            for (int j = 0; j <= _drawingSheet.getNOTilesY()*_r; j++) {
                ne = se;
                nw = sw;
                se = _drawingSheet.getTileType(i/_r, j/_r);
                if (i == 0) {
                    sw = _drawingSheet.getTileType(-1, j / _r);
                } else {
                    sw = _drawingSheet.getTileType((i - 1) / _r, j / _r);
                }
                counters[0] = 0;
                counters[1] = 0;
                counters[2] = 0;
                countTileTypes(ne, counters);
                countTileTypes(nw, counters);
                countTileTypes(sw, counters);
                countTileTypes(se, counters);
                if (counters[0] != 0) {
                    setSource(i, j);
                } else if (counters[1] + counters[2] == 4) {
                    setZero(i, j);
                } else if (counters[2] == 2) {
                    if (nw == E_TileType.Void) {
                        if (ne == E_TileType.Void) {
                            setBorder(i, j, E_Direction8.N);
                        } else {
                            setBorder(i, j, E_Direction8.W);
                        }
                    } else {
                        if (ne == E_TileType.Void) {
                            setBorder(i, j, E_Direction8.E);
                        } else {
                            setBorder(i, j, E_Direction8.S);
                        }
                    }
                } else if (counters[2] == 3) {
                    if (nw != E_TileType.Void) {
                        setBorder(i, j, E_Direction8.SE);
                    } else if (ne != E_TileType.Void) {
                        setBorder(i, j, E_Direction8.SW);
                    } else if (se != E_TileType.Void) {
                        setBorder(i, j, E_Direction8.NW);
                    } else {
                        setBorder(i, j, E_Direction8.NE);
                    }
                } else {
                    if (nw != E_TileType.Wall) {
                        if (ne != E_TileType.Wall) {
                            if (sw != E_TileType.Wall) {
                                if (se != E_TileType.Wall) {    //0000
                                    setFunction(i, j);
                                } else {                        //0001
                                    setWall(i, j, E_Direction8.SE);
                                }
                            } else {
                                if (se != E_TileType.Wall) {    //0010
                                    setWall(i, j, E_Direction8.SW);
                                } else {                        //0011
                                    setWall(i, j, E_Direction8.S);
                                }
                            }
                        } else {
                            if (sw != E_TileType.Wall) {
                                if (se != E_TileType.Wall) {    //0100
                                    setWall(i, j, E_Direction8.NE);
                                } else {                        //0101
                                    setWall(i, j, E_Direction8.E);
                                }
                            } else {
                                if (se != E_TileType.Wall) {    //0110
                                    setFunction(i, j);
                                } else {                        //0111
                                    setWall(i, j, E_Direction8.SE);
                                }
                            }
                        }
                    } else {
                        if (ne != E_TileType.Wall) {
                            if (sw != E_TileType.Wall) {
                                if (se != E_TileType.Wall) {    //1000
                                    setWall(i, j, E_Direction8.NW);
                                } else {                        //1001
                                    setFunction(i, j);
                                }
                            } else {
                                if (se != E_TileType.Wall) {    //1010
                                    setWall(i, j, E_Direction8.W);
                                } else {                        //1011
                                    setWall(i, j, E_Direction8.SW);
                                }
                            }
                        } else {
                            if (sw != E_TileType.Wall) {
                                if (se != E_TileType.Wall) {    //1100
                                    setWall(i, j, E_Direction8.N);
                                } else {                        //1101
                                    setWall(i, j, E_Direction8.NE);
                                }
                            } else {                            //1110
                                setWall(i, j, E_Direction8.NW);
                            }
                        }
                    }
                }
            }
        }
    }

    private void countTileTypes(E_TileType tile, int[] counters) {
        switch (tile) {
            case Source: {
                counters[0]++;
                break;
            }
            case Wall: {
                counters[1]++;
                break;
            }
            case Void: {
                counters[2]++;
                break;
            }
            case Air: {break;}
            default: {
                throw new TileTypeError("Unknown tile type!");
            }
        }
    }

    private void setSource(int x, int y) {
        int k = countK(x, y, true);
        _matrix.set(k, k, 1);
        _matrix.set(_matrix.getSizeX() - 1, k, 1);
        k = countK(x, y, false);
        _matrix.set(k, k, 1);
    }

    private void setFunction(int x, int y) {
        setFunction(x, y, true);
        setFunction(x, y, false);
    }

    private void setFunction(int x, int y, boolean ifReal) {
        int k = countK(x, y, ifReal);
        _matrix.set(k, k, _alpha);
        int l = countK(x-1, y, ifReal);
        _matrix.set(l, k, 1);
        l = countK(x+1, y, ifReal);
        _matrix.set(l, k, 1);
        l = countK(x, y-1, ifReal);
        _matrix.set(l, k, 1);
        l = countK(x, y+1, ifReal);
        _matrix.set(l, k, 1);
    }

    private void setWall(int x, int y, E_Direction8 dir) {
        setWall(x, y, dir, true);
        setWall(x, y, dir, false);
    }

    private void setWall(int x, int y, E_Direction8 dir, boolean ifReal) {
        int k = countK(x, y, ifReal);
        switch (dir) {
            case E: {
                _matrix.set(k, k, 1);
                int l = countK(x-1, y, ifReal);
                _matrix.set(l, k, -1);
                break;
            }
            case NE: {
                _matrix.set(k, k, 1);
                int l = countK(x-1, y+1, ifReal);
                _matrix.set(l, k, -1);
                break;
            }
            case NW: {
                _matrix.set(k, k, -1);
                int l = countK(x+1, y+1, ifReal);
                _matrix.set(l, k, 1);
                break;
            }
            case SE: {
                _matrix.set(k, k, 1);
                int l = countK(x-1, y-1, ifReal);
                _matrix.set(l, k, -1);
                break;
            }
            case SW: {
                _matrix.set(k, k, -1);
                int l = countK(x+1, y-1, ifReal);
                _matrix.set(l, k, 1);
                break;
            }
            case N: {
                _matrix.set(k, k, -1);
                int l = countK(x, y+1, ifReal);
                _matrix.set(l, k, 1);
                break;
            }
            case S: {
                _matrix.set(k, k, 1);
                int l = countK(x, y-1, ifReal);
                _matrix.set(l, k, -1);
                break;
            }
            case W: {
                _matrix.set(k, k, -1);
                int l = countK(x+1, y, ifReal);
                _matrix.set(l, k, 1);
                break;
            }
        }
    }

    private void setBorder(int x, int y, E_Direction8 dir) {
        int kr = countK(x, y, true);
        int ki = countK(x, y, false);
        double a = (_z)/(_w*_g*_d);
        int lr, li;
        switch (dir) {
            case E: {
                li = countK(x-1, y, false);
                lr = countK(x-1, y, true);
                break;
            }
            case NE: {
                a /= sqrt(2);
                li = countK(x-1, y+1, false);
                lr = countK(x-1, y+1, true);
                break;
            }
            case NW: {
                a /= sqrt(2);
                li = countK(x+1, y+1, false);
                lr = countK(x+1, y+1, true);
                break;
            }
            case SE: {
                a /= sqrt(2);
                li = countK(x-1, y-1, false);
                lr = countK(x-1, y-1, true);
                break;
            }
            case SW: {
                a /= sqrt(2);
                li = countK(x+1, y-1, false);
                lr = countK(x+1, y-1, true);
                break;
            }
            case N: {
                li = countK(x, y+1, false);
                lr = countK(x, y+1, true);
                break;
            }
            case S: {
                li = countK(x, y-1, false);
                lr = countK(x, y-1, true);
                break;
            }
            case W: {
                li = countK(x+1, y, false);
                lr = countK(x+1, y, true);
                break;
            }
            default: {
                throw new IllegalValueError("Unknown direction!");
            }
        }
        _matrix.set(kr, kr, 1);
        _matrix.set(ki, kr, a);
        _matrix.set(li, kr, -a);
        _matrix.set(ki, ki, 1);
        _matrix.set(kr, ki, -a);
        _matrix.set(lr, ki, a);
    }

    private void setZero(int x, int y) {
        setZero(x, y, true);
        setZero(x, y, false);
    }

    private void setZero(int x, int y, boolean ifReal) {
        int k = countK(x, y, ifReal);
        _matrix.set(k, k, 1);
    }

    private int countK(int x, int y, boolean ifReal) {
        if (ifReal) {
            return y*(2*(_drawingSheet.getNOTilesX()*_r + 1)) + 2*x;
        } else {
            return y*(2*(_drawingSheet.getNOTilesX()*_r + 1)) + 2*x + 1;
        }
    }

    private void createValueMatrix(E_DrawValueType valueType) {
        _valueMatrix = new Matrix(_drawingSheet.getNOTilesX()*_r + 1, _drawingSheet.getNOTilesY()*_r + 1);
        int x = 0;
        int y = 0;
        _minValue = 0;
        _maxValue = 0;
        double v;
        for (int i = 0; i < _n; i++) {
            switch (valueType) {
                case RealPartPa: {
                    v = _matrix.get(2*_n, 2*i);
                    break;
                }
                case ImaginaryPartPa: {
                    v = _matrix.get(2*_n, 2*i + 1);
                    break;
                }
                case AbsoluteValuePa: {
                    v = Math.sqrt(Math.pow(_matrix.get(2*_n, 2*i), 2) +
                            Math.pow(_matrix.get(2*_n, 2*i + 1), 2));
                    break;
                }
                default: {
                    throw new IllegalValueError("Unsupported draw value type");
                }
            }
            _valueMatrix.set(x, y, v);
            if (v > _maxValue) {
                _maxValue = v;
            }
            if (v < _minValue) {
                _minValue = v;
            }
            x++;
            if (x == _valueMatrix.getSizeX()) {
                x = 0;
                y++;
            }
        }
        _drawingSheet.setMinMaxValues(_minValue, _maxValue);
        //_valueMatrix.print();
    }

    private void scaleValues() {
        double max = Math.abs(_minValue);
        if (max < Math.abs(_maxValue)) {
            max = Math.abs(_maxValue);
        }
        _valueMatrix.scale(1/max);
    }

    private void setValueColors() {
        _drawingSheet.setValues(_valueMatrix, (float)_d);
        _drawingSheet.switchToValueDraw();
    }

}