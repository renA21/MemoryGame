package com.joker;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MemoryBoard extends JFrame {
    private JButton mainMenuButton;
    private JPanel mainPanel;
    private JPanel scorePanel;
    private JPanel buttonGridPanel;
    private JPanel endgamePanel;
    private JButton replayButton;
    private JButton exitButton;
    private JLabel scoreLabel;
    private JLabel highScoreLabel;
    private JLabel timeLabel;
    private JPanel bottomPanel;
    private JLabel endScoreLabel;
    private JLabel newHighScoreLabel;
    private JLabel endHighScoreLabel;
    private JLabel endTitleLabel;

    private final JButton[][] boardButton = new JButton[4][4];

    private boolean buttonPressed = false;
    public int temp1;
    public int temp2;
    private int prevRow, prevCol;
    private int disabledButtons = 0;
    private final int totalLength = MemoryGame.boardNumbers.length * MemoryGame.boardNumbers[0].length;

    private int currentScore = 0;
    boolean newScore = false;
    private Timer gameTimer;
    private int second = 60;
    private boolean gameOver = false;

    public MemoryBoard(String title) {
        super(title);
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);

        // Exit confirmation when closing the window
        JFrame confirmExit = new JFrame();
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                int option = JOptionPane.showConfirmDialog(confirmExit,
                        "Are you sure you want to exit? Any progress on this game session will be lost.",
                        "Exit", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        // High score
        highScoreLabel.setText("High Score: " + Main.highScore);

        endgamePanel.setVisible(false);

        buttonBoard();

        gameTimer = new Timer(1000, e -> {
            second--;
            timeLabel.setText("Time: " + second + " seconds");
            if (second == 0) {
                gameTimer.stop();
                gameOver = true;
                try {
                    endgame();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        gameTimer.start();

        replayButton.addActionListener(e -> {
            memoryBoard();
            dispose();
        });

        exitButton.addActionListener(e -> {
            try {
                int option = JOptionPane.showConfirmDialog(confirmExit,
                        "Are you sure you want to exit? Any progress on this game session will be lost.",
                        "Exit", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    MainMenu.mainMenu();
                    dispose();
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    public void buttonBoard() {
        buttonGridPanel.setLayout(new GridLayout(4, 4));
        buttonGridPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        for (int row = 0; row < MemoryGame.boardNumbers.length; row++) {
            for (int col = 0; col < MemoryGame.boardNumbers[row].length; col++) {
                boardButton[row][col] = new JButton();
                boardButton[row][col].setFont(new Font("Courier New", Font.BOLD, 40));
                int finalRow = row;
                int finalCol = col;
                boardButton[row][col].addActionListener(e -> {
                    boardButton[finalRow][finalCol].setEnabled(false);
                    boardButton[finalRow][finalCol].setText(String.valueOf(MemoryGame.boardNumbers[finalRow][finalCol]));
                    if (buttonPressed) {
                        temp2 = MemoryGame.boardNumbers[finalRow][finalCol];
                        if (!MemoryGame.compare(temp1, temp2)) {
                            boardButton[finalRow][finalCol].setEnabled(true);
                            boardButton[finalRow][finalCol].setText(" ");

                            boardButton[prevRow][prevCol].setEnabled(true);
                            boardButton[prevRow][prevCol].setText(" ");
                        } else {
                            temp1 = 0;
                            temp2 = 0;

                            setScore(10);

                            try {
                                checkButtons(2);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                        buttonPressed = false;
                    } else {
                        temp1 = MemoryGame.boardNumbers[finalRow][finalCol];
                        prevRow = finalRow;
                        prevCol = finalCol;
                        buttonPressed = true;
                    }
                });
                buttonGridPanel.add(boardButton[row][col]);
            }
        }
    }

    public void setScore(int points) {
        currentScore += points;
        scoreLabel.setText("Score: " + currentScore);
        setHighScore();
    }

    public void setHighScore() {
        if (currentScore > Main.highScore) {
            Main.highScore = currentScore;
            newScore = true;
        }
        highScoreLabel.setText("High Score: " + Main.highScore);
    }

    public void checkButtons(int buttonAmount) throws IOException {
        disabledButtons += buttonAmount;
        if (disabledButtons == totalLength) {
            gameTimer.stop();
            endgame();
        }
    }

    public void endgame() throws IOException {
        scorePanel.setVisible(false);
        buttonGridPanel.setVisible(false);
        bottomPanel.setVisible(false);
        endgamePanel.setVisible(true);
        newHighScoreLabel.setVisible(false);

        if (gameOver) {
            endTitleLabel.setText("Game Over");
        }

        endHighScoreLabel.setText("High Score: " + Main.highScore);
        endScoreLabel.setText("Score: " + currentScore);
        if (newScore) {
            newHighScoreLabel.setVisible(true);
        }

        // Save high score
        HighScore.saveScore();

        mainMenuButton.addActionListener(e -> {
            try {
                MainMenu.mainMenu();
                dispose();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    public static void memoryBoard() {
        JFrame frame = new MemoryBoard("MemoryGame " + Main.version + " | Memory Board");
        frame.setVisible(true);
        MemoryGame.shuffleBoard();
    }

    // Debug
    public static void main(String[] args) {
        memoryBoard();
    }
}