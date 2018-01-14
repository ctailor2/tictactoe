package root;

class Game {
    private final Board board;
    private final Player player;

    Game(Board board, Player player) {
        this.board = board;
        this.player = player;
    }

    void start() {
        board.draw();
        String move = player.move();
        board.mark(move);
        board.draw();
    }
}
