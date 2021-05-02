package robots.model.game;

public class Border {
    private final double startX;
    private final double startY;
    private final double finishX;
    private final double finishY;
    private final Side side;

    public Border(double xLeft, double yBottom, double xRight, double yTop, Side side){
        this.startX = xLeft;
        this.startY = yBottom;
        this.finishX = xRight;
        this.finishY = yTop;
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
