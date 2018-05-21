package solver;

import drawing.DrawingSheet;
import drawing.E_TileType;
import drawing.TileTypeError;

public class Solver {

    private final DrawingSheet _drawingSheet;

    private Matrix _realMatrix;
    private Matrix _imaginaryMatrix;
    private int _n;
    private double _c;
    private double _w;
    private double _k;
    private double _mapD;
    private int _r;
    private double _d;
    private double _alpha;

    public Solver(DrawingSheet drawingSheet) {
        _drawingSheet = drawingSheet;
    }

    public void solve(SimulationSettings settings) {
        updateConstants(settings);
        createMatrices();
        try {
            _realMatrix.solve();
            _imaginaryMatrix.solve();
        } catch (MatrixException e) {
            System.out.println(e.getMessage());
        }
        scaleValues();
        setValueColors();
    }

    private void  updateConstants(SimulationSettings settings) {
        _c = settings._c;
        _w = settings._f * 2 * Math.PI;
        _k = _w/_c;
        _mapD = settings._d;
        double d = _c/(6*settings._f);
        _r = (int)(_mapD / d) + 1;
        _d = _mapD / _r;
        _alpha = _d*_d*_k*_k - 4;
    }

    private void createMatrices() {
        _n = (_drawingSheet.getNOTilesX()*_r + 1) * (_drawingSheet.getNOTilesY()*_r + 1);
        _realMatrix = new Matrix(_n + 1, _n);
        _imaginaryMatrix = new Matrix(_n + 1, _n);
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
        int k = countK(x, y);
        _realMatrix.set(k, k, 1);
        _realMatrix.set(_realMatrix.getSizeX() - 1, k, 1);
        _imaginaryMatrix.set(k, k, 1);
    }

    private void setFunction(int x, int y) {
        int k = countK(x, y);
        _realMatrix.set(k, k, _alpha);
        _imaginaryMatrix.set(k, k, _alpha);
        int l = countK(x-1, y);
        _realMatrix.set(l, k, 1);
        _imaginaryMatrix.set(l, k, 1);
        l = countK(x+1, y);
        _realMatrix.set(l, k, 1);
        _imaginaryMatrix.set(l, k, 1);
        l = countK(x, y-1);
        _realMatrix.set(l, k, 1);
        _imaginaryMatrix.set(l, k, 1);
        l = countK(x, y+1);
        _realMatrix.set(l, k, 1);
        _imaginaryMatrix.set(l, k, 1);
    }

    private void setWall(int x, int y, E_Direction8 dir) {
        int k = countK(x, y);
        switch (dir) {
            case E: {
                _realMatrix.set(k, k, 1/_d);
                _imaginaryMatrix.set(k, k, 1/_d);
                int l = countK(x-1, y);
                _realMatrix.set(l, k, -1/_d);
                _imaginaryMatrix.set(l, k, -1/_d);
                break;
            }
            case NE: {
                _realMatrix.set(k, k, 1/_d);
                _imaginaryMatrix.set(k, k, 1/_d);
                int l = countK(x-1, y+1);
                _realMatrix.set(l, k, -1/(Math.sqrt(2)*_d));
                _imaginaryMatrix.set(l, k, -1/(Math.sqrt(2)*_d));
                break;
            }
            case NW: {
                _realMatrix.set(k, k, -1/_d);
                _imaginaryMatrix.set(k, k, -1/_d);
                int l = countK(x+1, y+1);
                _realMatrix.set(l, k, 1/(Math.sqrt(2)*_d));
                _imaginaryMatrix.set(l, k, 1/(Math.sqrt(2)*_d));
                break;
            }
            case SE: {
                _realMatrix.set(k, k, 1/_d);
                _imaginaryMatrix.set(k, k, 1/_d);
                int l = countK(x-1, y-1);
                _realMatrix.set(l, k, -1/(Math.sqrt(2)*_d));
                _imaginaryMatrix.set(l, k, -1/(Math.sqrt(2)*_d));
                break;
            }
            case SW: {
                _realMatrix.set(k, k, -1/_d);
                _imaginaryMatrix.set(k, k, -1/_d);
                int l = countK(x+1, y-1);
                _realMatrix.set(l, k, 1/(Math.sqrt(2)*_d));
                _imaginaryMatrix.set(l, k, 1/(Math.sqrt(2)*_d));
                break;
            }
            case N: {
                _realMatrix.set(k, k, -1/_d);
                _imaginaryMatrix.set(k, k, -1/_d);
                int l = countK(x, y+1);
                _realMatrix.set(l, k, 1/_d);
                _imaginaryMatrix.set(l, k, 1/_d);
                break;
            }
            case S: {
                _realMatrix.set(k, k, 1/_d);
                _imaginaryMatrix.set(k, k, 1/_d);
                int l = countK(x, y-1);
                _realMatrix.set(l, k, -1/_d);
                _imaginaryMatrix.set(l, k, -1/_d);
                break;
            }
            case W: {
                _realMatrix.set(k, k, -1/_d);
                _imaginaryMatrix.set(k, k, -1/_d);
                int l = countK(x+1, y);
                _realMatrix.set(l, k, 1/_d);
                _imaginaryMatrix.set(l, k, 1/_d);
                break;
            }
        }
    }

    private void setBorder(int x, int y, E_Direction8 dir) {
        setZero(x, y); //TODO
    }

    private void setZero(int x, int y) {
        int k = countK(x, y);
        _realMatrix.set(k, k, 1);
        _imaginaryMatrix.set(k, k, 1);
    }

    private int countK(int x, int y) {
        return y*(_drawingSheet.getNOTilesX()*_r + 1) + x;
    }

    private void scaleValues() {
        double max = _realMatrix.getMaxAbsValue();
        _realMatrix.scaleY(_n, 1/max);
    }

    private void setValueColors() {
        _drawingSheet.setValues(_realMatrix, (float)_d); //TODO: different matrix
        _drawingSheet.switchToValueDraw();
    }

}