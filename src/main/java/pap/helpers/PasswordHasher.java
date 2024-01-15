package pap.helpers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordHasher {
    /**
     * A class that hashes passwords.
     */
    public static String generateSalt() {
        /*
          A method that generates a random password salt.
         */
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);

        StringBuilder sb = new StringBuilder();
        for (byte b : salt) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
    public static String hashPassword(String password, String salt) {
        /*
          A method that hashes a password using SHA-512.
         */
        byte[] rawSalt = salt.getBytes(StandardCharsets.UTF_8);

        // hash the password
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(rawSalt);
        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        // convert the hashed password byte array to a string
        StringBuilder sb = new StringBuilder();
        for (byte b : hashedPassword) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
