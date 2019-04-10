package com.javarush.task.task35.task3507;

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Set;


/* 
ClassLoader - что это такое?
*/
public class Solution {

    public static void main(String[] args) {
        Set<? extends Animal> allAnimals = getAllAnimals(Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath() + Solution.class.getPackage().getName().replaceAll("[.]", "/") + "/data");
        System.out.println(allAnimals);
    }

    public static Set<? extends Animal> getAllAnimals(String pathToAnimals) {
        Set<Animal> allAnimals = new HashSet<>();

        try {
            File[] list = new File(pathToAnimals).listFiles();

            if (list != null) {
                for (File file : list) {
                    if (file.isFile() && file.getName().endsWith(".class")) {

                        String packageName = Solution.class.getPackage().getName() + ".data";

                        MyClassLoader loader = new MyClassLoader();

                        Class clazz = loader.load(file.toPath(), packageName);

                        int score = 0;
                        //find interface Animal
                        Class[] interfaces = clazz.getInterfaces();
                        for (Class interf : interfaces)
                            if (interf.getSimpleName().equals("Animal")) {
                                score++;
                                break;
                            }

                        //Find default constuctor
                        Constructor[] constructors = clazz.getConstructors();
                        for (Constructor constructor : constructors)
                            if (constructor.getParameterCount() == 0) {
                                score++;
                            }

                        //if all ok, add to set
                        if (score == 2)
                            try {
                                Animal animal = (Animal) clazz.newInstance();
                                allAnimals.add(animal);
                            } catch (InstantiationException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allAnimals;
    }
}