package org.game;


import java.io.IOException;

public class GameSession extends Thread {
    private final PlayerHandler player1;
    private final PlayerHandler player2;
    private final Game game;

    public GameSession(PlayerHandler player1, PlayerHandler player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.game = new Game();
    }

    @Override
    public void run() {
        PlayerHandler current = player1;
        PlayerHandler other = player2;

        try {
            while (true) {
                current.sendMessage(game.getBoard());
                other.sendMessage(game.getBoard());

                current.sendMessage("Your move (row and column: 0 2):");
                other.sendMessage("Waiting for opponent...");

                String move = current.readMove();
                if (move == null) break;

                String[] parts = move.trim().split(" ");
                if (parts.length != 2) {
                    current.sendMessage("Invalid input. Enter two numbers.");
                    continue;
                }

                int row, col;
                try {
                    row = Integer.parseInt(parts[0]);
                    col = Integer.parseInt(parts[1]);
                } catch (NumberFormatException e) {
                    current.sendMessage("Invalid numbers. Try again.");
                    continue;
                }

                if (!game.makeMove(row, col, current.getSymbol())) {
                    current.sendMessage("Invalid move or not your turn.");
                    continue;
                }

                if (game.checkWin(current.getSymbol())) {
                    current.sendMessage("You win!\n" + game.getBoard());
                    other.sendMessage("You lose!\n" + game.getBoard());
                    break;
                }

                if (game.isDraw()) {
                    current.sendMessage("It's a draw!\n" + game.getBoard());
                    other.sendMessage("It's a draw!\n" + game.getBoard());
                    break;
                }

                PlayerHandler temp = current;
                current = other;
                other = temp;
            }
        } catch (IOException e) {
            System.out.println("A player disconnected.");
        } finally {
            try {
                player1.close();
                player2.close();
            } catch (IOException ignored) {}
        }
    }
}
