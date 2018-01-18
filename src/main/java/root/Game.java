package root;

import java.util.List;
import java.util.ListIterator;

class Game {
    private final Board board;
    private final List<Player> players;
    private ListIterator<Player> playersInTurnOrder;

    Game(Board board, List<Player> players) {
        this.board = board;
        this.players = players;
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
