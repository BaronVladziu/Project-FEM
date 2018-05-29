package application;

import drawing.E_DrawValueType;

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
        if (event.getItem() == "Real part") {
            _drawValueType = E_DrawValueType.RealPart;
        } else if (event.getItem() == "Imaginary part") {
            _drawValueType = E_DrawValueType.ImaginaryPart;
        } else {
            _drawValueType = E_DrawValueType.AbsoluteValue;
        }
    }

    void set(E_DrawValueType drawValueType) {
        _drawValueType = drawValueType;
    }

    E_DrawValueType getDrawValueType() {
        return _drawValueType;
    }

}