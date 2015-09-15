package com.github.luiz290788.model;

import java.awt.Color;

public enum Temperature {

    VERY_HOT(new Color(255, 0, 0)),
    HOT(new Color(255, 153, 51)),
    GOOD(new Color(255, 255, 153)),
    COLD(new Color(153, 255, 255)),
    VERY_COLD(new Color(0, 128, 255));

    private Color color;

    public Color getColor() {
        return color;
    }

    private Temperature(Color color) {
        this.color = color;
    }

}
