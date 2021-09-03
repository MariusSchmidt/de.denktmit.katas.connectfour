package de.denktmit.katas.connectfour;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.denktmit.katas.connectfour.SampleData.*;
import static org.junit.jupiter.api.Assertions.*;

class ConnectFourGameTest {

    private ConnectFourGame game;

    @BeforeEach
    void setUp() {
        game = new ConnectFourGame();
    }

    @Test
    void printEmptyPlayfield() {
        assertEquals(emptyPlayfield, game.printPlayfield());
        assertEquals(game.getCurrentState(), GameState.PLAYER_ONE_TURN);
    }

    @Test
    void printPlayfieldAfterDropsToColumn5() {
        game.dropToken(4);
        assertEquals(playfieldAfterDropsToColumn5, game.printPlayfield());
        assertEquals(game.getCurrentState(), GameState.PLAYER_TWO_TURN);
    }

    @Test
    void dropsToInvalidColumns() {
        game.dropToken(-1);
        game.dropToken(7);
        game.dropToken(-2);
        assertEquals(emptyPlayfield, game.printPlayfield());
        assertEquals(game.getCurrentState(), GameState.PLAYER_ONE_TURN);
    }

    @Test
    void printPlayfieldAfterDropsToColumn1112234() {
        game.dropToken(0);
        game.dropToken(0);
        game.dropToken(0);
        game.dropToken(1);
        game.dropToken(1);
        game.dropToken(2);
        game.dropToken(3);
        assertEquals(game.getCurrentState(), GameState.PLAYER_TWO_TURN);
        assertEquals(playfieldAfterDropsToColumns1112234, game.printPlayfield());
    }

    @Test
    void printPlayfieldAfterDropsToColumn1111111() {
        game.dropToken(0);
        game.dropToken(0);
        game.dropToken(0);
        game.dropToken(0);
        game.dropToken(0);
        game.dropToken(0);
        game.dropToken(0);
        assertEquals(game.getCurrentState(), GameState.PLAYER_ONE_TURN);
        assertEquals(playfieldAfterDropsToColumns1111111, game.printPlayfield());
    }

    @Test
    void printPlayfieldAfterDraw() {
        dropAlternating(0,1);
        dropAlternating(1,0);
        dropAlternating(2,3);
        dropAlternating(3,2);
        dropAlternating(4,5);
        dropAlternating(5,4);
        dropAlternating(6,6);
        assertEquals(game.getCurrentState(), GameState.DRAW);
        assertEquals(playfieldAfterDraw, game.printPlayfield());
    }

    private void dropAlternating(int firstColumn, int secondColumn) {
        for (int i = 0; i < 3; i++) {
            game.dropToken(firstColumn);
            game.dropToken(secondColumn);
        }
    }


    @Test
    void printPlayfieldAfterDrawAdditionalDrops() {
        dropAlternating(0,1);
        dropAlternating(1,0);
        dropAlternating(2,3);
        dropAlternating(3,2);
        dropAlternating(4,5);
        dropAlternating(5,4);
        dropAlternating(6,6);
        game.dropToken(6);
        assertEquals(game.getCurrentState(), GameState.DRAW);
        assertEquals(playfieldAfterDraw, game.printPlayfield());
    }


}