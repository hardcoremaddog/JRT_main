package com.javarush.task.task35.task3509;

import java.util.*;


/* 
Collections & Generics
*/
public class Solution {

    public static void main(String[] args) {
        HashMap<Object, Number> map = newHashMap(newArrayList("first", "second"), newArrayList(1,2));
    }

    public static <T> ArrayList<T> newArrayList(T... elements) {
        ArrayList<T> arrayList = new ArrayList<>();
        arrayList.addAll(Arrays.asList(elements));
        return arrayList;
    }

    public static <T> HashSet<T> newHashSet(T... elements) {
        HashSet<T> hashSet = new HashSet<>();
        hashSet.addAll(Arrays.asList(elements));
        return hashSet;
    }

    public static <K, V> HashMap<K, V> newHashMap(List<? extends K> keys, List<? extends V> values) {
        HashMap<K, V> hashMap = new HashMap<>();
        if (keys.size() == values.size()) {
            for (int i = 0; i < keys.size(); i++) {
                hashMap.put(keys.get(i), values.get(i));
            }
            return hashMap;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
