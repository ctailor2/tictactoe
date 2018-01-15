package root;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.IntStream;

import static java.lang.Math.sqrt;
import static java.util.stream.Collectors.joining;

class Board {
    private static final String COLUMN_DELIMITER = "|";
    private static final String ROW_DELIMITER = "\n-----\n";

    private final HashMap<String, String> locationMarks = new HashMap<String, String>() {{
        put("1", "1");
        put("2", "2");
        put("3", "3");
        put("4", "4");
        put("5", "5");
        put("6", "6");
        put("7", "7");
        put("8", "8");
        put("9", "9");
    }};
    private final PrintStream printStream;

    Board(PrintStream printStream) {
        this.printStream = printStream;
    }

    void inspect() {
        printStream.println(buildGrid());
    }

    void mark(String location, String symbol) throws LocationTakenException {
        if (locationMarks.containsKey(location)) {
            boolean locationIsAlreadyTaken = !locationMarks.get(location).equals(location);
            if (locationIsAlreadyTaken) {
                throw new LocationTakenException();
            }
            locationMarks.replace(location, symbol);
        }
    }

    private String buildGrid() {
        int gridSize = (int) sqrt(locationMarks.size());
        return IntStream.rangeClosed(1, locationMarks.size())
            .filter(elementNumber -> elementNumber % gridSize == 0)
            .mapToObj(endOfRowElementNumber ->
                new ArrayList<>(locationMarks.values())
                    .subList(endOfRowElementNumber - gridSize, endOfRowElementNumber)
                    .stream()
                    .collect(joining(COLUMN_DELIMITER)))
            .collect(joining(ROW_DELIMITER));
    }
}
