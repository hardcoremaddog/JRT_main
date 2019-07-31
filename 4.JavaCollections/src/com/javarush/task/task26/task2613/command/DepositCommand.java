package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;

class DepositCommand implements Command {
    @Override
    public void execute() {
        String currency = ConsoleHelper.askCurrencyCode();
        String[] twoValidDigits = ConsoleHelper.getValidTwoDigits(currency);

        CurrencyManipulator manipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currency);

        int denomination = Integer.parseInt(twoValidDigits[0]);
        int count = Integer.parseInt(twoValidDigits[1]);

        manipulator.addAmount(denomination, count);
    }
}
