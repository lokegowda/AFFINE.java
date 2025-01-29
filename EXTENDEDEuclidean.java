import java.util.Scanner;

public class ExtendedEuclidean {
    // Iterative method to find GCD using Euclidean algorithm
    public static int gcdIterative(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
    
    // Recursive method to find GCD using Euclidean algorithm
    public static int gcdRecursive(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        
        if (b == 0) {
            return a;
        }
        return gcdRecursive(b, a % b);
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            while (true) {
                System.out.println("\n=== GCD Calculator ===");
                System.out.println("1. Calculate GCD");
                System.out.println("2. Exit");
                System.out.print("Enter your choice (1-2): ");
                
                int choice = scanner.nextInt();
                
                if (choice == 2) {
                    System.out.println("Goodbye!");
                    break;
                }
                
                if (choice != 1) {
                    System.out.println("Invalid choice. Please try again.");
                    continue;
                }
                
                // Get input numbers
                System.out.print("Enter first number: ");
                int a = scanner.nextInt();
                
                System.out.print("Enter second number: ");
                int b = scanner.nextInt();
                
                // Calculate and display results using both methods
                System.out.println("\nResults:");
                System.out.println("Using iterative method: GCD(" + a + ", " + b + ") = " 
                                 + gcdIterative(a, b));
                System.out.println("Using recursive method: GCD(" + a + ", " + b + ") = " 
                                 + gcdRecursive(a, b));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
