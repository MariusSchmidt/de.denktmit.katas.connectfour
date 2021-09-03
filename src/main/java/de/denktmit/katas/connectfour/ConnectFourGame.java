package de.denktmit.katas.connectfour;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.IntStream;

import static de.denktmit.katas.connectfour.GameState.*;
import static de.denktmit.katas.connectfour.Slot.EMPTY;

/**
 *
 */
public class ConnectFourGame {

    private static final int NUMBER_OF_COLUMNS = 7;
    private static final int NUMBER_OF_ROWS = 6;
    private static final int TOKENS_IN_ROW_TO_WIN = 4;

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
        if (slotIndex.isEmpty() || currentState == PLAYER_ONE_WON || currentState == PLAYER_TWO_WON) {
            return;
        }
        processTokenDrop(slotIndex.getAsInt());

    }

    private void processTokenDrop(int slotIndex) {
        slots[slotIndex] = (currentState == PLAYER_ONE_TURN)? Slot.P1 : Slot.P2;
        if (isWon(slotIndex)){
            currentState = (currentState == PLAYER_ONE_TURN)
                    ? PLAYER_ONE_WON
                    : PLAYER_TWO_WON;
        } else if (isDraw()) {
            currentState = DRAW;
        } else {
            currentState = (currentState == PLAYER_ONE_TURN)? PLAYER_TWO_TURN : PLAYER_ONE_TURN;
        }
    }

    private boolean isWon(int slotIndex) {
        return hasConnectedFourInDirection(slotIndex, 1, 1)
                || hasConnectedFourInDirection(slotIndex, -1, 1)
                || hasConnectedFourInDirection(slotIndex, 1, 0)
                || hasConnectedFourInDirection(slotIndex, 0, 1);
    }

    private boolean hasConnectedFourInDirection(int slotIndex, int rowDirection, int columnDirection) {
        List<Integer> slotsToCheck = getSlotsToCheck(slotIndex, rowDirection, columnDirection);
        if (slotsToCheck.size() < TOKENS_IN_ROW_TO_WIN) {
            return false;
        }
        return hasConnectedFourInDirection(slotsToCheck);
    }

    private List<Integer> getSlotsToCheck(int slotIndex, int rowDirection, int columnDirection) {
        Deque<Integer> deque = new ArrayDeque<>(7);
        deque.add(slotIndex);
        for(int i = 1; i < TOKENS_IN_ROW_TO_WIN; i++) {
            getOffsetIndex(slotIndex, rowDirection * -i, columnDirection * -i)
                    .ifPresent(deque::addLast);
            getOffsetIndex(slotIndex, rowDirection * i, columnDirection * i)
                    .ifPresent(deque::addFirst);
        }
        return deque.stream().toList();
    }

    private OptionalInt getOffsetIndex(int index, int columnOffset, int rowOffset) {
        int remainder = index % NUMBER_OF_COLUMNS;
        int columnOffsetIndex = remainder + columnOffset;
        if (columnOffsetIndex < 0 || columnOffsetIndex >= NUMBER_OF_COLUMNS) {
            return OptionalInt.empty();
        }
        int offsetIndex = index + columnOffset + (rowOffset * NUMBER_OF_COLUMNS);
        return (offsetIndex >= 0 && offsetIndex < NUMBER_OF_COLUMNS * NUMBER_OF_ROWS)
                ? OptionalInt.of(offsetIndex)
                : OptionalInt.empty();
    }

    private boolean hasConnectedFourInDirection(List<Integer> slotsToCheck) {
        Slot previousSlot = null;
        int connectCount = 0;
        for (Integer currentSlotIndex : slotsToCheck){
            Slot currentSlot = slots[currentSlotIndex];
            if (currentSlot != previousSlot) {
                connectCount = 0;
            }
            if (++connectCount == TOKENS_IN_ROW_TO_WIN) {
                return true;
            }
            previousSlot = currentSlot;
        }
        return false;
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
