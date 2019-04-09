package com.javarush.task.task34.task3408;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.WeakHashMap;

public class Cache<K, V> {
    private Map<K, V> cache = new WeakHashMap<>();

    public V getByKey(K key, Class<V> clazz) throws Exception {

        if (!cache.containsKey(key)) {
            Constructor constructor = clazz.getConstructor(key.getClass());
            Object object = constructor.newInstance(key);
            cache.put(key, (V) object);
            return (V) object;
        }
        return cache.get(key);
    }

    public boolean put(V obj) {
        try {
            Method method = obj.getClass().getDeclaredMethod("getKey");
            method.setAccessible(true);
            Object key = method.invoke(obj);
            cache.put((K) key, obj);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public int size() {
        return cache.size();
    }
}