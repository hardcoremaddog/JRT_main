package com.javarush.task.task20.task2026;

/* 
Алгоритмы-прямоугольники
*/
public class Solution {
    public static void main(String[] args) {
        byte[][] a1 = new byte[][]{
                {1, 1, 0, 0},
                {1, 1, 0, 0},
                {1, 1, 0, 0},
                {1, 1, 0, 1}
        };
        byte[][] a2 = new byte[][]{
                {1, 0, 0, 1},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {1, 0, 0, 1}
        };

        int count1 = getRectangleCount(a1);
        System.out.println("count = " + count1 + ". Должно быть 2");
        int count2 = getRectangleCount(a2);
        System.out.println("count = " + count2 + ". Должно быть 4");
    }

    public static int getRectangleCount(byte[][] a) {
        int count = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                if (a[i][j] == 1) {
                    int x1 = i;
                    int y1 = j;
                    while (i < a.length) {
                        if (a[i][j] == 0) break;
                        i++;
                    }
                    i--;
                    while (j < a.length) {
                        if (a[i][j] == 0) break;
                        j++;
                    }
                    j--;
                    for (int l = x1; l <= i; l++)
                        for (int k = y1; k <= j; k++)
                            a[l][k] = 0;
                    count++;
                }
            }
        }
        return count;
    }
}