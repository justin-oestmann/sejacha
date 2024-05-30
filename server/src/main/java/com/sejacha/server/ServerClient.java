package com.sejacha.server;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.mail.MessagingException;

import org.json.JSONObject;

import com.sejacha.server.exceptions.MissingParameterException;
import com.sejacha.server.exceptions.SocketMessageIsNotNewException;
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

    public void sendMessage(SocketMessage message) {
        out.println(message.toJSONString());
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
            } catch (Exception e) {
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

    private void handleMessages(String input) throws SocketMessageIsNotNewException, MissingParameterException {

        SocketMessage socketMessage = new SocketMessage(input);

        if (this.user == null) {
            if (socketMessage.getType() == SocketMessageType.LOGIN) {

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

            if (socketMessage.getType() == SocketMessageType.PING) {
                SysPrinter.println(this.socket, "ServerClient", "ping");

                // this.user.login(socketMessage.getData().getString("email"),
                // socketMessage.getData().getString("passwordhash"));

            }

        } else {

        }

    }
}
