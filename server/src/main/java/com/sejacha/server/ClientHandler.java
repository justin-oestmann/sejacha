package com.sejacha.server;

import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received from client: " + inputLine);
                out.println("Server received: " + inputLine);

                if (inputLine.equals("bye"))
                    break;
            }
            System.out.println("Client disconnected: " + clientSocket);
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error handling client");
            e.printStackTrace();
        }
    }
}
