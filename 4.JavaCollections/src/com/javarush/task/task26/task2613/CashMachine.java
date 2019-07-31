package com.javarush.task.task26.task2613;

import java.util.Locale;

public class CashMachine {

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);

//        String currency = ConsoleHelper.askCurrencyCode();
//        String[] twoValidDigits = ConsoleHelper.getValidTwoDigits(currency);
//
//        CurrencyManipulator manipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currency);
//
//        int denomination = Integer.parseInt(twoValidDigits[0]);
//        int count = Integer.parseInt(twoValidDigits[1]);
//
//        manipulator.addAmount(denomination, count);
//
//        System.out.println(manipulator.getTotalAmount());

        System.out.println(ConsoleHelper.askOperation());
    }
}
