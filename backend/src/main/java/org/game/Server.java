package org.game;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {
    private static final int PORT = 12345;
    private static final LinkedBlockingQueue<Socket> waitingPlayers = new LinkedBlockingQueue<>();

    public static void main(String[] args) {
        System.out.println("Server started on port " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New player connected: " + socket);

                waitingPlayers.offer(socket);

                if (waitingPlayers.size() >= 2) {
                    Socket p1 = waitingPlayers.poll();
                    Socket p2 = waitingPlayers.poll();

                    PlayerHandler player1 = new PlayerHandler(p1, 'X');
                    PlayerHandler player2 = new PlayerHandler(p2, 'O');

                    GameSession session = new GameSession(player1, player2);
                    session.start();
                }
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}
