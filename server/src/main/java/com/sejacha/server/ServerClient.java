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
        try {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                this.handleMessages(inputLine);
                // Handle incoming messages from client as needed
            }
        } catch (SocketException e) {
            SysPrinter.println("SocketClient", "client disconnected (" + e.getMessage() + ")");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                this.clientList.remove(this);
            } catch (IOException e) {
                e.printStackTrace();
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

                default:
                    break;
            }

            System.out.println(input);
        } catch (Exception ex) {

        }

    }
}
