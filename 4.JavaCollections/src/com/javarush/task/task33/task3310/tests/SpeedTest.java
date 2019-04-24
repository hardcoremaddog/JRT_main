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

//	15.2. ������ � ����� �����
// 	long getTimeToGetIds(Shortener shortener, Set<String> strings, Set<Long> ids).
	public long getTimeToGetIds(Shortener shortener, Set<String> strings, Set<Long> ids) {
//		�� ������ ���������� ����� � �������������
//		����������� ��� ��������� ���������������
//		��� ���� ����� �� strings.
//		�������������� ������ ���� �������� � ids.

		Date dateStart = new Date();
		for (String string : strings) {
			ids.add(shortener.getId(string));
		}
		Date dateStop = new Date();
		return dateStop.getTime() - dateStart.getTime();
	}

//	15.3. ������ � ����� �����
//	long getTimeToGetStrings(Shortener shortener, Set<Long> ids, Set<String> strings).
	public long getTimeToGetStrings(Shortener shortener, Set<Long> ids, Set<String> strings) {
//		�� ������ ���������� ����� � �������������
// 		����������� ��� ��������� ����� ��� ���� ��������������� �� ids.
// 		������ ������ ���� �������� � strings.

		Date dateStart = new Date();
		for (Long id : ids) {
			strings.add(shortener.getString(id));
		}
		Date dateStop = new Date();
		return dateStop.getTime() - dateStart.getTime();
	}

//	15.4. ������ � ����� SpeedTest ���� testHashMapStorage().
	@Test
	public void testHashMapStorage() {
//		�� ������:
//		15.4.1. ��������� ��� ������� ���� Shortener,
//		���� �� ���� HashMapStorageStrategy,
//		������ �� ���� HashBiMapStorageStrategy.
//		������� �� shortener1 � shortener2.
		Shortener shortener1 = new Shortener(new HashMapStorageStrategy());
		Shortener shortener2 = new Shortener(new HashBiMapStorageStrategy());

//		15.4.2. ������������ � ������� Helper 10000 �����
// 		� �������� �� � ��� �� ��������, ������� ��� origStrings.
		Set<String> origStrings = new HashSet<>();
		for (int i = 0; i < 10000; i++) {
			origStrings.add(Helper.generateRandomString());
		}
//		15.4.3. �������� ����� ��������� ��������������� ��� origStrings
// 		(�������� ����� getTimeToGetIds ��� shortener1, � ����� ��� shortener2).
		Set<Long> newLongs = new HashSet<>();

		long shortener1TimeToGetIds = getTimeToGetIds(shortener1, origStrings, newLongs);
		long shortener2TimeToGetIds = getTimeToGetIds(shortener2, origStrings, newLongs);

//		15.4.4. ��������� � ������� junit, ��� �����,
// 		���������� � ���������� ������ ��� shortener1 ������, ��� ��� shortener2.
		Assert.assertTrue(shortener1TimeToGetIds > shortener2TimeToGetIds);

//		15.4.5. �������� ����� ��������� �����
// 		(�������� ����� getTimeToGetStrings ��� shortener1 � shortener2).
		Set<String> newStrings = new HashSet<>();
		long shortener1TimeToGetStrings = getTimeToGetStrings(shortener1, newLongs, newStrings);
		long shortener2TimeToGetStrings = getTimeToGetStrings(shortener2, newLongs, newStrings);

//		15.4.6. ��������� � ������� junit, ��� �����,
// 		���������� � ���������� ������ ��� shortener1 �������� ����� ������� ��� shortener2.
// 		��������� ����� assertEquals(float expected, float actual, float delta).
// 		� �������� delta ����� ������������ 30, ����� ������ ���������� ��� ����� �������������.
		Assert.assertEquals(shortener1TimeToGetStrings, shortener2TimeToGetStrings, 30);
	}
}
