package com.joker;

import java.util.Arrays;
import java.util.Random;

public class MemoryGame {

    public static int[][] boardNumbers = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {1, 2, 3 ,4},
            {5, 6 ,7 ,8}
    };

    public static void shuffleBoard() {
        Random rand = new Random();

        for (int row = 0; row < boardNumbers.length; row++) {
            for (int col = 0; col < boardNumbers[row].length; col++) {
                int shuffleRow = rand.nextInt(boardNumbers.length);
                int shuffleCol = rand.nextInt(boardNumbers[row].length);

                int temp = boardNumbers[row][col];
                boardNumbers[row][col] = boardNumbers[shuffleRow][shuffleCol];

                boardNumbers[shuffleRow][shuffleCol] = temp;
            }
        }
        System.out.println(Arrays.deepToString(boardNumbers));
    }

    public static boolean compare(int temp1, int temp2) {
        return temp1 == temp2;
    }
}