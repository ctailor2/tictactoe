package root;

import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class PlayerTest {

    private Player player;
    private ByteArrayOutputStream outputStream;
    private String selectedLocation;
    private BufferedReader bufferedReader;
    private Board board;
    private String symbol;

    @Before
    public void setUp() throws Exception {
        selectedLocation = "someLocation";
        String input = selectedLocation + System.lineSeparator();
        symbol = "@";

        outputStream = new ByteArrayOutputStream();
        bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())));
        player = new Player(
            symbol,
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
    public void promptsForALocation_whenTakingATurn() {
        player.takeTurn(board);

        assertThat(outputStream.toString()).isEqualTo("Enter a number indicating where you want to mark the board: ");
    }

    @Test
    public void rejectsSelection_andPromptsForALocationAgain_whenTheSelectedLocationIsTaken_whenTakingATurn() throws LocationTakenException {
        doThrow(new LocationTakenException()).when(board).mark(selectedLocation, symbol);

        player.takeTurn(board);

        verify(board, times(2)).inspect();
        assertThat(outputStream.toString()).containsSequence(
            "Location already taken!\n",
            "Enter a number indicating where you want to mark the board: "
        );
    }

    @Test
    public void marksTheBoard_atTheSelectedLocation_withThePlayerSymbol_whenTakingATurn() throws IOException, LocationTakenException {
        player.takeTurn(board);

        verify(board).mark(selectedLocation, symbol);
    }

    @Test
    public void marksTheBoard_atThePassLocation_withThePlayerSymbol_whenTheSelectedLocationCannotBeCaptured_whenTakingATurn() throws IOException, LocationTakenException {
        bufferedReader.close();

        player.takeTurn(board);

        verify(board).mark("PASS", symbol);
    }
}