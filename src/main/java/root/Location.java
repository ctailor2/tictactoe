package root;

class Location {
    private String identifier;
    private String mark;
    private final int rowNumber;
    private final int columnNumber;

    Location(int rowNumber, int columnNumber) {
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        identifier = String.valueOf((rowNumber - 1) * 3 + columnNumber);
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
