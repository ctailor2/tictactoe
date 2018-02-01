package root;

import java.util.*;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.*;

class Board {
    private static final String COLUMN_DELIMITER = "|";

    private final List<Location> locations = new ArrayList<>();

    private final int gridSize;

    Board(int gridSize) {
        this.gridSize = gridSize;

        IntStream.rangeClosed(1, gridSize)
            .forEachOrdered(rowNumber ->
                IntStream.rangeClosed(1, gridSize)
                    .forEachOrdered(columnNumber -> {
                        String nextIdentifier = String.valueOf(locations.size() + 1);
                        locations.add(
                            new Location(
                                nextIdentifier,
                                rowNumber,
                                columnNumber));
                    }));
    }

    String grid() {
        int cellWidth = String.valueOf(gridSize * gridSize).length();
        int numberOfColumnsSeparators = gridSize - 1;
        int lengthOfRowDelimiter = cellWidth * gridSize + numberOfColumnsSeparators;
        char[] row = new char[lengthOfRowDelimiter];
        Arrays.fill(row, '-');
        String rowDelimiter = "\n" +
            String.valueOf(row) +
            "\n";
        return rows().stream()
            .map(columns -> columns.stream()
                .map(location ->
                    String.format("%1$" + cellWidth + "s", location.display()))
                .collect(joining(COLUMN_DELIMITER)))
            .collect(joining(rowDelimiter));
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
