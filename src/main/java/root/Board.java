package root;

import java.io.PrintStream;

class Board {
    private final PrintStream printStream;

    Board(PrintStream printStream) {
        this.printStream = printStream;
    }

    void draw() {
        printStream.println("" +
            "1|2|3\n" +
            "-----\n" +
            "4|5|6\n" +
            "-----\n" +
            "7|8|9");
    }
}
