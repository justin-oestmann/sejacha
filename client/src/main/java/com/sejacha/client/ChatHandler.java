package com.sejacha.client;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.sql.*;

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

public class ChatHandler {
    private String[] commands = { "room", "help", "login", "register" };
    private String[] subCommands = { "create", "join", "delete", "help" };
    private List<Room> rooms = new ArrayList<>();
    private int nextRoomId = 1;
    private String currentUser = null;
    private boolean isAdmin = false;
    private Properties config;
    private SocketClient socketClient;

    public ChatHandler() {
        this.socketClient = new SocketClient("127.0.0.1", 4999, new SocketClientResponse() {
            public void onLoginSuccess(SocketMessage socketMessage) {
                SysPrinter.println(SysPrinterType.INFO, "Login success");
            }

            public void onLoginFail(SocketMessage socketMessage) {
                SysPrinter.println(SysPrinterType.ERROR, "Login failed");
            }

            public void onRegisterSuccess(SocketMessage socketMessage) {
                SysPrinter.println(SysPrinterType.INFO, "Register success");
            }

            public void onRegisterFail(SocketMessage socketMessage) {
                SysPrinter.println(SysPrinterType.ERROR, "Register failed");
            }
        });

    }

    public void loadConfig(String configFilePath) {
        config = new Properties();
        try (FileInputStream fis = new FileInputStream(configFilePath)) {
            config.load(fis);
        } catch (IOException e) {
            System.err.println("Error loading config file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println(
                "Welcome! Please input a command to continue. Type '/help room' for a list of available commands.");

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
                    handleLogin(scanner);
                    break;
                case "register":
                    handleRegister(scanner);
                    break;
                default:
                    System.out.println("Unknown command. Type '/help room' for a list of available commands.");
                    break;
            }
        }
    }

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
                    System.out
                            .println("Invalid type. Type must be 0 (public) or 1 (private). Defaulting to public (0).");
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

    private void handleRoomJoin(String roomName, Scanner scanner) {
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
                        System.out.println(
                                "You have joined the private room '" + roomName + "' with ID " + room.getId() + "!");
                    } else {
                        System.out.println("Incorrect password. Access denied.");
                    }
                } else {
                    System.out.println(
                            "You have joined the public room '" + roomName + "' with ID " + room.getId() + "!");
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
                    System.out.println("You do not have permission to delete the room '" + roomName
                            + "'. Only the owner or an admin can delete this room.");
                }
                return;
            }
        }
        System.out.println("Room '" + roomName + "' not found.");
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

    private void handleLogin(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        String query = "SELECT password, isAdmin FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://" + config.getProperty("mysql.server") + ":" + config.getProperty("mysql.port") + "/"
                        + config.getProperty("mysql.database"),
                config.getProperty("mysql.user"), config.getProperty("mysql.password"));
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String dbPassword = rs.getString("password");
                    boolean dbIsAdmin = rs.getBoolean("isAdmin");
                    if (password.equals(dbPassword)) {
                        currentUser = username;
                        isAdmin = dbIsAdmin;
                        System.out.println("Login successful. Welcome, " + username + "!");
                    } else {
                        System.out.println("Incorrect password. Please try again.");
                    }
                } else {
                    System.out.println("Username not found. Please register first.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleRegister(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        String checkQuery = "SELECT username FROM users WHERE username = ?";
        String insertQuery = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://" + config.getProperty("mysql.server") + ":" + config.getProperty("mysql.port") + "/"
                        + config.getProperty("mysql.database"),
                config.getProperty("mysql.user"), config.getProperty("mysql.password"));
                PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

            checkStmt.setString(1, username);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Username already exists. Please choose another username.");
                } else {
                    insertStmt.setString(1, username);
                    insertStmt.setString(2, password);
                    insertStmt.executeUpdate();
                    System.out.println("Registration successful! You can now log in.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
