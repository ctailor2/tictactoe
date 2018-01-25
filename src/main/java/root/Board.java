package root;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.*;

class Board {
    private static final String COLUMN_DELIMITER = "|";
    private static final String ROW_DELIMITER = "\n-----\n";

    private final List<Location> locations = new ArrayList<>();

    private final PrintStream printStream;
    private final int gridSize;

    Board(PrintStream printStream, int gridSize) {
        this.printStream = printStream;
        this.gridSize = gridSize;

        IntStream.rangeClosed(1, gridSize)
            .forEachOrdered(rowNumber ->
                IntStream.rangeClosed(1, gridSize)
                    .forEachOrdered(columnNumber ->
                        locations.add(new Location(rowNumber, columnNumber))));
    }

    void inspect() {
        String grid = rows().stream()
            .map(columns -> columns.stream()
                .map(Location::display)
                .collect(joining(COLUMN_DELIMITER)))
            .collect(joining(ROW_DELIMITER));
        printStream.println(grid);
    }

    void mark(String locationIdentifier, String symbol) throws LocationTakenException {
        Optional<Location> locationOptional = locations.stream()
            .filter(location -> location.hasIdentifier(locationIdentifier))
            .findFirst();
        if (locationOptional.isPresent()) {
            Location location = locationOptional.get();
            if (location.isMarked()) {
                throw new LocationTakenException();
            }
            location.markWith(symbol);
        }
    }

    boolean isFilled() {
        return locations.stream().allMatch(Location::isMarked);
    }

    List<List<Location>> rows() {
        return locations.stream()
            .collect(groupingBy(Location::getRowNumber))
            .entrySet()
            .stream()
            .map(Map.Entry::getValue)
            .collect(toList());
    }

    List<List<Location>> columns() {
        return locations.stream()
            .collect(groupingBy(Location::getColumnNumber))
            .entrySet()
            .stream()
            .map(Map.Entry::getValue)
            .collect(toList());
    }

    List<List<Location>> diagonals() {
        int lastRowIndex = gridSize - 1;

        ArrayList<Location> firstDiagonal = new ArrayList<>();
        ArrayList<Location> secondDiagonal = new ArrayList<>();
        for (int rowIndex = 0; rowIndex <= lastRowIndex; rowIndex++) {
            List<Location> row = rows().get(rowIndex);

            firstDiagonal.add(row.get(rowIndex));
            secondDiagonal.add(row.get(lastRowIndex - rowIndex));
        }

        return asList(firstDiagonal, secondDiagonal);
    }
}
