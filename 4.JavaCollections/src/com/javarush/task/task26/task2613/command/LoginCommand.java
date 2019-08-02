package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.ResourceBundle;

public class LoginCommand implements Command {

    private ResourceBundle validCreditCards = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + ".verifiedCards");

    @Override
    public void execute() throws InterruptOperationException {
        while (true) {
            try {
                ConsoleHelper.writeMessage("Enter a valid card number: ");
                String cardNumber = ConsoleHelper.readString();

                ConsoleHelper.writeMessage("Enter pin: ");
                String cardPin = ConsoleHelper.readString();

                if (cardNumber != null && cardPin != null) {
                    if (cardNumber.length() != 11 && cardPin.length() != 3) {
                        if (validCreditCards.containsKey(cardNumber)) {
                            if (validCreditCards.getObject(cardNumber).equals(cardPin)) {
                                ConsoleHelper.writeMessage("Validation successful!");
                                break;
                            }
                        }
                    }
                }
                ConsoleHelper.writeMessage(ConsoleHelper.INVALID_DATA_MESSAGE);
            } catch (NumberFormatException e) {
                ConsoleHelper.writeMessage(ConsoleHelper.INVALID_DATA_MESSAGE);
            }
        }
    }
}
