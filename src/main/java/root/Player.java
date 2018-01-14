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

    void takeTurn(Board board) {
        board.inspect();
        printStream.print("Enter a number indicating where you want to mark the board: ");
        try {
            board.mark(bufferedReader.readLine());
        } catch (IOException e) {
            board.mark(PASS_MOVE);
        }
    }
}
