package com.github.luiz290788;

import com.github.luiz290788.sensors.FireAlarmSensors;
import com.github.luiz290788.ui.FireAlarmUI;
import com.github.luiz290788.ui.Window;

public class FireAlarm {

    public static void main(String[] args) {
        FireAlarmUI fireAlarmUI = new FireAlarmUI();

        Window window = new Window("Fire alarm", fireAlarmUI);
        window.setVisible(true);

        FireAlarmSensors.getSensors().subscribe(houseSensor -> {
            fireAlarmUI.addHouse(houseSensor.getLabel(), houseSensor.getX(), houseSensor.getY());

            houseSensor.getSensor().subscribe(onFire -> {
                fireAlarmUI.houseOnFire(houseSensor.getLabel(), onFire);
            }, e -> {
                fireAlarmUI.removeHouse(houseSensor.getLabel());
            });

            houseSensor.getSensor().filter(onFire -> onFire).subscribe(onFire -> {
                System.out.println("Sending SMS to firemen!");
            }, e -> {
                System.out.println("Sending SMS to technician!");
            });
        }, e -> {
            fireAlarmUI.removeAllHouses();
        });
    }
}
