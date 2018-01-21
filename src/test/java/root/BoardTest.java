package root;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static root.Board.Conclusion.DRAW;
import static root.Board.Conclusion.WIN;

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
    public void doesNotHaveConclusion_whenAnyLocationsAreNotMarked() {
        boolean filled = board.hasConclusion();

        assertThat(filled).isFalse();
    }

    @Test
    public void hasConclusion_whenAllLocationsAreMarked() throws LocationTakenException {
        List<String> identifiers = IntStream.rangeClosed(1, 9)
            .mapToObj(String::valueOf)
            .collect(toList());
        for (String identifier : identifiers) {
            board.mark(identifier, "$");
        }

        boolean hasConclusion = board.hasConclusion();

        assertThat(hasConclusion).isTrue();
    }

    @Test
    public void hasConclusion_whenAllLocationsInARowHaveTheSameMark() throws LocationTakenException {
        List<String> identifiers = asList("1", "2", "3");
        for (String identifier : identifiers) {
            board.mark(identifier, "$");
        }

        boolean hasConclusion = board.hasConclusion();

        assertThat(hasConclusion).isTrue();
    }

    @Test
    public void concludesADraw_whenAllLocationsAreMarked() throws LocationTakenException {
        List<String> identifiers = IntStream.rangeClosed(1, 9)
            .mapToObj(String::valueOf)
            .collect(toList());
        for (String identifier : identifiers) {
            board.mark(identifier, "$");
        }

        Board.Conclusion conclusion = board.conclusion();

        assertThat(conclusion).isEqualTo(DRAW);
    }

    @Test
    public void concludesAWin_whenAllLocationsInARowHaveTheSameMark() throws LocationTakenException {
        List<String> identifiers = asList("4", "5", "6");
        for (String identifier : identifiers) {
            board.mark(identifier, "$");
        }

        Board.Conclusion conclusion = board.conclusion();

        assertThat(conclusion).isEqualTo(WIN);
    }
}