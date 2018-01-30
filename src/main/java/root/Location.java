package root;

class Location {
    private String identifier;
    private String mark;
    private final int rowNumber;
    private final int columnNumber;

    Location(int rowNumber, int columnNumber, int gridSize) {
        this.columnNumber = columnNumber;
        this.rowNumber = rowNumber;
        identifier = String.valueOf((rowNumber - 1) * gridSize + columnNumber);
        mark = "";
    }

    boolean hasIdentifier(String otherIdentifier) {
        return otherIdentifier.equals(identifier);
    }

    boolean isMarked() {
        return !mark.isEmpty();
    }

    void markWith(String symbol) {
        mark = symbol;
    }

    String display() {
        return isMarked() ? mark : identifier;
    }

    int getRowNumber() {
        return rowNumber;
    }

    int getColumnNumber() {
        return columnNumber;
    }
}
