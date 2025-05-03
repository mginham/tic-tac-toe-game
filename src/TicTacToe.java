import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe {
    int boardTextPanelHeight = 50;
    int boardWidth = 600;
    int boardHeight = boardWidth + boardTextPanelHeight;

    JFrame frame = new JFrame("Tic-Tac-Toe Game");
    JLabel txtLabel = new JLabel();
    JPanel txtPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    JButton[][] board = new JButton[3][3];
    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX; // By default, playerX will always go first

    boolean gameOver = false;
    int turns = 0;

    TicTacToe() {
        // Window settings
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Text Label settings
        txtLabel.setBackground(Color.darkGray);
        txtLabel.setForeground(Color.white);
        txtLabel.setFont(new Font("Arial", Font.BOLD, 50));
        txtLabel.setHorizontalAlignment(JLabel.CENTER);
        txtLabel.setText("Tic-Tac-Toe");
        txtLabel.setOpaque(true);

        // Text Panel settings
        txtPanel.setLayout(new BorderLayout());
        txtPanel.add(txtLabel);
        frame.add(txtPanel, BorderLayout.NORTH);

        // Board Panel settings
        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.darkGray);
        frame.add(boardPanel);

        // Tile settings
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);

                tile.setBackground(Color.darkGray);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);

                // On click, populate tile with player's marker
                tile.addActionListener(new ActionListener() {
                    public void actionPerformed (ActionEvent e) {
                        // End the game as needed
                        if (gameOver) return;

                        JButton tile = (JButton) e.getSource();

                        // Only populate tile if empty
                        if(tile.getText() == "") {
                            tile.setText(currentPlayer);

                            turns++;

                            // Check if current player won
                            checkWinner();

                            if(!gameOver) {
                                // Switch player turn
                                currentPlayer = currentPlayer == playerX ? playerO : playerX;
                                txtLabel.setText(currentPlayer + "'s turn.");
                            }
                        }
                    }
                });
            }
        }
    }

    void checkWinner () {
        // Horizonal win
        for (int r = 0; r < 3; r++) {
            if(board[r][0].getText() == "") {
                // First tile is empty, skip
                continue;
            } else if (board[r][0].getText() == board[r][1].getText() && board[r][1].getText() == board[r][2].getText()) {
                // Markers across the row match, and that player has won
                for (int i = 0; i < 3; i++) {
                    setWinner(board[r][i]);
                }
                gameOver = true;
                return;
            }
        }

        // Vertical win
        for (int c = 0; c < 3; c++) {
            if(board[0][c].getText() == "") {
                // First tile is empty, skip
                continue;
            } else if (board[0][c].getText() == board[1][c].getText() && board[1][c].getText() == board[2][c].getText()) {
                // Markers across the column match, and that player has won
                for (int i = 0; i < 3; i++) {
                    setWinner(board[i][c]);
                }
                gameOver = true;
                return;
            }
        }

        // Diagonal win
        if (board[0][0].getText() == board[1][1].getText() && board[1][1].getText() == board[2][2].getText() && board[0][0].getText() != "") {
            // Markers across the diagonal match, and that player has won
            for (int i = 0; i < 3; i++) {
                setWinner(board[i][i]);
            }
            gameOver = true;
            return;
        } else if (board[0][2].getText() == board[1][1].getText() && board[1][1].getText() == board[2][0].getText() && board[0][2].getText() != "") {
            // Markers across the diagonal match, and that player has won
            for (int i = 0; i < 3; i++) {
                setWinner(board[i][2-i]);
            }
            gameOver = true;
            return;
        }

        // Check for a draw
        if (turns == 9) {
            for(int r = 0; r < 3; r++) {
                for(int c = 0; c < 3; c++) {
                    setDraw(board[r][c]);
                }
            }
            gameOver = true;
        }
    }

    void setWinner(JButton tile) {
        // Change color of the winning row
        tile.setForeground(Color.green);
        tile.setBackground(Color.gray);

        // Inform the players who has won
        txtLabel.setText(currentPlayer + " is the winner!");
    }

    void setDraw(JButton tile) {
         // Change color of the whole board
         tile.setForeground(Color.orange);
         tile.setBackground(Color.gray);

         // Inform the players of draw
         txtLabel.setText("It's a draw!");
    }
}
