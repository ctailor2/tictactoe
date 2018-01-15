package root;

import java.io.PrintStream;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

class Board {
    private String grid;
    private final PrintStream printStream;

    Board(PrintStream printStream) {
        grid = "" +
            "1|2|3\n" +
            "-----\n" +
            "4|5|6\n" +
            "-----\n" +
            "7|8|9";
        this.printStream = printStream;
    }

    List<String> inspect() {
        printStream.println(grid);
        return IntStream.rangeClosed(1, 9)
            .mapToObj(String::valueOf)
            .filter(location -> grid.contains(location))
            .collect(toList());
    }

    void mark(String location, String symbol) {
        grid = grid.replace(location, symbol);
    }
}
