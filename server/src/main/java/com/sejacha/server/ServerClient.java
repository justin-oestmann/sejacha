package com.sejacha.server;

import java.io.*;
import java.net.*;
import java.util.*;

import org.json.JSONObject;

public class ServerClient extends Thread {
    private Socket socket;
    private List<ServerClient> clientList;
    private PrintWriter out;
    private BufferedReader in;
    private int authTries = 0;
    private User user;

    public ServerClient(Socket socket, List<ServerClient> clientList) {
        this.socket = socket;
        this.clientList = clientList;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public void run() {
        while (true) {
            try {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    this.handleMessages(inputLine);
                    if (authTries > 4) {
                        SysPrinter.println("SocketClient",
                                "Client reached maximum auth tries (" + this.socket.getInetAddress() + ")");
                        socket.close();
                        this.clientList.remove(this);
                    }
                }
            } catch (SocketException e) {
                SysPrinter.println("SocketClient",
                        "Client disconnected (" + this.socket.getInetAddress() + ")(" + e.getMessage() + ")");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                    this.clientList.remove(this);
                    return;
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }
    }

    private void handleMessages(String input) {

        try {

            JSONObject jsonObject = new JSONObject(input);

            switch (jsonObject.getString("exec")) {
                case "send":
                    SysPrinter.println("ServerClient", "exec command from " + this.socket.getInetAddress());
                    break;
                case "auth":
                    SysPrinter.println("ServerClient", "Client sent auth request " + this.socket.getInetAddress());
                    this.user = new User();
                    user.login(jsonObject.getString("username"), jsonObject.getString("password"));

                    break;

                default:
                    break;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
