package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Helper;
import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.*;
import org.junit.Assert;
import org.junit.Test;

public class FunctionalTest {

	public void testStorage(Shortener shortener) {
		String s1 = Helper.generateRandomString();
		String s2 = Helper.generateRandomString();
		String s3 = String.valueOf(s1);

		Long l1 = shortener.getId(s1);
		Long l2 = shortener.getId(s2);
		Long l3 = shortener.getId(s3);

		Assert.assertNotEquals(l2, l1);
		Assert.assertNotEquals(l2, l3);

		Assert.assertEquals(l1, l3);

		String s1FromL1 = shortener.getString(l1);
		String s2FromL2 = shortener.getString(l2);
		String s3FromL3 = shortener.getString(l3);

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
