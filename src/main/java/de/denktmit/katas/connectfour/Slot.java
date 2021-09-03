package de.denktmit.katas.connectfour;

public enum Slot {

    EMPTY(" "), P1("1"), P2("2");

    private final String fieldRepresentation;

    Slot(String fieldRepresentation) {
        this.fieldRepresentation = fieldRepresentation;
    }

    public String getFieldRepresentation() {
        return fieldRepresentation;
    }
}
