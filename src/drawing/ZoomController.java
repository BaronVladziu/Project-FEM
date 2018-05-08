package drawing;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class ZoomController implements MouseWheelListener {

    private static final float _ZOOMING_SPEED = 1.05f;

    private DrawingSheet _sheet;
    private float _zoom = 100;

    ZoomController(DrawingSheet sheet) {
        _sheet = sheet;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent event) {
        if (event.getWheelRotation() == 1) {
            zoomOut();
        } else {
            zoomIn();
        }
        _sheet.redrawTiles();
    }

    float getZoom() {
        return _zoom;
    }

    private void zoomIn() {
        _zoom *= _ZOOMING_SPEED;
    }

    private void zoomOut() {
        _zoom /= _ZOOMING_SPEED;
    }

}
