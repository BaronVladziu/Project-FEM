package application;

import drawing.Drawer;
import drawing.DrawingPlaneSettings;
import drawing.E_DrawValueType;
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
    private Solver _solver;
    private Drawer _drawer;

    private final SettingInputPanel _planeXPanel = new SettingInputPanel("Plane length [m]:", "4");
    private final SettingInputPanel _planeYPanel = new SettingInputPanel("Plane width [m]:", "5");
    private final SettingInputPanel _tileXPanel = new SettingInputPanel("Tile length [m]:", "0.5");
    private final JButton _generatePlaneButton = new JButton("Generate plane");
    private final SettingInputPanel _cPanel = new SettingInputPanel("Speed of sound [m/s]:", "340");
    private final SettingInputPanel _gPanel = new SettingInputPanel("Density of air [kg/m3]:", "1.2");
    private final SettingInputPanel _fPanel = new SettingInputPanel("Frequency [Hz]:", "100");
    private final JButton _solveButton = new JButton("Solve");
    private final ChooseList _chooseList = new ChooseList();
    private final JButton _showButton = new JButton("Show result");
    private final ConsolePanel _console = new ConsolePanel(_NAME_LENGTH + _GAP_LENGTH + _INPUT_LENGTH, 800);

    UIPanel(Window window) {
        _chooseList.add("Real part");
        _chooseList.set(E_DrawValueType.RealPart);
        _chooseList.add("Imaginary part");
        _chooseList.add("Absolute value");
        JComponent _comp1 = new JPanel();
        _comp1.setPreferredSize(new Dimension(_NAME_LENGTH + _GAP_LENGTH + _INPUT_LENGTH, 800));
        _comp1.setLayout(new FlowLayout());
        _comp1.add(_planeXPanel);
        _comp1.add(_planeYPanel);
        _comp1.add(_tileXPanel);
        _comp1.add(_generatePlaneButton);
        _generatePlaneButton.addActionListener(window);
        _generatePlaneButton.setPreferredSize(new Dimension(_comp1.getPreferredSize().width, _BAR_HEIGHT));
        _comp1.add(_generatePlaneButton);
        _comp1.add(_cPanel);
        _comp1.add(_gPanel);
        _comp1.add(_fPanel);
        _solveButton.addActionListener(this);
        _solveButton.setPreferredSize(new Dimension(_comp1.getPreferredSize().width, _BAR_HEIGHT));
        _comp1.add(_solveButton);
        _chooseList.setPreferredSize(new Dimension(_comp1.getPreferredSize().width, _BAR_HEIGHT));
        _comp1.add(_chooseList);
        _showButton.setPreferredSize(new Dimension(_comp1.getPreferredSize().width, _BAR_HEIGHT));
        _comp1.add(_showButton);
        _comp1.setVisible(true);
        setPreferredSize(new Dimension( _comp1.getPreferredSize().width + _GAP_LENGTH + _console.getPreferredSize().width, 900));
        setLayout(new FlowLayout());
        add(_comp1);
        add(_console);
        setVisible(true);
        _console.display("Generate new plane to draw on.");
    }

    void setSolver(Solver solver) {
        _solver = solver;
        _drawer = new Drawer(this, _solver);
        _showButton.addActionListener(_drawer);
    }

    public ConsolePanel getConsole() {
        return _console;
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
        return new SimulationSettings(Double.parseDouble(_cPanel.getInput()),
                Double.parseDouble(_gPanel.getInput()),
                Double.parseDouble(_fPanel.getInput()),
                Double.parseDouble(_tileXPanel.getInput()),
                _chooseList.getDrawValueType());
    }

    public E_DrawValueType getDrawValueType() {
        return _chooseList.getDrawValueType();
    }

}