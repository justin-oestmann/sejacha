package com.sejacha.server;

import java.sql.*;
import java.util.Scanner;

class Main {
    public static void main(String[] args) throws SQLException, Exception {

        System.out.println("Starting Server...");

        SocketServer socketServer = new SocketServer(4999);

        socketServer.start();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String data = scanner.nextLine();
            if (data == "close") {
                scanner.close();
                System.exit(0);
            }
        }

    }
}