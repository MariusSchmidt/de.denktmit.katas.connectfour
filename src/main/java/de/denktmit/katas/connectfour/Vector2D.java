package de.denktmit.katas.connectfour;

import java.util.Objects;

public class Vector2D {

    public final int x;
    public final int y;

    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D scalarMultiply(int multiplicand) {
        return new Vector2D(this.x * multiplicand, this.y * multiplicand);
    }

    public Vector2D add(Vector2D displacement) {
        return new Vector2D(this.x + displacement.x, this.y + displacement.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2D vector2D = (Vector2D) o;
        return x == vector2D.x && y == vector2D.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }


    @Override
    public String toString() {
        return "Vector2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
