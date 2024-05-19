package com.sejacha.server;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;

/**
 * Eine Klasse, die verschiedene kryptografische Funktionen implementiert.
 */
public class Crypt {

    /**
     * Vergleicht zwei Strings anhand ihres SHA-512-Hashwerts.
     * 
     * @param str1 Der erste String.
     * @param str2 Der zweite String.
     * @return true, wenn die Hashwerte der beiden Strings übereinstimmen, ansonsten
     *         false.
     */
    public static boolean compareSHA512(String str1, String str2) {
        String hash1 = calculateSHA512(str1);
        String hash2 = calculateSHA512(str2);
        return hash1.equals(hash2);
    }

    /**
     * Berechnet den SHA-512-Hashwert eines Strings.
     * 
     * @param input Der Eingabestring.
     * @return Der SHA-512-Hashwert des Eingabestrings als Hexadezimalzeichenfolge.
     */
    public static String calculateSHA512(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hashBytes = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Verschlüsselt einen String mit einem öffentlichen Schlüssel.
     * 
     * @param input     Der zu verschlüsselnde String.
     * @param publicKey Der öffentliche Schlüssel.
     * @return Der verschlüsselte String.
     */
    public static byte[] encryptWithPublicKey(String input, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(input.getBytes());
    }

    /**
     * Entschlüsselt einen verschlüsselten String mit einem privaten Schlüssel.
     * 
     * @param encryptedData Die verschlüsselten Daten.
     * @param privateKey    Der private Schlüssel.
     * @return Der entschlüsselte String.
     */
    public static String decryptWithPrivateKey(byte[] encryptedData, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedData);
        return new String(decryptedBytes);
    }

    /**
     * Generiert ein Schlüsselpaar für asymmetrische Verschlüsselung.
     * 
     * @return Ein KeyPair-Objekt mit einem öffentlichen und einem privaten
     *         Schlüssel.
     */
    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }
}
