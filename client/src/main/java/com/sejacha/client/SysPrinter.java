package com.sejacha.client;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SysPrinter {
    public static void println(String weight, String message) {
        System.out.println(weight + " | " + message);
        return;
    }

    public static void println(SysPrinterType type, String message) {
        System.out.println(type.getNameOfType() + " | " + message);
        return;
    }

    public static void printCursor() {
        System.out.print("> ");
        return;
    }

}
