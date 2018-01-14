package root;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

class Player {
    private static final String PASS_MOVE = "PASS";
    private final PrintStream printStream;
    private final BufferedReader bufferedReader;

    Player(PrintStream printStream, BufferedReader bufferedReader) {
        this.printStream = printStream;
        this.bufferedReader = bufferedReader;
    }

    String move() {
        printStream.print("Enter a number indicating where you want to mark the board: ");
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            return PASS_MOVE;
        }
    }
}
