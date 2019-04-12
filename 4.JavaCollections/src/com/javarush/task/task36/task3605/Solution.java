package com.javarush.task.task36.task3605;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.TreeSet;
import java.io.*;

/* 
Использование TreeSet
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        File file = new File(args[0]);
        TreeSet<Character> set = new TreeSet();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

        while (br.ready()) {
            String line = br.readLine().toLowerCase();
            for (Character c : line.toCharArray()) {
                if (c.toString().matches("[A-Za-z]")) {
                    set.add(c);
                }
            }
        }

        int count = 0;
        for (Character c : set) {
            if (count < 5) {
                System.out.print(c);
                count++;
            }
        }
    }
}