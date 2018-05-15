package drawing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class DrawingSheet extends JPanel implements MouseListener, MouseMotionListener {

    private final static Color OUTLINE_COLOR = new Color(0,0,0);
    private final static Color WALL_COLOR = new Color(50, 50, 50);
    private final static Color AIR_COLOR = new Color(200, 200, 255);

    private float _tileSize;
    private int _NOTilesx;
    private int _NOTilesy;
    private Tile[][] _tiles;
    private ZoomController _zoom = new ZoomController(this);
    private TranslationController _translation = new TranslationController(this);
    private boolean _ifDrawingWall = true;

    public DrawingSheet() {
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(_zoom);
        addMouseListener(_translation);
        addMouseMotionListener(_translation);
    }

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

    @Override
    public void mouseDragged(MouseEvent event) {
        if (SwingUtilities.isLeftMouseButton(event)) {
            try {
                if (_ifDrawingWall) {
                    _tiles[(event.getX() - _translation.getTransX()) / (int) (_zoom.getZoom() * _tileSize)]
                            [(event.getY() - _translation.getTransY()) / (int) (_zoom.getZoom() * _tileSize)]
                            .setColor(WALL_COLOR);
                } else {
                    _tiles[(event.getX() - _translation.getTransX()) / (int) (_zoom.getZoom() * _tileSize)]
                            [(event.getY() - _translation.getTransY()) / (int) (_zoom.getZoom() * _tileSize)]
                            .setColor(AIR_COLOR);
                }
                this.redrawTiles();
            } catch (ArrayIndexOutOfBoundsException e) {
            } catch (ArithmeticException e) {}
        }
    }

    @Override
    public void mouseMoved(MouseEvent event) {}

    @Override
    public void mouseClicked(MouseEvent event) {
        System.out.println("Clicked");
    }

    @Override
    public void mousePressed(MouseEvent event) {
        System.out.println("Pressed");
        if (SwingUtilities.isLeftMouseButton(event)) {
            try {
                if (_tiles[(event.getX() - _translation.getTransX()) / (int) (_zoom.getZoom() * _tileSize)]
                        [(event.getY() - _translation.getTransY()) / (int) (_zoom.getZoom() * _tileSize)]
                        .getColor() == WALL_COLOR) {
                    _ifDrawingWall = false;
                } else {
                    _ifDrawingWall = true;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
            } catch (ArithmeticException e) {}
        }
    }

    @Override
    public void mouseReleased(MouseEvent event) {}

    @Override
    public void mouseEntered(MouseEvent event) {}

    @Override
    public void mouseExited(MouseEvent event) {}

    public void generatePlane(float planeX, float planeY, float tileSize) {
        _tileSize = tileSize;
        _NOTilesx = (int)(planeX/_tileSize);
        _NOTilesy = (int)(planeY/_tileSize);
        _tiles = new Tile[_NOTilesx][_NOTilesy];
        for (int j = 0; j < _NOTilesy; j++) {
            for (int i = 0; i < _NOTilesx; i++) {
                _tiles[i][j] = new Tile(i*_tileSize, j*_tileSize, _tileSize, AIR_COLOR);
            }
        }
        redrawTiles();
    }

    void redrawTiles() {
        for (int j = 0; j < _NOTilesy; j++) {
            for (int i = 0; i < _NOTilesx; i++) {
                _tiles[i][j].setTranslation(_translation.getTransX(), _translation.getTransY(), _zoom.getZoom());
            }
        }
        this.repaint();
    }

    public E_TileType getTileType(int x, int y) {
        if (x >= 0 && x < _NOTilesx && y >= 0 && y < _NOTilesy) {
            Color tileColor = _tiles[x][y].getColor();
            if (tileColor == AIR_COLOR) {
                return E_TileType.Air;
            } else if (tileColor == WALL_COLOR) {
                return E_TileType.Wall;
            }
            throw new TileTypeError("Unknown tile type!");
        }
        return E_TileType.Void;
    }

    public int getNOTilesX() {
        return _NOTilesx;
    }

    public int getNOTilesY() {
        return _NOTilesy;
    }

}