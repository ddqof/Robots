package robots.model.game;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LiveEntity extends GameEntity {
    private final double damage;
    private double hp;
    private double viewRange = 0;
    private long lastDealTime;
    private long restTime = Long.MAX_VALUE;

    @Builder(builderMethodName = "liveEntityBuilder")
    public LiveEntity(double x, double y, double damage, double hp, double viewRange, long restTime) {
        super(x, y);
        this.damage = damage;
        this.hp = hp;
        this.viewRange = viewRange;
        this.restTime = restTime;
    }

    public boolean dealDamage(LiveEntity entity) {
        boolean isReadyForDeal = System.currentTimeMillis() - lastDealTime > restTime;
        if (getDistanceTo(entity) <= viewRange && isReadyForDeal && entity.hp > 0) {
            entity.hp = entity.hp - damage;
            lastDealTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }
}
