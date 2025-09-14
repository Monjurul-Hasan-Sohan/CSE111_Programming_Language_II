import java.util.Scanner;

public class PreviousAlphabet {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a string in small letters: ");
        String input = scanner.nextLine();

        String result = "";

        for (int i = 0; i < input.length(); i++) {
            char currentCh = input.charAt(i);

            if (currentCh == 'a') {
                result = result + 'z';
            } else {
                char newCh = (char) (currentCh - 1);
                result = result + newCh;
            }
        }

        System.out.println("Previous alphabets: " + result);
    }
}
