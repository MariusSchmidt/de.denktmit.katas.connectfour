package de.denktmit.katas.connectfour;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DiscreteBoundedCoordinateSystemTest {

    private DiscreteBoundedCoordinateSystem cs = new DiscreteBoundedCoordinateSystem(7, 6);

    @Test
    void numberOfCoordinates() {
    }

    @Test
    void isContained() {
    }

    @Test
    void positionsInDirection() {
        List<Vector2D> positionList = cs.positionsInDirection(new Vector2D(0, 0), Direction.TOP, 6)
                .collect(Collectors.toList());
        assertEquals(7, positionList.size());
        assertEquals(new Vector2D(0, 6), positionList.get(6));
    }
}