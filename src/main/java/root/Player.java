package root;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

class Player {
    private static final String PASS_LOCATION = "PASS";
    private final String symbol;
    private final PrintStream printStream;
    private final BufferedReader bufferedReader;

    Player(String symbol, PrintStream printStream, BufferedReader bufferedReader) {
        this.symbol = symbol;
        this.printStream = printStream;
        this.bufferedReader = bufferedReader;
    }

    void takeTurn(Board board) {
        printStream.println(board.grid());
        printStream.print("Enter a number indicating where you want to mark the board: ");
        String location = selectedLocation();
        try {
            board.mark(location, symbol);
        } catch (LocationTakenException e) {
            printStream.println("Location already taken!");
            takeTurn(board);
        }
    }

    private String selectedLocation() {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            return PASS_LOCATION;
        }
    }
}
