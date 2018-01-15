package root;

import java.io.PrintStream;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

class Board {
    private final List<String> locationIdentifiers;
    private String grid;
    private final PrintStream printStream;

    Board(PrintStream printStream) {
        this.printStream = printStream;

        locationIdentifiers = IntStream.rangeClosed(1, 9)
            .mapToObj(String::valueOf)
            .collect(toList());
        grid = "" +
            "1|2|3\n" +
            "-----\n" +
            "4|5|6\n" +
            "-----\n" +
            "7|8|9";
    }

    void inspect() {
        printStream.println(grid);
    }

    void mark(String location, String symbol) throws LocationTakenException {
        List<String> takenLocations = locationIdentifiers.stream()
            .filter(identifier -> !grid.contains(identifier))
            .collect(toList());
        if (takenLocations.contains(location)) {
            throw new LocationTakenException();
        }
        grid = grid.replace(location, symbol);
    }
}
