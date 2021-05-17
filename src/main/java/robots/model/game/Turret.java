package robots.model.game;

public class Turret {
    private final double range;
    private final double damage;
    private final double x;
    private final double y;
    private final double timeout; // милисекунды
    private double lastShotTime = 0;

    public double getRange() {
        return range;
    }

    public Turret() {
        this(50, 50);
    }

    public Turret(double x, double y) {
        this(x, y, 50, 5, 1000);
    }

    public Turret(double x, double y, double range, double damage, double timeout) {
        this.range = range;
        this.damage = damage;
        this.x = x;
        this.y = y;
        this.timeout = timeout;
    }

    public void dealDamage(Robot robot) {
        if (robot.getDistanceTo(x, y) <= range && (lastShotTime == 0 || System.currentTimeMillis() - lastShotTime > timeout)) {
            robot.setHp(robot.getHp() - damage);
            lastShotTime = System.currentTimeMillis();
        }
    }
}
