package root;

import java.io.PrintStream;
import java.util.List;
import java.util.ListIterator;

class Game {
    private final Board board;
    private final List<Player> players;
    private final PrintStream printStream;
    private ListIterator<Player> playersInTurnOrder;

    Game(Board board, List<Player> players, PrintStream printStream) {
        this.board = board;
        this.players = players;
        this.printStream = printStream;
    }

    void start() {
        startNextRound();
        while (!board.isFilled()) {
            nextPlayer().takeTurn(board);
            if (roundHasEnded()) {
                startNextRound();
            }
        }
        board.inspect();
        printStream.println("Game is a draw");
    }

    private Player nextPlayer() {
        return playersInTurnOrder.next();
    }

    private boolean roundHasEnded() {
        return !playersInTurnOrder.hasNext();
    }

    private void startNextRound() {
        playersInTurnOrder = players.listIterator();
    }
}
