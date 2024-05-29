package com.sejacha.client;

import java.io.Console;
import java.net.Socket;
import java.sql.Time;
import java.util.Scanner;

import com.sejacha.client.SysPrinter;

public class Main {
    public static void main(String[] args) throws Exception {
        ChatHandler chatHandler;

        while (true) {
            chatHandler = new ChatHandler();
            chatHandler.run();
        }

        /*
         * String serverAddress = "127.0.0.1";
         * 
         * String deineMudda = "sehr groß";
         * int serverPort = 4999;
         * System.out.println("Server:" + serverAddress + ":" + serverPort);
         * System.out.println("Connecting...");
         * 
         * String authToken = "";
         * Scanner scanner = new Scanner(System.in); // Move scanner initialization here
         * 
         * while (authToken.equals("")) {
         * String username = "";
         * String password = "";
         * 
         * // Benutzername eingeben mit Überprüfung
         * while (username.isEmpty()) {
         * System.out.print("Benutzername: ");
         * if (scanner.hasNextLine()) {
         * username = scanner.nextLine().trim();
         * if (username.isEmpty()) {
         * System.out.
         * println("Benutzername darf nicht leer sein. Bitte erneut eingeben.");
         * }
         * } else {
         * System.out.println("Keine Eingabe gefunden. Bitte erneut eingeben.");
         * scanner.next(); // Zum nächsten Token springen
         * }
         * }
         * 
         * // Passwort eingeben mit Überprüfung
         * while (password.isEmpty()) {
         * System.out.print("Passwort: ");
         * if (scanner.hasNextLine()) {
         * password = scanner.nextLine().trim();
         * if (password.isEmpty()) {
         * System.out.println("Passwort darf nicht leer sein. Bitte erneut eingeben.");
         * }
         * } else {
         * System.out.println("Keine Eingabe gefunden. Bitte erneut eingeben.");
         * scanner.next(); // Zum nächsten Token springen
         * }
         * }
         * 
         * System.out.println(username);
         * System.out.println(password);
         * if (!username.isEmpty() && !password.isEmpty()) {
         * 
         * client.sendMessage(
         * "{\"exec\":\"auth\", \"username\":\"" + username + "\", \"password\":\"" +
         * Crypt.calculateSHA512(password) + "\"}");
         * SysPrinter.println("Info", "Checking credentials...");
         * }
         * }
         * 
         * // Send messages to the server
         * 
         * while (true) {
         * System.out.print(">");
         * String data = scanner.nextLine();
         * if (data != null) {
         * switch (data) {
         * case "pong":
         * client.sendMessage("{\"exec\" : \"send\", \"data\": \"test\"}");
         * SysPrinter.println("Info", "Command sent!");
         * break;
         * 
         * default:
         * SysPrinter.println("Error", "Command not found!");
         * break;
         * }
         * }
         * }
         */
    }
}
