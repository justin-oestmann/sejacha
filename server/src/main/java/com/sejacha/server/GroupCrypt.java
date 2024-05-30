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

/**
 * The {@code GroupCrypt} class provides methods for generating asymmetric and
 * symmetric keys,
 * encrypting and decrypting group keys, as well as encrypting and decrypting
 * messages using
 * symmetric encryption.
 */
public class GroupCrypt {

    /**
     * Generates an asymmetric key pair using RSA algorithm.
     *
     * @return the generated {@code KeyPair}
     * @throws Exception if an error occurs during key pair generation
     */
    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }

    /**
     * Generates a symmetric key using AES algorithm.
     *
     * @return the generated {@code SecretKey}
     * @throws Exception if an error occurs during symmetric key generation
     */
    public static SecretKey generateSymmetricKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        return keyGen.generateKey();
    }

    /**
     * Encrypts the group key using a public key.
     *
     * @param groupKey  the group key to encrypt
     * @param publicKey the public key used for encryption
     * @return the encrypted group key as a Base64 encoded string
     * @throws Exception if an error occurs during encryption
     */
    public static String encryptGroupKey(SecretKey groupKey, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedKey = cipher.doFinal(groupKey.getEncoded());
        return Base64.getEncoder().encodeToString(encryptedKey);
    }

    /**
     * Decrypts the group key using a private key.
     *
     * @param encryptedGroupKey the encrypted group key as a Base64 encoded string
     * @param privateKey        the private key used for decryption
     * @return the decrypted group key
     * @throws Exception if an error occurs during decryption
     */
    public static SecretKey decryptGroupKey(String encryptedGroupKey, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedKey = cipher.doFinal(Base64.getDecoder().decode(encryptedGroupKey));
        return new SecretKeySpec(decryptedKey, "AES");
    }

    /**
     * Encrypts a message using a symmetric key.
     *
     * @param message  the message to encrypt
     * @param groupKey the symmetric key used for encryption
     * @return the encrypted message as a Base64 encoded string
     * @throws Exception if an error occurs during encryption
     */
    public static String encryptMessage(String message, SecretKey groupKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, groupKey);
        byte[] encryptedBytes = cipher.doFinal(message.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * Decrypts a message using a symmetric key.
     *
     * @param encryptedMessage the encrypted message as a Base64 encoded string
     * @param groupKey         the symmetric key used for decryption
     * @return the decrypted message
     * @throws Exception if an error occurs during decryption
     */
    public static String decryptMessage(String encryptedMessage, SecretKey groupKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, groupKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedMessage));
        return new String(decryptedBytes);
    }
}
