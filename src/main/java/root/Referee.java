package root;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
        ArrayList<List<Location>> winPatterns = new ArrayList<>();
        winPatterns.addAll(board.rows());
        winPatterns.addAll(board.columns());
        winPatterns.addAll(board.diagonals());
        return winPatterns.stream()
            .map(locations -> locations.stream().map(Location::display).collect(toList()))
            .anyMatch(row -> new HashSet<>(row).size() == 1);
    }
}
