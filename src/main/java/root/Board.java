package root;

import java.io.PrintStream;

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

    void inspect() {
        printStream.println(grid);
    }

    void mark(String location) {
        grid = grid.replace(location, "X");
    }
}
