package com.joker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class HighScore {

    private static final File highScoreFile = new File(Main.dataDir, "highScore.txt");

    public static void readFile() throws IOException {

        if (highScoreFile.exists()) {
            Scanner reader = new Scanner(highScoreFile);
            Main.highScore = reader.nextInt();
            reader.close();
        } else {
            Main.highScore = 0;
        }
    }

    public static void saveScore() throws IOException {
        FileWriter writer = new FileWriter(highScoreFile);
        writer.append(Integer.toString(Main.highScore));
        writer.flush();
        writer.close();
    }
}
