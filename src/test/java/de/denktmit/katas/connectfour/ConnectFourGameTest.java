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
    }

    @Test
    void printPlayfieldAfterDropsToColumn5() {
        game.dropToken(5);
        assertEquals(playfieldAfterDropsToColumn5, game.printPlayfield());
    }
}