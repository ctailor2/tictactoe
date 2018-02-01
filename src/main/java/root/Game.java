package root;

import java.io.PrintStream;
import java.util.List;
import java.util.ListIterator;

import static root.Game.Result.INCONCLUSIVE;

class Game {
    private final Referee referee;
    private final Board board;
    private final List<Player> players;
    private final PrintStream printStream;
    private ListIterator<Player> playersInTurnOrder;

    Game(Referee referee, Board board, List<Player> players, PrintStream printStream) {
        this.referee = referee;
        this.board = board;
        this.players = players;
        this.printStream = printStream;
    }

    void start() {
        startNextRound();
        do {
            if (roundIsOver()) {
                startNextRound();
            }
            nextPlayer().takeTurn(board);
        } while (referee.determineResult(board).equals(INCONCLUSIVE));
        end();
    }

    private void end() {
        printStream.println(board.grid());
        switch (referee.determineResult(board)) {
            case DRAW:
                printStream.println("Game is a draw.");
                break;
            case WIN:
                int playerNumber = currentPlayerNumber();
                printStream.printf("Player %d wins!\n", playerNumber);
                break;
        }
    }

    private int currentPlayerNumber() {
        return playersInTurnOrder.nextIndex();
    }

    private Player nextPlayer() {
        return playersInTurnOrder.next();
    }

    private boolean roundIsOver() {
        return !playersInTurnOrder.hasNext();
    }

    private void startNextRound() {
        playersInTurnOrder = players.listIterator();
    }

    enum Result {
        INCONCLUSIVE,
        DRAW,
        WIN
    }
}
