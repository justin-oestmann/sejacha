package com.sejacha.client;

import java.io.Console;
import java.util.Scanner;

import com.sejacha.client.SysPrinter;

public class Main {
    public static void main(String[] args) throws Exception {

        String serverAddress = "127.0.0.1";
        int serverPort = 4999;
        System.out.println("Server:" + serverAddress + ":" + serverPort);
        System.out.println("Connecting...");
        SocketClient client = new SocketClient(serverAddress, serverPort);

        String authToken = "";

        while (authToken == "") {
            String username = null;
            String password = null;

            Scanner scanner1 = new Scanner(System.in);
            System.out.println("Please enter your username:");

            System.out.print("> ");

            username = scanner1.nextLine();
            scanner1.close();

            Scanner scanner2 = new Scanner(System.in);
            System.out.println("Please enter your password:");

            System.out.print("> ");

            password = scanner2.nextLine();

            scanner2.close();

            if (username != "" && password != "") {

                client.sendMessage(
                        "{\"exec\":\"auth\", \"username\":\"" + username + "\", \"password\":\"" + password + "\"}");
                SysPrinter.println("Info", "Checking credentials...");
            }
        }

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