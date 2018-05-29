package solver;

import drawing.E_DrawValueType;

public class SimulationSettings {

    private double _c;
    private double _g;
    private double _f;
    private double _d;
    private E_DrawValueType _drawValueType;

    public SimulationSettings(double c, double g, double f, double d, E_DrawValueType drawValueType) {
        _c = c;
        _g = g;
        _f = f;
        _d = d;
        _drawValueType = drawValueType;
    }

    public double getC() {
        return _c;
    }

    public double getG() {
        return _g;
    }

    public double getF() {
        return _f;
    }

    public double getD() {
        return _d;
    }

    public E_DrawValueType getDrawValueType() {
        return _drawValueType;
    }

}