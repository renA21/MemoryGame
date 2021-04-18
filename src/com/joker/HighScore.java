package com.joker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class HighScore {
    // Sets the filename and directory of highScore.txt
    private static final File highScoreFile = new File(Main.dataDir, "highScore.txt");

    /**
     * Find highScore.txt and read the text inside the file.
     * @throws IOException I/O error handling
     */
    public static void readFile() throws IOException {
        if (highScoreFile.exists()) {
            Scanner reader = new Scanner(highScoreFile);
            Main.highScore = reader.nextInt();
            reader.close();
        } else {
            Main.highScore = 0; // Set high score to 0 if file not found
        }
    }

    /**
     * Saves new high score to highScore.txt
     * @throws IOException I/O error handling
     */
    public static void saveScore() throws IOException {
        FileWriter writer = new FileWriter(highScoreFile);
        writer.append(Integer.toString(Main.highScore));
        writer.flush();
        writer.close();
    }
}
