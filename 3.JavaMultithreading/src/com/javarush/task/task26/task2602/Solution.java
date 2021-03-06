package com.javarush.task.task26.task2602;

import java.util.Set;
import java.util.TreeSet;

/* 
Был бы ум - будет и успех
*/
public class Solution {
    public static void main(String[] args) {
        String nickName = "hardcoremaddog";
        System.out.println(nickName.matches("^(hardcore)?(maddog)|\\+\\+$"));
    }

    public static class Soldier implements Comparable<Soldier> {
        private String name;
        private int height;

        public Soldier(String name, int height) {
            this.name = name;
            this.height = height;
        }


        @Override
        public int compareTo(Soldier o) {
            return o.height - this.height;
        }
    }
}
