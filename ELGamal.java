import java.util.Scanner;
import java.math.BigInteger;
import java.util.Random;

public class ElGamal {
    private static BigInteger p; // Prime number
    private static BigInteger g; // Generator
    private static BigInteger d; // Private key
    private static BigInteger e1; // Public key part 1
    private static BigInteger e2; // Public key part 2
    private static final int CERTAINTY = 20; // For prime number testing
    
    // Method to generate ElGamal keys
    public static void generateKeys(int bitLength) {
        Random rand = new Random();
        
        // Generate a large prime number p
        p = BigInteger.probablePrime(bitLength, rand);
        
        // Find a generator g
        g = findGenerator(p);
        
        // Generate private key d (1 < d < p-1)
        d = new BigInteger(bitLength - 1, rand);
        while (d.compareTo(p.subtract(BigInteger.ONE)) >= 0 || d.compareTo(BigInteger.ONE) <= 0) {
            d = new BigInteger(bitLength - 1, rand);
        }
        
        // Calculate e1 = g^d mod p
        e1 = g.modPow(d, p);
        
        System.out.println("\nGenerated ElGamal Keys:");
        System.out.println("Prime (p): " + p);
        System.out.println("Generator (g): " + g);
        System.out.println("Private Key (d): " + d);
        System.out.println("Public Key (e1): " + e1);
    }
    
    // Method to find a generator for the multiplicative group mod p
    private static BigInteger findGenerator(BigInteger p) {
        BigInteger pMinusOne = p.subtract(BigInteger.ONE);
        
        // Start with g = 2
        BigInteger g = BigInteger.valueOf(2);
        
        while (true) {
            // Check if g is a generator
            if (g.modPow(pMinusOne, p).equals(BigInteger.ONE) &&
                !g.modPow(pMinusOne.divide(BigInteger.TWO), p).equals(BigInteger.ONE)) {
                return g;
            }
            g = g.add(BigInteger.ONE);
        }
    }
    
    // Method to encrypt a message
    public static BigInteger[] encrypt(BigInteger message, BigInteger r) {
        // Validate r
        if (r.compareTo(p.subtract(BigInteger.ONE)) >= 0 || r.compareTo(BigInteger.ONE) <= 0) {
            throw new IllegalArgumentException("r must be between 1 and p-1");
        }
        
        // Calculate c1 = g^r mod p
        BigInteger c1 = g.modPow(r, p);
        
        // Calculate c2 = m * (e1^r) mod p
        BigInteger c2 = message.multiply(e1.modPow(r, p)).mod(p);
        
        return new BigInteger[] {c1, c2};
    }
    
    // Method to decrypt a message
    public static BigInteger decrypt(BigInteger c1, BigInteger c2) {
        // Calculate s = c1^d mod p
        BigInteger s = c1.modPow(d, p);
        
        // Calculate s^(-1) mod p
        BigInteger sInverse = s.modInverse(p);
        
        // Calculate m = c2 * s^(-1) mod p
        return c2.multiply(sInverse).mod(p);
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            while (true) {
                System.out.println("\n=== ElGamal Encryption/Decryption System ===");
                System.out.println("1. Generate new keys");
                System.out.println("2. Encrypt a message");
                System.out.println("3. Decrypt a message");
                System.out.println("4. Exit");
                System.out.print("Enter your choice (1-4): ");
                
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                switch (choice) {
                    case 1:
                        System.out.print("Enter bit length for prime number (e.g., 64): ");
                        int bitLength = scanner.nextInt();
                        generateKeys(bitLength);
                        break;
                        
                    case 2:
                        if (p == null || g == null || e1 == null) {
                            System.out.println("Please generate keys first!");
                            break;
                        }
                        System.out.print("Enter message (as a number): ");
                        BigInteger message = scanner.nextBigInteger();
                        System.out.print("Enter random number r (1 < r < p-1): ");
                        BigInteger r = scanner.nextBigInteger();
                        
                        BigInteger[] encrypted = encrypt(message, r);
                        System.out.println("Encrypted message:");
                        System.out.println("c1: " + encrypted[0]);
                        System.out.println("c2: " + encrypted[1]);
                        break;
                        
                    case 3:
                        if (d == null || p == null) {
                            System.out.println("Please generate keys first!");
                            break;
                        }
                        System.out.print("Enter c1: ");
                        BigInteger c1 = scanner.nextBigInteger();
                        System.out.print("Enter c2: ");
                        BigInteger c2 = scanner.nextBigInteger();
                        
                        BigInteger decrypted = decrypt(c1, c2);
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
