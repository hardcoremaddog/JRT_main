package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;

class InfoCommand implements Command {
    @Override
    public void execute() {

        if (CurrencyManipulatorFactory.getAllCurrencyManipulators().isEmpty()) {
            ConsoleHelper.writeMessage("No money available.");
        } else {
            for (CurrencyManipulator manipulator : CurrencyManipulatorFactory.getAllCurrencyManipulators()) {
                if (manipulator.hasMoney()) {
                    ConsoleHelper.writeMessage(manipulator.getCurrencyCode() + " - " + manipulator.getTotalAmount());
                } else {
                    ConsoleHelper.writeMessage("No money available.");
                }
            }
        }

    }
}
