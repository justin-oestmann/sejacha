package com.sejacha.client;

import java.io.*;
import java.net.*;

import org.json.JSONObject;

import com.sejacha.client.exceptions.SocketMessageIsNotNewException;

public class SocketClient {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private SocketClientResponse socketClientResponse;

    // public SocketClient(String serverAddress, int serverPort) {
    // this.run(serverAddress, serverPort);
    // }

    public SocketClient(String serverAddress, int port, SocketClientResponse socketClientResponse) {
        this.socketClientResponse = socketClientResponse;
        this.run(serverAddress, port);
    }

    public boolean isConnected() {
        if (socket == null) {
            return false;
        }

        return socket.isConnected();
    }

    private void run(String serverAddress, int serverPort) {
        try {
            socket = new Socket(serverAddress, serverPort);
            SysPrinter.println(SysPrinterType.INFO, "Connecting to " + serverAddress + ":" + serverPort);

            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Start a thread to handle incoming messages from the server
            new Thread(() -> {
                while (true) {
                    try {
                        String serverResponse;
                        while ((serverResponse = in.readLine()) != null) {
                            this.handleResponses(serverResponse);
                        }

                    } catch (SocketException ex) {

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

            }).start();

        } catch (java.net.ConnectException e) {
            SysPrinter.println(SysPrinterType.INFO,
                    "Connection to " + serverAddress + ":" + serverPort + " failed/disconnected");
            return;
        }

        catch (IOException e) {
            SysPrinter.println(SysPrinterType.INFO,
                    "Connection to " + serverAddress + ":" + serverPort + " failed/disconnected");
            return;
        }
    }

    public void sendMessage(SocketMessage socketMessage) {
        out.println(socketMessage.toJSONString());
    }

    private void handleResponses(String responseString) {
        // SysPrinter.println("DataString", responseString);
        SocketMessage socketMessage = new SocketMessage(responseString);

        switch (socketMessage.getType()) {

            case VERIFY_RESPONSE_SUCCESS:
                this.onVerifySuccess(socketMessage);
                break;

            case VERIFY_RESPONSE_FAIL:
                this.onVerifyFail(socketMessage);
                break;

            case LOGIN_RESPONSE_SUCCESS:
                this.onLoginSuccess(socketMessage);
                break;

            case LOGIN_RESPONSE_FAIL:
                this.onLoginFail(socketMessage);
                break;

            case REGISTER_RESPONSE_SUCCESS:
                this.onRegisterSuccess(socketMessage);
                break;

            case REGISTER_RESPONSE_FAIL:
                this.onRegisterFail(socketMessage);
                break;

            case LOGOUT_RESPONSE_SUCCESS:
                this.onLogoutSuccess(socketMessage);
                break;

            case LOGOUT_RESPONSE_FAIL:
                this.onLogoutFail(socketMessage);
                break;

            case NEWMESSAGE_RESPONSE_SUCCESS:
                this.onNewMessageSuccess(socketMessage);
                break;

            case NEWMESSAGE_RESPONSE_FAIL:
                this.onNewMessageFail(socketMessage);
                break;

            case ROOM_JOIN_RESPONSE_SUCCESS:
                this.onRoomJoinSuccess(socketMessage);
                break;

            case ROOM_JOIN_RESPONSE_FAIL:
                this.onRoomJoinFail(socketMessage);
                break;

            case ROOM_JOIN_W_PASSWORD_RESPONSE_SUCCESS:
                this.onRoomJoinWPasswordSuccess(socketMessage);
                break;

            case ROOM_JOIN_W_PASSWORD_RESPONSE_FAIL:
                this.onRoomJoinWPasswordFail(socketMessage);
                break;

            case ROOM_LEAVE_RESPONSE_SUCCESS:
                this.onRoomLeaveSuccess(socketMessage);
                break;

            case ROOM_LEAVE_RESPONSE_FAIL:
                this.onRoomLeaveFail(socketMessage);
                break;

            case ROOM_CREATE_RESPONSE_SUCCESS:
                this.onRoomCreateSuccess(socketMessage);
                break;

            case ROOM_CREATE_RESPONSE_FAIL:
                this.onRoomCreateFail(socketMessage);
                break;

            case ROOM_GETINFO_RESPONSE_SUCCESS:
                this.onRoomGetInfoSuccess(socketMessage);
                break;

            case ROOM_GETINFO_RESPONSE_FAIL:
                this.onRoomGetInfoFail(socketMessage);
                break;

            case ROOM_INVITE_CONTACT_RESPONSE_SUCCESS:
                this.onRoomInviteContactSuccess(socketMessage);
                break;

            case ROOM_INVITE_CONTACT_RESPONSE_FAIL:
                this.onRoomInviteContactFail(socketMessage);
                break;

            case CONTACT_ADD_RESPONSE_SUCCESS:
                this.onContactAddSuccess(socketMessage);
                break;

            case CONTACT_ADD_RESPONSE_FAIL:
                this.onContactAddFail(socketMessage);
                break;

            case CONTACT_REMOVE_RESPONSE_SUCCESS:
                this.onContactRemoveSuccess(socketMessage);
                break;

            case CONTACT_REMOVE_RESPONSE_FAIL:
                this.onContactRemoveFail(socketMessage);
                break;

            case CONTACT_CREATE_DM_ROOM_RESPONSE_SUCCESS:
                this.onContactCreateDMRoomSuccess(socketMessage);
                break;

            case CONTACT_CREATE_DM_ROOM_RESPONSE_FAIL:
                this.onContactCreateDMRoomFail(socketMessage);
                break;

            case PING:
                this.onPing(socketMessage);
                break;

            case NOTIFY:
                this.onNotify(socketMessage);
                break;

            default:
                SysPrinter.println("Server", "sent invalid message");
                break;
        }
    }

    public void login(String email, String password) throws SocketMessageIsNotNewException {
        SocketMessage socketMessage = new SocketMessage();

        socketMessage.setType(SocketMessageType.LOGIN);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", email);
        jsonObject.put("password", Crypt.calculateSHA512(password));

        socketMessage.setData(jsonObject);

        this.sendMessage(socketMessage);
    }

    public void logout(String authKey) throws SocketMessageIsNotNewException {
        SocketMessage socketMessage = new SocketMessage();

        socketMessage.setType(SocketMessageType.LOGOUT);
        socketMessage.setAuthKey(authKey);

        this.sendMessage(socketMessage);
    }

    public void register(String username, String email, String password) throws SocketMessageIsNotNewException {
        SocketMessage socketMessage = new SocketMessage();

        socketMessage.setType(SocketMessageType.REGISTER);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("email", email);
        jsonObject.put("password", Crypt.calculateSHA512(password));

        socketMessage.setData(jsonObject);

        this.sendMessage(socketMessage);
    }

    private void onVerifySuccess(SocketMessage socketMessage) {
        socketClientResponse.onVerifySuccess(socketMessage);
    }

    private void onVerifyFail(SocketMessage socketMessage) {
        socketClientResponse.onVerifyFail(socketMessage);
    }

    private void onLoginSuccess(SocketMessage socketMessage) {
        socketClientResponse.onLoginSuccess(socketMessage);
    }

    private void onLoginFail(SocketMessage socketMessage) {
        socketClientResponse.onLoginFail(socketMessage);
    }

    private void onRegisterSuccess(SocketMessage socketMessage) {
        socketClientResponse.onRegisterSuccess(socketMessage);
    }

    private void onRegisterFail(SocketMessage socketMessage) {
        socketClientResponse.onRegisterFail(socketMessage);
    }

    private void onLogoutSuccess(SocketMessage socketMessage) {
        socketClientResponse.onLogoutSuccess(socketMessage);
    }

    private void onLogoutFail(SocketMessage socketMessage) {
        socketClientResponse.onLogoutFail(socketMessage);
    }

    private void onNewMessageSuccess(SocketMessage socketMessage) {
        socketClientResponse.onNewMessageSuccess(socketMessage);
    }

    private void onNewMessageFail(SocketMessage socketMessage) {
        socketClientResponse.onNewMessageFail(socketMessage);
    }

    private void onRoomJoinSuccess(SocketMessage socketMessage) {
        socketClientResponse.onRoomJoinSuccess(socketMessage);
    }

    private void onRoomJoinFail(SocketMessage socketMessage) {
        socketClientResponse.onRoomJoinFail(socketMessage);
    }

    private void onRoomJoinWPasswordSuccess(SocketMessage socketMessage) {
        socketClientResponse.onRoomJoinWPasswordSuccess(socketMessage);
    }

    private void onRoomJoinWPasswordFail(SocketMessage socketMessage) {
        socketClientResponse.onRoomJoinWPasswordFail(socketMessage);
    }

    private void onRoomLeaveSuccess(SocketMessage socketMessage) {
        socketClientResponse.onRoomLeaveSuccess(socketMessage);
    }

    private void onRoomLeaveFail(SocketMessage socketMessage) {
        socketClientResponse.onRoomLeaveFail(socketMessage);
    }

    private void onRoomCreateSuccess(SocketMessage socketMessage) {
        socketClientResponse.onRoomCreateSuccess(socketMessage);
    }

    private void onRoomCreateFail(SocketMessage socketMessage) {
        socketClientResponse.onRoomCreateFail(socketMessage);
    }

    private void onRoomGetInfoSuccess(SocketMessage socketMessage) {
        socketClientResponse.onRoomGetInfoSuccess(socketMessage);
    }

    private void onRoomGetInfoFail(SocketMessage socketMessage) {
        socketClientResponse.onRoomGetInfoFail(socketMessage);
    }

    private void onRoomInviteContactSuccess(SocketMessage socketMessage) {
        socketClientResponse.onRoomInviteContactSuccess(socketMessage);
    }

    private void onRoomInviteContactFail(SocketMessage socketMessage) {
        socketClientResponse.onRoomInviteContactFail(socketMessage);
    }

    private void onContactAddSuccess(SocketMessage socketMessage) {
        socketClientResponse.onContactAddSuccess(socketMessage);
    }

    private void onContactAddFail(SocketMessage socketMessage) {
        socketClientResponse.onContactAddFail(socketMessage);
    }

    private void onContactRemoveSuccess(SocketMessage socketMessage) {
        socketClientResponse.onContactRemoveSuccess(socketMessage);
    }

    private void onContactRemoveFail(SocketMessage socketMessage) {
        socketClientResponse.onContactRemoveFail(socketMessage);
    }

    private void onContactCreateDMRoomSuccess(SocketMessage socketMessage) {
        socketClientResponse.onContactCreateDMRoomSuccess(socketMessage);
    }

    private void onContactCreateDMRoomFail(SocketMessage socketMessage) {
        socketClientResponse.onContactCreateDMRoomFail(socketMessage);
    }

    private void onPing(SocketMessage socketMessage) {
        socketClientResponse.onPing(socketMessage);
    }

    private void onNotify(SocketMessage socketMessage) {
        socketClientResponse.onNotify(socketMessage);
    }
}
