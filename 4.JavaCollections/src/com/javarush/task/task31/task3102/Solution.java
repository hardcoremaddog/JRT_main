package com.javarush.task.task31.task3102;

import java.awt.desktop.PrintFilesEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.BlockingQueue;

/* 
Находим все файлы
*/
public class Solution {


    public static List<String> getFileTree(String root) throws IOException {
        List<String> list = new ArrayList<>();

        Queue<String> taskList = new PriorityQueue<>();
        taskList.add(root);
        while (!taskList.isEmpty()) {
            String dir = taskList.poll();
            File directory = new File(dir);
            for (File file : directory.listFiles()) {
                if (file.isDirectory()) {
                    taskList.add(file.getAbsolutePath());
                } else {
                    list.add(file.getAbsolutePath());
                }
            }
        }

        return list;


    }

    public static void main(String[] args) {
        try {
            System.out.println(getFileTree("D:/1/"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
