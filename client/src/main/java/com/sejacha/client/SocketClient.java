package com.sejacha.client;

import java.net.*;
import java.io.*;

public class SocketClient {
    private int port = 4999;
    private String host;     
    private Socket clientSocket;

    public SocketClient(int port, String host) throws IOException {
        this.port = port;
        this.clientSocket = new Socket(this.host, this.port);
    }

}