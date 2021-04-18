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

    // JButton array
    private final JButton[][] boardButton = new JButton[4][4];

    private boolean buttonPressed = false; // Represents whether the 1st selected button is pressed.
    public int temp1;
    public int temp2;
    private int prevRow, prevCol;
    private int disabledButtons = 0; // Pressed buttons count
    // Total amounts of elements in the grid. In this case its 16 elements in total for a 4x4 grid.
    private final int totalLength = MemoryGame.boardNumbers.length * MemoryGame.boardNumbers[0].length;

    private int currentScore = 0;
    boolean newScore = false;
    private Timer gameTimer;
    private int second = 60; // 60 second timer.
    private boolean timeOut = false;

    /**
     * Draws the Memory Board UI
     * @param title Sets the window title
     */
    public MemoryBoard(String title) {
        // JFrame properties
        super(title); // Window title
        this.setContentPane(mainPanel); // Set JPanel
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Default operation for closing the window.
        this.pack();
        this.setLocationRelativeTo(null); // Set window position to center.

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

        // Set high score value from saved file to its respective label.
        highScoreLabel.setText("High Score: " + Main.highScore);

        // Hide Game Over JPanel
        endgamePanel.setVisible(false);

        // Initialize method for setting up array of JButtons and the game logic.
        buttonBoard();

        // Set 60 second game timer
        gameTimer = new Timer(1000, e -> {
            second--;
            timeLabel.setText("Time: " + second + " seconds");
            // Game over when time has run out.
            if (second == 0) {
                gameTimer.stop();
                timeOut = true;
                try {
                    endgame();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        gameTimer.start(); // Start timer

        // Restarts the game by opening another Memory Board window and destroy the previous instance.
        replayButton.addActionListener(e -> {
            memoryBoard();
            dispose();
        });

        // Stop the game and return to the Main Menu with a confirmation message.
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

    /**
     * Draw JButton array and setting the game logic.
     */
    public void buttonBoard() {
        // Set 4x4 button grid layout for JPanel.
        buttonGridPanel.setLayout(new GridLayout(4, 4));
        // Set JPanel border
        buttonGridPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        for (int row = 0; row < MemoryGame.boardNumbers.length; row++) {
            for (int col = 0; col < MemoryGame.boardNumbers[row].length; col++) {
                // Instantiate JButton array according to the length and width of boardNumbers 2D array in MemoryGame class.
                boardButton[row][col] = new JButton();
                // Set JButton font type, weight and size
                boardButton[row][col].setFont(new Font("Courier New", Font.BOLD, 40));
                // Required variable declarations for lambda expressions in JButton ActionListeners.
                int finalRow = row;
                int finalCol = col;
                // Sets functionality for every JButton in the board.
                boardButton[row][col].addActionListener(e -> {
                    // Disable the button and shows the hidden number when its pressed.
                    boardButton[finalRow][finalCol].setEnabled(false);
                    boardButton[finalRow][finalCol].setText(String.valueOf(MemoryGame.boardNumbers[finalRow][finalCol]));

                    // Conditional statement for the selected buttons
                    if (buttonPressed) {
                        // Store number from the 2nd selection to temp2
                        temp2 = MemoryGame.boardNumbers[finalRow][finalCol];
                        // Compare numbers from 1st and 2nd selection
                        if (!MemoryGame.compare(temp1, temp2)) {
                            // Set 100ms delay so that the wrong selections are not cleared instantly.
                            new java.util.Timer().schedule(
                                    new java.util.TimerTask() {
                                        @Override
                                        public void run() {
                                            // Enable and hide text for the wrong selection of JButtons.
                                            boardButton[finalRow][finalCol].setEnabled(true);
                                            boardButton[finalRow][finalCol].setText(" ");
                                            boardButton[prevRow][prevCol].setEnabled(true);
                                            boardButton[prevRow][prevCol].setText(" ");
                                        }
                                    },
                                    100
                            );
                        } else {
                            // Clear temporary stored numbers for comparison when button selections match.
                            temp1 = 0;
                            temp2 = 0;

                            // Add 10 points to setScore method
                            setScore(10);

                            // Adds the count of correct buttons
                            try {
                                checkButtons(2);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                        buttonPressed = false;
                    } else {
                        // Store number from the 1st selection to temp1
                        temp1 = MemoryGame.boardNumbers[finalRow][finalCol];
                        prevRow = finalRow;
                        prevCol = finalCol;
                        buttonPressed = true;
                    }
                });
                buttonGridPanel.add(boardButton[row][col]); // Add JButton array to JPanel
            }
        }
    }

    /**
     * Sets the current score from earned points
     * @param points Points earned from matching selections
     */
    public void setScore(int points) {
        currentScore += points;
        scoreLabel.setText("Score: " + currentScore);
        setHighScore();
    }

    /**
     * Sets new high score if current score exceeds previous high score
     */
    public void setHighScore() {
        if (currentScore > Main.highScore) {
            Main.highScore = currentScore;
            newScore = true;
        }
        highScoreLabel.setText("High Score: " + Main.highScore);
    }

    /**
     * Checks the amount of correct matches to end the game
     * @param buttonAmount Correct button matches
     * @throws IOException I/O error handling
     */
    public void checkButtons(int buttonAmount) throws IOException {
        disabledButtons += buttonAmount;
        if (disabledButtons == totalLength) {
            gameTimer.stop(); // Stop game timer
            endgame();// Game over
        }
    }

    /**
     * Hides the Memory Board UI components and draws Game over JPanel.
     * @throws IOException I/O error handling
     */
    public void endgame() throws IOException {
        scorePanel.setVisible(false);
        buttonGridPanel.setVisible(false);
        bottomPanel.setVisible(false);
        endgamePanel.setVisible(true);
        newHighScoreLabel.setVisible(false);

        // Change Game over title if time has run out.
        if (timeOut) {
            endTitleLabel.setText("Time's Up!");
        }

        endHighScoreLabel.setText("High Score: " + Main.highScore);
        endScoreLabel.setText("Score: " + currentScore);
        // Shows "New High Score!"
        if (newScore) {
            newHighScoreLabel.setVisible(true);
        }

        // Save high score to highScore.txt
        HighScore.saveScore();

        // Return to Main Menu and destroy Memory Board window
        mainMenuButton.addActionListener(e -> {
            try {
                MainMenu.mainMenu();
                dispose();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    /**
     * Initialize Memory Board UI
     */
    public static void memoryBoard() {
        JFrame frame = new MemoryBoard("MemoryGame " + Main.version + " | Memory Board");
        frame.setVisible(true);
        MemoryGame.shuffleBoard();
    }
}