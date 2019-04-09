package com.javarush.task.task30.task3008.client;

import com.javarush.task.task30.task3008.ConsoleHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BotClient extends Client {

    public static void main(String[] args) {
        BotClient botClient = new BotClient();
        botClient.run();
    }

    @Override
    protected SocketThread getSocketThread() {
        return new BotSocketThread();
    }

    @Override
    protected boolean shouldSendTextFromConsole() {
        return false;
    }

    @Override
    protected String getUserName() {
        return "date_bot_" + ((int) (Math.random() * 100));
    }

    public class BotSocketThread extends SocketThread {

        @Override
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            sendTextMessage("Привет чатику. Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды.");
            super.clientMainLoop();
        }

        @Override
        protected void processIncomingMessage(String message) {
            ConsoleHelper.writeMessage(message);
            if (message != null && message.contains(": ")) {

                String[] data = message.split(": ");

                if (data.length == 2 && data[1] != null) {
                    String userName = data[0];
                    String text = data[1];


                    SimpleDateFormat simpleDateFormat = null;

                    Date date = new Calendar() {
                        @Override
                        protected void computeTime() {

                        }

                        @Override
                        protected void computeFields() {

                        }

                        @Override
                        public void add(int field, int amount) {

                        }

                        @Override
                        public void roll(int field, boolean up) {

                        }

                        @Override
                        public int getMinimum(int field) {
                            return 0;
                        }

                        @Override
                        public int getMaximum(int field) {
                            return 0;
                        }

                        @Override
                        public int getGreatestMinimum(int field) {
                            return 0;
                        }

                        @Override
                        public int getLeastMaximum(int field) {
                            return 0;
                        }
                    }.getTime();


                    switch (text) {
                        case "дата": {
                            simpleDateFormat = new SimpleDateFormat("d.MM.YYYY");
                            break;
                        }
                        case "день": {
                            simpleDateFormat = new SimpleDateFormat("d");
                            break;
                        }

                        case "месяц": {
                            simpleDateFormat = new SimpleDateFormat("MMMM");
                            break;
                        }

                        case "год": {
                            simpleDateFormat = new SimpleDateFormat("YYYY");
                            break;
                        }

                        case "время": {
                            simpleDateFormat = new SimpleDateFormat("H:mm:ss");
                            break;
                        }

                        case "час": {
                            simpleDateFormat = new SimpleDateFormat("H");
                            break;
                        }

                        case "минуты": {
                            simpleDateFormat = new SimpleDateFormat("m");
                            break;
                        }

                        case "секунды": {
                            simpleDateFormat = new SimpleDateFormat("s");
                            break;
                        }
                    }
                    if (simpleDateFormat != null) {
                        sendTextMessage(String.format("Информация для %s: %s", userName, simpleDateFormat.format(Calendar.getInstance().getTime())));
                    }
                }
            }
        }
    }
}