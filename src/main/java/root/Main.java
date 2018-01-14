package root;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        PrintStream standardOutput = System.out;
        InputStream standardInput = System.in;
        Board board = new Board(standardOutput);
        Player player = new Player(standardOutput, new BufferedReader(new InputStreamReader(standardInput)));
        new Game(board, player).start();
    }
}
