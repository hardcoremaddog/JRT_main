package com.javarush.task.task25.task2505;

import java.util.logging.Logger;

/*
Без дураков
*/
public class Solution {

    public static void main(String[] args) {
        MyThread myThread = new Solution().new MyThread("super secret key");
        myThread.start();
    }

    public class MyThread extends Thread {
        private String secretKey;

        public MyThread(String secretKey) {
            this.secretKey = secretKey;
            setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
            setDaemon(false);
        }

        @Override
        public void run() {
            throw new NullPointerException("it's an example");
        }


        private class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

            private Logger logger = Logger.getLogger(MyThread.class.getName());

            public MyUncaughtExceptionHandler() {

            }

            @Override
            public void uncaughtException(Thread t, Throwable e) {

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                System.out.println(String.format("%s, %s, %s", secretKey, t.getName(), e.getMessage()));
                logger.info("Хосейо");

            }
        }
    }
}

