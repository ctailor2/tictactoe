package root;

class Game {
    private final Board board;

    Game(Board board) {
        this.board = board;
    }

    void start() {
        board.draw();
    }
}
