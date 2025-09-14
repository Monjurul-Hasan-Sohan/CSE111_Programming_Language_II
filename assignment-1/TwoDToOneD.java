import java.util.Scanner;

public class TwoDToOneD {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter row: ");
        int rowNumber = input.nextInt();

        System.out.print("Enter column: ");
        int columnNumber = input.nextInt();

        
        int[][] amarMatrix = new int[rowNumber][columnNumber];

        
        System.out.println("Enter elements:");
        for (int i = 0; i < rowNumber; i++) {
            for (int j = 0; j < columnNumber; j++) {
                amarMatrix[i][j] = input.nextInt();
            }
        }

        
        System.out.println("2D Array:");
        for (int i = 0; i < rowNumber; i++) {
            for (int j = 0; j < columnNumber; j++) {
                System.out.print(amarMatrix[i][j] + " ");
            }
            System.out.println();
        }

        
        int[] ekDArray = new int[rowNumber * columnNumber];
        int index = 0;

        for (int i = 0; i < rowNumber; i++) {
            for (int j = 0; j < columnNumber; j++) {
                ekDArray[index] = amarMatrix[i][j];
                index++;
            }
        }

        
        System.out.println("1D Array:");
        for (int i = 0; i < ekDArray.length; i++) {
            System.out.print(ekDArray[i] + " ");
        }
    }
}
