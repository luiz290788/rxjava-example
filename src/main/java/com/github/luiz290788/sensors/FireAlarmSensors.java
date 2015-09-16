package com.github.luiz290788.sensors;

import java.util.Random;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import rx.Observable;
import rx.Subscriber;
import rx.observables.ConnectableObservable;
import rx.schedulers.Schedulers;

public class FireAlarmSensors {

    private static final Random random = new Random();

    public static Observable<HouseSensor> getSensors() {
        return Observable.create(subscriber -> {
            try (Jedis jedis = new Jedis("localhost")) {
                jedis.smembers("houses").forEach(label -> createHouseSensor(subscriber, label));

                jedis.subscribe(new JedisPubSub() {
                    @Override
                    public void onMessage(String channel, String message) {
                        createHouseSensor(subscriber, message);
                    }
                }, "new-houses");
            }
        });
    }

    private static void createHouseSensor(Subscriber<? super HouseSensor> subscriber, String label) {
        HouseSensor houseSensor = new HouseSensor();
        houseSensor.label = label;
        houseSensor.x = random.nextInt(360);
        houseSensor.y = random.nextInt(360);
        houseSensor.sensor = getSensorObservable(label);
        subscriber.onNext(houseSensor);
    }

    private static Observable<Boolean> getSensorObservable(String label) {
        ConnectableObservable<Boolean> observable = Observable.<Boolean> create(subscriber -> {
            try (Jedis jedis = new Jedis("localhost")) {
                String channel = "fire-alarm-" + label;
                jedis.subscribe(new JedisPubSub() {
                    @Override
                    public void onMessage(String channel, String message) {
                        if ("fire".equals(message.toLowerCase())) {
                            subscriber.onNext(true);
                        } else if ("ok".equals(message.toLowerCase())) {
                            subscriber.onNext(false);
                        } else if ("error".equals(message.toLowerCase())) {
                            throw new RuntimeException();
                        }
                    }
                }, channel);

            } catch (Exception e) {
                subscriber.onError(e);
            }
            subscriber.onCompleted();
        }).subscribeOn(Schedulers.io()).publish();

        observable.connect();

        return observable;
    }

    public static final class HouseSensor {
        private String label;
        private int x, y;
        private Observable<Boolean> sensor;

        public String getLabel() {
            return label;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Observable<Boolean> getSensor() {
            return sensor;
        }
    }
}
