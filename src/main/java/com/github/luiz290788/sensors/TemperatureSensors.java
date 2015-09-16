package com.github.luiz290788.sensors;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.Observable;

public class TemperatureSensors {

    private static final Random random = new Random();

    public static Observable<Double> genNewTemperatureGenerators() {

        return Observable.interval(500 + random.nextInt(1000), TimeUnit.MILLISECONDS).map(i -> {
            if (i == 0) {
                return 0 + random.nextDouble() * 40;
            } else {
                return random.nextDouble() * 6 - 3;
            }
        }).scan((current, delta) -> Math.min(40, Math.max(0, current + delta)));
    }

    public static Observable<Sensor> getSensors() {
        return Observable.range(0, 100).map(id -> new Long(id)).map(id -> {
            Sensor sensor = new Sensor();
            sensor.id = id;
            sensor.sensor = genNewTemperatureGenerators();
            return sensor;
        });
    }

    public static final class Sensor {
        private Long id;
        private Observable<Double> sensor;

        public Long getId() {
            return id;
        }

        public Observable<Double> getSensor() {
            return sensor;
        }

    }
}
