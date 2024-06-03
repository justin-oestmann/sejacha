package com.sejacha.client;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONObject;

import com.sejacha.client.exceptions.SocketMessageIsNotNewException;

class Room {
    private String id;
    private String name;
    private String owner;
    private int type;
    private String password;
    private LocalDateTime timestamp;

    public Room(String id, String name, String owner, int type, String password, LocalDateTime timestamp) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.type = type;
        this.password = password;
        this.timestamp = timestamp;
    }

    public String getId() {
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
    private String[] commands = { "room", "help", "login", "register", "restart", "ping", "exit" };
    private String[] subCommands = { "create", "join", "delete", "help" };
    private List<Room> rooms = new ArrayList<>();
    // private String nextRoomId = "1";
    private String currentUser = null;
    private boolean isAdmin = false;
    private SocketClient socketClient;
    private String authKey = null;
    private boolean in_room = false;

    private long pingTime = 0;

    public ChatHandler(String ip, int port) {
        this.socketClient = new SocketClient(ip, port, new SocketClientResponse() {
            public void onLoginSuccess(SocketMessage socketMessage) {
                try {
                    authKey = socketMessage.getData().getString("authkey");
                    currentUser = socketMessage.getData().getString("username");
                } catch (Exception ex) {
                    authKey = null;
                    currentUser = null;
                    SysPrinter.println(SysPrinterType.ERROR, "Login failed. Please try again. (clientside failure)");
                    return;
                }
                SysPrinter.clear();
                SysPrinter.setCursorDouble(true);
                SysPrinter.println(SysPrinterType.INFO, "Login successful. Welcome, " + currentUser + "!");
                SysPrinter.printCursor();

            }

            public void onLoginFail(SocketMessage socketMessage) {
                SysPrinter.println(SysPrinterType.ERROR, socketMessage.getData().getString("reason"));
                SysPrinter.printCursor();
            }

            public void onRegisterSuccess(SocketMessage socketMessage) {
                SysPrinter.println(SysPrinterType.INFO,
                        "Registration successful! Now verify your account with /verify");
                SysPrinter.printCursor();
            }

            public void onRegisterFail(SocketMessage socketMessage) {
                SysPrinter.println(SysPrinterType.ERROR, "Registration failed. Username might already be taken.");
                SysPrinter.printCursor();
            }

            @Override
            public void onLogoutSuccess(SocketMessage response) {
                SysPrinter.println(SysPrinterType.INFO, "Logout successful.");
                authKey = null;
                currentUser = null;
                SysPrinter.setCursorDouble(false);
                SysPrinter.printCursor();
            }

            @Override
            public void onLogoutFail(SocketMessage response) {
                SysPrinter.println(SysPrinterType.ERROR, "Logout failed. Try again.");
            }

            @Override
            public void onNewMessageSuccess(SocketMessage response) {

            }

            @Override
            public void onNewMessageFail(SocketMessage response) {
                SysPrinter.println(SysPrinterType.ERROR, "Message failed to send");
                System.out.println("Failed to send message.");
            }

            @Override
            public void onRoomJoinSuccess(SocketMessage response) {
                SysPrinter.println(SysPrinterType.INFO, "You joined the room \"" + response.getData()
                        .getString("room_name") + "\" successfully!");
                in_room = true;
                SysPrinter.setRoomState(currentUser);
                SysPrinter.printCursor();
            }

            @Override
            public void onRoomJoinFail(SocketMessage socketMessage) {
                SysPrinter.println(SysPrinterType.ERROR, socketMessage.getData().getString("reason"));
                SysPrinter.printCursor();
            }

            @Override
            public void onRoomJoinWPasswordSuccess(SocketMessage response) {
                SysPrinter.println(SysPrinterType.INFO, "You joined the room \"" + response.getData()
                        .getString("room_name") + "\" successfully!");
                in_room = true;
                SysPrinter.setRoomState(currentUser);
                SysPrinter.printCursor();
            }

            @Override
            public void onRoomJoinWPasswordFail(SocketMessage socketMessage) {
                SysPrinter.println(SysPrinterType.ERROR, socketMessage.getData().getString("reason"));
                SysPrinter.printCursor();
            }

            @Override
            public void onRoomLeaveSuccess(SocketMessage response) {
                SysPrinter.println(SysPrinterType.INFO, "Left the room successfully");
                System.out.println("You have left the room.");
            }

            @Override
            public void onRoomLeaveFail(SocketMessage socketMessage) {
                SysPrinter.println(SysPrinterType.ERROR, socketMessage.getData().getString("reason"));
                SysPrinter.printCursor();
            }

            @Override
            public void onRoomCreateSuccess(SocketMessage response) {
                SysPrinter.println(SysPrinterType.INFO, "Room created successfully");
                System.out.println("A new room has been created.");
            }

            @Override
            public void onRoomCreateFail(SocketMessage socketMessage) {
                SysPrinter.println(SysPrinterType.ERROR, socketMessage.getData().getString("reason"));
                SysPrinter.printCursor();
            }

            @Override
            public void onRoomGetInfoSuccess(SocketMessage response) {
                // SysPrinter.println(SysPrinterType.INFO, "Room info retrieved successfully");
                // System.out.println("Room information: " + response.getRoomInfo());
            }

            @Override
            public void onRoomGetInfoFail(SocketMessage socketMessage) {
                SysPrinter.println(SysPrinterType.ERROR, socketMessage.getData().getString("reason"));
                SysPrinter.printCursor();
            }

            @Override
            public void onRoomInviteContactSuccess(SocketMessage response) {
                SysPrinter.println(SysPrinterType.INFO, "Contact invited successfully");
                System.out.println("Contact has been invited to the room.");
            }

            @Override
            public void onRoomInviteContactFail(SocketMessage socketMessage) {
                SysPrinter.println(SysPrinterType.ERROR, socketMessage.getData().getString("reason"));
                SysPrinter.printCursor();
            }

            @Override
            public void onContactAddSuccess(SocketMessage response) {
                SysPrinter.println(SysPrinterType.INFO, "Contact added successfully");
                System.out.println("Contact has been added.");
            }

            @Override
            public void onContactAddFail(SocketMessage socketMessage) {
                SysPrinter.println(SysPrinterType.ERROR, socketMessage.getData().getString("reason"));
                SysPrinter.printCursor();
            }

            @Override
            public void onContactRemoveSuccess(SocketMessage response) {
                SysPrinter.println(SysPrinterType.INFO, "Contact removed successfully");
                System.out.println("Contact has been removed.");
            }

            @Override
            public void onContactRemoveFail(SocketMessage socketMessage) {
                SysPrinter.println(SysPrinterType.ERROR, socketMessage.getData().getString("reason"));
                SysPrinter.printCursor();
            }

            @Override
            public void onContactCreateDMRoomSuccess(SocketMessage response) {
                SysPrinter.println(SysPrinterType.INFO, "DM room created successfully");
                System.out.println("Direct message room has been created.");
            }

            @Override
            public void onContactCreateDMRoomFail(SocketMessage socketMessage) {
                SysPrinter.println(SysPrinterType.ERROR, socketMessage.getData().getString("reason"));
                SysPrinter.printCursor();
            }

            @Override
            public void onPing(SocketMessage response) {
                long endTime = System.currentTimeMillis();
                long duration = endTime - pingTime;
                SysPrinter.println(SysPrinterType.INFO, "PING:" + duration + " ms!");
                SysPrinter.printCursor();
            }

            @Override
            public void onVerifySuccess(SocketMessage response) {
                SysPrinter.println(SysPrinterType.INFO, "Your account has been verified!");
                SysPrinter.printCursor();
            }

            @Override
            public void onVerifyFail(SocketMessage socketMessage) {
                SysPrinter.println(SysPrinterType.ERROR, socketMessage.getData().getString("reason"));
                SysPrinter.printCursor();
            }

            @Override
            public void onNotify(SocketMessage response) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'onNotify'");
            }

            @Override
            public void onNewMessage(SocketMessage response) {
                if (response.getData().getString("author") != currentUser) {
                    SysPrinter.cleanLine();
                    SysPrinter.println(response.getData().getString("author"), response.getData().getString("msg"));
                    SysPrinter.printCursor();
                }

            }
        });
    }

    public void run() throws SocketMessageIsNotNewException {
        SysPrinter.setCursorDouble(false);
        Scanner scanner = new Scanner(System.in);
        String input;

        if (!socketClient.isConnected()) {
            SysPrinter.println(SysPrinterType.ERROR,
                    "Connection failed or disconnected! Please type '/connect' to try again!");
            SysPrinter.printCursor();
            while (true) {

                if (scanner.hasNextLine()) {
                    input = scanner.nextLine().toLowerCase();

                    String[] inputParts = input.split(" ");

                    if (inputParts[0].equalsIgnoreCase("/connect")) {
                        return;
                    } else {
                        SysPrinter.println(SysPrinterType.ERROR,
                                "Connection failed or disconnected again! Please type '/connect' to try again!>");
                    }
                }
                scanner.close();
            }
        }

        SysPrinter.println(SysPrinterType.INFO,
                "Welcome! Please input a command to continue. Type '/help' for a list of available commands.");

        while (true) {
            SysPrinter.printCursor();
            input = scanner.nextLine().toLowerCase();

            String[] inputParts = input.split(" ");
            String command = inputParts[0];
            if (this.authKey != null) {
                switch (command) {
                    case "/room":
                        handleRoom(inputParts, scanner);
                        break;
                    case "/help":
                        handleHelp();
                        break;
                    case "/ping":
                        handlePing(scanner);
                        break;
                    case "/restart":
                        SysPrinter.clear();
                        return;
                    case "/exit":
                        System.exit(0);
                        break;
                    case "/logout":
                        handleLogout(scanner);
                        break;

                    default:
                        if (in_room) {
                            handleNewMessage(input);
                        } else {
                            System.out.println("Unknown command. Type '/help' for a list of available commands.");
                        }
                        break;
                }

            } else {
                switch (command) {
                    case "/login":
                        handleLogin(scanner);
                        break;
                    case "/register":
                        handleRegister(scanner);
                        break;
                    case "/verify":
                        handleVerify(scanner);
                        break;
                    case "/help":
                        handleHelp();
                        break;
                    case "/restart":
                        SysPrinter.clear();
                        return;
                    case "/exit":
                        System.exit(0);
                        break;
                    case "/ping":
                        handlePing(scanner);
                        break;

                    default:
                        System.out.println("Unknown command. Type '/help' for a list of available commands.");
                        break;
                }
            }
        }
    }

    private void handleRoom(String[] inputParts, Scanner scanner) throws SocketMessageIsNotNewException {
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
                    try {
                        String roomId = inputParts[2];
                        handleRoomJoin(roomId, scanner);
                    } catch (NumberFormatException e) {
                        System.out.println("Usage: /room join <id>");
                    }
                } else {
                    System.out.println("Usage: /room join <id>");
                }
                break;
            case "delete":
                if (inputParts.length >= 3) {
                    handleRoomDelete(inputParts[2]);
                } else {
                    System.out.println("Usage: /room delete <name>");
                }
                break;
            case "/help room":
                handleRoomHelp();
                break;
            default:
                System.out.println("Unknown room command. Type '/help room' for a list of available commands.");
                break;
        }
    }

    private void handleRoomCreate(String[] inputParts) throws SocketMessageIsNotNewException {
        if (inputParts.length < 4) {
            System.out.println("Usage: /room create <id> <name> [password] [type]");
            return;
        }

        String roomId = inputParts[2];
        String roomName = inputParts[3];
        String owner = currentUser;
        String password = inputParts.length >= 5 ? inputParts[4] : "";
        int type = password.isEmpty() ? 0 : 1;

        if (inputParts.length >= 6) {
            try {
                type = Integer.parseInt(inputParts[5]);
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

        LocalDateTime timestamp = LocalDateTime.now();
        Room newRoom = new Room(roomId, roomName, owner, type, password, timestamp);
        rooms.add(newRoom);

        SocketMessage socketMessage = new SocketMessage();
        socketMessage.setAuthKey(authKey);
        socketMessage.setType(SocketMessageType.ROOM_CREATE);

        JSONObject data = new JSONObject();
        data.put("room_id", roomId);
        data.put("name", roomName);
        data.put("type", type);
        data.put("password", password);

        socketMessage.setData(data);
        socketClient.sendMessage(socketMessage);

        System.out.println("A new room has been created! Room ID: " + roomId +
                ", Name: " + newRoom.getName() +
                ", Owner: " + newRoom.getOwner() +
                ", Type: " + newRoom.getType() +
                ", Password: " + (password.isEmpty() ? "None" : password) +
                ", Timestamp: " + newRoom.getTimestamp());
    }

    private void handleRoomJoin(String roomId, Scanner scanner) throws SocketMessageIsNotNewException {
        SocketMessage socketMessage = new SocketMessage();
        socketMessage.setAuthKey(authKey);
        socketMessage.setType(SocketMessageType.ROOM_JOIN);

        JSONObject data = new JSONObject();
        data.put("room_id", roomId);

        socketMessage.setData(data);

        socketClient.sendMessage(socketMessage);
    }

    private void handleRoomDelete(String roomId) {
        if (roomId.isEmpty()) {
            System.out.println("Please specify an ID for the room.");
            return;
        }
        for (Room room : rooms) {
            if (room.getId().equalsIgnoreCase(roomId)) {
                if (room.getOwner().equals(currentUser) || isAdmin) {
                    rooms.remove(room);
                    System.out.println("The room with ID '" + roomId + "' has been deleted.");
                } else {
                    System.out.println("You do not have permission to delete the room with ID '" + roomId
                            + "'. Only the owner or an admin can delete this room.");
                }
                return;
            }
        }
        System.out.println("Room with ID '" + roomId + "' not found.");
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

    private void handleLogin(Scanner scanner) throws SocketMessageIsNotNewException {
        System.out.print("Enter email: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        socketClient.login(username, password);
    }

    private void handleLogout(Scanner scanner) throws SocketMessageIsNotNewException {
        socketClient.logout(this.authKey);
    }

    private void handleRegister(Scanner scanner) throws SocketMessageIsNotNewException {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        socketClient.register(username, email, password);
    }

    private void handlePing(Scanner scanner) throws SocketMessageIsNotNewException {

        SocketMessage socketMessage = new SocketMessage();

        socketMessage.setType(SocketMessageType.PING);

        JSONObject data = new JSONObject();
        data.put("ping", "hello world!");

        socketMessage.setData(data);

        socketMessage.setAuthKey(null);

        socketClient.sendMessage(socketMessage);
        pingTime = System.currentTimeMillis();

        SysPrinter.println(SysPrinterType.INFO, "ping sent!");
    }

    private void handleVerify(Scanner scanner) throws SocketMessageIsNotNewException {

        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter verification code from email!: ");
        String code = scanner.nextLine();

        SocketMessage socketMessage = new SocketMessage();

        socketMessage.setType(SocketMessageType.VERIFY);

        JSONObject data = new JSONObject();
        data.put("verifykey", code);
        data.put("email", email);

        socketMessage.setData(data);

        socketMessage.setAuthKey(null);

        socketClient.sendMessage(socketMessage);

        SysPrinter.println(SysPrinterType.INFO, "verifing code...");
    }

    private void handleNewMessage(String msg) throws SocketMessageIsNotNewException {
        SocketMessage socketMessage = new SocketMessage();

        socketMessage.setType(SocketMessageType.NEWMESSAGE);

        JSONObject data = new JSONObject();
        data.put("msg", msg);

        socketMessage.setData(data);

        socketMessage.setAuthKey(authKey);

        socketClient.sendMessage(socketMessage);
    }
}
