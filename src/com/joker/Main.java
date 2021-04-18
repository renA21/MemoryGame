package com.joker;

import java.io.File;
import java.io.IOException;

public class Main {
    // High score
    public static int highScore = 0;

    // Program version
    public static final String version = "1.0";

    // Folder name for the application
    public static final File dataDir = new File("memoryGameData/");

    /**
     * Main method
     * @param args Command arguments
     * @throws IOException I/O error handling
     */
    public static void main(String[] args) throws IOException {
        dataDir.mkdir(); // Create directory
        MainMenu.mainMenu(); // Initialize Main Menu UI
    }
}
