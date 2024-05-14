package com.sejacha.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient {

    private String serverAddress;
    private int port;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public SocketClient(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
    }

    public void connect() throws IOException {
        // Connect to the server
        socket = new Socket(serverAddress, port);
        System.out.println("Connected to server: " + serverAddress);

        // Initialize input and output streams
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void disconnect() {
        try {
            // Close all resources
            if (socket != null) {
                socket.close();
            }
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            System.out.println("Disconnected from server.");
        } catch (IOException e) {
            System.err.println("Error in disconnecting: " + e.getMessage());
        }
    }

    public void sendData(String data) {
        // Send data to the server
        if (out != null) {
            out.println(data);
        } else {
            System.err.println("Error: Output stream is not initialized.");
        }
    }

    public String receiveData() {
        // Receive data from the server
        try {
            if (in != null) {
                return in.readLine();
            } else {
                System.err.println("Error: Input stream is not initialized.");
            }
        } catch (IOException e) {
            System.err.println("Error in receiving data: " + e.getMessage());
        }
        return null;
    }

}
