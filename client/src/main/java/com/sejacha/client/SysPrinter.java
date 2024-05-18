package com.sejacha.client;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SysPrinter {
    public static void println(String weight, String message) {
        System.out.println(weight + " | " + message);
        return;
    }
}
