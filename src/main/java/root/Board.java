package root;

import java.io.PrintStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static java.lang.Math.sqrt;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Board {
    private static final String COLUMN_DELIMITER = "|";
    private static final String ROW_DELIMITER = "\n-----\n";

    private final List<Location> locations = IntStream.rangeClosed(1, 9)
        .mapToObj(String::valueOf)
        .map(Location::new)
        .collect(toList());

    private final PrintStream printStream;

    Board(PrintStream printStream) {
        this.printStream = printStream;
    }

    void inspect() {
        printStream.println(buildGrid());
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

    private String buildGrid() {
        int gridSize = (int) sqrt(locations.size());
        return IntStream.rangeClosed(1, locations.size())
            .filter(elementNumber -> elementNumber % gridSize == 0)
            .mapToObj(endOfRowElementNumber ->
                locations.stream()
                    .map(Location::display)
                    .collect(toList())
                    .subList(endOfRowElementNumber - gridSize, endOfRowElementNumber)
                    .stream()
                    .collect(joining(COLUMN_DELIMITER)))
            .collect(joining(ROW_DELIMITER));
    }
}
