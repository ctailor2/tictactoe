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
import static root.Board.Conclusion.DRAW;
import static root.Board.Conclusion.WIN;

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
    public void whenStarted_alternatesTurnsBetweenPlayers_beginningWithFirstPlayer_untilTheBoardHasAConclusion() {
        InOrder turnOrder = inOrder(playerOne, playerTwo, playerOne);
        when(board.hasConclusion()).thenReturn(false, false, false, true);
        when(board.conclusion()).thenReturn(DRAW);

        game.start();

        turnOrder.verify(playerOne).takeTurn(board);
        turnOrder.verify(playerTwo).takeTurn(board);
        turnOrder.verify(playerOne).takeTurn(board);
        turnOrder.verifyNoMoreInteractions();
    }

    @Test
    public void whenStarted_inspectsTheBoard_onceItHasAConclusion() {
        when(board.hasConclusion()).thenReturn(true);
        when(board.conclusion()).thenReturn(DRAW);

        game.start();

        verify(board).inspect();
    }

    @Test
    public void whenStarted_declaresTheGameADraw_whenTheBoardConclusionIsADraw() {
        when(board.hasConclusion()).thenReturn(true);
        when(board.conclusion()).thenReturn(DRAW);

        game.start();

        assertThat(outputStream.toString()).isEqualTo("Game is a draw.\n");
    }

    @Test
    public void whenStarted_declaresThePlayerWithTheLastTurnAsTheWinner_whenTheBoardConclusionIsAWin() {
        when(board.hasConclusion()).thenReturn(false, false, true);
        when(board.conclusion()).thenReturn(WIN);

        game.start();

        assertThat(outputStream.toString()).isEqualTo("Player 2 wins!\n");
    }
}