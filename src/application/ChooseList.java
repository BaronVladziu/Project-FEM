package application;

import drawing.E_DrawValueType;
import drawing.IllegalValueError;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ChooseList extends Choice implements ItemListener {

    private E_DrawValueType _drawValueType = E_DrawValueType.None;

    public ChooseList() {
        this.addItemListener(this);
    }

    @Override
    public void itemStateChanged(ItemEvent event) {
        if (event.getItem() == "Real part [Pa]") {
            _drawValueType = E_DrawValueType.RealPartPa;
        } else if (event.getItem() == "Imaginary part [Pa]") {
            _drawValueType = E_DrawValueType.ImaginaryPartPa;
        } else if (event.getItem() == "Absolute value [Pa]") {
            _drawValueType = E_DrawValueType.AbsoluteValuePa;
        } else {
            throw new IllegalValueError("Unknown draw value type!");
        }
    }

    void set(E_DrawValueType drawValueType) {
        _drawValueType = drawValueType;
    }

    E_DrawValueType getDrawValueType() {
        return _drawValueType;
    }

}