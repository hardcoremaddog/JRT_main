package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;

import java.util.ResourceBundle;

class InfoCommand implements Command {

    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "info_en");

    @Override
    public void execute() {
        boolean moneyAvailable = false;
        ConsoleHelper.writeMessage(res.getString("before"));
        for (CurrencyManipulator c : CurrencyManipulatorFactory.getAllCurrencyManipulators()) {
            if (c.hasMoney()) {
                if (c.getTotalAmount() > 0) {
                    ConsoleHelper.writeMessage(c.getCurrencyCode() + " - " + c.getTotalAmount());
                    moneyAvailable = true;
                }
            }
        }
        if (!moneyAvailable) {
            ConsoleHelper.writeMessage(res.getString("no.money"));
        }
    }
}
