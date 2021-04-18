package com.joker;

import javax.swing.*;
import java.io.IOException;

public class MainMenu extends JFrame {
    private JPanel mainPanel;
    private JButton startGameButton;
    private JButton exitButton;
    private JLabel versionLabel;
    private JLabel highScoreLabel;

    /**
     * Draws the Main Menu UI
     * @param title Sets the window title
     * @throws IOException I/O error handling
     */
    public MainMenu(String title) throws IOException {
        // JFrame properties
        super(title); // Window title
        this.setContentPane(mainPanel); // Set JPanel
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Default operation for closing the window.
        this.pack();
        this.setLocationRelativeTo(null); // Set window position to center.

        // Set OS theme to the GUI
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException |
                InstantiationException |
                IllegalAccessException |
                UnsupportedLookAndFeelException e
        ) {
            e.printStackTrace();
        }

        // Set program version
        versionLabel.setText("Version: " + Main.version);

        // Read high score file
        HighScore.readFile();

        // Set high score value to its respective label.
        highScoreLabel.setText("High Score: " + Main.highScore);

        // Starting the game by opening the Memory Board window and destroys Main Menu window.
        startGameButton.addActionListener(e -> {
            MemoryBoard.memoryBoard();
            dispose();
        });

        // Close the program with a confirmation message.
        exitButton.addActionListener(e -> {
            JFrame confirmExit = new JFrame();
            int option = JOptionPane.showConfirmDialog(
                    confirmExit,
                    "Are you sure you want to exit?",
                    "Exit", JOptionPane.YES_NO_OPTION
            );
            if (option == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }

    /**
     * Initialize Main Menu UI
     * @throws IOException I/O error handling
     */
    public static void mainMenu() throws IOException {
        JFrame frame = new MainMenu("MemoryGame " + Main.version + " | Main Menu");
        frame.setVisible(true);
    }
}
