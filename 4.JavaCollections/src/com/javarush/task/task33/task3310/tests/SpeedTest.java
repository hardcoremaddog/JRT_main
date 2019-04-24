package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Helper;
import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.HashBiMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.HashMapStorageStrategy;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SpeedTest {

//	15.2. Добавь в класс метод
// 	long getTimeToGetIds(Shortener shortener, Set<String> strings, Set<Long> ids).
	public long getTimeToGetIds(Shortener shortener, Set<String> strings, Set<Long> ids) {
//		Он должен возвращать время в миллисекундах
//		необходимое для получения идентификаторов
//		для всех строк из strings.
//		Идентификаторы должны быть записаны в ids.

		Date dateStart = new Date();
		for (String string : strings) {
			ids.add(shortener.getId(string));
		}
		Date dateStop = new Date();
		return dateStop.getTime() - dateStart.getTime();
	}

//	15.3. Добавь в класс метод
//	long getTimeToGetStrings(Shortener shortener, Set<Long> ids, Set<String> strings).
	public long getTimeToGetStrings(Shortener shortener, Set<Long> ids, Set<String> strings) {
//		Он должен возвращать время в миллисекундах
// 		необходимое для получения строк для всех идентификаторов из ids.
// 		Строки должны быть записаны в strings.

		Date dateStart = new Date();
		for (Long id : ids) {
			strings.add(shortener.getString(id));
		}
		Date dateStop = new Date();
		return dateStop.getTime() - dateStart.getTime();
	}

//	15.4. Добавь в класс SpeedTest тест testHashMapStorage().
	@Test
	public void testHashMapStorage() {
//		Он должен:
//		15.4.1. Создавать два объекта типа Shortener,
//		один на базе HashMapStorageStrategy,
//		второй на базе HashBiMapStorageStrategy.
//		Назовем их shortener1 и shortener2.
		Shortener shortener1 = new Shortener(new HashMapStorageStrategy());
		Shortener shortener2 = new Shortener(new HashBiMapStorageStrategy());

//		15.4.2. Генерировать с помощью Helper 10000 строк
// 		и помещать их в сет со строками, назовем его origStrings.
		Set<String> origStrings = new HashSet<>();
		for (int i = 0; i < 10000; i++) {
			origStrings.add(Helper.generateRandomString());
		}
//		15.4.3. Получать время получения идентификаторов для origStrings
// 		(вызывать метод getTimeToGetIds для shortener1, а затем для shortener2).
		Set<Long> newLongs = new HashSet<>();

		long shortener1TimeToGetIds = getTimeToGetIds(shortener1, origStrings, newLongs);
		long shortener2TimeToGetIds = getTimeToGetIds(shortener2, origStrings, newLongs);

//		15.4.4. Проверять с помощью junit, что время,
// 		полученное в предыдущем пункте для shortener1 больше, чем для shortener2.
		Assert.assertTrue(shortener1TimeToGetIds > shortener2TimeToGetIds);

//		15.4.5. Получать время получения строк
// 		(вызывать метод getTimeToGetStrings для shortener1 и shortener2).
		Set<String> newStrings = new HashSet<>();
		long shortener1TimeToGetStrings = getTimeToGetStrings(shortener1, newLongs, newStrings);
		long shortener2TimeToGetStrings = getTimeToGetStrings(shortener2, newLongs, newStrings);

//		15.4.6. Проверять с помощью junit, что время,
// 		полученное в предыдущем пункте для shortener1 примерно равно времени для shortener2.
// 		Используй метод assertEquals(float expected, float actual, float delta).
// 		В качестве delta можно использовать 30, этого вполне достаточно для наших экспериментов.
		Assert.assertEquals(shortener1TimeToGetStrings, shortener2TimeToGetStrings, 30);
	}
}
