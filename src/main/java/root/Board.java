package root;

import java.io.PrintStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;

class Board {
    private final List<Location> locations;
    private String grid;
    private final PrintStream printStream;

    Board(PrintStream printStream) {
        locations = asList(
            new Location("1"),
            new Location("2"),
            new Location("3"),
            new Location("4"),
            new Location("5"),
            new Location("6"),
            new Location("7"),
            new Location("8"),
            new Location("9")
        );
        grid = "" +
            "1|2|3\n" +
            "-----\n" +
            "4|5|6\n" +
            "-----\n" +
            "7|8|9";
        this.printStream = printStream;
    }

    void inspect() {
        Integer gridSize = 3;
        String grid  = IntStream.rangeClosed(1, locations.size())
            .filter(elementNumber -> elementNumber % gridSize == 0)
            .mapToObj(rowEndingElementNumber ->
                locations.subList(rowEndingElementNumber - gridSize, rowEndingElementNumber))
            .map(rowLocations -> rowLocations.stream()
                .map(Location::getDisplay)
                .collect(Collectors.joining("|")))
            .collect(Collectors.joining("\n-----\n"));
        printStream.println(grid);
    }

    void mark(String selectedLocation, String symbol) throws LocationTakenException {
        Optional<Location> locationOptional = locations.stream()
            .filter(location -> location.getIdentifier().equals(selectedLocation))
            .findFirst();
        if (locationOptional.isPresent()){
            Location location = locationOptional.get();
            location.mark(symbol);
//            grid = grid.replace(selectedLocation, symbol);
        }
    }
}
