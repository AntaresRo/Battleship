package battleship;

public enum Ships {
    AIRCRAFTCARRIER(5, "Aircraft Carrier"),
    BATTLESHIP(4, "Battleship"),
    SUBMARINE(3, "Submarine"),
    CRUISER(3, "Cruiser"),
    DESTROYER(2, "Destroyer");

    final int size;
    final String name;

    Ships (int size, String name) {
        this.size = size;
        this.name = name;
    }

}
