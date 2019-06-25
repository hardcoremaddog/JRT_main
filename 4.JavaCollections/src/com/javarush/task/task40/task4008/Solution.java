package com.javarush.task.task40.task4008;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;
import java.util.Locale;

/* 
Работа с Java 8 DateTime API
*/

public class Solution {
	public static void main(String[] args) {
		printDate("9.10.2017 5:56:45");
		System.out.println();
		printDate("21.12.2014");
		System.out.println();
		printDate("17:33:40");
	}

	public static void printDate(String date) {

		String[] splt = date.split(" ");
		String sdate = null;
		String stime = null;

		if (splt.length == 1) {
			sdate = splt[0].contains(".") ? splt[0] : null;
			stime = splt[0].contains(":") ? splt[0] : null;
		}
		if (splt.length == 2) {
			sdate = splt[0].contains(".") ? splt[0] : null;
			stime = splt[1].contains(":") ? splt[1] : null;
		}


		DateTimeFormatter formatter;
		if (sdate != null) {
			formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
			LocalDate dateTime = LocalDate.parse(sdate, formatter);

			WeekFields weekFields = WeekFields.of(Locale.getDefault());

			int weekOfTheMonthNumber = dateTime.get(weekFields.weekOfMonth());
			int weekOfTheYearNumber = dateTime.get(weekFields.weekOfYear());

			System.out.println("День: " + dateTime.getDayOfMonth());
			System.out.println("День недели: " + dateTime.getDayOfWeek().getValue());
			System.out.println("День месяца: " + dateTime.getDayOfMonth());
			System.out.println("День года: " + dateTime.getDayOfYear());
			System.out.println("Неделя месяца: " + weekOfTheMonthNumber);
			System.out.println("Неделя года: " + weekOfTheYearNumber);
			System.out.println("Месяц: " + dateTime.getMonthValue());
			System.out.println("Год: " + dateTime.getYear());
		}

		if (stime != null) {
			formatter = DateTimeFormatter.ofPattern("H:m:s");
			LocalTime dateTime = LocalTime.parse(stime, formatter);
			System.out.println("AM или PM: " + (dateTime.get(ChronoField.AMPM_OF_DAY) == 0 ? "AM" : "PM"));
			System.out.println("Часы: " + dateTime.get(ChronoField.CLOCK_HOUR_OF_AMPM));
			System.out.println("Часы дня: " + dateTime.getHour());
			System.out.println("Минуты: " + dateTime.getMinute());
			System.out.println("Секунды: " + dateTime.getSecond());
		}
	}
}