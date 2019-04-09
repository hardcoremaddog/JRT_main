package com.javarush.task.task22.task2212;

public class Solution {
    public static boolean checkTelNumber(String telNumber) {

        if (telNumber != null) {
            boolean b1 = telNumber.matches("^[+]\\d{12}$"); //1) если номер начинается с '+', то он содержит 12 цифр
            boolean b2 = telNumber.matches("^[(]\\d{3}[)]\\d{10}$|^\\d{10}$"); //2) если номер начинается с цифры или открывающей скобки, то он содержит 10 цифр
            boolean b3 = telNumber.matches(".(\\d+[-]\\d+){0,2}$"); //3) может содержать 0-2 знаков '-', которые не могут идти подряд
            boolean b4 = telNumber.matches("^[+]?\\d+?([(]\\d{3}[)])\\d+[-]?\\d+[-]?\\d+$"); //4)может содержать 1 пару скобок '(' и ')' , причем если она есть, то она расположена левее знаков '-'
            boolean b6 = telNumber.matches("^\\W+$"); //6) номер не содержит букв

            return b1 || b2 || b3 || b4 || b6;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println("+380501234567 - " + checkTelNumber("+380501234567"));
        System.out.println("+38(050)1234567 - " + checkTelNumber("+38(050)1234567"));
        System.out.println("+38050123-45-67 - " + checkTelNumber("+38050123-45-67"));
        System.out.println("050123-4567 - " + checkTelNumber("050123-4567"));
        System.out.println("+38)050(1234567 - " + checkTelNumber("+38)050(1234567"));
        System.out.println("+38(050)1-23-45-6-7 - " + checkTelNumber("+38(050)1-23-45-6-7"));
        System.out.println("050ххх4567 - " + checkTelNumber("050ххх4567"));
        System.out.println("050123456 - " + checkTelNumber("050123456"));
        System.out.println("(0)501234567 - " + checkTelNumber("(0)501234567"));
    }
}