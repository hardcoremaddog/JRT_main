package com.javarush.task.task22.task2208;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/* 
Формируем WHERE
*/

//        Сформируй часть запроса WHERE используя StringBuilder.
//        Если значение null, то параметр не должен попадать в запрос.
//
//        Пример:
//        {name=Ivanov, country=Ukraine, city=Kiev, age=null}
//
//        Результат:
//        name = 'Ivanov' and country = 'Ukraine' and city = 'Kiev'
//
//
//        Требования:
//        1. Метод getQuery должен принимать один параметр типа Map.
//        2. Метод getQuery должен иметь тип возвращаемого значения String.
//        3. Метод getQuery должен быть статическим.
//        4. Метод getQuery должен возвращать строку сформированную по правилам описанным в условии задачи.

public class Solution {
    public static void main(String[] args) {
        try {
            Map<String, String> map = new HashMap<>();
//        map.put("name", "Ivanov");
//        map.put("country", "Ukraine");
//        map.put("city", "Kiev");
//        map.put("age", null);
            map.put(null, null);
            System.out.println(getQuery(map));
        } catch (Exception e) {
            System.out.println("");
        }
    }

    public static String getQuery(Map<String, String> params) {
        if (params.keySet().size() == 0 && params.values().size() == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    sb.append(entry.getKey() + " = '" + entry.getValue() + "' and ");
                }
            }

        sb.delete(sb.lastIndexOf("and"), sb.length());
        return sb.toString().trim();
    }
}
