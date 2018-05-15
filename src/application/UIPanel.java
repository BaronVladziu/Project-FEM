package application;

import drawing.DrawingPlaneSettings;
import solver.SimulationSettings;
import solver.Solver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UIPanel extends JComponent implements ActionListener {

    private static final int _NAME_LENGTH = 130;
    private static final int _INPUT_LENGTH = 30;
    private static final int _BAR_HEIGHT = 25;
    private static final int _GAP_LENGTH = 10;
    private final Solver _solver;

    private final SettingInputPanel _planeXPanel = new SettingInputPanel("Plane length [m]:", "6");
    private final SettingInputPanel _planeYPanel = new SettingInputPanel("Plane width [m]:", "4");
    private final SettingInputPanel _tileXPanel = new SettingInputPanel("Tile length [m]:", "1");
    private final JButton _generatePlaneButton = new JButton("Generate plane");
    private final SettingInputPanel _cPanel = new SettingInputPanel("Sound speed [m/s]:", "340");
    private final SettingInputPanel _fPanel = new SettingInputPanel("Frequency [Hz]:", "100");
    private final JButton _solveButton = new JButton("Solve");

    UIPanel(Window window, Solver solver) {
        _solver = solver;
        JComponent _comp1 = new JPanel();
        _comp1.setPreferredSize(new Dimension(_NAME_LENGTH + _GAP_LENGTH + _INPUT_LENGTH, 800));
        _comp1.setLayout(new FlowLayout());
        _comp1.add(_planeXPanel);
        _comp1.add(_planeYPanel);
        _comp1.add(_tileXPanel);
        _comp1.add(_generatePlaneButton);
        _generatePlaneButton.addActionListener(window);
        _generatePlaneButton.setPreferredSize(new Dimension(_comp1.getPreferredSize().width, _BAR_HEIGHT));
        _solveButton.addActionListener(this);
        _solveButton.setPreferredSize(new Dimension(_comp1.getPreferredSize().width, _BAR_HEIGHT));
        _comp1.add(_generatePlaneButton);
        _comp1.add(_cPanel);
        _comp1.add(_fPanel);
        _comp1.add(_solveButton);
        _comp1.setVisible(true);
        setPreferredSize(new Dimension( _comp1.getPreferredSize().width + _GAP_LENGTH, 900));
        setLayout(new FlowLayout());
        add(_comp1);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        _solver.solve(getSimulationSettings());
    }

    public DrawingPlaneSettings getPlaneSettings() {
        DrawingPlaneSettings settings = new DrawingPlaneSettings();
        settings.planeX = Float.parseFloat(_planeXPanel.getInput());
        settings.planeY = Float.parseFloat(_planeYPanel.getInput());
        settings.tileSize = Float.parseFloat(_tileXPanel.getInput());
        return settings;
    }

    public SimulationSettings getSimulationSettings() {
        SimulationSettings settings = new SimulationSettings();
        settings._c = Double.parseDouble(_cPanel.getInput());
        settings._f = Double.parseDouble(_fPanel.getInput());
        settings._d = Double.parseDouble(_tileXPanel.getInput());
        return settings;
    }

}