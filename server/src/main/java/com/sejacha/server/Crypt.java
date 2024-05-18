package com.sejacha.server;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;

public class Crypt {

    // Constants for password hashing
    private static final String HASH_ALGORITHM = "SHA-512";
    private static final int SALT_LENGTH = 16;

    // Method to hash a password with a salt
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);

        MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
        digest.update(salt);
        byte[] hashedPassword = digest.digest(password.getBytes());

        byte[] saltedHash = new byte[salt.length + hashedPassword.length];
        System.arraycopy(salt, 0, saltedHash, 0, salt.length);
        System.arraycopy(hashedPassword, 0, saltedHash, salt.length, hashedPassword.length);

        return Base64.getEncoder().encodeToString(saltedHash);
    }

    // Method to verify a password against a stored hash
    public static boolean verifyPassword(String password, String storedHash) throws NoSuchAlgorithmException {
        byte[] saltedHash = Base64.getDecoder().decode(storedHash);
        byte[] salt = Arrays.copyOfRange(saltedHash, 0, SALT_LENGTH);
        byte[] storedPasswordHash = Arrays.copyOfRange(saltedHash, SALT_LENGTH, saltedHash.length);

        MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
        digest.update(salt);
        byte[] hashedPassword = digest.digest(password.getBytes());

        return Arrays.equals(storedPasswordHash, hashedPassword);
    }

    // Constants for end-to-end encryption
    private static final String ENCRYPTION_ALGORITHM = "AES";

    // Method to generate a new AES key
    public static SecretKey generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance(ENCRYPTION_ALGORITHM);
        keyGen.init(256);
        return keyGen.generateKey();
    }

    // Method to encrypt a string with a given AES key
    public static String encrypt(String data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    // Method to decrypt a string with a given AES key
    public static String decrypt(String encryptedData, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedData);
    }

    // RSA key pair generation
    public static KeyPair generateRSAKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }

    // Encrypt the AES key with the RSA public key
    public static String encryptKey(SecretKey aesKey, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedKey = cipher.doFinal(aesKey.getEncoded());
        return Base64.getEncoder().encodeToString(encryptedKey);
    }

    // Decrypt the AES key with the RSA private key
    public static SecretKey decryptKey(String encryptedKey, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decodedKey = Base64.getDecoder().decode(encryptedKey);
        byte[] decryptedKey = cipher.doFinal(decodedKey);
        return new SecretKeySpec(decryptedKey, ENCRYPTION_ALGORITHM);
    }

    /*
     * public static void main(String[] args) {
     * try {
     * // Test password hashing and verification
     * String password = "mySecurePassword";
     * String hashedPassword = hashPassword(password);
     * System.out.println("Hashed Password: " + hashedPassword);
     * System.out.println("Password Verified: " + verifyPassword(password,
     * hashedPassword));
     * 
     * // Test end-to-end encryption with key exchange
     * String message = "This is a secret message.";
     * 
     * // Generate AES key
     * SecretKey aesKey = generateKey();
     * 
     * // Generate RSA key pair for the receiver
     * KeyPair rsaKeyPair = generateRSAKeyPair();
     * 
     * // Encrypt the AES key with the receiver's public RSA key
     * String encryptedKey = encryptKey(aesKey, rsaKeyPair.getPublic());
     * 
     * // The receiver decrypts the AES key with their private RSA key
     * SecretKey decryptedAesKey = decryptKey(encryptedKey,
     * rsaKeyPair.getPrivate());
     * 
     * // Encrypt and decrypt the message using the decrypted AES key
     * String encryptedMessage = encrypt(message, decryptedAesKey);
     * String decryptedMessage = decrypt(encryptedMessage, decryptedAesKey);
     * 
     * System.out.println("Original Message: " + message);
     * System.out.println("Encrypted Message: " + encryptedMessage);
     * System.out.println("Decrypted Message: " + decryptedMessage);
     * 
     * } catch (Exception e) {
     * e.printStackTrace();
     * }
     * }
     */
}
