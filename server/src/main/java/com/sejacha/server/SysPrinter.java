package com.sejacha.server;

import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SysPrinter {
    public static void println(String source, String message) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(time + " | " + source + " |-> " + message);
        return;
    }

    public static void println(Socket socket, String source, String message) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(
                time + " | " + source + " " + socket.getInetAddress() + ":" + socket.getPort() + " |-> " + message);
        return;
    }

    public static void println(Exception exception) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(time + " | EXCEPTION! |-> " + exception.getMessage());
        return;
    }
}
