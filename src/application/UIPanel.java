package application;

import drawing.DrawingPlaneSettings;
import solver.Solver;

import javax.swing.*;
import java.awt.*;

class UIPanel extends JComponent {

    private static final int _NAME_LENGTH = 130;
    private static final int _INPUT_LENGTH = 30;
    private static final int _BAR_HEIGHT = 25;
    private static final int _GAP_LENGTH = 10;

    private final SettingInputPanel _planeXPanel = new SettingInputPanel("Plane length [m]:", "6");
    private final SettingInputPanel _planeYPanel = new SettingInputPanel("Plane width [m]:", "4");
    private final SettingInputPanel _tileXPanel = new SettingInputPanel("Tile length [m]:", "1");
    private final JButton _generatePlaneButton = new JButton("Generate plane");
    private final JButton _solveButton = new JButton("Solve");

    UIPanel(Window window, Solver solver) {
        JComponent _comp1 = new JPanel();
        _comp1.setPreferredSize(new Dimension(_NAME_LENGTH + _GAP_LENGTH + _INPUT_LENGTH,
                3 * (_BAR_HEIGHT + _GAP_LENGTH)));
        _comp1.setLayout(new GridLayout(3, 1));
        _comp1.add(_planeXPanel);
        _comp1.add(_planeYPanel);
        _comp1.add(_tileXPanel);
        _comp1.add(_generatePlaneButton);
        _comp1.setVisible(true);
        _generatePlaneButton.addActionListener(window);
        _generatePlaneButton.setPreferredSize(new Dimension(_comp1.getPreferredSize().width,
                (900 - _comp1.getPreferredSize().height - 2*_GAP_LENGTH)/4));
        _solveButton.addActionListener(solver);
        _solveButton.setPreferredSize(new Dimension(_comp1.getPreferredSize().width,
                (900 - _comp1.getPreferredSize().height - 2*_GAP_LENGTH)/4));
        JComponent _comp3 = new JPanel();
        _comp3.setPreferredSize(new Dimension(_comp1.getPreferredSize().width,
                900));
        _comp3.setLayout(new FlowLayout());
        _comp3.add(_comp1);
        _comp3.add(_generatePlaneButton);
        _comp3.add(_solveButton);
        _comp3.setVisible(true);

        setPreferredSize(new Dimension( _comp3.getPreferredSize().width + _GAP_LENGTH,
                900));
        setLayout(new FlowLayout());
        add(_comp3);
        setVisible(true);
    }

    public DrawingPlaneSettings getPlaneSettings() {
        DrawingPlaneSettings settings = new DrawingPlaneSettings();
        settings.planeX = Float.parseFloat(_planeXPanel.getInput());
        settings.planeY = Float.parseFloat(_planeYPanel.getInput());
        settings.tileSize = Float.parseFloat(_tileXPanel.getInput());
        return settings;
    }

}