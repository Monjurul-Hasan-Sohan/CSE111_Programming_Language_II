import java.util.Scanner;

public class PrimeNumberCounter {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter first number: ");
        int n1 = input.nextInt();

        System.out.print("Enter 2nd number: ");
        int n2 = input.nextInt();

        int start;
        int end;

        if (n1 < n2) {
            start = n1;
            end = n2;
        } else {
            start = n2;
            end = n1;
        }

        int count = 0;

        for (int num = start; num <= end; num++) {
            if (checkPrime(num)) {
                count++;
            }
        }

        System.out.println("Between " + start + " and " + end + ", there are " + count + " prime numbers.");
    }
    
    static boolean checkPrime(int number) {
        if (number <= 1) {
            return false;
        }

        boolean isPrime = true;

        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                isPrime = false;
                break;
            }
        }

        return isPrime;
    }
}
