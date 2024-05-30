/**
 * The {@code RandomString} class provides methods for generating random strings.
 */
package com.sejacha.server;

import java.security.SecureRandom;

public class RandomString {

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String NUMBERS = "0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Generates a random string consisting of alphanumeric characters.
     * 
     * @param length the length of the random string to generate
     * @return a random string of the specified length
     * @throws IllegalArgumentException if the length is less than 1
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
     * Generates a random string consisting of numeric characters.
     * 
     * @param length the length of the random string to generate
     * @return a random string of numeric characters of the specified length
     * @throws IllegalArgumentException if the length is less than 1
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
