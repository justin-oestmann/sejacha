package com.sejacha.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Room {
    private int id;
    private String name;

    public Room(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

public class ChatHandler {
    private String[] commands = { "room", "help", "login", "register" };
    private String[] subCommands = {"create", "join", "delete", "help"};
    private List<Room> rooms = new ArrayList<>();
    private int nextRoomId = 1;

    public static void main(String[] args) {
        ChatHandler chatHandler = new ChatHandler();
        chatHandler.run();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println(
                "Willkommen! Geben Sie einen Befehl zum Fortfahren ein. Mit '/help' erhalten Sie eine Übersicht aller Befehle.");

        while (true) {
            System.out.print("> ");
            input = scanner.nextLine().toLowerCase();

            String[] inputParts = input.split(" ");
            String command = inputParts[0];
            switch (command) {
                case "room":
                    handleRoom(inputParts);
                    break;
                case "help":
                    handleHelp();
                    break;
                case "login":
                    handleLogin();
                    break;
                case "register":
                    handleRegister();
                    break;
                default:
                    System.out.println("Unbekannter Befehl. Geben Sie '/help' ein, um eine Liste der verfügbaren Befehle zu sehen.");
                    break;
            }
        }
    }

    private void handleRoom(String[] inputParts) {
        if (inputParts.length < 2) {
            System.out.println("Falsche Verwendung des Befehls. Geben Sie '/help room' ein, um eine Liste der verfügbaren Befehle zu sehen.");
            return;
        }
        
        String subCommand = inputParts[1];
        String roomName = inputParts.length > 2 ? inputParts[2] : "";

        switch (subCommand) {
            case "create":
                handleRoomCreate(roomName);
                break;
            case "join":
                handleRoomJoin(roomName);
                break;
            case "delete":
                handleRoomDelete(roomName);
                break;
            case "help":
                handleRoomHelp();
                break;
            default:
                System.out.println("Unbekannter Raum-Befehl. Geben Sie '/help room' ein, um eine Liste der verfügbaren Befehle zu sehen.");
                break;
        }
    }

    private void handleRoomCreate(String roomName) {
        if (roomName.isEmpty()) {
            System.out.println("Bitte geben Sie einen Namen für den Raum an.");
            return;
        }
        Room newRoom = new Room(nextRoomId++, roomName);
        rooms.add(newRoom);
        System.out.println("Ein neuer Raum wurde erstellt! Raum-ID: " + newRoom.getId() + ", Name: " + newRoom.getName());
    }

    private void handleRoomJoin(String roomName) {
        if (roomName.isEmpty()) {
            System.out.println("Bitte geben Sie einen Namen für den Raum an.");
            return;
        }
        for (Room room : rooms) {
            if (room.getName().equalsIgnoreCase(roomName)) {
                System.out.println("Sie sind dem Raum '" + roomName + "' mit der ID " + room.getId() + " beigetreten!");
                return;
            }
        }
        System.out.println("Raum '" + roomName + "' nicht gefunden.");
    }

    private void handleRoomDelete(String roomName) {
        if (roomName.isEmpty()) {
            System.out.println("Bitte geben Sie einen Namen für den Raum an.");
            return;
        }
        for (Room room : rooms) {
            if (room.getName().equalsIgnoreCase(roomName)) {
                rooms.remove(room);
                System.out.println("Der Raum '" + roomName + "' wurde gelöscht.");
                return;
            }
        }
        System.out.println("Raum '" + roomName + "' nicht gefunden.");
    }

    private void handleRoomHelp() {
        System.out.println("Verfügbare Befehle:");
        for (String subCommand : subCommands) {
            System.out.println(" - " + subCommand);
        }
    }

    private void handleHelp() {
        System.out.println("Verfügbare Befehle:");
        for (String command : commands) {
            System.out.println(" - " + command);
        }
    }

    private void handleLogin() {
    
    }

    private void handleRegister() {

    }
}
