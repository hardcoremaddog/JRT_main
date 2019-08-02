package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

public class LoginCommand implements Command {

    private final String validCardNumber = "123456789012";
    private final String validPin = "1234";

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
                        if (cardNumber.equals(validCardNumber) && cardPin.equals(validPin)) {
                            ConsoleHelper.writeMessage("Validation successful!");
                            break;
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
