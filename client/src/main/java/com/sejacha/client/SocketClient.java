package com.sejacha.client;

import java.io.*;
import java.net.*;
import java.util.*;

public class SocketClient {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String authKey;
    private SocketClientResponse socketClientResponse;

    // public SocketClient(String serverAddress, int serverPort) {
    // this.run(serverAddress, serverPort);
    // }

    public SocketClient(String serverAddress, int port, SocketClientResponse socketClientResponse) {
        this.socketClientResponse = socketClientResponse;
        this.run(serverAddress, port);
    }

    private void run(String serverAddress, int serverPort) {
        try {
            socket = new Socket(serverAddress, serverPort);
            System.out.println("Connected to server at " + serverAddress + ":" + serverPort);

            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Start a thread to handle incoming messages from the server
            new Thread(() -> {
                try {
                    String serverResponse;
                    while ((serverResponse = in.readLine()) != null) {
                        this.handleResponses(serverResponse);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String message) {

    }

    public void sendMessage(SocketMessage socketMessage) {
        out.println(socketMessage.toJSONString());
    }

    private void handleResponses(String responseString) {
        SocketMessage socketMessage = new SocketMessage(responseString);

        switch (socketMessage.getType()) {
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
                this.onLoginFail(socketMessage);
                break;

            default:
                SysPrinter.println("Server", "sent invalid message");
                break;
        }
    }

    public void login() {

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
}
