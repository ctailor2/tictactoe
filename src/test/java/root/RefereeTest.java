package root;

import org.junit.Before;
import org.junit.Test;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static root.Game.Result.*;

public class RefereeTest {
    private Board board;

    private Referee referee;

    @Before
    public void setUp() throws Exception {
        board = mock(Board.class);
        referee = new Referee();
    }

    @Test
    public void hasAnInconclusiveResult_whenNoLocationsAreMarked() {
        Game.Result result = referee.determineResult(board);

        assertThat(result).isEqualTo(INCONCLUSIVE);
    }

    @Test
    public void resultsInADraw_whenBoardIsFilled() throws LocationTakenException {
        when(board.isFilled()).thenReturn(true);

        Game.Result result = referee.determineResult(board);

        assertThat(result).isEqualTo(DRAW);
    }

    @Test
    public void resultsInAWin_whenAllLocationsInARowHaveTheSameMark() throws LocationTakenException {
        Location location = new Location(1, 1);
        location.markWith("X");
        when(board.rows()).thenReturn(singletonList(singletonList(location)));

        Game.Result result = referee.determineResult(board);

        assertThat(result).isEqualTo(WIN);
    }

    @Test
    public void resultsInAWin_whenAllLocationsInAColumnHaveTheSameMark() {
        Location location = new Location(1, 1);
        location.markWith("X");
        when(board.columns()).thenReturn(singletonList(singletonList(location)));

        Game.Result result = referee.determineResult(board);

        assertThat(result).isEqualTo(WIN);
    }

    @Test
    public void resultsInAWin_whenAWinPatternExists_andTheBoardIsFilled() {
        Location location = new Location(1, 1);
        location.markWith("X");
        when(board.rows()).thenReturn(singletonList(singletonList(location)));
        when(board.isFilled()).thenReturn(true);

        Game.Result result = referee.determineResult(board);

        assertThat(result).isEqualTo(WIN);
    }
}