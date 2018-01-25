package root;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

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
    public void rowsContainHorizontalGroupsOfLocations() {
        List<List<Location>> rows = board.rows();

        assertThat(rows).hasSize(3);
        assertThat(rows.get(0)).extracting(Location::display).containsExactly("1", "2", "3");
        assertThat(rows.get(1)).extracting(Location::display).containsExactly("4", "5", "6");
        assertThat(rows.get(2)).extracting(Location::display).containsExactly("7", "8", "9");
    }

    @Test
    public void columnsContainVerticalGroupsOfLocations() {
        List<List<Location>> columns = board.columns();

        assertThat(columns).hasSize(3);
        
        assertThat(columns.get(0)).extracting(Location::display).containsExactly("1", "4", "7");
        assertThat(columns.get(1)).extracting(Location::display).containsExactly("2", "5", "8");
        assertThat(columns.get(2)).extracting(Location::display).containsExactly("3", "6", "9");
    }
}