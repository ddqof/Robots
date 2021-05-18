package robots.model.game;

enum Tile {
    TURRET('*'),
    ROBOT('1'),
    TARGET('0'),
    EMPTY('@'), // клетки, по которым идет робот
    WALL('#') // клетки, где можно поставить турель
    ;
    public final char c;

    Tile(char c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return String.valueOf(c);
    }
}
