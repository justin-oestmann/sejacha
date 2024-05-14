package com.sejacha.client;

public class Main {
    public static void main(String[] args) throws Exception {
        SocketClient serverSocket = new SocketClient("127.0.0.1", 4999);
        serverSocket.connect();
        serverSocket.sendData("test123");

    }
}