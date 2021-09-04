package de.denktmit.katas.connectfour;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public record DiscreteBoundedCoordinateSystem(int width, int height) {

    public int numberOfCoordinates() {
        return width * height;
    }

    public boolean isContained(Vector2D position) {
        return position.x >= 0 && position.x < width && position.y >= 0 && position.y < height;
    }

    public Stream<Vector2D> positionsInDirection(Vector2D position, Direction direction, int steps) {
        return (steps < 0)
                ? Stream.empty()
                : IntStream.range(0, steps + 1).mapToObj(i -> direction.displace(position, i));
    }
}
