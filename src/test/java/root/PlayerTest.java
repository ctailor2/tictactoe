package root;

import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerTest {

    private Player player;
    private ByteArrayOutputStream outputStream;
    private String selectedMove;
    private BufferedReader bufferedReader;

    @Before
    public void setUp() throws Exception {
        outputStream = new ByteArrayOutputStream();
        selectedMove = "someMove";
        String input = selectedMove + System.lineSeparator();
        bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())));
        player = new Player(
            new PrintStream(outputStream),
            bufferedReader);
    }

    @Test
    public void promptsForANumberOnMove() {
        player.move();

        assertThat(outputStream.toString()).isEqualTo("Enter a number indicating where you want to mark the board: ");
    }

    @Test
    public void capturesTheSelectedMove() throws IOException {
        String move = player.move();

        assertThat(move).isEqualTo(selectedMove);
    }

    @Test
    public void capturesAPassMoveWhenTheSelectedMoveCannotBeCaptured() throws IOException {
        bufferedReader.close();

        String move = player.move();

        assertThat(move).isEqualTo("PASS");
    }
}