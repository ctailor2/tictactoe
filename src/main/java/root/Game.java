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
        while (!board.hasConclusion()) {
            if (roundIsOver()) {
                startNextRound();
            }
            nextPlayer().takeTurn(board);
        }
        end();
    }

    private void end() {
        board.inspect();
        switch (board.conclusion()) {
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
}
