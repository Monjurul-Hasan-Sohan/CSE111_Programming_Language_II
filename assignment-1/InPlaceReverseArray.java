
import java.util.Scanner;

public class InPlaceReverseArray {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the length of the array: ");
        int length = input.nextInt();

        int[] numbers = new int[length];


        for (int i = 0; i < length; i++) {
            numbers[i] = input.nextInt();
        }

        for (int i = 0; i < length / 2; i++) {
            int temp = numbers[i];
            numbers[i] = numbers[length - 1 - i];
            numbers[length - 1 - i] = temp;
        }

        for (int i = 0; i < length; i++) {
            System.out.print(numbers[i] + " ");
        }
    }
}
