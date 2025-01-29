import java.util.Scanner;

public class VigenereCipher {
    private static final int ALPHABET_SIZE = 26;
    
    // Method to generate the key stream by repeating the keyword to match message length
    private static String generateKeyStream(String message, String keyword) {
        StringBuilder keyStream = new StringBuilder();
        keyword = keyword.toUpperCase();
        
        // Remove non-alphabetic characters from keyword
        keyword = keyword.replaceAll("[^A-Z]", "");
        
        if (keyword.isEmpty()) {
            throw new IllegalArgumentException("Keyword must contain at least one letter");
        }
        
        int keywordIndex = 0;
        for (char c : message.toCharArray()) {
            if (Character.isLetter(c)) {
                keyStream.append(keyword.charAt(keywordIndex));
                keywordIndex = (keywordIndex + 1) % keyword.length();
            } else {
                keyStream.append(c);
            }
        }
        
        return keyStream.toString();
    }
    
    // Method to encrypt a message
    public static String encrypt(String message, String keyword) {
        StringBuilder encrypted = new StringBuilder();
        message = message.toUpperCase();
        String keyStream = generateKeyStream(message, keyword);
        
        for (int i = 0; i < message.length(); i++) {
            char messageChar = message.charAt(i);
            char keyChar = keyStream.charAt(i);
            
            if (Character.isLetter(messageChar)) {
                // Convert letters to numbers (A=0, B=1, etc.)
                int msgNum = messageChar - 'A';
                int keyNum = keyChar - 'A';
                
                // Apply Vigenère encryption formula
                int encryptedNum = (msgNum + keyNum) % ALPHABET_SIZE;
                
                // Convert back to letter
                char encryptedChar = (char) (encryptedNum + 'A');
                encrypted.append(encryptedChar);
            } else {
                encrypted.append(messageChar);
            }
        }
        
        return encrypted.toString();
    }
    
    // Method to decrypt a message
    public static String decrypt(String message, String keyword) {
        StringBuilder decrypted = new StringBuilder();
        message = message.toUpperCase();
        String keyStream = generateKeyStream(message, keyword);
        
        for (int i = 0; i < message.length(); i++) {
            char messageChar = message.charAt(i);
            char keyChar = keyStream.charAt(i);
            
            if (Character.isLetter(messageChar)) {
                // Convert letters to numbers (A=0, B=1, etc.)
                int msgNum = messageChar - 'A';
                int keyNum = keyChar - 'A';
                
                // Apply Vigenère decryption formula
                int decryptedNum = (msgNum - keyNum + ALPHABET_SIZE) % ALPHABET_SIZE;
                
                // Convert back to letter
                char decryptedChar = (char) (decryptedNum + 'A');
                decrypted.append(decryptedChar);
            } else {
                decrypted.append(messageChar);
            }
        }
        
        return decrypted.toString();
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            while (true) {
                System.out.println("\n=== Vigenère Cipher Program ===");
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
                
                // Get keyword input
                System.out.print("Enter the keyword: ");
                String keyword = scanner.nextLine();
                
                String result;
                if (choice == 1) {
                    result = encrypt(message, keyword);
                    System.out.println("Encrypted message: " + result);
                } else {
                    result = decrypt(message, keyword);
                    System.out.println("Decrypted message: " + result);
                }
                
                // Display the generated key stream for educational purposes
                System.out.println("Generated key stream: " + generateKeyStream(message, keyword));
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
