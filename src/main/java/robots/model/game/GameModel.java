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
    private boolean isGameOver;

    private boolean wasRobotDamaged = false;

    private final ScheduledExecutorService executor =
            Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

    private final Set<Observer> observers = new HashSet<>();

    public boolean wasRobotDamaged() {
        return wasRobotDamaged;
    }

    public List<Turret> getTurrets() {
        return turrets;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
        this.level = Levels.getLevel(currentLevel);
        this.path = new PathFinder(this.level).findPath();
        this.currentTarget = this.path.pop();
        this.turrets = new LinkedList<>();
    }

    private List<Turret> turrets = new LinkedList<>();

    public void addTurret(Turret t) {
        double x = t.getX();
        double y = t.getY();
        int c = 0;
        if (level.getTurretsCount() <= turrets.size()) return;
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
        this(Levels.getLevel(0));
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
            wasRobotDamaged = false;
        } else {
            robot.move(currentTarget);
            wasRobotDamaged = turrets.stream()
                    .map(turret -> turret.dealDamage(robot))
                    .anyMatch(b -> b.equals(true));
            if (robot.getHp() <= 0) {
                setCurrentLevel(getCurrentLevel() + 1);
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