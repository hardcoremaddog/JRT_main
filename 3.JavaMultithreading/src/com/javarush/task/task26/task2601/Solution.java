package com.javarush.task.task26.task2601;

import java.util.Arrays;
import java.util.Comparator;

/*
Почитать в инете про медиану выборки
*/
public class Solution {

    public static void main(String[] args) {

    }

    public static Integer[] sort(Integer[] array) {

        Arrays.sort(array);
        int len = array.length;
        int mediana = len % 2 != 0 ? array[len / 2] : ((array[len / 2] + array[len / 2 - 1]) / 2);


        Arrays.sort(array, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Math.abs(o1 - mediana) - Math.abs(o2 - mediana);
            }
        });

        return array;
    }
}
