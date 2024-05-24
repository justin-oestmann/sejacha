package com.sejacha.server;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.mail.MessagingException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sejacha.server.exceptions.UserInvalidStateException;

public class ServerClient extends Thread {
    private Socket socket;
    private List<ServerClient> clientList;
    private PrintWriter out;
    private BufferedReader in;

    private User user = null;

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

        SocketMessage socketMessage = new SocketMessage(input);

        if (this.user == null) {
            if (socketMessage.getType() == SocketMessageType.LOGIN) {
                this.user = new User();

                // this.user.login(socketMessage.getData().getString("email"),
                // socketMessage.getData().getString("passwordhash"));

            }

            if (socketMessage.getType() == SocketMessageType.REGISTER) {
                this.user = new User();

                this.user.setName(socketMessage.getData().getString("name"));
                this.user.setEmail(socketMessage.getData().getString("email"));
                this.user.setPasswordHash(socketMessage.getData().getString("passwordhash"));

                try {
                    this.user.sendVerificationCode();
                } catch (UserInvalidStateException ex) {
                    SysPrinter.println("ServerClient", "User has invalid state");
                    this.user = null;
                } catch (MessagingException ex) {
                    SysPrinter.println("ServerClient", "ERROR while sending verification-mail (" + ex + ")");
                    this.user = null;
                }

                if (this.user.create()) {
                    SysPrinter.println("ServerClient",
                            "User has created account successfully (" + this.user.getID() + ")");
                    return;
                }
                SysPrinter.println("ServerClient",
                        "User account creation failed");
                this.user = null;
                return;
            }

        } else {

        }

    }
}
