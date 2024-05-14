package com.sejacha.server;

import java.sql.*;

class Main {
    public static void main(String[] args) throws SQLException, Exception {
        System.out.println("starting socket...");

        SocketServer serverSocket = new SocketServer();
        serverSocket.start();

    }
}