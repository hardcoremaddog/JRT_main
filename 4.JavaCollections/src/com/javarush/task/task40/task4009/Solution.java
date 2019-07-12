package com.javarush.task.task40.task4009;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Formatter;
import java.util.Locale;

/* 
Buon Compleanno!
*/

public class Solution {
    public static void main(String[] args) {
        System.out.println(getWeekdayOfBirthday("30.05.1984", "2015"));
    }

    public static String getWeekdayOfBirthday(String birthday, String year) {
        Year.parse(year);//just for validation

        birthday = birthday.substring(0, birthday.lastIndexOf(".")) + "." + year;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");

        LocalDate birthDayDate = LocalDate.parse(birthday, formatter);

        return birthDayDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ITALIAN);
    }
}
