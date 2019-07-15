package com.javarush.task.task40.task4011;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/* 
Свойства URL
*/

public class Solution {
    public static void main(String[] args) throws IOException {
        decodeURLString("https://www.amrood.com/index.htm?language=en#j2se");
        decodeURLString("nourljustexample");
    }

    public static void decodeURLString(String s) throws MalformedURLException {
        URL url = null;
        try {
            url = new URL(s);
        } catch (MalformedURLException e) {
            System.out.println("Parameter " + s + " is not a valid URL.");
        }

        if (url != null) {
            System.out.println(url.getProtocol());
            System.out.println(url.getAuthority());
            System.out.println(url.getFile());
            System.out.println(url.getHost());
            System.out.println(url.getPath());
            System.out.println(url.getPort());
            System.out.println(url.getDefaultPort());
            System.out.println(url.getQuery());
            System.out.println(url.getRef());
        }
    }
}

