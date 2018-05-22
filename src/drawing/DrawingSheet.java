package drawing;

import solver.Matrix;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DrawingSheet extends JPanel implements MouseListener, MouseMotionListener, KeyListener {

    private final static Color OUTLINE_COLOR = new Color(0,0,0);
    private final static Color WALL_COLOR = new Color(50, 50, 50);
    private final static Color AIR_COLOR = new Color(200, 200, 255);
    private final static Color SOURCE_COLOR = new Color(150, 0, 150);

    private float _tileSize;
    private int _NOTilesx;
    private int _NOTilesy;
    private Tile[][] _tiles;
    private ZoomController _zoom = new ZoomController(this);
    private TranslationController _translation = new TranslationController(this);
    private E_TileType _drawnTile = E_TileType.Wall;
    private boolean _ifValueDraw = false;
    private GradientPrinter _gradientPrinter = new GradientPrinter();

    public DrawingSheet() {
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        addMouseWheelListener(_zoom);
        addMouseListener(_translation);
        addMouseMotionListener(_translation);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2d = (Graphics2D) graphics;
        if (_ifValueDraw) {
            _gradientPrinter.print(g2d, _translation.getTransX(), _translation.getTransY(), _zoom.getZoom());
            for (int j = 0; j < _NOTilesy; j++) {
                for (int i = 0; i < _NOTilesx; i++) {
                    if (_tiles[i][j].getType() != E_TileType.Air) {
                        drawSubtile(g2d, i, j);
                    }
                }
            }
        } else {
            for (int j = 0; j < _NOTilesy; j++) {
                for (int i = 0; i < _NOTilesx; i++) {
                    drawSubtile(g2d, i, j);
                }
            }
        }
    }

    private void drawSubtile(Graphics2D g2d, int x, int y) {
        SubTile subtile = _tiles[x][y].getFillSubTile();
        g2d.setColor(subtile.getColor(0));
        g2d.fill(subtile);
        g2d.setColor(OUTLINE_COLOR);
        g2d.draw(subtile);
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        if (SwingUtilities.isLeftMouseButton(event)) {
            try {
                switch (_drawnTile) {
                    case Wall: {
                        _tiles[(event.getX() - _translation.getTransX()) / (int) (_zoom.getZoom() * _tileSize)]
                                [(event.getY() - _translation.getTransY()) / (int) (_zoom.getZoom() * _tileSize)]
                                .setType(E_TileType.Wall, WALL_COLOR);
                        break;
                    }
                    case Air: {
                        _tiles[(event.getX() - _translation.getTransX()) / (int) (_zoom.getZoom() * _tileSize)]
                                [(event.getY() - _translation.getTransY()) / (int) (_zoom.getZoom() * _tileSize)]
                                .setType(E_TileType.Air, AIR_COLOR);
                        break;
                    }
                    case Source: {
                        _tiles[(event.getX() - _translation.getTransX()) / (int) (_zoom.getZoom() * _tileSize)]
                                [(event.getY() - _translation.getTransY()) / (int) (_zoom.getZoom() * _tileSize)]
                                .setType(E_TileType.Source, SOURCE_COLOR);
                        break;
                    }
                    default: {
                        throw new TileTypeError("Unknown tile type!");
                    }
                }
                this.redrawTiles();
            } catch (ArrayIndexOutOfBoundsException e) {
            } catch (ArithmeticException e) {}
        }
    }

    @Override
    public void mouseMoved(MouseEvent event) {}

    @Override
    public void mouseClicked(MouseEvent event) {}

    @Override
    public void mousePressed(MouseEvent event) {
        requestFocusInWindow();
    }

    @Override
    public void mouseReleased(MouseEvent event) {}

    @Override
    public void mouseEntered(MouseEvent event) {}

    @Override
    public void mouseExited(MouseEvent event) {}

    @Override
    public void keyPressed(KeyEvent evt) {}

    @Override
    public void keyReleased(KeyEvent evt) {
        switch (evt.getKeyChar()) {
            case '+': {
                _zoom.zoomIn();
                break;
            }
            case '=': {
                _zoom.zoomIn();
                break;
            }
            case '-': {
                _zoom.zoomOut();
                break;
            }
            case 'w': {
                _drawnTile = E_TileType.Wall;
                break;
            }
            case 'a': {
                _drawnTile = E_TileType.Air;
                break;
            }
            case 's': {
                _drawnTile = E_TileType.Source;
                break;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent evt) {}

    public void generatePlane(float planeX, float planeY, float tileSize) {
        _ifValueDraw = false;
        _tileSize = tileSize;
        _NOTilesx = (int)(planeX/_tileSize);
        _NOTilesy = (int)(planeY/_tileSize);
        _tiles = new Tile[_NOTilesx][_NOTilesy];
        for (int j = 0; j < _NOTilesy; j++) {
            for (int i = 0; i < _NOTilesx; i++) {
                _tiles[i][j] = new Tile(i*_tileSize, j*_tileSize, _tileSize, E_TileType.Air, AIR_COLOR);
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
            return _tiles[x][y].getType();
        }
        return E_TileType.Void;
    }

    public void setValues(Matrix matrix, float subtileSize) {
        _gradientPrinter.initialize(matrix, subtileSize);
    }

    public int getNOTilesX() {
        return _NOTilesx;
    }

    public int getNOTilesY() {
        return _NOTilesy;
    }

    public void switchToValueDraw() {
        _ifValueDraw = true;
        redrawTiles();
    }

}