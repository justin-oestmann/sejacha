package com.sejacha.server;

import java.net.*;
import java.io.*;

public class WebSocketServer {
    private int port;
    private ServerSocket serverSocket;

    public WebSocketServer() throws IOException {
        this.port = 4999;
        this.serverSocket = new ServerSocket(this.port);
    }

    public WebSocketServer(int port) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(this.port);
    }

}