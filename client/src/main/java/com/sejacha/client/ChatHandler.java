package com.sejacha.client;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

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
    private SocketClient socketClient;

    public ChatHandler() {
        this.socketClient = new SocketClient("127.0.0.1", 4999, new SocketClientResponse() {
            public void onLoginSuccess(SocketMessage socketMessage) {
                currentUser = socketMessage.getUsername();
                isAdmin = socketMessage.isAdmin();
                SysPrinter.println(SysPrinterType.INFO, "Login success");
                System.out.println("Login successful. Welcome, " + currentUser + "!");
            }

            public void onLoginFail(SocketMessage socketMessage) {
                SysPrinter.println(SysPrinterType.ERROR, "Login failed");
                System.out.println("Incorrect username or password. Please try again.");
            }

            public void onRegisterSuccess(SocketMessage socketMessage) {
                SysPrinter.println(SysPrinterType.INFO, "Register success");
                System.out.println("Registration successful! You can now log in.");
            }

            public void onRegisterFail(SocketMessage socketMessage) {
                SysPrinter.println(SysPrinterType.ERROR, "Register failed");
                System.out.println("Registration failed. Username might already be taken.");
            }

            @Override
            public void onLogoutSuccess(SocketMessage response) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'onLogoutSuccess'");
            }

            @Override
            public void onLogoutFail(SocketMessage response) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'onLogoutFail'");
            }

            @Override
            public void onNewMessageSuccess(SocketMessage response) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'onNewMessageSuccess'");
            }

            @Override
            public void onNewMessageFail(SocketMessage response) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'onNewMessageFail'");
            }

            @Override
            public void onRoomJoinSuccess(SocketMessage response) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'onRoomJoinSuccess'");
            }

            @Override
            public void onRoomJoinFail(SocketMessage response) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'onRoomJoinFail'");
            }

            @Override
            public void onRoomJoinWPasswordSuccess(SocketMessage response) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'onRoomJoinWPasswordSuccess'");
            }

            @Override
            public void onRoomJoinWPasswordFail(SocketMessage response) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'onRoomJoinWPasswordFail'");
            }

            @Override
            public void onRoomLeaveSuccess(SocketMessage response) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'onRoomLeaveSuccess'");
            }

            @Override
            public void onRoomLeaveFail(SocketMessage response) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'onRoomLeaveFail'");
            }

            @Override
            public void onRoomCreateSuccess(SocketMessage response) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'onRoomCreateSuccess'");
            }

            @Override
            public void onRoomCreateFail(SocketMessage response) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'onRoomCreateFail'");
            }

            @Override
            public void onRoomGetInfoSuccess(SocketMessage response) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'onRoomGetInfoSuccess'");
            }

            @Override
            public void onRoomGetInfoFail(SocketMessage response) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'onRoomGetInfoFail'");
            }

            @Override
            public void onRoomInviteContactSuccess(SocketMessage response) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'onRoomInviteContactSuccess'");
            }

            @Override
            public void onRoomInviteContactFail(SocketMessage response) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'onRoomInviteContactFail'");
            }

            @Override
            public void onContactAddSuccess(SocketMessage response) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'onContactAddSuccess'");
            }

            @Override
            public void onContactAddFail(SocketMessage response) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'onContactAddFail'");
            }

            @Override
            public void onContactRemoveSuccess(SocketMessage response) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'onContactRemoveSuccess'");
            }

            @Override
            public void onContactRemoveFail(SocketMessage response) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'onContactRemoveFail'");
            }

            @Override
            public void onContactCreateDMRoomSuccess(SocketMessage response) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'onContactCreateDMRoomSuccess'");
            }

            @Override
            public void onContactCreateDMRoomFail(SocketMessage response) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'onContactCreateDMRoomFail'");
            }
        });
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

    private void handleLogin(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        socketClient.login(username, password);
    }

    private void handleRegister(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        socketClient.register(username, password);
    }
}
