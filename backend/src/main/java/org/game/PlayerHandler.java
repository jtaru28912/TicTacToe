package org.game;


import java.io.*;
import java.net.Socket;

public class PlayerHandler {
    private final Socket socket;
    private final BufferedReader input;
    private final PrintWriter output;
    private final char symbol;

    public PlayerHandler(Socket socket, char symbol) throws IOException {
        this.socket = socket;
        this.symbol = symbol;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.output = new PrintWriter(socket.getOutputStream(), true);
        sendMessage("Welcome! You are Player " + symbol);
    }

    public String readMove() throws IOException {
        return input.readLine();
    }

    public void sendMessage(String message) {
        output.println(message);
    }

    public char getSymbol() {
        return symbol;
    }

    public void close() throws IOException {
        socket.close();
    }
}
