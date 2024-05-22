package com.sejacha.server;

import java.security.SecureRandom;

public class RandomString {

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final String NUMBERS = "0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Generates Random String out of this available chars:
     * ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789
     * 
     * @param length
     * @return String of random generated chars
     */
    public static String generate(int length) {
        if (length < 1) {
            throw new IllegalArgumentException("Length must be a positive integer.");
        }

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }

        return sb.toString();
    }

    /**
     * Generates Random String out of this available chars:
     * 0123456789
     * 
     * @param length
     * @return String of random generated numbers
     */
    public static String generateNumberCode(int length) {
        if (length < 1) {
            throw new IllegalArgumentException("Length must be a positive integer.");
        }

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(NUMBERS.length());
            sb.append(NUMBERS.charAt(index));
        }

        return sb.toString();
    }
}
