package root;

public class Main {
    public static void main(String[] args) {
        Board board = new Board(System.out);
        new Game(board).start();
    }
}
