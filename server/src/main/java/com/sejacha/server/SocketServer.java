/**
 * The {@code SocketServer} class represents a server that listens for incoming socket connections on a specified port.
 * It manages a list of connected clients and handles client connections.
 */
package com.sejacha.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    private ServerSocket serverSocket;
    private int port;
    private List<ServerClient> clients = new ArrayList<>();

    /**
     * Constructs a {@code SocketServer} with the default port 4999.
     */
    public SocketServer() {
        this.port = 4999;
    }

    /**
     * Constructs a {@code SocketServer} with the specified port.
     *
     * @param port the port on which the server will listen for connections
     */
    public SocketServer(int port) {
        this.port = port;
    }

    /**
     * Constructs a {@code SocketServer} with the specified port and starts it if
     * {@code directStart} is true.
     *
     * @param port        the port on which the server will listen for connections
     * @param directStart if true, the server starts immediately
     * @throws IOException if an I/O error occurs when starting the server
     */
    public SocketServer(int port, boolean directStart) throws IOException {
        this.port = port;
        if (directStart) {
            this.start();
        }
    }

    /**
     * Starts the server and begins listening for incoming connections.
     *
     * @throws IOException if an I/O error occurs when starting the server
     */
    public void start() throws IOException {
        if (this.serverSocket != null) {
            SysPrinter.println("SocketServer", "Server is already running");
            return;
        }

        this.serverSocket = new ServerSocket(this.port);
        SysPrinter.println("SocketServer", "Server started!");
        handleServer(this.serverSocket);
    }

    /**
     * Returns the list of connected clients.
     *
     * @return the list of connected clients
     */
    public List<ServerClient> getClients() {
        return this.clients;
    }

    /**
     * Handles incoming client connections.
     *
     * @param serverSocket the server socket
     * @return a {@code Runnable} that continuously accepts and handles client
     *         connections
     */
    private Runnable handleServer(ServerSocket serverSocket) {
        SysPrinter.println("SocketClient", "starting handling clients...");
        while (true) {
            try {
                Socket socket = serverSocket.accept();

                ServerClient client = new ServerClient(socket, this.clients);

                clients.add(client);
                client.start();

            } catch (Exception ex) {

            }
        }

    }
}
