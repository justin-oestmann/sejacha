package com.sejacha.client;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SysPrinter {

    protected static boolean cursor_double = false;

    public static void setCursorDouble(boolean state) {
        cursor_double = state;
    }

    public static void clear() {
        System.out.println(" clear ");
        for (int i = 0; i < 50; ++i) {
            System.out.println(" ");
        }
    }

    public static void println(String weight, String message) {
        System.out.println(weight + " | " + message);
        return;
    }

    public static void println(SysPrinterType type, String message) {
        System.out.println(type.getNameOfType() + " | " + message);
        return;
    }

    public static void printCursor() {
        if (cursor_double) {
            System.out.print(">> ");
            return;
        }
        System.out.print("> ");
        return;
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
