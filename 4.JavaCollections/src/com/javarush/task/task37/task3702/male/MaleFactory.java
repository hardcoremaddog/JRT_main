package com.javarush.task.task37.task3702.male;

import com.javarush.task.task37.task3702.Human;

public class MaleFactory {

	public Human getPerson(int age) {
		if (age > 0 && age < 13) {
			return new KidBoy();
		}

		else if (age > 12 && age < 20) {
			return new TeenBoy();
		}

		else if (age > 19) {
			return new Man();
		}

		return null;
	}
}
