package root;

import java.io.*;

import static java.util.Arrays.asList;

public class Main {
    public static void main(String[] args) throws IOException {
        PrintStream standardOutput = System.out;
        InputStream standardInput = System.in;
        Board board = new Board(standardOutput);
        Player playerOne = new Player("X", standardOutput, new BufferedReader(new InputStreamReader(standardInput)));
        Player playerTwo = new Player("O", standardOutput, new BufferedReader(new InputStreamReader(standardInput)));
        new Game(board, asList(playerOne, playerTwo), standardOutput).start();
    }
}
