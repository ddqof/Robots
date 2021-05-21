package robots.model.game;

import robots.serialize.JsonSerializable;
import robots.serialize.save.Save;
import robots.serialize.save.Saves;
import robots.view.Observer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class GameModel implements JsonSerializable {
    public static final File SAVES_FILE = new File(Saves.PATH,
            String.format("gameModel.%s", Saves.JSON_EXTENSION));

    public static final int WIDTH = 550;
    public static final int HEIGHT = 550;
    private int currentLevel;
    private List<Robot> damagedRobots = new ArrayList<>();
    private List<Robot> aliveRobots = new ArrayList<>();
    private List<Turret> turrets = new LinkedList<>();
    private Level level;
    private State state;

    private final Set<Observer> observers = new HashSet<>();

    public enum State {
        STOPPED, RUNNING, ROBOTS_WON, ROBOT_LOST;
    }

    private final ScheduledExecutorService scheduledExecutor =
            Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

    private final ExecutorService fixedExecutor =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public List<Robot> getAliveRobots() {
        return aliveRobots;
    }

    public Level getLevel() {
        return level;
    }

    public State getState() {
        return state;
    }

    public List<Turret> getTurrets() {
        return turrets;
    }

    public List<Robot> getDamagedRobots() {
        return damagedRobots;
    }

    public GameModel() {
        this(Levels.getLevel(0));
    }

    public GameModel(Level level) {
        this(level, State.STOPPED);
    }

    public GameModel(Level level, State state) {
        this.level = level;
        this.state = state;
    }


    public void updateLevel(int currentLevel) {
        this.level = Levels.getLevel(currentLevel);
        this.turrets = new LinkedList<>();
    }


    public void addTurret(Turret t) {
        double x = t.getX();
        double y = t.getY();
        if (level.getTurretsCount() <= turrets.size()) return;
        if (PathFinder.isNotNearBorders(level.getBorders(), x, y, (int) (Levels.SPACE / 2)))
            turrets.add(t);

    }

    public void update() {
        boolean isRobotsAlive = false;
        damagedRobots = new ArrayList<>();
        List<Robot> dead = new ArrayList<>();
        for (Robot robot : aliveRobots) {
            boolean isTargetReached = robot.move();
            if (isTargetReached) state = State.ROBOTS_WON;
            for (Turret turret : turrets) {
                boolean wasDamaged = turret.dealDamage(robot);
                if (wasDamaged) damagedRobots.add(robot);
            }
            if (robot.getHp() > 0) {
                isRobotsAlive = true;
            } else {
                dead.add(robot);
            }
        }
        aliveRobots.removeAll(dead);
        if (!isRobotsAlive) {
            currentLevel = currentLevel + 1;
            if (currentLevel <= Levels.levelsCount() - 1) {
                updateLevel(currentLevel);
            } else {
                state = State.ROBOT_LOST;
            }
            aliveRobots = new ArrayList<>(level.getRobots());
        }
    }

    public void registerObs(Observer obs) {
        observers.add(obs);
    }

    public void unregisterObs(Observer obs) {
        observers.remove(obs);
    }


    public void start() throws InterruptedException {
        state = State.RUNNING;
        int robotsCount = getLevel().getRobots().size();
        for (int i = 1; i <= robotsCount; i++) {
            aliveRobots = new ArrayList<>(getLevel().getRobots().subList(0, i));
            ScheduledFuture<?> task = scheduleRobots();
            if (i < robotsCount) {
                Thread.sleep(1000);
                task.cancel(false);
            }
        }
    }

    private ScheduledFuture<?> scheduleRobots() {
        return scheduledExecutor.scheduleWithFixedDelay(
                () -> {
                    try {
                        if (state == State.RUNNING) {
                            update();
                            observers.forEach(x -> fixedExecutor.execute(x::onModelUpdate));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, 0, 10, TimeUnit.MILLISECONDS
        );
    }

    @Override
    public boolean serialize() {
        return Save.storeObject(SAVES_FILE, this);
    }
}