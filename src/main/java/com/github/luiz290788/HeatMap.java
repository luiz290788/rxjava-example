package com.github.luiz290788;

import java.awt.Color;

import com.github.luiz290788.model.Temperature;
import com.github.luiz290788.sensors.TemperatureSensors;
import com.github.luiz290788.ui.HeatMapUI;
import com.github.luiz290788.ui.Window;

public class HeatMap {

    private static Color getColorFromTemp(Double dTemperature) {
        Temperature temperature = null;
        if (dTemperature < 10) {
            temperature = Temperature.VERY_COLD;
        } else if (dTemperature < 20) {
            temperature = Temperature.COLD;
        } else if (dTemperature < 30) {
            temperature = Temperature.GOOD;
        } else if (dTemperature < 40) {
            temperature = Temperature.HOT;
        } else if (dTemperature >= 40) {
            temperature = Temperature.VERY_HOT;
        }
        return temperature.getColor();
    }

    public static void main(String[] args) {
        HeatMapUI heatMapUI = new HeatMapUI(10, 10);

        Window window = new Window("Heat Map", heatMapUI);
        window.setVisible(true);

        TemperatureSensors.getSensors().subscribe(sensor -> {
            sensor.getSensor().map(temp -> getColorFromTemp(temp)).distinctUntilChanged().subscribe(color -> {
                heatMapUI.setColor(new Long(sensor.getId() / 10).intValue(), new Long(sensor.getId() % 10).intValue(), color);
            });
        });
    }
}
