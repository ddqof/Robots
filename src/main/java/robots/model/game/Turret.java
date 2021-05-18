package robots.model.game;

import java.util.concurrent.ThreadLocalRandom;

public class Turret {
    public static final double DEFAULT_RANGE = 50;

    public static final int RAPID_FIRE = 0; //высокая скоротрельность, низкий урон
    public static final int BURST_DAMAGE = 1; //низкая скорострельность, высокий урон
    public static final int RANDOM_BONUS = 2; // эта штука рандомно ведет себя полностью рандомно

    private final double range;
    private final double damage;
    private final double x;
    private final double y;
    private final double timeout; // миллисекунды
    private double lastShotTime = 0;
    private final int type;

    private static final double maxRange = 500;
    private static final double maxTimeout = 3000;
    private static final double maxDamage = 50;

    public int getType() {
        return type;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public static Turret ofType(int type, double x, double y) {
        switch (type) {
            case RAPID_FIRE:
                return new Turret(x, y, DEFAULT_RANGE, 5, 100, RAPID_FIRE);
            case BURST_DAMAGE:
                return new Turret(x, y, DEFAULT_RANGE, 20, 1000, BURST_DAMAGE);
            case RANDOM_BONUS:
                double range = ThreadLocalRandom.current().nextDouble(maxRange);
                double timeout = ThreadLocalRandom.current().nextDouble(maxTimeout);
                double damage = ThreadLocalRandom.current().nextDouble(maxDamage);
                return new Turret(x, y, range, damage, timeout, RANDOM_BONUS);
            default:
                throw new IllegalArgumentException("Illegal type of turret was passed");
        }
    }

    private Turret() {
        this(50, 50, RAPID_FIRE);
    }

    private Turret(double x, double y, int type) {
        this(x, y, 50, 5, 1000, type);
    }

    private Turret(double x, double y, double range, double damage, double timeout, int type) {
        this.range = range;
        this.damage = damage;
        this.x = x;
        this.y = y;
        this.timeout = timeout;
        this.type = type;
    }

    public boolean dealDamage(Robot robot) {
        if (robot.getDistanceTo(x, y) <= range &&
                (lastShotTime == 0 || System.currentTimeMillis() - lastShotTime > timeout)) {
            robot.setHp(robot.getHp() - damage);
            lastShotTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }
}
