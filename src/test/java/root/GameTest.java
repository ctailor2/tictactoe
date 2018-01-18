package root;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import static java.util.Arrays.asList;
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
        game = new Game(board, asList(playerOne, playerTwo));
    }

    @Test
    public void whenStarted_alternatesTurnsBetweenPlayers_beginningWithFirstPlayer_untilTheBoardIsFilled() {
        InOrder turnOrder = inOrder(playerOne, playerTwo, playerOne);
        when(board.isFilled()).thenReturn(false, false, false, true);

        game.start();

        turnOrder.verify(playerOne).takeTurn(board);
        turnOrder.verify(playerTwo).takeTurn(board);
        turnOrder.verify(playerOne).takeTurn(board);
        turnOrder.verifyNoMoreInteractions();
        verify(board).inspect();
    }
}