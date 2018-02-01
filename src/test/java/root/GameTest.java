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
import static root.Game.Result.*;

public class GameTest {

    private Game game;
    private Board board;
    private Player playerOne;
    private Player playerTwo;
    private OutputStream outputStream;
    private Referee referee;

    @Before
    public void setUp() throws Exception {
        board = mock(Board.class);
        playerOne = mock(Player.class);
        playerTwo = mock(Player.class);
        referee = mock(Referee.class);
        outputStream = new ByteArrayOutputStream();
        game = new Game(
            referee,
            board,
            asList(playerOne, playerTwo),
            new PrintStream(outputStream));
    }

    @Test
    public void whenStarted_alternatesTurnsBetweenPlayers_beginningWithFirstPlayer_untilTheRefereeDeterminesAConclusiveResult() {
        InOrder turnOrder = inOrder(playerOne, playerTwo, playerOne);
        when(referee.determineResult(board)).thenReturn(INCONCLUSIVE, INCONCLUSIVE, DRAW);

        game.start();

        turnOrder.verify(playerOne).takeTurn(board);
        turnOrder.verify(playerTwo).takeTurn(board);
        turnOrder.verify(playerOne).takeTurn(board);
        turnOrder.verifyNoMoreInteractions();
    }

    @Test
    public void whenStarted_showsTheBoard_onceTheRefereeDeterminesAConclusiveResult() {
        when(referee.determineResult(board)).thenReturn(DRAW);
        String boardGrid = "this is a board grid";
        when(board.grid()).thenReturn(boardGrid);

        game.start();

        assertThat(outputStream.toString()).contains(boardGrid + "\n");
    }

    @Test
    public void whenStarted_declaresTheGameADraw_whenTheRefereeDeterminesTheResultIsADraw() {
        when(referee.determineResult(board)).thenReturn(DRAW);

        game.start();

        assertThat(outputStream.toString()).contains("Game is a draw.\n");
    }

    @Test
    public void whenStarted_declaresThePlayerWithTheLastTurnAsTheWinner_whenTheRefereeDeterminesTheResultIsAWin() {
        when(referee.determineResult(board)).thenReturn(INCONCLUSIVE, WIN);

        game.start();

        assertThat(outputStream.toString()).contains("Player 2 wins!\n");
    }
}