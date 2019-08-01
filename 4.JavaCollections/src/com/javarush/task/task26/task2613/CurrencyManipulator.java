package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.*;

public class CurrencyManipulator {

    private String currencyCode;
    private Map<Integer, Integer> denominations = new TreeMap<>(Comparator.reverseOrder());

    public String getCurrencyCode() {
        return currencyCode;
    }

    public CurrencyManipulator(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public void addAmount(int denomination, int count) {
        if (denominations.containsKey(denomination)) {
            denominations.put(denomination, denominations.get(denomination) + count);
        } else {
            denominations.put(denomination, count);
        }
    }

    public int getTotalAmount() {
        int total = 0;

        for (Map.Entry<Integer, Integer> entry : denominations.entrySet()) {
            total += entry.getKey() * entry.getValue();
        }

        return total;
    }

    public boolean hasMoney() {
        return !denominations.isEmpty();
    }

    public boolean isAmountAvailable(int expectedAmount) {
        return getTotalAmount() >= expectedAmount;
    }

    //https://gist.github.com/Ahdiriony/759fcf29b466b7ef9e475926b0942f6d#file-task2613_currencymanipulator-java
    public Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException {
        int amountToWithdraw = expectedAmount;
        Map<Integer, Integer> temp = new TreeMap<>(Comparator.reverseOrder());
        temp.putAll(denominations);
        ArrayList<Integer> list = new ArrayList<>();

        for (Map.Entry<Integer, Integer> pair : temp.entrySet())
            list.add(pair.getKey());

        TreeMap<Integer, Integer> result = new TreeMap<>(Comparator.reverseOrder());

        for (Integer aList : list) {
            int key = aList;
            int value = temp.get(key);
            while (true) {
                if (amountToWithdraw < key || value <= 0) {
                    temp.put(key, value);
                    break;
                }
                amountToWithdraw -= key;
                value--;

                if (result.containsKey(key))
                    result.put(key, result.get(key) + 1);
                else
                    result.put(key, 1);
            }
        }

        if (amountToWithdraw > 0)
            throw new NotEnoughMoneyException();

        else {
            denominations.clear();
            denominations.putAll(temp);
            return result;
        }
    }
}
