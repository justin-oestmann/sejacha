package com.sejacha.server;

import java.io.*;
import java.net.*;

public class SocketServer {
    private int port;
    private ServerSocket serverSocket;
    private boolean running;

    public SocketServer() {
        try {
            this.port = 4999;
            this.serverSocket = new ServerSocket(this.port);
            running = true;
        } catch (IOException e) {
            System.err.println("Error: Unable to start server on port " + port);
            e.printStackTrace();
            running = false;
        }
    }

    public SocketServer(int port) {
        try {
            this.port = port;
            this.serverSocket = new ServerSocket(this.port);
            running = true;
        } catch (IOException e) {
            System.err.println("Error: Unable to start server on port " + port);
            e.printStackTrace();
            running = false;
        }
    }

    public void start() {
        if (!running) {
            System.err.println("Error: Server not running");
            return;
        }

        System.out.println("Waiting for clients to connect...");

        while (running) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                // Handle client in a new thread
                ClientHandler handler = new ClientHandler(clientSocket);
                new Thread(handler).start();

            } catch (IOException e) {
                System.err.println("Error accepting client connection");
                e.printStackTrace();
            }
        }
    }

}