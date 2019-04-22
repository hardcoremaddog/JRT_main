package com.javarush.task.task33.task3310;

import com.javarush.task.task33.task3310.strategy.FileStorageStrategy;
import com.javarush.task.task33.task3310.strategy.HashMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.OurHashMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.StorageStrategy;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Solution {


	public static void main(String[] args) {
		testStrategy(new HashMapStorageStrategy(), 100);
		testStrategy(new OurHashMapStorageStrategy(), 100);
		testStrategy(new FileStorageStrategy(), 100);
	}


//	Этот метод должен для переданного множества строк возвращать множество идентификаторов.
//	Идентификатор для каждой отдельной строки нужно получить, используя shortener.
	public static Set<Long> getIds(Shortener shortener, Set<String> strings) {
		Set<Long> ids = new HashSet<>();

		for (String string : strings) {
			ids.add(shortener.getId(string));
		}
		return ids;
	}

//	Метод будет возвращать множество строк,
//	которое соответствует переданному множеству идентификаторов.
	public static Set<String> getStrings(Shortener shortener, Set<Long> keys) {
		Set<String> strings = new HashSet<>();

		for (Long id : keys) {
			strings.add(shortener.getString(id));
		}
		return strings;
	}

//	Метод будет тестировать работу переданной стратегии на определенном количестве элементов elementsNumber.
//	Реализация метода должна:
//	1. Выводить имя класса стратегии. Имя не должно включать имя пакета.
//	2. Генерировать тестовое множество строк, используя Helper и заданное количество элементов elementsNumber.
//	3. Создавать объект типа Shortener, используя переданную стратегию.
//	4. Замерять и выводить время необходимое для отработки метода getIds для заданной стратегии и заданного множества элементов.
//	Время вывести в миллисекундах. При замере времени работы метода можно пренебречь переключением процессора на другие потоки, временем,
//	которое тратится на сам вызов, возврат значений и вызов методов получения времени (даты). Замер времени произведи с использованием объектов типа Date.
//	5. Замерять и выводить время необходимое для отработки метода getStrings
//	для заданной стратегии и полученного в предыдущем пункте множества идентификаторов.
//	6. Сравнивать одинаковое ли содержимое множества строк, которое было сгенерировано и множества, которое было возвращено методом getStrings.
//	Если множества одинаковы, то выведи "Тест пройден.",
//	иначе "Тест не пройден.".
	public static void testStrategy(StorageStrategy strategy, long elementsNumber) {
		Helper.printMessage(strategy.getClass().getSimpleName()); //1

		Set<String> testSetStrings = new HashSet<>(); //2
		for (int i = 0; i < elementsNumber; i++) {
			testSetStrings.add(Helper.generateRandomString());
		}

		Shortener shortener = new Shortener(strategy); //3

		Date dateStart;
		Date dateStop;
		Set<Long> keys;
		Set<String> strings;

		dateStart = new Date(); // 4
		keys = getIds(shortener, testSetStrings);
		dateStop = new Date();

		Helper.printMessage("Время работы метода getIds составило: " + (dateStop.getTime() - dateStart.getTime()) + "ms");

		dateStart = new Date(); //5
		strings = getStrings(shortener, keys);
		dateStop = new Date();

		Helper.printMessage("Время работы метода getStrings составило: " + (dateStop.getTime() - dateStart.getTime()) + "ms");

		if (testSetStrings.equals(strings)) { //6
			Helper.printMessage("Тест пройден.");
		} else {
			Helper.printMessage("Тест не пройден.");
		}
	}

}
