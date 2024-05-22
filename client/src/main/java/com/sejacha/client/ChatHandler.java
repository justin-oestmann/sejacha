package com.sejacha.client;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//region Room Class
class Room {
    private int id;
    private String name;
    private String owner;
    private int type;
    private String password;
    private LocalDateTime timestamp;

    public Room(int id, String name, String owner, int type, String password, LocalDateTime timestamp) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.type = type;
        this.password = password;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public int getType() {
        return type;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
//endregion

//region Main Handler for chat commands
public class ChatHandler {
    private String[] commands = { "room", "help", "login", "register" };
    private String[] subCommands = {"create", "join", "delete", "help"};
    private List<Room> rooms = new ArrayList<>();
    private int nextRoomId = 1;

    public static void main(String[] args) {
        ChatHandler chatHandler = new ChatHandler();
        chatHandler.run();
    }
    //region Main loop to process inputs and execute commands
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
                    handleRoom(inputParts, scanner);
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
    //endregion
//endregion

    //region Loop for room-related commands
    private void handleRoom(String[] inputParts, Scanner scanner) {
        if (inputParts.length < 2) {
            System.out.println("Incorrect usage of the command. Type '/help room' for a list of available commands.");
            return;
        }

        if (currentUser == null) {
            System.out.println("You need to be logged in to execute this command.");
            return;
        }

        String subCommand = inputParts[1];
        switch (subCommand) {
            case "create":
                handleRoomCreate(inputParts);
                break;
            case "join":
                if (inputParts.length >= 3) {
                    handleRoomJoin(inputParts[2], scanner);
                } else {
                    System.out.println("Usage: /room join <name>");
                }
                break;
            case "delete":
                if (inputParts.length >= 3) {
                    handleRoomDelete(inputParts[2]);
                } else {
                    System.out.println("Usage: /room delete <name>");
                }
                break;
            case "help":
                handleRoomHelp();
                break;
            default:
                System.out.println("Unknown room command. Type '/help room' for a list of available commands.");
                break;
        }
    }
    //endregion

    //region Room-related commands
    private void handleRoomCreate(String[] inputParts) {
        if (inputParts.length < 4) {
            System.out.println("Usage: /room create <name> <password> [type]");
            return;
        }

        String roomName = inputParts[2];
        String owner = currentUser;  // Set the owner to the currently logged-in user
        String password = inputParts[3];
        int type = 0;  // Default to public

        if (inputParts.length >= 5) {
            try {
                type = Integer.parseInt(inputParts[4]);
                if (type != 0 && type != 1) {
                    System.out.println("Invalid type. Type must be 0 (public) or 1 (private). Defaulting to public (0).");
                    type = 0;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid type format. Type must be an integer (0 or 1). Defaulting to public (0).");
                type = 0;
            }
        }

        // If type is private (1), password must be provided
        if (type == 1 && password.isEmpty()) {
            System.out.println("Password is required for a private room.");
            return;
        }

        LocalDateTime timestamp = LocalDateTime.now();

        Room newRoom = new Room(nextRoomId++, roomName, owner, type, password, timestamp);
        rooms.add(newRoom);
        System.out.println("A new room has been created! Room ID: " + newRoom.getId() + 
                           ", Name: " + newRoom.getName() + 
                           ", Owner: " + newRoom.getOwner() + 
                           ", Type: " + newRoom.getType() + 
                           ", Password: " + newRoom.getPassword() + 
                           ", Timestamp: " + newRoom.getTimestamp());
    }

    private void handleRoomJoin(String roomName, Scanner scanner) {
        if (roomName.isEmpty()) {
            System.out.println("Please specify a name for the room.");
            return;
        }
        for (Room room : rooms) {
            if (room.getName().equalsIgnoreCase(roomName)) {
                if (room.getType() == 1) {  // Private room
                    System.out.print("This room is private. Please enter the password: ");
                    String enteredPassword = scanner.nextLine();
                    if (room.getPassword().equals(enteredPassword)) {
                        System.out.println("You have joined the private room '" + roomName + "' with ID " + room.getId() + "!");
                    } else {
                        System.out.println("Incorrect password. Access denied.");
                    }
                } else {  // Public room
                    System.out.println("You have joined the public room '" + roomName + "' with ID " + room.getId() + "!");
                }
                return;
            }
        }
        System.out.println("Room '" + roomName + "' not found.");
    }

    private void handleRoomDelete(String roomName) {
        if (roomName.isEmpty()) {
            System.out.println("Please specify a name for the room.");
            return;
        }
        for (Room room : rooms) {
            if (room.getName().equalsIgnoreCase(roomName)) {
                if (room.getOwner().equals(currentUser) || isAdmin) {
                    rooms.remove(room);
                    System.out.println("The room '" + roomName + "' has been deleted.");
                } else {
                    System.out.println("You do not have permission to delete the room '" + roomName + "'. Only the owner or an admin can delete this room.");
                }
                return;
            }
        }
        System.out.println("Room '" + roomName + "' not found.");
    }
    //endregion 

    //region Help command
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
    //endregion

    //TODO Login
    private void handleLogin() {
    
    }

    //TODO Register
    private void handleRegister() {

    }
}

    //TODO Admin einpflegen und CurrentUser