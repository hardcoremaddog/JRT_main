package com.javarush.task.task26.task2613;

import java.util.Map;
import java.util.TreeMap;

public class CurrencyManipulatorFactory {

    private CurrencyManipulatorFactory() {

    }

    private static Map<String, CurrencyManipulator> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);;

    public static CurrencyManipulator getManipulatorByCurrencyCode(String currencyCode) {
        if (!map.containsKey( currencyCode))
            map.put(currencyCode, new CurrencyManipulator(currencyCode));

        return map.get(currencyCode.toUpperCase());
    }


}
