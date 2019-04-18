package com.javarush.task.task36.task3606;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MyClassLoader extends ClassLoader {

	public Class<?> load(Path path) {
		try {
			if (path.getFileName().toString().lastIndexOf(".class") == -1)
				return null;

			byte[] b = Files.readAllBytes(path);
			return defineClass(null, b, 0, b.length);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}