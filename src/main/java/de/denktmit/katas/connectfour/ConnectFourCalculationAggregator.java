package de.denktmit.katas.connectfour;

public class ConnectFourCalculationAggregator {

    private static final int NUMBER_OF_OBJECTS_TO_CONNECT = 4;
    private final Object currentObject;
    private Object previousObject = null;
    private int consecutiveObjectsCount = 0 ;

    public ConnectFourCalculationAggregator() {
        this(null);
    }

    public ConnectFourCalculationAggregator(Object currentObject) {
        this.currentObject = currentObject;
    }

    public ConnectFourCalculationAggregator reduce(ConnectFourCalculationAggregator aggregator) {
        if (consecutiveObjectsCount == NUMBER_OF_OBJECTS_TO_CONNECT) {
            return this;
        }
        if (aggregator.currentObject == null) {
            consecutiveObjectsCount = 0;
        } else if (aggregator.currentObject.equals(previousObject)) {
            ++consecutiveObjectsCount;
        } else {
            consecutiveObjectsCount = 1;
        }
        previousObject = aggregator.currentObject;
        return this;
    }

    public boolean hasConnectedFour() {
        return consecutiveObjectsCount == NUMBER_OF_OBJECTS_TO_CONNECT;
    }

}
