package com.javarush.task.task20.task2019;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/* 
Исправить ошибку. Сериализация
*/
public class Solution implements Serializable {

    private static Map<String, String> m = new HashMap<>();

    static {
        m.put("Mickey", "Mouse");
        m.put("Mickey", "Mantle");
    }

    public static void main(String args[]) throws Exception {
        FileOutputStream fileOutput = new FileOutputStream("temp.bin");
        ObjectOutputStream outputStream = new ObjectOutputStream(fileOutput);

        Solution solution = new Solution();
        outputStream.writeObject(solution);

        fileOutput.close();
        outputStream.close();

        //loading
        FileInputStream fiStream = new FileInputStream("temp.bin");
        ObjectInputStream objectStream = new ObjectInputStream(fiStream);

        Solution loadedObject = (Solution) objectStream.readObject();

        fiStream.close();
        objectStream.close();

        //Attention!!
        System.out.println(loadedObject.size());
    }

    public Map<String, String> getMap() {
        return m;
    }

    public int size() {
        return m.size();
    }
}
