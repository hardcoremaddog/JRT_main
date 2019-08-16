package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ResourceBundle;

public class ConsoleHelper {

    public static final String INVALID_DATA_MESSAGE = "Invalid data! Please, try again...";
    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));
    private static ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "common_en");

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws InterruptOperationException {
        try {
            String readLine = bis.readLine();
            if (readLine.equalsIgnoreCase("EXIT")) {
                throw new InterruptOperationException();
            } else {
                return readLine;
            }
        } catch (IOException e) {
            return null;
        }
    }

    public static String askCurrencyCode() throws InterruptOperationException {
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

    public static String askAmount() throws InterruptOperationException {
        while (true) {
            writeMessage("Enter the amount to withdraw: ");
            String readLine = readString();

            if (readLine == null
                    || !readLine.replaceAll(" ", "").matches("\\d+")) {
                writeMessage(INVALID_DATA_MESSAGE);
            } else {
                return readLine;
            }
        }
    }


    public static Operation askOperation() throws InterruptOperationException {
        while (true) {
            writeMessage("Enter an operation number \n"
                    + "1 - INFO \n"
                    + "2 - DEPOSIT \n"
                    + "3 - WITHDRAW \n"
                    + "4 - EXIT");
            String readLine = readString();

            if (readLine != null) {
                try {
                    int operationId = Integer.parseInt(readLine);
                    return Operation.getAllowableOperationByOrdinal(operationId);
                } catch (IllegalArgumentException e) {
                    writeMessage(INVALID_DATA_MESSAGE);
                }
            }
        }
    }

    public static String[] getValidTwoDigits(String currencyCode) throws InterruptOperationException {
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

    public static void printExitMessage() {
        ConsoleHelper.writeMessage(res.getString("the.end"));
    }
}