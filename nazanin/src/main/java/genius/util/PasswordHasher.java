package genius.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// Utility class for password hashing and verification
public class PasswordHasher {

    // Hash a password using SHA-256 algorithm
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256"); // Create SHA-256 digest instance
            byte[] hashedBytes = md.digest(password.getBytes()); // Hash the input password bytes
            StringBuilder sb = new StringBuilder();

            // Convert hashed bytes to hexadecimal string
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString(); // Return hashed password as hex string
        } catch (NoSuchAlgorithmException e) {
            // SHA-256 should always be available; rethrow as unchecked exception
            throw new RuntimeException("Error hashing password", e);
        }
    }

    // Verify if a plain password matches the stored hash
    public static boolean verifyPassword(String password, String passwordHash) {
        return hashPassword(password).equals(passwordHash); // Compare hash of input with stored hash
    }
}
