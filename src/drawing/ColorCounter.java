package drawing;

import java.awt.*;

public final class ColorCounter {

    static Color countColor(double value) {
        if (value < -1 || value > 1) {
            throw new IllegalValueError("Color value not between -1 and 1!");
        }
        int valueRange = 2;
        value *= valueRange;
        int rest = (int)(255*((value + valueRange) - ((int)(value + valueRange))));
        //System.out.println(Double.toString(value) + " --> " + Double.toString((value + valueRange) - ((int)(value + valueRange))));
        if (value >= 0) {
            if (value >= 1) {
                return new Color(255, 255-rest, 0);
            } else {
                return new Color(rest, 255, 0);
            }
        } else {
            if (value >= -1) {
                return new Color(0, 255, 255-rest);
            } else {
                return new Color(0, rest, 255);
            }
        }
    }

    static float colorFadeFunction(int sizeX, int sizeY, int x, int y) {
        return linFun((float)x / (float)sizeX)*linFun((float)y / (float)sizeY);
    }

    static float linFun(float x) {
        return 1.f - x;
    }

}