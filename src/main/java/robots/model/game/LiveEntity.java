package robots.model.game;

public class LiveEntity extends GameEntity {
    private final double damage;
    private double hp;
    private final double viewRange;
    private long lastDealTime;
    private final long restTime;

    public double getDamage() {
        return damage;
    }

    public double getHp() {
        return hp;
    }

    public double getViewRange() {
        return viewRange;
    }

    public LiveEntity(double x, double y, double damage, double hp, double viewRange, long restTime) {
        super(x, y);
        this.damage = damage;
        this.hp = hp;
        this.viewRange = viewRange;
        this.restTime = restTime;
    }

    public boolean dealDamage(LiveEntity entity) {
        boolean isReadyForDeal = System.currentTimeMillis() - lastDealTime > restTime;
        if (getDistanceTo(entity) <= viewRange && isReadyForDeal) {
            entity.hp = entity.hp - damage;
            lastDealTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }

}
