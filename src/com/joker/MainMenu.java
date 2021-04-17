package com.joker;

import javax.swing.*;
import java.io.IOException;

public class MainMenu extends JFrame {
    private JPanel mainPanel;
    private JButton startGameButton;
    private JButton exitButton;
    private JLabel versionLabel;
    private JLabel highScoreLabel;

    public MainMenu(String title) throws IOException {
        super(title);
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);

        // Set system theme
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

        // High score
        highScoreLabel.setText("High Score: " + Main.highScore);

        startGameButton.addActionListener(e -> {
            MemoryBoard.memoryBoard();
            dispose();
        });

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

    public static void mainMenu() throws IOException {
        JFrame frame = new MainMenu("MemoryGame " + Main.version + " | Main Menu");
        frame.setVisible(true);
    }
}
