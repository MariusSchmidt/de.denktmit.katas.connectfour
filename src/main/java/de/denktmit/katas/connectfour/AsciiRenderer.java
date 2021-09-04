package de.denktmit.katas.connectfour;

import java.util.Collections;

public class AsciiRenderer {

    private static final String PLAYFIELD_CELL_DELIMITER = "|";
    private static final String PLAYFIELD_CELL = PLAYFIELD_CELL_DELIMITER + "%s";
    private final ConnectFourGame game;
    private final String rowBorder;

    public AsciiRenderer(ConnectFourGame game) {
        this.game = game;
        rowBorder = String.join("", Collections.nCopies(game.width(), "+-")) + "+";
    }

    /**
     * Prints the playfield as ASCII text
     *
     * @return An ASCII representation of the current playfield
     */
    public String printPlayfield() {
        StringBuilder playfield = new StringBuilder();
        for (int row = game.height() - 1; row >= 0; row--) {
            playfield.append(rowBorder).append("\n");
            playfield.append(printSlotRow(row)).append("\n");
        }
        playfield.append(rowBorder).append("\n");
        return playfield.toString();
    }

    private String printSlotRow(int row) {
        final StringBuilder slotRow = new StringBuilder();
        game.getPlayerTokensInRow(row)
                .forEach(p -> slotRow.append(String.format(PLAYFIELD_CELL, playerRepresentation(p))));
        return slotRow.append(PLAYFIELD_CELL_DELIMITER).toString();
    }

    private Character playerRepresentation(Player p) {
        if (p == null) return ' ';
        return (p == Player.PLAYER_ONE) ? '1' : '2';
    }

}
