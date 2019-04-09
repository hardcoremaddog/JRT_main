package com.javarush.task.task31.task3101;

import java.io.*;
import java.util.*;

/*
Проход по дереву файлов
*/
public class Solution {
	private List<File> lowerThan50bytes = new ArrayList<>();

	private void processFilesFromFolder(File folder) {
		for (File entry : folder.listFiles()) {
			if (entry.isDirectory()) {
				processFilesFromFolder(entry);
				continue;
			}
			if (entry.length() <= 50) {
				lowerThan50bytes.add(entry);
			}
		}
	}

	private void writeToFile(List<File> files, File destination) throws IOException {
		try (FileWriter fileWriter = new FileWriter(destination)) {
		for (File file : files) {
				try (FileReader fileReader = new FileReader(file)) {
					int c;
					while ((c = fileReader.read()) != -1) {
						fileWriter.write(c);
					}
					fileWriter.write("\n");
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		File path = new File(args[0]);
		File resultFileAbsolutePath = new File(args[1]);


		Solution solution = new Solution();
		solution.processFilesFromFolder(path);

		File renamedFile = new File(resultFileAbsolutePath.getParent() + "allFilesContent.txt");
		if (!FileUtils.isExist(renamedFile)) {
			FileUtils.renameFile(resultFileAbsolutePath.getAbsoluteFile(), renamedFile);
		}

		solution.lowerThan50bytes.sort(Comparator.comparing(File::getName));
		solution.writeToFile(solution.lowerThan50bytes, renamedFile);

	}
}