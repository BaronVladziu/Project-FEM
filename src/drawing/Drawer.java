package drawing;

import application.UIPanel;
import solver.Solver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Drawer implements ActionListener {

    private UIPanel _ui;
    private Solver _solver;

    public Drawer(UIPanel ui, Solver solver) {
        _ui = ui;
        _solver = solver;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        _solver.updateValueMatrix(_ui.getDrawValueType());
    }

}