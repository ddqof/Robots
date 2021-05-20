package robots.model.game;

public class GameEntity {

    private double x;
    private double y;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public GameEntity(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getDistanceTo(GameEntity entity) {
        return getDistanceTo(entity.x, entity.y);
    }

    public double getDistanceTo(double x, double y) {
        double diffX = this.x - x;
        double diffY = this.y - y;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    public double angleTo(GameEntity entity) {
        return angleTo(entity.x, entity.y);
    }

    public double angleTo(double x, double y) {
        double diffX = x - this.x;
        double diffY = y - this.y;
        System.out.println(diffX + " " + diffY );
        return Math.atan2(diffY, diffX);
    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Target)) {
            return false;
        }
        return ((GameEntity) obj).x == this.x && ((GameEntity) obj).y == this.y;
    }

    @Override
    public int hashCode() {
        int res = 17;
        res = (int) (res * 31 + Math.min(x, y));
        res = (int) (res * 31 + Math.max(x, y));
        return res;
    }
}
