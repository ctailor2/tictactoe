package root;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class GameTest {

    private Game game;
    private Board board;
    private Player player;

    @Before
    public void setUp() throws Exception {
        board = mock(Board.class);
        player = mock(Player.class);
        game = new Game(board, player);
    }

    @Test
    public void drawsTheBoardTwiceWhenStarted() {
        game.start();

        verify(board, times(2)).draw();
    }

    @Test
    public void tellsPlayerOneToMoveWhenStarted() {
        game.start();

        verify(player).move();
    }

    @Test
    public void marksTheBoardWithPlayerOneMoveWhenStarted() {
        String playerOneMove = "someMove";
        when(player.move()).thenReturn(playerOneMove);
        
        game.start();
        
        verify(board).mark(playerOneMove);
    }
}