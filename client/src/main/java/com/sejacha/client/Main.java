package com.sejacha.client;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        String serverAddress = "84.252.120.50";
        int serverPort = 4999;
        System.out.println("Server:" + serverAddress + ":" + serverPort);
        System.out.println("Connecting...");
        SocketClient client = new SocketClient(serverAddress, serverPort);

        // Send messages to the server

        while (true) {
            Scanner scanner = new Scanner(System.in);
            String data = null;
            data = scanner.nextLine();
            if (data != null) {
                switch (data) {
                    case "pong":
                        client.sendMessage("\"exec\" : \"send\", \"data\": \"test\"");
                        break;

                    default:
                        break;
                }
            }
        }
    }
}