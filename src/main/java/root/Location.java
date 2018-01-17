package root;

class Location {
    private final String identifier;
    private String mark;

    Location(String identifier) {
        this.identifier = identifier;
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
}
