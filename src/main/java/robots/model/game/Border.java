package robots.model.game;

public class Border {
    private final double startX;
    private final double startY;
    private final double finishX;
    private final double finishY;
    private final Side side;

    public Border(double startX, double startY, double finishX, double finishY, Side side){
        this.startX = startX;
        this.startY = startY;
        this.finishX = finishX;
        this.finishY = finishY;
        this.side = side;
    }

    public Side getSide() {
        return side;
    }

    public double getStartX() {
        return startX;
    }

    public double getStartY() {
        return startY;
    }

    public double getFinishX() {
        return finishX;
    }

    public double getFinishY() {
        return finishY;
    }
}
