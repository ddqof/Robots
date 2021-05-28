package robots.model.game;

import lombok.Builder;
import lombok.Getter;

import java.util.concurrent.ThreadLocalRandom;

@Getter
public class Turret extends LiveEntity {
    public static final double DEFAULT_RANGE = 75;
    public static final int RAPID_FIRE = 0; //высокая скоротрельность, низкий урон
    public static final int BURST_DAMAGE = 1; //низкая скорострельность, высокий урон
    public static final int RANDOM_BONUS = 2; // эта штука ведет себя полностью рандомно
    private static final double maxRange = 500;
    private static final double maxTimeout = 3000;
    private static final double maxDamage = 50;

    private final int type;

    public static Turret ofType(int type, double x, double y) {
        TurretBuilder builder = new TurretBuilder();
        if (type == RAPID_FIRE) {
            builder.hp(50).damage(5).timeout(100).range(DEFAULT_RANGE);
        } else if (type == BURST_DAMAGE) {
            builder.damage(40).timeout(1000).range(DEFAULT_RANGE);
        } else if (type == RANDOM_BONUS) {
            double range = ThreadLocalRandom.current().nextDouble(maxRange);
            double timeout = ThreadLocalRandom.current().nextDouble(maxTimeout);
            double damage = ThreadLocalRandom.current().nextDouble(maxDamage);
            builder.range(range).timeout(timeout).damage(damage).hp(50);
        } else {
            throw new IllegalArgumentException("Illegal type of turret was passed");
        }
        return builder.x(x).y(y).type(type).build();
    }

    @Builder(builderMethodName = "turretBuilder")
    private Turret(double x, double y, double hp, double range, double damage, double timeout, int type) {
        super(x, y, damage, hp, range, (long) timeout);
        this.type = type;
    }
}
