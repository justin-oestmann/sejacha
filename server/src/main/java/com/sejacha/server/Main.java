package com.sejacha.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.*;
import java.util.Scanner;

class Main {
    public static void main(String[] args) throws SQLException, Exception {

        System.out.println("Starting Server...");

        SocketServer ss = new SocketServer(4999, true);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String data = scanner.nextLine();
            if (data == "ping") {
                for (ServerClient client : ss.getClients()) {
                    client.sendMessage("ping!");
                }
            }
            if (data == "close") {
                scanner.close();
                System.exit(0);
            }
        }

    }
}