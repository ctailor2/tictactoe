package root;

class Location {
    private String mark;
    private final String identifier;

    Location(String identifier) {
        this.identifier = identifier;

        mark = "";
    }

    String getDisplay() {
        if (mark.isEmpty()) {
            return identifier;
        } else {
            return mark;
        }
    }

    String getIdentifier() {
        return identifier;
    }

    void mark(String symbol) throws LocationTakenException {
        if (mark.isEmpty()) {
            mark = symbol;
        } else {
            throw new LocationTakenException();
        }
    }
}
