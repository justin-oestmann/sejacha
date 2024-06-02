/**
 * The {@code ServerClient} class represents a client connected to the server. 
 * It manages communication with the client, handles incoming messages, and processes client requests.
 */
package com.sejacha.server;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.mail.MessagingException;
import org.json.JSONObject;

import com.sejacha.server.exceptions.UserInvalidStateException;

public class ServerClient extends Thread {
    private Socket socket;
    private List<ServerClient> clientList;
    private PrintWriter out;
    private BufferedReader in;
    private User user = null;

    /**
     * Constructs a {@code ServerClient} with the specified socket and client list.
     *
     * @param socket     the socket representing the client connection
     * @param clientList the list of server clients
     */
    public ServerClient(Socket socket, List<ServerClient> clientList) {
        this.socket = socket;
        this.clientList = clientList;
        try {
            SysPrinter.println("SocketClient",
                    "client connected (" + this.socket.getInetAddress() + ":" + this.socket.getPort() + ")");
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a message to the client.
     *
     * @param message the message to send
     */
    public void sendMessage(SocketMessage message) {
        out.println(message.toJSONString());
    }

    /**
     * Runs the server client thread, continuously listening for incoming messages.
     */
    public void run() {
        while (true) {
            try {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    this.handleMessages(inputLine);
                }
            } catch (SocketException e) {
                SysPrinter.println("SocketClient",
                        "client disconnected (" + this.socket.getInetAddress() + ":" + this.socket.getPort()
                                + ")(" + e.getMessage() + ")");
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

    /**
     * Handles incoming messages from the client.
     *
     * @param input the message received from the client
     * @throws Exception
     */
    private void handleMessages(String input)
            throws Exception {
        SocketMessage socketMessage = new SocketMessage(input);
        SysPrinter.println("debug", input);
        if (this.user == null) {
            if (socketMessage.getType() == SocketMessageType.LOGIN) {

                this.user = new User();

                if (!socketMessage.getData().has("email") || !socketMessage.getData().has("password")) {
                    SocketMessage returnMessage = new SocketMessage(null, SocketMessageType.LOGIN_RESPONSE_FAIL,
                            new JSONObject().put("reason", "email or password not set!"));
                    this.sendMessage(returnMessage);
                    this.user = null;

                    SysPrinter.println(socket, SocketMessageType.LOGIN.getNameOfType(), "invalid credentials");

                    return;
                }

                if (!this.user.loadByEmail(socketMessage.getData().getString("email"))) {
                    SocketMessage returnMessage = new SocketMessage(null, SocketMessageType.LOGIN_RESPONSE_FAIL,
                            new JSONObject().put("reason", "loading account failed!"));
                    this.sendMessage(returnMessage);
                    this.user = null;

                    SysPrinter.println(socket, SocketMessageType.LOGIN.getNameOfType(), "loading failed");

                    return;
                }

                if (!this.user.verifyPassword(socketMessage.getData().getString("password"))) {
                    SocketMessage returnMessage = new SocketMessage(null, SocketMessageType.LOGIN_RESPONSE_FAIL,
                            new JSONObject().put("reason", "email or password are invalid!"));
                    this.sendMessage(returnMessage);
                    this.user = null;

                    SysPrinter.println(socket, SocketMessageType.LOGIN.getNameOfType(), "password invalid");

                    return;
                }
                JSONObject returnJsonObject = new JSONObject();
                returnJsonObject.put("username", this.user.getName());
                returnJsonObject.put("authkey", this.user.generateAuthKey());

                SocketMessage returnMessage = new SocketMessage(null, SocketMessageType.LOGIN_RESPONSE_SUCCESS,
                        returnJsonObject);
                this.sendMessage(returnMessage);

                SysPrinter.println(socket, SocketMessageType.LOGIN.getNameOfType(), "successful");

                return;

            }

            if (socketMessage.getType() == SocketMessageType.REGISTER) {
                this.user = new User();
                this.user.setName(socketMessage.getData().getString("username"));
                this.user.setEmail(socketMessage.getData().getString("email"));
                this.user.setPasswordHash(socketMessage.getData().getString("password"));

                this.user.genID();

                try {
                    this.user.sendVerificationCode();
                } catch (UserInvalidStateException ex) {
                    SysPrinter.println("ServerClient", "User has invalid state");
                    this.user = null;
                    return;
                } catch (MessagingException ex) {
                    SysPrinter.println("ServerClient", "ERROR while sending verification-mail (" + ex + ")");
                    this.user = null;
                    return;
                }

                if (!this.user.create()) {
                    SysPrinter.println("ServerClient", "User account creation failed");
                    this.user = null;
                    this.sendMessage(new SocketMessage(null, SocketMessageType.REGISTER_RESPONSE_FAIL, null));
                    return;
                }
                SysPrinter.println("ServerClient", "User account created successfully (" + this.user.getID() + ")");

                this.sendMessage(new SocketMessage(null, SocketMessageType.REGISTER_RESPONSE_SUCCESS, null));

                return;
            }

            if (socketMessage.getType() == SocketMessageType.PING) {
                SysPrinter.println(this.socket, "ServerClient", "ping");
                SocketMessage returnMessage = new SocketMessage(null, SocketMessageType.PING, null);
                this.sendMessage(returnMessage);
            }
        } else {
            if (socketMessage.getType() == SocketMessageType.LOGOUT) {

                if (this.user.verifyAuthKey(socketMessage.getAuthKey())) {

                    this.user = null;

                    SocketMessage returnMessage = new SocketMessage(null, SocketMessageType.LOGOUT_RESPONSE_SUCCESS,
                            null);
                    this.sendMessage(returnMessage);
                    SysPrinter.println(socket, SocketMessageType.LOGOUT.getNameOfType(), "successful");
                    return;
                }
                SocketMessage returnMessage = new SocketMessage(null, SocketMessageType.LOGOUT_RESPONSE_FAIL,
                        null);
                this.sendMessage(returnMessage);
                SysPrinter.println(socket, SocketMessageType.LOGOUT.getNameOfType(), "failed");
                return;
            }
        }
    }
}
