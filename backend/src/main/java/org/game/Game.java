package org.game;

public class Game {
    private final char[][] board;
    private char currentPlayer;

    public Game() {
        board = new char[3][3];
        resetBoard();
        currentPlayer = 'X'; // X always starts first
    }

    // Reset or initialize the board
    private void resetBoard() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = ' ';
    }

    // Returns true if the move is valid and applied
    public synchronized boolean makeMove(int row, int col, char player) {
        if (row < 0 || row >= 3 || col < 0 || col >= 3)
            return false; // out of bounds

        if (board[row][col] != ' ' || player != currentPlayer)
            return false; // already taken or not your turn

        board[row][col] = player;
        currentPlayer = (player == 'X') ? 'O' : 'X';
        return true;
    }

    // Check if a player has won the game
    public synchronized boolean checkWin(char player) {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == player &&
                    board[i][1] == player &&
                    board[i][2] == player) ||
                    (board[0][i] == player &&
                            board[1][i] == player &&
                            board[2][i] == player)) {
                return true;
            }
        }

        // Check diagonals
        return (board[0][0] == player &&
                board[1][1] == player &&
                board[2][2] == player) ||
                (board[0][2] == player &&
                        board[1][1] == player &&
                        board[2][0] == player);
    }

    // Check if the board is full without a winner
    public synchronized boolean isDraw() {
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == ' ')
                    return false; // empty cell found
            }
        }
        return true; // full board and no winner = draw
    }

    // Get a text version of the board for printing

    //Try removing synchronized from makeMove() and play fast moves from both clients. You'll see glitches like:
    //
    //Overwriting moves
    //
    //Skipping turns
    //
    //Corrupted board
    //
    //This is why concurrency control matters.

    public synchronized String getBoard() {
        StringBuilder sb = new StringBuilder();
        sb.append("Board:\n");
        for (int i = 0; i < 3; i++) {
            sb.append(" ");
            for (int j = 0; j < 3; j++) {
                sb.append(board[i][j] == ' ' ? '-' : board[i][j]);
                if (j < 2) sb.append(" | ");
            }
            sb.append("\n");
            if (i < 2) sb.append("---+---+---\n");
        }
        return sb.toString();
    }

    // Get who's turn it is
    public synchronized char getCurrentPlayer() {
        return currentPlayer;
    }
}
