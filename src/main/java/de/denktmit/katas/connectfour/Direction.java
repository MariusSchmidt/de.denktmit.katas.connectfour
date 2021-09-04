package de.denktmit.katas.connectfour;

public enum Direction {
    
    TOP(new Vector2D(0,1)),
    TOPRIGHT(new Vector2D(1,1)),
    RIGHT(new Vector2D(1,0)),
    BOTTOMRIGHT(new Vector2D(1,-1)),
    BOTTOM(new Vector2D(0,-1)),
    BOTTOMLEFT(new Vector2D(-1,-1)),
    LEFT(new Vector2D(-1,0)),
    TOPLEFT(new Vector2D(-1,1));

    private final Vector2D displacementVector;
    
    Direction(Vector2D displacementVector) {
        this.displacementVector = displacementVector;
    }

    public Vector2D displace(Vector2D position, int numberOfDisplacements) {
        return position.add(this.displacementVector.scalarMultiply(numberOfDisplacements));
    }
}
