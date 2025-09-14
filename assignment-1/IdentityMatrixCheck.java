import java.util.Scanner;

public class IdentityMatrixCheck {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter size of matrix: ");
        int size = input.nextInt();

        int[][] amarMatrix = new int[size][size];

        System.out.println("Enter matrix elements:");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                amarMatrix[i][j] = input.nextInt();
            }
        }

        boolean identityAche = true;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j && amarMatrix[i][j] != 1) {
                    identityAche = false;
                    break;
                } else if (i != j && amarMatrix[i][j] != 0) {
                    identityAche = false;
                    break;
                }
            }
            if (!identityAche) {
                break;
            }
        }

        if (identityAche) {
            System.out.println("Identity Matrix");
        } else {
            System.out.println("Not an Identity Matrix");
        }
    }
}
