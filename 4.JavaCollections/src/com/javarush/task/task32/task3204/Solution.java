package com.javarush.task.task32.task3204;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/* 
Генератор паролей
*/
public class Solution {
	public static void main(String[] args) {
		ByteArrayOutputStream password = getPassword();
		System.out.println(password.toString());
	}

	public static ByteArrayOutputStream getPassword() {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		List<Byte> byteList = new ArrayList<>();

		for (int i = 65; i <= 90; i++) {
			byteList.add((byte) i);
		}

		for (int i = 97; i <= 122; i++) {
			byteList.add((byte) i);
		}

		for (int i = 48; i <= 57; i++) {
			byteList.add((byte) i);
		}

		for (int i = 0; i < 8; i++) {
			int index = (int) (Math.random() * byteList.size());
			byteArrayOutputStream.write(byteList.get(index));
		}

		return byteArrayOutputStream;
	}
}