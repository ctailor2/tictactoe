package root;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardTest {

    private Board board;
    private ByteArrayOutputStream outputStream;

    @Before
    public void setUp() throws Exception {
        outputStream = new ByteArrayOutputStream();
        board = new Board(new PrintStream(outputStream));
    }

    @Test
    public void outputsA3x3GridWhenDrawn() {
        board.draw();

        assertThat(outputStream.toString()).isEqualTo("" +
            "1|2|3\n" +
            "-----\n" +
            "4|5|6\n" +
            "-----\n" +
            "7|8|9\n");
    }

    @Test
    public void replacesTheSelectedLocationWithAnXWhenMarked() {
        board.mark("4");

        board.draw();
        assertThat(outputStream.toString()).isEqualTo("" +
            "1|2|3\n" +
            "-----\n" +
            "X|5|6\n" +
            "-----\n" +
            "7|8|9\n");
    }
}