package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Helper;
import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.*;
import org.junit.Assert;
import org.junit.Test;

public class FunctionalTest {

//	14.4. Добавь в класс FunctionalTest
// 	метод testStorage(Shortener shortener). Он должен:
	public void testStorage(Shortener shortener) {
//		14.4.1. Создавать три строки.
// 		Текст 1 и 3 строк должен быть одинаковым.
		String s1 = Helper.generateRandomString();
		String s2 = Helper.generateRandomString();
		String s3 = String.valueOf(s1);

//		14.4.2. Получать и сохранять идентификаторы
// 		для всех трех строк с помощью shortener.
		Long l1 = shortener.getId(s1);
		Long l2 = shortener.getId(s2);
		Long l3 = shortener.getId(s3);

//		14.4.3. Проверять, что идентификатор
//		для 2 строки не равен идентификатору для 1 и 3 строк.
		Assert.assertNotEquals(l2, l1);
		Assert.assertNotEquals(l2, l3);

//		14.4.4. Проверять, что идентификаторы
//		для 1 и 3 строк равны.
		Assert.assertEquals(l1, l3);

//		14.4.5. Получать три строки по трем идентификаторам
//		с помощью shortener.
		String s1FromL1 = shortener.getString(l1);
		String s2FromL2 = shortener.getString(l2);
		String s3FromL3 = shortener.getString(l3);

//		14.4.6. Проверять, что строки,
//		полученные в предыдущем пункте, эквивалентны оригинальным.
		Assert.assertEquals(s1, s1FromL1);
		Assert.assertEquals(s2, s2FromL2);
		Assert.assertEquals(s3, s3FromL3);
	}

	@Test
	public void testHashMapStorageStrategy(){
		Shortener shortener = new Shortener(new HashMapStorageStrategy());
		testStorage(shortener);
	}

	@Test
	public void testOurHashMapStorageStrategy() {
		Shortener shortener = new Shortener(new OurHashMapStorageStrategy());
		testStorage(shortener);
	}

	@Test
	public void testFileStorageStrategy() {
		Shortener shortener = new Shortener(new FileStorageStrategy());
		testStorage(shortener);
	}

	@Test
	public void testHashBiMapStorageStrategy() {
		Shortener shortener = new Shortener(new HashBiMapStorageStrategy());
		testStorage(shortener);
	}

	@Test
	public void testDualHashBidiMapStorageStrategy() {
		Shortener shortener = new Shortener(new DualHashBidiMapStorageStrategy());
		testStorage(shortener);
	}

	@Test
	public void  testOurHashBiMapStorageStrategy() {
		Shortener shortener = new Shortener(new OurHashBiMapStorageStrategy());
		testStorage(shortener);
	}
}
