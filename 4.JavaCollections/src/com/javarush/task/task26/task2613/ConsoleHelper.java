package com.javarush.task.task26.task2613;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHelper {

    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() {
        try {
            return bis.readLine();
        } catch (IOException e) {
        }
        return null;
    }

    public static String askCurrencyCode() {
        try {
            System.out.println("Введите три символа кода валюты: ");
            String code = bis.readLine();

            while (code.length() != 3) {
                System.out.println("Введены некорректные данные. Повторите ввод: ");
                code = bis.readLine();
            }

            return code.toUpperCase();
        } catch (IOException e) {
            return null;
        }
    }

    public static String[] getValidTwoDigits(String currencyCode) {
        String invalidDataMessage = "Введены некорректные данные. Повторите ввод: ";
        try {
            System.out.println("Введите целых положительных числа: ");

            String readLine = bis.readLine();
            String[] twoDigits = readLine.split(" ");

            while (twoDigits.length != 2) {
                System.out.println(invalidDataMessage);
                readLine = bis.readLine();
                twoDigits = readLine.split(" ");
            }

            int a = Integer.parseInt(twoDigits[0]);
            int b = Integer.parseInt(twoDigits[1]);

            while (a <= 0 || b <= 0) {
                System.out.println(invalidDataMessage);
                readLine = bis.readLine();
                twoDigits = readLine.split(" ");
                a = Integer.parseInt(twoDigits[0]);
                b = Integer.parseInt(twoDigits[1]);
            }

            return twoDigits;
        } catch (IOException | NumberFormatException e) {
            System.out.println(invalidDataMessage);
            getValidTwoDigits(currencyCode);
        }
        return null;
    }
}