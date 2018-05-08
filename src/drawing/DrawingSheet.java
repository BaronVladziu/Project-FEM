package drawing;

import javax.swing.*;
import java.awt.*;

public class DrawingSheet extends JPanel {

    private final static Color OUTLINE_COLOR = new Color(0,0,0);

    private int _tileSize;
    private int _NOTilesx;
    private int _NOTilesy;
    private Tile[][] _tiles;

    public DrawingSheet() {}

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2d = (Graphics2D) graphics;
        for (int j = 0; j < _NOTilesy; j++) {
            for (int i = 0; i < _NOTilesx; i++) {
                g2d.setColor(_tiles[i][j].getColor());
                g2d.fill(_tiles[i][j]);
                g2d.setColor(OUTLINE_COLOR);
                g2d.draw(_tiles[i][j]);
            }
        }

    }

    public void generatePlane(int planeX, int planeY, int tileSize) {
        _tileSize = tileSize;
        _NOTilesx = planeX/_tileSize;
        _NOTilesy = planeY/_tileSize;
        _tiles = new Tile[_NOTilesx][_NOTilesy];
        for (int j = 0; j < _NOTilesy; j++) {
            for (int i = 0; i < _NOTilesx; i++) {
                _tiles[i][j] = new Tile(i*_tileSize, j*_tileSize, _tileSize);
            }
        }
    }

}