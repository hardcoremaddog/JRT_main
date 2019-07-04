package com.javarush.task.task39.task3909;

import java.util.Arrays;

/*
Одно изменение
*/
public class Solution {
    public static void main(String[] args) {

    }

    public static boolean isOneEditAway(String first, String second) {
        if (first.equals(second)) {
            return true;
        }

        int result = calculate(first, second);

        return result == 1;
    }

    static int calculate(String first, String second) {
        if (first.isEmpty()) {
            return second.length();
        }

        if (second.isEmpty()) {
            return first.length();
        }

        int substitution = calculate(first.substring(1), second.substring(1))
                + costOfSubstitution(first.charAt(0), second.charAt(0));
        int insertion = calculate(first, second.substring(1)) + 1;
        int deletion = calculate(first.substring(1), second) + 1;

        return min(substitution, insertion, deletion);
    }

    public static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }

    public static int min(int... numbers) {
        return Arrays.stream(numbers)
                .min().orElse(Integer.MAX_VALUE);
    }
}
