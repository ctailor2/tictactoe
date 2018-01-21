package root;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class GameTest {

    private Game game;
    private Board board;
    private Player playerOne;
    private Player playerTwo;
    private OutputStream outputStream;

    @Before
    public void setUp() throws Exception {
        board = mock(Board.class);
        playerOne = mock(Player.class);
        playerTwo = mock(Player.class);
        outputStream = new ByteArrayOutputStream();
        game = new Game(
            board,
            asList(playerOne, playerTwo),
            new PrintStream(outputStream));
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
    }

    @Test
    public void whenStarted_inspectsTheBoard_onceFilled_andDeclaresTheGameADraw() {
        when(board.isFilled()).thenReturn(true);

        game.start();

        verify(board).inspect();
        assertThat(outputStream.toString()).isEqualTo("Game is a draw\n");
    }
}