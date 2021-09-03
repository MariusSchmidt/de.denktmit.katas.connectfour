package de.denktmit.katas.connectfour;

import java.util.Arrays;
import java.util.Collections;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import static de.denktmit.katas.connectfour.GameState.*;
import static de.denktmit.katas.connectfour.Slot.EMPTY;

/**
 *
 */
public class ConnectFourGame {

    private static final int NUMBER_OF_COLUMNS = 7;
    private static final int NUMBER_OF_ROWS = 6;

    private static final String PLAYFIELD_ROW_BORDER = String.join(
            "", Collections.nCopies(NUMBER_OF_COLUMNS, "+-")) + "+";
    private static final String PLAYFIELD_CELL_DELIMITER = "|";
    private static final String PLAYFIELD_CELL = PLAYFIELD_CELL_DELIMITER + "%s";

    /**
     * The playfield is modeled as an array of slots accessible by a single index starting in
     * the bottom left corner. The index is increased by one for every step to the right and
     * increased by {@link #NUMBER_OF_COLUMNS} by every step to the top.
     *
     *<pre>
     * +-+-+-+-+-+-+-+
     * | | | | | | | |
     * +-+-+-+-+-+-+-+
     * | | | | | | | |
     * +-+-+-+-+-+-+-+
     * | | | | | | | |
     * +-+-+-+-+-+-+-+
     * |14|15|16|17|18|19|20|
     * +-+-+-+-+-+-+-+
     * |07|08|09|10|11|12|13|
     * +-+-+-+-+-+-+-+
     * |00|01|02|03|04|05|06|
     * +-+-+-+-+-+-+-+
     * </pre>
     */
    private final Slot[] slots;
    private GameState currentState = PLAYER_ONE_TURN;

    public ConnectFourGame() {
        slots = new Slot[NUMBER_OF_COLUMNS * NUMBER_OF_ROWS];
        Arrays.fill(slots, EMPTY);
    }

    /**
     * Prints the playfield as ASCII text
     *
     * @return An ASCII representation of the current playfield
     */
    public String printPlayfield() {
        StringBuilder playfield = new StringBuilder();
        for (int row = NUMBER_OF_ROWS - 1; row >= 0; row--) {
            playfield.append(PLAYFIELD_ROW_BORDER).append("\n");
            playfield.append(printSlotRow(row)).append("\n");
        }
        playfield.append(PLAYFIELD_ROW_BORDER).append("\n");
        return playfield.toString();
    }

    private String printSlotRow(int row) {
        StringBuilder slotRow = new StringBuilder();
        for (int currentColumnIndex = 0; currentColumnIndex < NUMBER_OF_COLUMNS; currentColumnIndex++) {
            int rowIndexOffset = row * NUMBER_OF_COLUMNS;
            int slotIndex = rowIndexOffset + currentColumnIndex;
            slotRow.append(String.format(PLAYFIELD_CELL, slots[slotIndex].getFieldRepresentation()));
        }
        slotRow.append(PLAYFIELD_CELL_DELIMITER);
        return slotRow.toString();
    }

    /**
     * Drop a coin to the n-th column. If column is already full or column index is out of bounds
     * the drop is ignored. The method therefore rejects any invalid input by ignoring it.
     *
     * @param columnIndex The column to drop the token at. Columns index start at 0.
     */
    public void dropToken(int columnIndex) {
        OptionalInt slotIndex = firstEmptySlotOfColumn(columnIndex);
        if (slotIndex.isEmpty()) {
            return;
        }
        processTokenDrop(slotIndex.getAsInt());
    }

    private void processTokenDrop(Integer slotIndex) {
        slots[slotIndex] = (currentState == PLAYER_ONE_TURN)? Slot.P1 : Slot.P2;
        if (isDraw()) {
            currentState = DRAW;
        } else {
            currentState = (currentState == PLAYER_ONE_TURN)? PLAYER_TWO_TURN : PLAYER_ONE_TURN;
        }
    }

    private boolean isDraw() {
        long filledSlotsCount = Arrays.stream(slots).filter(slot -> slot != EMPTY).count();
        return NUMBER_OF_COLUMNS * NUMBER_OF_ROWS == filledSlotsCount;
    }

    private OptionalInt firstEmptySlotOfColumn(int columnIndex) {
        return IntStream.range(0, slots.length)
                .filter(i -> (i % NUMBER_OF_COLUMNS == columnIndex && slots[i] == EMPTY))
                .findFirst();
    }

    public GameState getCurrentState() {
        return currentState;
    }
}
