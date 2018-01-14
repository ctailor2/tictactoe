package root;

import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PlayerTest {

    private Player player;
    private ByteArrayOutputStream outputStream;
    private String selectedMove;
    private BufferedReader bufferedReader;
    private Board board;

    @Before
    public void setUp() throws Exception {
        outputStream = new ByteArrayOutputStream();
        selectedMove = "someMove";
        String input = selectedMove + System.lineSeparator();
        bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())));
        player = new Player(
            new PrintStream(outputStream),
            bufferedReader);
        board = mock(Board.class);
    }

    @Test
    public void inspectsTheBoard_whenTakingATurn() {
        player.takeTurn(board);

        verify(board).inspect();
    }

    @Test
    public void promptsForAMove_whenTakingATurn() {
        player.takeTurn(board);

        assertThat(outputStream.toString()).isEqualTo("Enter a number indicating where you want to mark the board: ");
    }

    @Test
    public void marksTheBoard_withTheSelectedMove_whenTakingATurn() throws IOException {
        player.takeTurn(board);

        verify(board).mark(selectedMove);
    }

    @Test
    public void marksTheBoard_withAPassMoveWhenTheSelectedMoveCannotBeCaptured_whenTakingATurn() throws IOException {
        bufferedReader.close();

        player.takeTurn(board);

        verify(board).mark("PASS");
    }
}