import java.util.Scanner;

public class AffineCipher {
    private static final int ALPHABET_SIZE = 26;
    
    // Method to encrypt a message
    public static String encrypt(String message, int a, int b) {
        if (!isCoprime(a, ALPHABET_SIZE)) {
            throw new IllegalArgumentException("'a' must be coprime with " + ALPHABET_SIZE);
        }
        
        StringBuilder result = new StringBuilder();
        message = message.toUpperCase();
        
        for (char character : message.toCharArray()) {
            if (Character.isLetter(character)) {
                int x = character - 'A';
                // Apply encryption formula (ax + b) mod m
                int encryptedX = (a * x + b) % ALPHABET_SIZE;
                // Convert back to character
                char encryptedChar = (char) (encryptedX + 'A');
                result.append(encryptedChar);
            } else {
                result.append(character);
            }
        }
        
        return result.toString();
    }
    
    // Method to decrypt a message
    public static String decrypt(String message, int a, int b) {
        if (!isCoprime(a, ALPHABET_SIZE)) {
            throw new IllegalArgumentException("'a' must be coprime with " + ALPHABET_SIZE);
        }
        
        StringBuilder result = new StringBuilder();
        message = message.toUpperCase();
        
        // Find modular multiplicative inverse of a
        int aInverse = findMultiplicativeInverse(a, ALPHABET_SIZE);
        
        for (char character : message.toCharArray()) {
            if (Character.isLetter(character)) {
                int y = character - 'A';
                // Apply decryption formula a^(-1)(y - b) mod m
                int decryptedY = (aInverse * (y - b + ALPHABET_SIZE)) % ALPHABET_SIZE;
                // Convert back to character
                char decryptedChar = (char) (decryptedY + 'A');
                result.append(decryptedChar);
            } else {
                result.append(character);
            }
        }
        
        return result.toString();
    }
    
    // Method to check if two numbers are coprime
    private static boolean isCoprime(int a, int b) {
        return findGCD(a, b) == 1;
    }
    
    // Method to find Greatest Common Divisor using Euclidean algorithm
    private static int findGCD(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
    
    // Method to find modular multiplicative inverse using Extended Euclidean algorithm
    private static int findMultiplicativeInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        throw new IllegalArgumentException("Multiplicative inverse does not exist");
    }
    
    // Method to display valid 'a' values
    private static void displayValidAValues() {
        System.out.println("Valid values for 'a' (must be coprime with 26):");
        System.out.println("1, 3, 5, 7, 11, 15, 17, 19, 21, 23, 25");
    }
    
    // Main method with user input
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            while (true) {
                System.out.println("\n=== Affine Cipher Program ===");
                System.out.println("1. Encrypt");
                System.out.println("2. Decrypt");
                System.out.println("3. Exit");
                System.out.print("Enter your choice (1-3): ");
                
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                if (choice == 3) {
                    System.out.println("Goodbye!");
                    break;
                }
                
                if (choice != 1 && choice != 2) {
                    System.out.println("Invalid choice. Please try again.");
                    continue;
                }
                
                // Get message input
                System.out.print("Enter the message: ");
                String message = scanner.nextLine();
                
                // Display valid 'a' values and get 'a' input
                displayValidAValues();
                System.out.print("Enter the value of 'a': ");
                int a = scanner.nextInt();
                
                System.out.print("Enter the value of 'b' (any integer): ");
                int b = scanner.nextInt();
                
                String result;
                if (choice == 1) {
                    result = encrypt(message, a, b);
                    System.out.println("Encrypted message: " + result);
                } else {
                    result = decrypt(message, a, b);
                    System.out.println("Decrypted message: " + result);
                }
                
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
