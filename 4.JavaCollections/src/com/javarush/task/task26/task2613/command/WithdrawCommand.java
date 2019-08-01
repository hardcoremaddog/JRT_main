package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;
import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.Map;

class WithdrawCommand implements Command {
    @Override
    public void execute() throws InterruptOperationException {
        String currency = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator manipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currency);

        while (true) {
            int amount = Integer.parseInt(ConsoleHelper.askAmount());
            if (manipulator.isAmountAvailable(amount)) {
                try {
                    manipulator.withdrawAmount(amount);
                    for (Map.Entry<Integer, Integer> entry : manipulator.withdrawAmount(amount).entrySet()) {
                        ConsoleHelper.writeMessage("\t" + entry.getKey() + " - " + entry.getValue());
                    }
                } catch (NotEnoughMoneyException e) {
                    ConsoleHelper.writeMessage("Not enough money in ATM!");
                    continue;
                }
                ConsoleHelper.writeMessage("Transaction was successful!");
                break;
            }
        }
    }
}
