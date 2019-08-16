package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.ResourceBundle;

class DepositCommand implements Command {
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "deposit_en");

    @Override
    public void execute() throws InterruptOperationException {
        String currency = ConsoleHelper.askCurrencyCode();
        String[] twoValidDigits = ConsoleHelper.getValidTwoDigits(currency);

        ConsoleHelper.writeMessage(res.getString("before"));
        CurrencyManipulator manipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currency);

        int denomination = Integer.parseInt(twoValidDigits[0]);
        int count = Integer.parseInt(twoValidDigits[1]);

        manipulator.addAmount(denomination, count);
        ConsoleHelper.writeMessage(res.getString("success.format"));
    }
}
