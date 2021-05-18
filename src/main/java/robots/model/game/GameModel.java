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
    private int currentLevel;

    private Level level;
    private Target currentTarget;
    private Stack<Target> path;
    private State state;

    public enum State {
        STOPPED, RUNNING, ROBOT_WIN, ROBOT_LOST
    }

    private boolean wasRobotDamaged = false;

    private final ScheduledExecutorService executor =
            Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

    private final Set<Observer> observers = new HashSet<>();

    public boolean wasRobotDamaged() {
        return wasRobotDamaged;
    }

    public State getState() {
        return state;
    }

    public List<Turret> getTurrets() {
        return turrets;
    }

    public void updateLevel(int currentLevel) {
        this.level = Levels.getLevel(currentLevel);
        this.path = new PathFinder(this.level).findPath();
        this.currentTarget = this.path.pop();
        this.turrets = new LinkedList<>();
    }

    private List<Turret> turrets = new LinkedList<>();

    public void addTurret(Turret t) {
        double x = t.getX();
        double y = t.getY();
        if (level.getTurretsCount() <= turrets.size()) return;
        if (PathFinder.isNotNearBorders(level.getBorders(), x, y, (int)(Levels.SPACE / 2)))
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

    public GameModel() {
        this(Levels.getLevel(0));
    }

    @JsonCreator
    public GameModel(
            @JsonProperty("level") Level level,
            @JsonProperty("state") State state,
            @JsonProperty("path") Stack<Target> path,
            @JsonProperty("currentTarget") Target currentTarget) {
        this.level = level;
        this.path = path;
        this.currentTarget = currentTarget;
        this.state = state;
    }

    public GameModel(Level level) {
        this(level, State.STOPPED);
    }

    public GameModel(Level level, State state, Stack<Target> path) {
        this(level, state, path, path.pop());
    }

    public GameModel(Level level, State state) {
        this(level, state, new PathFinder(level).findPath());
    }

    public void moveRobot() {
        Robot robot = level.getRobot();
        if (robot.getDistanceTo(currentTarget.getPositionX(), currentTarget.getPositionY()) < 1) {
            if (!path.empty()) {
                currentTarget = path.pop();
            } else {
                state = State.ROBOT_WIN;
            }
            wasRobotDamaged = false;
        } else {
            robot.move(currentTarget);
            wasRobotDamaged = turrets.stream()
                    .map(turret -> turret.dealDamage(robot))
                    .anyMatch(b -> b.equals(true));
            if (robot.getHp() <= 0) {
                currentLevel = currentLevel + 1;
                if (currentLevel <= Levels.levelsCount()) {
                    updateLevel(currentLevel);
                } else {
                    state = State.ROBOT_LOST;
                }
            }
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
        state = State.RUNNING;
        executor.scheduleWithFixedDelay(
                () -> {
                    if (state == State.RUNNING) {
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