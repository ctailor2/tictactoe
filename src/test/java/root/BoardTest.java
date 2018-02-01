package root;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardTest {

    private Board board;

    @Before
    public void setUp() throws Exception {
        board = new Board(3);
    }

    @Test
    public void hasProperlyFormattedGridWithOfSize3() {
        assertThat(board.grid()).isEqualTo("" +
            "1|2|3\n" +
            "-----\n" +
            "4|5|6\n" +
            "-----\n" +
            "7|8|9");
    }

    @Test
    public void hasProperlyFormattedGridOfSize4() {
        Board board = new Board(4);

        assertThat(board.grid()).isEqualTo("" +
            " 1| 2| 3| 4\n" +
            "-----------\n" +
            " 5| 6| 7| 8\n" +
            "-----------\n" +
            " 9|10|11|12\n" +
            "-----------\n" +
            "13|14|15|16"
        );
    }

    @Test
    public void replacesTheSelectedLocationWithTheDesiredSymbol_whenMarked_atALocationThatExists() throws LocationTakenException {
        board.mark("4", "$");

        assertThat(board.grid()).isEqualTo("" +
            "1|2|3\n" +
            "-----\n" +
            "$|5|6\n" +
            "-----\n" +
            "7|8|9");
    }

    @Test
    public void doesNothing_whenMarked_atALocationThatDoesNotExist() throws LocationTakenException {
        board.mark("does not exist", "$");

        assertThat(board.grid()).isEqualTo("" +
            "1|2|3\n" +
            "-----\n" +
            "4|5|6\n" +
            "-----\n" +
            "7|8|9");
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

    @Test
    public void diagonalsContainDiagonalGroupsOfLocations() {
        List<List<Location>> diagonals = board.diagonals();

        assertThat(diagonals).hasSize(2);

        assertThat(diagonals.get(0)).extracting(Location::display).containsExactly("1", "5", "9");
        assertThat(diagonals.get(1)).extracting(Location::display).containsExactly("3", "5", "7");
    }
}