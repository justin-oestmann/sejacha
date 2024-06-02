/**
 * The {@code SysPrinter} class provides utility methods for printing formatted log messages to the console.
 * It supports logging messages with a timestamp, source, and optionally information about a socket or an exception.
 */
package com.sejacha.server;

import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SysPrinter {

    /**
     * Prints a log message with a timestamp and source to the console.
     *
     * @param source  the source of the message
     * @param message the message to be logged
     */
    public static void println(String source, String message) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(time + " | " + source + " |-> " + message);
    }

    /**
     * Prints a log message with a timestamp, source, and socket information to the
     * console.
     *
     * @param socket  the socket associated with the message
     * @param source  the source of the message
     * @param message the message to be logged
     */
    public static void println(Socket socket, String source, String message) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(
                time + " | " + socket.getInetAddress() + ":" + socket.getPort() + " | " + source + " |-> " + message);
    }

    /**
     * Prints a log message with a timestamp and exception details to the console.
     *
     * @param exception the exception to be logged
     */
    public static void println(Exception exception) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(time + " | EXCEPTION! |-> " + exception.getMessage());
    }
}
