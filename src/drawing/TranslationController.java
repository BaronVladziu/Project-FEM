package drawing;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class TranslationController implements MouseMotionListener, MouseListener {

    private DrawingSheet _sheet;
    private int _transX = 0;
    private int _transY = 0;
    private int _dragStartX = 0;
    private int _dragStartY = 0;

    TranslationController(DrawingSheet sheet) {
        _sheet = sheet;
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        if (SwingUtilities.isRightMouseButton(event)) {
            translate(event.getX(), event.getY());
        }
    }

    @Override
    public void mouseMoved(MouseEvent event) {}

    @Override
    public void mouseClicked(MouseEvent event) {}

    @Override
    public void mousePressed(MouseEvent event) {
        if (SwingUtilities.isRightMouseButton(event)) {
            _dragStartX = event.getX();
            _dragStartY = event.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent event) {}

    @Override
    public void mouseEntered(MouseEvent event) {}

    @Override
    public void mouseExited(MouseEvent event) {}

    int getTransX() {
        return _transX;
    }

    int getTransY() {
        return _transY;
    }

    private void translate(int x, int y) {
        _transX += x - _dragStartX;
        _transY += y - _dragStartY;
        _dragStartX = x;
        _dragStartY = y;
        _sheet.redrawTiles();
    }

}