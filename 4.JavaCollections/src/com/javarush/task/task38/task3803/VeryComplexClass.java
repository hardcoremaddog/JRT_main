package com.javarush.task.task38.task3803;

/* 
Runtime исключения (unchecked exception)
*/

import com.sun.org.apache.xpath.internal.operations.String;

public class VeryComplexClass {
    public void methodThrowsClassCastException() {
    	Object x = 56;
    	String s = (String)x;
    }

    public void methodThrowsNullPointerException() {
    	Object x = null;
		x.equals(new Object());
    }

    public static void main(String[] args) {

    }
}
