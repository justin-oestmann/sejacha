package com.sejacha.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.io.*;
import java.net.*;
import java.util.*;

public class SocketServer {

    private ServerSocket serverSocket;
    private int port;
    private List<ServerClient> clients = new ArrayList<>();

    public SocketServer() {
        this.port = 4999;
    }

    public SocketServer(int port) {
        this.port = port;
    }

    public SocketServer(int port, boolean directStart) throws IOException {
        this.port = port;
        this.start();
    }

    public void start() throws IOException {
        if (this.serverSocket != null) {
            SysPrinter.println("SocketClient", "server is already running");
            return;
        }

        ServerSocket ss = new ServerSocket(this.port);

        this.serverSocket = ss;
        SysPrinter.println("SocketClient", "server started!");
        handleServer(this.serverSocket);

    }

    public List<ServerClient> getClients() {
        return this.clients;
    }

    private Runnable handleServer(ServerSocket serverSocket) {
        SysPrinter.println("SocketClient", "starting handling clients...");
        while (true) {
            try {
                Socket socket = serverSocket.accept();

                SysPrinter.println("ServerSocket",
                        "Client connected (" + socket.getInetAddress() + ":" + socket.getPort()
                                + ")");

                ServerClient client = new ServerClient(socket, this.clients);

                clients.add(client);
                client.start();

            } catch (Exception ex) {

            }
        }

    }

}
