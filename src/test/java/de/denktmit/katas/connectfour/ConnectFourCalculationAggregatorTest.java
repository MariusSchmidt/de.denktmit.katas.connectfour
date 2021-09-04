package de.denktmit.katas.connectfour;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static de.denktmit.katas.connectfour.Player.PLAYER_ONE;
import static de.denktmit.katas.connectfour.Player.PLAYER_TWO;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConnectFourCalculationAggregatorTest {

    @Test
    void notConnectedFour() {
        int[] observables = new int[] {1, 2, 3, 4, 5, 6, 7};
        boolean connectedFour = Arrays.stream(observables)
                .mapToObj(ConnectFourCalculationAggregator::new)
                .reduce(new ConnectFourCalculationAggregator(), ConnectFourCalculationAggregator::reduce)
                .hasConnectedFour();
        assertFalse(connectedFour);
    }

    @Test
    void connectedFourInTotalButNotConsecutive() {
        int[] observables = new int[] {1, 2, 1, 2, 1, 2, 1, 2};
        boolean connectedFour = Arrays.stream(observables)
                .mapToObj(ConnectFourCalculationAggregator::new)
                .reduce(new ConnectFourCalculationAggregator(), ConnectFourCalculationAggregator::reduce)
                .hasConnectedFour();
        assertFalse(connectedFour);
    }

    @Test
    void connectedFourAtStart() {
        int[] observables = new int[] {1, 1, 1, 1, 2, 3, 4};
        boolean connectedFour = Arrays.stream(observables)
                .mapToObj(ConnectFourCalculationAggregator::new)
                .reduce(new ConnectFourCalculationAggregator(), ConnectFourCalculationAggregator::reduce)
                .hasConnectedFour();
        assertTrue(connectedFour);
    }

    @Test
    void connectedFourInTheMiddle() {
        int[] observables = new int[] {3,2, 1, 1, 1, 1, 2, 3};
        boolean connectedFour = Arrays.stream(observables)
                .mapToObj(ConnectFourCalculationAggregator::new)
                .reduce(new ConnectFourCalculationAggregator(), ConnectFourCalculationAggregator::reduce)
                .hasConnectedFour();
        assertTrue(connectedFour);
    }

    @Test
    void connectedFourInTheEnd() {
        int[] observables = new int[] {5, 4, 3, 2, 1, 1, 1, 1};
        boolean connectedFour = Arrays.stream(observables)
                .mapToObj(ConnectFourCalculationAggregator::new)
                .reduce(new ConnectFourCalculationAggregator(), ConnectFourCalculationAggregator::reduce)
                .hasConnectedFour();
        assertTrue(connectedFour);
    }

    @Test
    void notConnectedFourInTheEndWithPlayers() {
        Player[] observables = new Player[] {null, PLAYER_ONE, PLAYER_TWO, PLAYER_TWO, PLAYER_TWO};
        boolean connectedFour = Arrays.stream(observables)
                .map(ConnectFourCalculationAggregator::new)
                .reduce(new ConnectFourCalculationAggregator(), ConnectFourCalculationAggregator::reduce)
                .hasConnectedFour();
        assertFalse(connectedFour);
    }

    @Test
    void connectedFourInTheEndWithSlots() {
        Player[] observables = new Player[] {null, PLAYER_ONE, PLAYER_TWO, PLAYER_TWO, PLAYER_TWO, PLAYER_TWO};
        boolean connectedFour = Arrays.stream(observables)
                .map(ConnectFourCalculationAggregator::new)
                .reduce(new ConnectFourCalculationAggregator(), ConnectFourCalculationAggregator::reduce)
                .hasConnectedFour();
        assertTrue(connectedFour);
    }
}
