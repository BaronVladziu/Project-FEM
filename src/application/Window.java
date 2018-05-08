package application;

import drawing.DrawingPlaneSettings;
import drawing.DrawingSheet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Window extends JFrame implements ActionListener {

    private final DrawingSheet _drawPanel = new DrawingSheet();
    private final UIPanel _uiPanel = new UIPanel(this);

    public Window() {
        super("Project-FEM");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout());
        add(_drawPanel, BorderLayout.CENTER);
        add(_uiPanel, BorderLayout.EAST);
        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        DrawingPlaneSettings settings = _uiPanel.getPlaneSettings();
        _drawPanel.generatePlane(settings.planeX, settings.planeY, settings.tileSize);
        _drawPanel.repaint();
    }

}