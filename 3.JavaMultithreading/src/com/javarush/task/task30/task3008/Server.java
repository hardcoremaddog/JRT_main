package com.javarush.task.task30.task3008;

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();

    public static void sendBroadcastMessage(Message message) {
        for (Map.Entry<String, Connection> entry : connectionMap.entrySet()) {
            try {
                entry.getValue().send(message);
            } catch (IOException e) {
                System.out.println("Error!. Can't send the message to " + entry.getKey());
            }
        }
    }


    public static void main(String[] args) throws Exception {

        try (ServerSocket serverSocket = new ServerSocket(ConsoleHelper.readInt())) {
            ConsoleHelper.writeMessage("Server started...");
            while (true) {
                new Handler(serverSocket.accept()).start();
            }
        } catch (Exception e) {
            ConsoleHelper.writeMessage("Something wrong, Server socket closed");
        }


    }


    private static class Handler extends Thread implements AutoCloseable {
        private Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.println("Connection established in " + socket.getRemoteSocketAddress());
            try {
                Connection connection = new Connection(socket);
                String userName = serverHandshake(connection);
                sendBroadcastMessage(new Message(MessageType.USER_ADDED, userName));
                sendListOfUsers(connection, userName);
                serverMainLoop(connection, userName);
                connectionMap.remove(userName);
                sendBroadcastMessage(new Message(MessageType.USER_REMOVED, userName));
                ConsoleHelper.writeMessage("Connection " + socket.getRemoteSocketAddress() + " was closed");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                ConsoleHelper.writeMessage("ERROR!");
            }

        }

        @Override
        public void close() throws Exception {
            this.close();
        }

        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException {

            Message receive;

            while (true) {
                connection.send(new Message(MessageType.NAME_REQUEST));
                receive = connection.receive();
                if (receive.getType() == MessageType.USER_NAME && !(receive.getData().equals("")) && (!(connectionMap.containsKey(receive.getData())))) {
                    connectionMap.put(receive.getData(), connection);
                    connection.send(new Message(MessageType.NAME_ACCEPTED));
                    return receive.getData();
                }
            }
        }

        private void sendListOfUsers(Connection connection, String userName) throws IOException {
            for (Map.Entry<String, Connection> entry : connectionMap.entrySet()) {
                if (!entry.getKey().equals(userName)) {
                    connection.send(new Message(MessageType.USER_ADDED, entry.getKey()));
                }
            }
        }

        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException {
            Message receive;

            while (true) {
                receive = connection.receive();
                if (receive.getType() == MessageType.TEXT) {
                    sendBroadcastMessage(new Message(MessageType.TEXT, userName + ": " + receive.getData()));
                } else {
                    ConsoleHelper.writeMessage("ERROR!");
                }
            }
        }
    }
}
