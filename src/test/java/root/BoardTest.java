package root;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardTest {
    @Test
    public void outputsA3x3GridWhenDrawn() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        Board board = new Board(printStream);

        board.draw();

        assertThat(outputStream.toString()).isEqualTo("" +
            "1|2|3\n" +
            "-----\n" +
            "4|5|6\n" +
            "-----\n" +
            "7|8|9\n");
    }
}