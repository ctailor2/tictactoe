package root;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class BoardTest {

    private Board board;
    private OutputStream outputStream;

    @Before
    public void setUp() throws Exception {
        outputStream = new ByteArrayOutputStream();
        board = new Board(new PrintStream(outputStream));
    }

    @Test
    public void outputsItsGrid_whenInspected() {
        board.inspect();

        assertThat(outputStream.toString()).isEqualTo("" +
            "1|2|3\n" +
            "-----\n" +
            "4|5|6\n" +
            "-----\n" +
            "7|8|9\n");
    }

    @Test
    public void replacesTheSelectedLocationWithTheDesiredSymbol_whenMarked_atALocationThatExists() throws LocationTakenException {
        board.mark("4", "$");

        board.inspect();
        assertThat(outputStream.toString()).isEqualTo("" +
            "1|2|3\n" +
            "-----\n" +
            "$|5|6\n" +
            "-----\n" +
            "7|8|9\n");
    }

    @Test
    public void doesNothing_whenMarked_atALocationThatDoesNotExist() throws LocationTakenException {
        board.mark("does not exist", "$");

        board.inspect();
        assertThat(outputStream.toString()).isEqualTo("" +
            "1|2|3\n" +
            "-----\n" +
            "4|5|6\n" +
            "-----\n" +
            "7|8|9\n");
    }

    @Test(expected = LocationTakenException.class)
    public void fails_whenMarked_atALocationThatIsAlreadyMarked() throws LocationTakenException {
        board.mark("4", "$");
        board.mark("4", "$");
    }

    @Test
    public void isNotFilled_whenAnyLocationsAreNotMarked() {
        boolean filled = board.isFilled();

        assertThat(filled).isFalse();
    }

    @Test
    public void isFilled_whenAllLocationsAreMarked() throws LocationTakenException {
        List<String> identifiers = IntStream.rangeClosed(1, 9)
            .mapToObj(String::valueOf)
            .collect(toList());
        for (String identifier : identifiers) {
            board.mark(identifier, "$");
        }

        boolean filled = board.isFilled();

        assertThat(filled).isTrue();
    }
}