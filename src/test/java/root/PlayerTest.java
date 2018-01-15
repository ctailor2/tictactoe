package root;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PlayerTest {

    private Player player;
    private ByteArrayOutputStream outputStream;
    private String firstSelectedLocation;
    private BufferedReader bufferedReader;
    private Board board;
    private String symbol;
    private String secondSelectedLocation;

    @Before
    public void setUp() throws Exception {
        firstSelectedLocation = "someLocation";
        secondSelectedLocation = "someOtherLocation";
        String input = firstSelectedLocation +
            System.lineSeparator() +
            secondSelectedLocation;
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
        List<String> availableLocations = singletonList(firstSelectedLocation);
        when(board.inspect()).thenReturn(availableLocations);

        player.takeTurn(board);

        verify(board).inspect();
    }

    @Test
    public void promptsForALocation_whenTakingATurn() {
        List<String> availableLocations = singletonList(firstSelectedLocation);
        when(board.inspect()).thenReturn(availableLocations);

        player.takeTurn(board);

        assertThat(outputStream.toString()).isEqualTo("Enter a number indicating where you want to mark the board: ");
    }

    @Test
    public void rejectsTheSelection_andPromptsForALocationAgain_whenTheSelectedLocationIsAlreadyTaken_whenTakingATurn() {
        List<String> availableLocations = asList("foo", secondSelectedLocation);
        when(board.inspect()).thenReturn(availableLocations);

        player.takeTurn(board);

        assertThat(outputStream.toString()).containsSequence(
            "Location already taken!\n",
            "Enter a number indicating where you want to mark the board: "
        );
    }

    @Test
    public void marksTheBoard_atTheSelectedLocation_withThePlayerSymbol_whenTakingATurn() throws IOException {
        List<String> availableLocations = singletonList(firstSelectedLocation);
        when(board.inspect()).thenReturn(availableLocations);

        player.takeTurn(board);

        verify(board).mark(firstSelectedLocation, symbol);
    }

    @Test
    public void marksTheBoard_atThePassLocation_withThePlayerSymbol_whenTheSelectedLocationCannotBeCaptured_whenTakingATurn() throws IOException {
        bufferedReader.close();

        player.takeTurn(board);

        verify(board).mark("PASS", symbol);
    }
}