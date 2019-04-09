package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleHelper {

	public static void writeMessage(String message) {
		System.out.println(message);
	}

	static String readString() throws IOException {
		return new BufferedReader(new InputStreamReader(System.in)).readLine();
	}

	public static List<Dish> getAllDishesForOrder() throws IOException {
		List<Dish> clientOrder = new ArrayList<>();
		String answer;

		writeMessage("Пожалуйста, выберите блюдо:");
		writeMessage(Dish.allDishesToString());
		while ((answer = readString()) != null) {
			if (!(answer.equalsIgnoreCase("exit"))) {
				if (Dish.allDishesToString().contains(answer)) {
					clientOrder.add(Dish.valueOf(answer));
				} else {
					writeMessage("Извините, но такого блюда у нас нет. :(");
				}
			} else {
				break;
			}
		}
		return clientOrder;
	}
}
