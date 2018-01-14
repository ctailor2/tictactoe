package root;

class Game {
    private final Board board;
    private final Player playerOne;
    private final Player playerTwo;

    Game(Board board, Player playerOne, Player playerTwo) {
        this.board = board;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    void start() {
        playerOne.takeTurn(board);
        playerTwo.takeTurn(board);
    }
}
