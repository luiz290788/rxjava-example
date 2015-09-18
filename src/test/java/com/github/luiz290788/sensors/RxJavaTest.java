package com.github.luiz290788.sensors;

import org.junit.Test;

import rx.Observable;

public class RxJavaTest {

	@Test
	public void testName() throws Exception {
		Observable<String> observable = Observable
				.<String> create(subscriber -> {
					System.out.println("ok 1");
					subscriber.onNext("ok");
					subscriber.onCompleted();
				}).cache();
		observable.subscribe(str -> {
			System.out.println(str);
		});
		observable.subscribe(str -> {
			System.out.println("new str = " + str);
		});
	}
}
