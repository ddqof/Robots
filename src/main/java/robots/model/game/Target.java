package robots.model.game;

public class Target {
    private final int positionX;
    private final int positionY;

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public Target(int startPositionX, int startPositionY) {
        positionX = startPositionX;
        positionY = startPositionY;
    }

}
