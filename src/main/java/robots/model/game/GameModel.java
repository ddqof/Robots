package robots.model.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import robots.serialize.JsonSerializable;
import robots.serialize.save.Save;
import robots.serialize.save.Saves;
import robots.view.Observer;

import java.io.File;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameModel implements JsonSerializable {
    public static final File SAVES_FILE = new File(Saves.PATH,
            String.format("gameModel.%s", Saves.JSON_EXTENSION));

    public static final int WIDTH = 550;
    public static final int HEIGHT = 550;

    private final Level level;
    private Target currentTarget;
    private final Stack<Target> path;
    private boolean isGameOver;
    private final ScheduledExecutorService executor =
            Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

    private final Set<Observer> observers = new HashSet<>();

    public List<Turret> getTurrets() {
        return turrets;
    }

    private final List<Turret> turrets = new LinkedList<>();

    public void addTurret(Turret t) {
        double x = t.getX();
        double y = t.getY();
        int c = 0;
        for (Border border : level.getBorders()) {
            if (!(border.getSide() == Side.LEFT &&
                    x < border.getStartX() &&
                    y <= border.getStartY() &&
                    y >= border.getFinishY()
                    ||
                    border.getSide() == Side.RIGHT &&
                            x > border.getStartX() &&
                            y <= border.getStartY() &&
                            y >= border.getFinishY()
                    ||
                    border.getSide() == Side.BOTTOM &&
                            y > border.getStartY() &&
                            x <= border.getFinishX() &&
                            x >= border.getStartX()
                    ||
                    border.getSide() == Side.TOP &&
                            y < border.getStartY() &&
                            x <= border.getFinishX() &&
                            x >= border.getStartX()
            )) {
                c++;
            }
        }
        if (c != level.getBorders().size())
            turrets.add(t); // накринжевал

    }

    @JsonGetter("currentTarget")
    public Target getCurrentTarget() {
        return currentTarget;
    }

    @JsonGetter("path")
    public Stack<Target> getPath() {
        return path;
    }

    public Level getLevel() {
        return level;
    }

    public boolean isGameOver() {
        return isGameOver;
    }


    public GameModel() {
        this(Levels.getLevel(1));
    }

    @JsonCreator
    public GameModel(
            @JsonProperty("level") Level level,
            @JsonProperty("gameOver") boolean isGameOver,
            @JsonProperty("path") Stack<Target> path,
            @JsonProperty("currentTarget") Target currentTarget) {
        this.level = level;
        this.path = path;
        this.currentTarget = currentTarget;
        this.isGameOver = isGameOver;
    }

    public GameModel(Level level) {
        this(level, false);
    }

    public GameModel(Level level, boolean isGameOver, Stack<Target> path) {
        this(level, isGameOver, path, path.pop());
    }

    public GameModel(Level level, boolean isGameOver) {
        this(level, isGameOver, new PathFinder(level).findPath());
    }

    public void moveRobot() {
        Robot robot = level.getRobot();
        if (robot.getDistanceTo(currentTarget.getPositionX(), currentTarget.getPositionY()) < 1) {
            if (!path.empty()) currentTarget = path.pop();
            else isGameOver = true;
        } else {
            robot.move(currentTarget);
            turrets.forEach(x -> x.dealDamage(robot));
        }
        observers.forEach(Observer::onUpdate);
    }

    public void registerObs(Observer obs) {
        observers.add(obs);
    }

    public void unregisterObs(Observer obs) {
        observers.remove(obs);
    }

    public void start() {
        executor.scheduleWithFixedDelay(
                () -> {
                    if (!isGameOver) {
                        moveRobot();
                    }
                }, 0, 10, TimeUnit.MILLISECONDS
        );
    }

    @Override
    public boolean serialize() {
        return Save.storeObject(SAVES_FILE, this);
    }
}