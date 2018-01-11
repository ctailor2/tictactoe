package root;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class GameTest {
    @Test
    public void drawsTheBoardWhenStarted() {
        Board board = mock(Board.class);
        Game game = new Game(board);

        game.start();

        verify(board).draw();
    }
}