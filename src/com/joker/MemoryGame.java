package com.joker;

public class MemoryGame {
    // Numbers for the button board
    public static int[][] boardNumbers = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {1, 2, 3 ,4},
            {5, 6 ,7 ,8}
    };

    /**
     * Shuffles the arrangement of elements in the boardNumbers array.
     */
    public static void shuffleBoard() {
        for (int row = 0; row < boardNumbers.length; row++) {
            for (int col = 0; col < boardNumbers[row].length; col++) {
                int randRow = (int) (Math.random() * boardNumbers.length);
                int randCol = (int) (Math.random() * boardNumbers[row].length);

                int temp = boardNumbers[row][col];
                boardNumbers[row][col] = boardNumbers[randRow][randCol];
                boardNumbers[randRow][randCol] = temp;
            }
        }

        // Debug: Print boardNumbers array to console.
        System.out.println("Current Board:");
        for (int[] boardNumber : boardNumbers) {
            System.out.print("| ");
            for (int i : boardNumber) {
                System.out.print(i + " | ");
            }
            System.out.println();
        }
    }

    /**
     * Compares numbers between the selected buttons
     * @param temp1 The number stored in the first button selection.
     * @param temp2 The number stored in the second button selection.
     * @return Boolean value whether both numbers match with each other.
     */
    public static boolean compare(int temp1, int temp2) {
        return temp1 == temp2;
    }
}