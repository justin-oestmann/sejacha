package com.sejacha.client;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class ChatHandler implements Runnable {
    private String[] commands = {"room", "help", "login", "register"};
    private String[] subCommands = {"create", "join", "delete", "help"};
    private List<Room> rooms = new ArrayList<>();
    private int nextRoomId = 1;
    private String currentUser = null;
    private boolean isAdmin = false;
    private JsonObject config;
    private static final Scanner scanner = new Scanner(System.in);
    private Map<String, String> users = new HashMap<>();
    private Map<String, Boolean> adminStatus = new HashMap<>();

    public void loadConfig(String configFilePath) {
        try (FileReader reader = new FileReader(configFilePath)) {
            config = JsonParser.parseReader(reader).getAsJsonObject();
        } catch (IOException e) {
            System.err.println("Error loading config file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void run() {
        String welcomeMessage = config != null && config.has("welcomeMessage") ? config.get("welcomeMessage").getAsString() : 
                "Welcome! Please input a command to continue. Type '/help room' for a list of available commands.";
        
        System.out.println(welcomeMessage);

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().toLowerCase();

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
                    System.out.println("Unknown command. Type '/help room' for a list of available commands.");
                    break;
            }
        }
    }

    private void handleRoom(String[] inputParts) {
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
                    handleRoomJoin(inputParts[2]);
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

    private void handleRoomCreate(String[] inputParts) {
        if (inputParts.length < 4) {
            System.out.println("Usage: /room create <name> <password> [type]");
            return;
        }

        String roomName = inputParts[2];
        String owner = currentUser;
        String password = inputParts[3];
        int type = 0;

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

    private void handleRoomJoin(String roomName) {
        if (roomName.isEmpty()) {
            System.out.println("Please specify a name for the room.");
            return;
        }
        for (Room room : rooms) {
            if (room.getName().equalsIgnoreCase(roomName)) {
                if (room.getType() == 1) {
                    System.out.print("This room is private. Please enter the password: ");
                    String enteredPassword = scanner.nextLine();
                    if (room.getPassword().equals(enteredPassword)) {
                        System.out.println("You have joined the private room '" + roomName + "' with ID " + room.getId() + "!");
                    } else {
                        System.out.println("Incorrect password. Access denied.");
                    }
                } else {
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

    private void handleRoomHelp() {
        System.out.println("Available commands:");
        for (String subCommand : subCommands) {
            System.out.println(" - " + subCommand);
        }
    }

    private void handleHelp() {
        System.out.println("Available commands:");
        for (String command : commands) {
            System.out.println(" - " + command);
        }
    }

    private void handleLogin() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (users.containsKey(username) && users.get(username).equals(password)) {
            currentUser = username;
            isAdmin = adminStatus.get(username);
            System.out.println("Login successful. Welcome, " + username + "!");
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    private void handleRegister() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (users.containsKey(username)) {
            System.out.println("Username already exists. Please choose another username.");
        } else {
            users.put(username, password);
            adminStatus.put(username, false); // Default to non-admin user
            System.out.println("Registration successful! You can now log in.");
        }
    }
}
