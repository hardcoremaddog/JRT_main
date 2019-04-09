package com.javarush.task.task26.task2611;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer implements Runnable {
    private ConcurrentHashMap<String, String> map;

    public Producer(ConcurrentHashMap<String, String> map) {
        this.map = map;
    }

    @Override
    public void run() {
        AtomicInteger x = new AtomicInteger(1);
        while (true) {
            try {
                String value = "Some text for " + x.get();
                map.put(String.valueOf(x.getAndIncrement()), value);
                Thread.sleep(500);
            } catch (Exception e) {
                System.out.println(Thread.currentThread().getName() + " thread was terminated");
                break;
            }
        }
    }
}
