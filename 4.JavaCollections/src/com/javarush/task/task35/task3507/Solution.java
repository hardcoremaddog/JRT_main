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
        Set<? extends Animal> allAnimals = getAllAnimals
                (Solution.class
                        .getProtectionDomain()
                        .getCodeSource()
                        .getLocation()
                        .getPath()
                        + Solution.class
                        .getPackage()
                        .getName()
                        .replaceAll("[.]", "/") + "/data");
        System.out.println(allAnimals);
    }

    public static Set<? extends Animal> getAllAnimals(String pathToAnimals) {
        Set<Animal> allAnimals = new HashSet<>();

        try {
            //get list of files from path
            File[] list = new File(pathToAnimals).listFiles();

            if (list != null) {
                for (File file : list) {
                    //if it's .class file ->
                    if (file.isFile() && file.getName().endsWith(".class")) {



                        String packageName = Solution.class.getPackage().getName() + ".data";

                        System.out.println(file.toPath());
                        System.out.println(packageName);

                        MyClassLoader loader = new MyClassLoader();

                        Class clazz = loader.load(file.toPath(), packageName);

                        //Reflection:

                        //flags for check
                        boolean isImplAnimals = false;
                        boolean haveDefaultConstructor = false;

                        //implement Animal interface?
                        Class[] interfaces = clazz.getInterfaces();
                        for (Class interf : interfaces)
                            if (interf.getSimpleName().equals("Animal")) {
                                isImplAnimals = true;
                                break;
                            }

                        //have default constructor?
                        Constructor[] constructors = clazz.getConstructors();
                        for (Constructor constructor : constructors)
                            if (constructor.getParameterCount() == 0) {
                                haveDefaultConstructor = true;
                            }

                        //check flags. all good? -> add to set
                        if (isImplAnimals && haveDefaultConstructor) {
                            Animal animal = (Animal) clazz.newInstance();
                            allAnimals.add(animal);
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