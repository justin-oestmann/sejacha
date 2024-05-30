package com.sejacha.server;

import java.sql.*;
import java.util.Scanner;

/**
 * Entry point for the server application.
 */
class Main {
    /**
     * The main method, starting point of the server.
     *
     * @param args command-line arguments (not used)
     * @throws SQLException if a database access error occurs
     * @throws Exception    if any other error occurs
     */
    public static void main(String[] args) throws SQLException, Exception {

        System.out.println("Starting Server...");

        // Create and start the socket server
        SocketServer socketServer = new SocketServer(4999);
        socketServer.start();

        // Wait for user input to close the server
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String data = scanner.nextLine();
            if (data.equals("close")) {
                scanner.close();
                System.exit(0);
            }
        }
    }
}
