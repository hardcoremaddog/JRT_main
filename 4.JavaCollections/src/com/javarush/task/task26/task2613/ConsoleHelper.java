package com.javarush.task.task26.task2613;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHelper {

    private static final String INVALID_DATA_MESSAGE = "Invalid data! Please, try again...";
    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() {
        try {
            return bis.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    public static String askCurrencyCode() {
        while (true) {
            writeMessage("Enter a currency code: ");
            String code = readString();

            if (code == null || code.length() != 3) {
                writeMessage(INVALID_DATA_MESSAGE);
            } else {
                return code.toUpperCase();
            }
        }
    }

    public static String[] getValidTwoDigits(String currencyCode) {
        while (true) {
            writeMessage("Enter a two positive numbers: ");
            String readLine = readString();

            if (readLine != null) {
                String[] twoDigits = readLine.split(" ");

                if (twoDigits.length != 2
                        || !readLine.replaceAll(" ", "").matches("\\d+")
                        || (Integer.parseInt(twoDigits[0]) <= 0 || Integer.parseInt(twoDigits[1]) <= 0)) {
                    writeMessage(INVALID_DATA_MESSAGE);
                    continue;
                }
                return twoDigits;
            }
        }
    }
}