package com.javarush.task.task33.task3310.strategy;

import com.javarush.task.task33.task3310.Helper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileBucket {

	Path path;

	public FileBucket() {
		try {
			this.path = Files.createTempFile(Helper.generateRandomString(), ".tmp");
			Files.deleteIfExists(path);
			Files.createFile(path);
		} catch (IOException e) {
		}

		if (path != null) {
			path.toFile().deleteOnExit();
		}
	}

	public long getFileSize() {
		try {
			return Files.size(path);
		} catch (IOException e) {
		}
		return 0;
	}

	public void putEntry(Entry entry) {
		try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path))) {
			oos.writeObject(entry);
		} catch (IOException e) {
		}
	}

	public Entry getEntry() {
		Entry entry = null;

		if (getFileSize() <= 0) {
			return null;
		}
		try {
			ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(path));
			entry = (Entry) ois.readObject();
			ois.close();

		} catch (IOException | ClassNotFoundException e) {
		}
		return entry;
	}

	public void remove() {
		try {
			Files.delete(path);
		} catch (IOException e) {
		}
	}
}
