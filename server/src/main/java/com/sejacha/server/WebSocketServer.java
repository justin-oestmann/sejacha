package com.sejacha.server;

import java.net.*;
import java.io.*;

public class WebSocketServer{
    private int port;
    private ServerSocket serverSocket;


    public WebSocketServer(int port = 4999) {
        this.port = port;
        this.serverSocket = new ServerSocket(this.port);
        
    }





}