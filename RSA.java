import java.util.Scanner;
import java.math.BigInteger;
import java.util.Random;

public class RSA {
    private static BigInteger n, e, d;
    private static final int CERTAINTY = 20; // For prime number testing
    
    // Method to generate RSA keys
    public static void generateKeys(int bitLength) {
        Random rand = new Random();
        
        // Generate two large prime numbers
        BigInteger p = BigInteger.probablePrime(bitLength, rand);
        BigInteger q = BigInteger.probablePrime(bitLength, rand);
        
        // Calculate n = p * q
        n = p.multiply(q);
        
        // Calculate Euler's totient function φ(n) = (p-1)(q-1)
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        
        // Choose e (public exponent) - commonly 65537
        e = BigInteger.valueOf(65537);
        
        // If e isn't coprime with φ(n), find a suitable e
        while (phi.gcd(e).compareTo(BigInteger.ONE) != 0) {
            e = e.add(BigInteger.TWO);
        }
        
        // Calculate d (private exponent) - modular multiplicative inverse of e
        d = e.modInverse(phi);
        
        System.out.println("\nGenerated RSA Keys:");
        System.out.println("Public Key (e, n): (" + e + ", " + n + ")");
        System.out.println("Private Key (d, n): (" + d + ", " + n + ")");
    }
    
    // Method to encrypt a message
    public static BigInteger[] encrypt(String message) {
        byte[] messageBytes = message.getBytes();
        BigInteger[] encrypted = new BigInteger[messageBytes.length];
        
        for (int i = 0; i < messageBytes.length; i++) {
            BigInteger m = BigInteger.valueOf(messageBytes[i]);
            encrypted[i] = m.modPow(e, n);
        }
        
        return encrypted;
    }
    
    // Method to decrypt a message
    public static String decrypt(BigInteger[] encrypted) {
        byte[] decrypted = new byte[encrypted.length];
        
        for (int i = 0; i < encrypted.length; i++) {
            BigInteger c = encrypted[i];
            decrypted[i] = c.modPow(d, n).byteValue();
        }
        
        return new String(decrypted);
    }
    
    // Method to display encrypted data in readable format
    public static void displayEncrypted(BigInteger[] encrypted) {
        System.out.println("\nEncrypted message (numeric form):");
        for (BigInteger num : encrypted) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            while (true) {
                System.out.println("\n=== RSA Encryption/Decryption System ===");
                System.out.println("1. Generate new RSA keys");
                System.out.println("2. Encrypt a message");
                System.out.println("3. Decrypt a message");
                System.out.println("4. Exit");
                System.out.print("Enter your choice (1-4): ");
                
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                switch (choice) {
                    case 1:
                        System.out.print("Enter bit length for prime numbers (e.g., 512): ");
                        int bitLength = scanner.nextInt();
                        generateKeys(bitLength);
                        break;
                        
                    case 2:
                        if (e == null || n == null) {
                            System.out.println("Please generate keys first!");
                            break;
                        }
                        System.out.print("Enter message to encrypt: ");
                        String message = scanner.nextLine();
                        BigInteger[] encrypted = encrypt(message);
                        displayEncrypted(encrypted);
                        break;
                        
                    case 3:
                        if (d == null || n == null) {
                            System.out.println("Please generate keys first!");
                            break;
                        }
                        System.out.println("Enter the encrypted numbers (space-separated):");
                        String[] numbers = scanner.nextLine().split(" ");
                        BigInteger[] encryptedInput = new BigInteger[numbers.length];
                        for (int i = 0; i < numbers.length; i++) {
                            encryptedInput[i] = new BigInteger(numbers[i]);
                        }
                        String decrypted = decrypt(encryptedInput);
                        System.out.println("Decrypted message: " + decrypted);
                        break;
                        
                    case 4:
                        System.out.println("Goodbye!");
                        return;
                        
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
