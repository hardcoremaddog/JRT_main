package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;
import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.Map;
import java.util.ResourceBundle;

class WithdrawCommand implements Command {

//    @Override
//    public void execute() throws InterruptOperationException {
//        String currency = ConsoleHelper.askCurrencyCode();
//        CurrencyManipulator manipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currency);
//
//        while (true) {
//            int amount = Integer.parseInt(ConsoleHelper.askAmount());
//            if (manipulator.isAmountAvailable(amount)) {
//                try {
//                    manipulator.withdrawAmount(amount);
//                    for (Map.Entry<Integer, Integer> entry : manipulator.withdrawAmount(amount).entrySet()) {
//                        ConsoleHelper.writeMessage("\t" + entry.getKey() + " - " + entry.getValue());
//                    }
//                } catch (NotEnoughMoneyException e) {
//                    ConsoleHelper.writeMessage("Not enough money in ATM!");
//                    continue;
//                }
//                ConsoleHelper.writeMessage("Transaction was successful!");
//                break;
//            }
//        }
//    }
public void execute() throws InterruptOperationException
{

    String currencyCode = ConsoleHelper.askCurrencyCode();
    CurrencyManipulator currencyManipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode);
    int sum;
    while(true)
    {

        String s = ConsoleHelper.readString();
        try
        {
            sum = Integer.parseInt(s);
        } catch (NumberFormatException e)
        {
            continue;
        }
        if (sum <= 0)
        {

            continue;
        }
        if (!currencyManipulator.isAmountAvailable(sum))
        {

            continue;
        }
        try
        {
            currencyManipulator.withdrawAmount(sum);
        } catch (NotEnoughMoneyException e)
        {

            continue;
        }

        break;
    }

}
}
