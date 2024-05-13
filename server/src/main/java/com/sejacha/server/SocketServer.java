package com.sejacha.server;

import java.net.*;
import java.io.*;

public class SocketServer {
    private int port;
    private ServerSocket serverSocket;

    public SocketServer() throws IOException {
        this.port = 4999;
        this.serverSocket = new ServerSocket(this.port);
    }

    public SocketServer(int port) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(this.port);
    }

}