package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.command.CommandExecutor;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;
import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.Locale;

public class CashMachine {

    public static void main(String[] args) {
        try {
            Locale.setDefault(Locale.ENGLISH);

            while (true) {
                CommandExecutor.execute(Operation.LOGIN);

                Operation operation = ConsoleHelper.askOperation();
                CommandExecutor.execute(operation);

                if (operation == Operation.EXIT) {
                    break;
                }
            }
        } catch (InterruptOperationException e) {
            ConsoleHelper.writeMessage("Good bye!");
        } catch (NotEnoughMoneyException e) {
            ConsoleHelper.writeMessage("Not enough money in ATM!");
        }
    }
}
