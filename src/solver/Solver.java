package solver;

import drawing.DrawingSheet;
import drawing.E_TileType;
import drawing.TileTypeError;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Solver implements ActionListener {

    private final DrawingSheet _drawingSheet;

    private Matrix _realMatrix;
    private Matrix _imaginaryMatrix;
    private int _n;

    public Solver(DrawingSheet drawingSheet) {
        _drawingSheet = drawingSheet;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        createMatrices();
        _realMatrix.print();
        _imaginaryMatrix.print();
    }

    private void createMatrices() {
        _n = (_drawingSheet.getNOTilesX() + 1) * (_drawingSheet.getNOTilesY() + 1);
        _realMatrix = new Matrix(_n + 1, _n);
        _imaginaryMatrix = new Matrix(_n + 1, _n);
        E_TileType ne, se, sw, nw;
        int counters[] = new int[3]; //SOURCES, WALLS, VOIDS
        for (int i = 0; i <= _drawingSheet.getNOTilesX(); i++) {
            se = _drawingSheet.getTileType(i, -1);
            sw = _drawingSheet.getTileType(i - 1, -1);
            for (int j = 0; j <= _drawingSheet.getNOTilesY(); j++) {
                ne = se;
                nw = sw;
                se = _drawingSheet.getTileType(i, j);
                sw = _drawingSheet.getTileType(i - 1, j);
                counters[0] = 0;
                counters[1] = 0;
                counters[2] = 0;
                countTileTypes(ne, counters);
                countTileTypes(nw, counters);
                countTileTypes(sw, counters);
                countTileTypes(se, counters);
                System.out.println(Integer.toString(counters[0]) + " \t" + Integer.toString(counters[1]) + " \t" + Integer.toString(counters[2]));
                if (counters[0] != 0) {
                    setSource(i, j);
                } else if (counters[1] + counters[2] == 4) {
                    setZero(i, j);
                } else if (counters[2] != 0) {
                    //TODO: Borders
                } else if (counters[1] != 0) {
                    //TODO: Walls
                } else {
                    setFunction(i, j);
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
        //TODO
    }

    private void setFunction(int x, int y) {
        //TODO
    }

    private void setWall(int x, int y, E_Direction8 dir) {
        //TODO
    }

    private void setBorder(int x, int y, E_Direction8 dir) {
        //TODO
    }

    private void setZero(int x, int y) {
        //TODO
    }

}