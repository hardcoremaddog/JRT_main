package com.javarush.task.task35.task3512;

public class Generator<T> {

    Class<T> t;

    public Generator(Class<T> t) {
        this.t = t;
    }

    T newInstance() throws IllegalAccessException, InstantiationException {
        return t.newInstance();
    }
}
