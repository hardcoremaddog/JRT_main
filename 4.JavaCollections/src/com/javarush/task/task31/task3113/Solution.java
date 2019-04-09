/*
Что внутри папки?
Напиши программу, которая будет считать подробную информацию о папке и выводить ее на консоль.

Первым делом считай путь к папке с консоли.
Если введенный путь не является директорией - выведи "[полный путь] - не папка" и заверши работу.
Затем посчитай и выведи следующую информацию:

Всего папок - [количество папок в директории и поддиректориях]
Всего файлов - [количество файлов в директории и поддиректориях]
Общий размер - [общее количество байт, которое хранится в директории]

Используй только классы и методы из пакета java.nio.

Квадратные скобки [ ] выводить на экран не нужно.

Требования:
1. Метод main должен считывать путь к папке с консоли.
2. Если введенный путь не является директорией - нужно вывести "[полный путь] - не папка" и завершить работу.
3. Используй только классы и методы из пакета java.nio.
4. На консоль должна быть выведена следующая информация: "Всего папок - [количество папок в директории и поддиректориях]".
5. На консоль должна быть выведена следующая информация: "Всего файлов - [количество файлов в директории и поддиректориях]".
6. На консоль должна быть выведена следующая информация: "Общий размер - [общее количество байт, которое хранится в директории]".
*/

package com.javarush.task.task31.task3113;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class Solution extends SimpleFileVisitor<Path> {

    private int foldersCount = -1;
    private int filesCount = 0;
    private int bytesCount = 0;

    public int getFoldersCount() {
        return foldersCount;
    }

    public int getFilesCount() {
        return filesCount;
    }

    public int getBytesCount() {
        return bytesCount;
    }

    public static void main(String[] args) throws IOException {
        Path directory;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
             directory = Paths.get(br.readLine());
        }

        if (!Files.isDirectory(directory)) {
            System.out.println(directory.toAbsolutePath().toString() + " - не папка");
            return;
        }

        Solution solution = new Solution();

        Files.walkFileTree(directory, solution);

        System.out.println("Всего папок - " + solution.getFoldersCount());
        System.out.println("Всего файлов - " + solution.getFilesCount());
        System.out.println("Общий размер - " + solution.getBytesCount());
    }


    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        if (attrs.isDirectory()) {
            foldersCount++;
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

        byte[] bytes = Files.readAllBytes(file);

        bytesCount += bytes.length;

        if (attrs.isRegularFile()) {
            filesCount++;
        }

        return FileVisitResult.CONTINUE;
    }
}