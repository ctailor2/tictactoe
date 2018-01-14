package root;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class GameTest {

    private Game game;
    private Board board;
    private Player playerOne;
    private Player playerTwo;

    @Before
    public void setUp() throws Exception {
        board = mock(Board.class);
        playerOne = mock(Player.class);
        playerTwo = mock(Player.class);
        game = new Game(board, playerOne, playerTwo);
    }

    @Test
    public void playerOneTakesTheirTurnWithTheBoardWhenStarted() {
        game.start();

        verify(playerOne).takeTurn(board);
    }

    @Test
    public void playerTwoTakesTheirTurnWithTheBoardWhenStarted() {
        game.start();

        verify(playerTwo).takeTurn(board);
    }

    @Test
    public void inspectsTheBoardWhenStarted() {
        game.start();

        verify(board).inspect();
    }
}