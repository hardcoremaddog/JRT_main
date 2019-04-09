package com.javarush.task.task30.task3010;

/* 
Минимальное допустимое основание системы счисления
*/

import java.math.BigInteger;

public class Solution {
    public static void main(String[] args) {

        try {
        if (args[0].replaceAll("\\w", "").matches("\\W")) {
            System.out.println("incorrect");
        }


            for (int i = 2; i <= 36; i++) {
                try {
                    new BigInteger(args[0].toLowerCase(), i);
                    System.out.println(i);
                } catch (Exception e) {

                }
            }
        } catch (Exception e) {

        }
    }
}