package com.sejacha.client;

import java.util.Scanner;

import com.sejacha.client.SysPrinter;

public class Main {
    public static void main(String[] args) throws Exception {
        String serverAddress = "127.0.0.1";
        int serverPort = 4999;
        System.out.println("Server:" + serverAddress + ":" + serverPort);
        System.out.println("Connecting...");
        SocketClient client = new SocketClient(serverAddress, serverPort);

        // Send messages to the server

        while (true) {
            Scanner scanner = new Scanner(System.in);
            String data = null;
            System.out.print(">");
            data = scanner.nextLine();
            if (data != null) {
                switch (data) {
                    case "pong":
                        client.sendMessage("{\"exec\" : \"send\", \"data\": \"test\"}");
                        SysPrinter.println("Info", "Command sent!");
                        break;

                    default:
                        SysPrinter.println("Error", "Command not found!");
                        break;
                }
            }
        }
    }
}