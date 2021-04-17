package com.joker;

import java.io.File;
import java.io.IOException;

public class Main {

    // High score
    public static int highScore = 0;

    // Program version
    public static final String version = "1.0-rc1";

    // Create directories for the application
    public static final File dataDir = new File("memoryGameData/");

    // TODO: High score file read/store

    public static void main(String[] args) throws IOException {
        dataDir.mkdir();
        MainMenu.mainMenu();
    }
}
