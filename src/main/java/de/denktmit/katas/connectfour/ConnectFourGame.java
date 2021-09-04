package de.denktmit.katas.connectfour;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static de.denktmit.katas.connectfour.GameState.*;

/**
 *
 */
public class ConnectFourGame {

    // GAME SETUP
    private static final int NUMBER_OF_COLUMNS = 7;
    private static final int NUMBER_OF_ROWS = 6;
    private static final int TOKENS_IN_ROW_TO_WIN = 4;

    // GAME VARS
    private final DiscreteBoundedCoordinateSystem coordinateSystem = new DiscreteBoundedCoordinateSystem(NUMBER_OF_COLUMNS, NUMBER_OF_ROWS);
    private final Map<Vector2D, Player> playerTokenPlacements = new HashMap<>();
    private final AsciiRenderer renderer = new AsciiRenderer(this);
    private GameState currentState = PLAYER_ONE_TURN;


    /**
     * Prints the playfield as ASCII text
     *
     * @return An ASCII representation of the current playfield
     */
    public String printPlayfield() {
        return renderer.printPlayfield();
    }

    public Stream<Player> getPlayerTokensInRow(int row) {
        Vector2D rowStartingPosition = new Vector2D(0, row);
        return coordinateSystem.positionsInDirection(rowStartingPosition, Direction.RIGHT, coordinateSystem.width())
                .filter(coordinateSystem::isContained)
                .map(playerTokenPlacements::get);
    }

    /**
     * Drop a coin to the n-th column. If column is already full or column index is out of bounds
     * the drop is ignored. The method therefore rejects any invalid input by ignoring it.
     *
     * @param columnIndex The column to drop the token at. Columns index start at 0.
     */
    public void dropToken(int columnIndex) {
        Optional<Vector2D> position = firstEmptyPositionOfColumn(columnIndex);
        if (position.isEmpty() || currentState == PLAYER_ONE_WON || currentState == PLAYER_TWO_WON) {
            return;
        }
        processTokenDrop(position.get());
    }

    private Optional<Vector2D> firstEmptyPositionOfColumn(int columnIndex) {
        return coordinateSystem
                .positionsInDirection(new Vector2D(columnIndex, 0), Direction.TOP, NUMBER_OF_ROWS - 1)
                .filter(coordinateSystem::isContained)
                .filter(Predicate.not(playerTokenPlacements::containsKey))
                .findFirst();
    }

    private void processTokenDrop(Vector2D position) {
        playerTokenPlacements.put(position, (currentState == PLAYER_ONE_TURN) ? Player.PLAYER_ONE : Player.PLAYER_TWO);
        if (isWon(position)) {
            currentState = (currentState == PLAYER_ONE_TURN)
                    ? PLAYER_ONE_WON
                    : PLAYER_TWO_WON;
        } else if (isDraw()) {
            currentState = DRAW;
        } else {
            currentState = (currentState == PLAYER_ONE_TURN) ? PLAYER_TWO_TURN : PLAYER_ONE_TURN;
        }
    }

    private boolean isWon(Vector2D position) {
        return hasConnectedFourInDirection(position, Direction.TOP)
                || hasConnectedFourInDirection(position, Direction.TOPRIGHT)
                || hasConnectedFourInDirection(position, Direction.RIGHT)
                || hasConnectedFourInDirection(position, Direction.BOTTOMRIGHT);
    }

    private boolean hasConnectedFourInDirection(Vector2D position, Direction direction) {
        Vector2D startWalkPosition = direction.displace(position, -3);
        int numberOfStepsToEvaluate = 2 * TOKENS_IN_ROW_TO_WIN - 2;
        return coordinateSystem.positionsInDirection(startWalkPosition, direction, numberOfStepsToEvaluate)
                .filter(coordinateSystem::isContained)
                .map(playerTokenPlacements::get)
                .map(ConnectFourCalculationAggregator::new)
                .reduce(new ConnectFourCalculationAggregator(), ConnectFourCalculationAggregator::reduce)
                .hasConnectedFour();
    }

    private boolean isDraw() {
        return coordinateSystem.numberOfCoordinates() == playerTokenPlacements.size();
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public int width() {
        return coordinateSystem.width();
    }

    public int height() {
        return coordinateSystem.height();
    }

}
