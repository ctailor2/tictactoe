package root;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

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
        List<String> availableLocations = board.inspect();
        String selectedLocation = promptForLocation();
        while (!selectedLocationIsValid(availableLocations, selectedLocation)) {
            printStream.println("Location already taken!");
            selectedLocation = promptForLocation();
        }
        board.mark(selectedLocation, symbol);
    }

    private boolean selectedLocationIsValid(List<String> availableLocations, String selectedLocation) {
        return selectedLocation.equals(PASS_LOCATION) ||
            availableLocations.contains(selectedLocation);
    }

    private String promptForLocation() {
        printStream.print("Enter a number indicating where you want to mark the board: ");
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            return PASS_LOCATION;
        }
    }
}
