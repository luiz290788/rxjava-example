package com.github.luiz290788.sensors;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;

import rx.Observable;

public class TemperatureSensors {

    private static final Random random = new Random();

    public static Observable<Double> genNewTemperatureGenerators() {

        return Observable.interval(500 + random.nextInt(1000), TimeUnit.MILLISECONDS).map(i -> {
            return 0 + random.nextDouble() * 40;
        });
    }

    public static Map<Long, Observable<Double>> createSensors() {
        Map<Long, Observable<Double>> sensors = new HashMap<Long, Observable<Double>>();
        LongStream.range(0, 100).forEach(i -> {
            sensors.put(i, genNewTemperatureGenerators());
        });
        return sensors;
    }
}
