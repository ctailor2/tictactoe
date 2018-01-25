package root;

import java.util.HashSet;

import static java.util.stream.Collectors.toList;

class Referee {
    Game.Result determineResult(Board board) {
        if (aWinPatternExists(board)) {
            return Game.Result.WIN;
        } else if (board.isFilled()) {
            return Game.Result.DRAW;
        } else {
            return Game.Result.INCONCLUSIVE;
        }
    }

    private boolean aWinPatternExists(Board board) {
        return board.rows().stream()
            .map(locations -> locations.stream().map(Location::display).collect(toList()))
            .anyMatch(row -> new HashSet<>(row).size() == 1);
    }
}
