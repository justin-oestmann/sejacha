package com.sejacha.server;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class GroupCrypt {

    /*
     * public static void main(String[] args) throws Exception {
     * // Beispiel für Schlüsselerzeugung für drei Gruppenmitglieder
     * KeyPair member1 = generateKeyPair();
     * KeyPair member2 = generateKeyPair();
     * KeyPair member3 = generateKeyPair();
     * 
     * // Gruppenschlüssel generieren
     * SecretKey groupKey = generateSymmetricKey();
     * 
     * // Gruppenschlüssel für jedes Mitglied verschlüsseln
     * Map<PublicKey, String> encryptedGroupKeys = new HashMap<>();
     * encryptedGroupKeys.put(member1.getPublic(), encryptGroupKey(groupKey,
     * member1.getPublic()));
     * encryptedGroupKeys.put(member2.getPublic(), encryptGroupKey(groupKey,
     * member2.getPublic()));
     * encryptedGroupKeys.put(member3.getPublic(), encryptGroupKey(groupKey,
     * member3.getPublic()));
     * 
     * // Beispielnachricht
     * String message = "Diese Nachricht ist geheim.";
     * 
     * // Nachricht verschlüsseln
     * String encryptedMessage = encryptMessage(message, groupKey);
     * System.out.println("Verschlüsselte Nachricht: " + encryptedMessage);
     * 
     * // Nachricht für jedes Mitglied entschlüsseln
     * for (KeyPair member : new KeyPair[] { member1, member2, member3 }) {
     * SecretKey decryptedGroupKey =
     * decryptGroupKey(encryptedGroupKeys.get(member.getPublic()),
     * member.getPrivate());
     * String decryptedMessage = decryptMessage(encryptedMessage,
     * decryptedGroupKey);
     * System.out.println("Entschlüsselte Nachricht für Mitglied: " +
     * decryptedMessage);
     * }
     * }
     */

    // Generiert ein asymmetrisches Schlüsselpaar
    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }

    // Generiert einen symmetrischen Schlüssel
    public static SecretKey generateSymmetricKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        return keyGen.generateKey();
    }

    // Verschlüsselt den Gruppenschlüssel mit einem öffentlichen Schlüssel
    public static String encryptGroupKey(SecretKey groupKey, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedKey = cipher.doFinal(groupKey.getEncoded());
        return Base64.getEncoder().encodeToString(encryptedKey);
    }

    // Entschlüsselt den Gruppenschlüssel mit einem privaten Schlüssel
    public static SecretKey decryptGroupKey(String encryptedGroupKey, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedKey = cipher.doFinal(Base64.getDecoder().decode(encryptedGroupKey));
        return new SecretKeySpec(decryptedKey, "AES");
    }

    // Verschlüsselt eine Nachricht mit einem symmetrischen Schlüssel
    public static String encryptMessage(String message, SecretKey groupKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, groupKey);
        byte[] encryptedBytes = cipher.doFinal(message.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Entschlüsselt eine Nachricht mit einem symmetrischen Schlüssel
    public static String decryptMessage(String encryptedMessage, SecretKey groupKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, groupKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedMessage));
        return new String(decryptedBytes);
    }
}
