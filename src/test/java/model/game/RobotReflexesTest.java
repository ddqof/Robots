package model.game;

import org.junit.Assert;
import org.junit.Test;
import robots.model.game.Robot;
import robots.model.game.Target;

import java.util.*;

public class RobotReflexesTest {
    private static final int DEFAULT_SPACE_HEIGHT = 100;
    private static final int DEFAULT_SPACE_WIDTH = 100;
    private static final int DEFAULT_MOVE_COUNTS = 100;

    private Map<String, List<Double>> createRobotMovedForDefaultStepsCount(
            int robotPosX, int robotPosY, double robotDirection, int targetPosX, int targetPosY) {
        Robot robot = new Robot(robotPosX, robotPosY, robotDirection);
        Target target = new Target(targetPosX, targetPosY);
        List<Double> xValues = new ArrayList<>();
        List<Double> yValues = new ArrayList<>();
        for (int i = 0; i < DEFAULT_MOVE_COUNTS; i++) {
            robot.move(target);
            xValues.add(robot.getPositionX());
            yValues.add(robot.getPositionY());
        }
        Map<String, List<Double>> result = new HashMap<>();
        result.put("xValues", xValues);
        result.put("yValues", yValues);
        return result;
    }

    /**
     * Test that robot reflexes when X coordinate close to zero.
     *
     * Model:
     * |
     * |
     * |   <--  *
     * |
     * |
     *
     * Where <-- is robot location and * is target.
     */
    @Test
    public void testRobotReflexesWhenXTendsToZero() {
        List<Double> xValues = createRobotMovedForDefaultStepsCount(
                20, 20, Math.PI, 30, 20
        ).get("xValues");
        int indexOfMin = xValues.indexOf(Collections.min(xValues));
        double minX = xValues.get(indexOfMin);
        Assert.assertTrue("robot didn't touch x = 0 border", Math.abs(minX) < 1);
        Assert.assertTrue("robot didn't reflex after touches border", xValues.get(indexOfMin + 1) > minX);
    }

    /**
     * Test that robot reflexes when Y coordinate close to zero.
     *
     * Model looks like this:
     * |
     * |   ↑
     * |   |
     * |   *
     * |
     *
     * Where <-- is robot location and * is target.
     */
    @Test
    public void testRobotReflexesWhenYTendsToZero() {
        List<Double> yValues = createRobotMovedForDefaultStepsCount(
                20, 20, 3 * Math.PI / 2, 20, 30
        ).get("yValues");
        int indexOfMin = yValues.indexOf(Collections.min(yValues));
        double minY = yValues.get(indexOfMin);
        Assert.assertTrue("robot didn't touch y = 0 border", Math.abs(minY) < 1);
        Assert.assertTrue("robot didn't reflex after touches border", yValues.get(indexOfMin + 1) > minY);
    }

    /**
     * Test that robot reflexes when Y coordinate close to maximum.
     *
     * Model looks like this:
     * |
     * |   *
     * |   |
     * |   ↓
     * |
     *
     * Where <-- is robot location and * is target.
     */
    @Test
    public void testRobotReflexesWhenYTendsToMax() {
        List<Double> yValues = createRobotMovedForDefaultStepsCount(
                40, 40, Math.PI / 2, 40, 30
        ).get("yValues");
        int indexOfMax = yValues.indexOf(Collections.max(yValues));
        double maxY = yValues.get(indexOfMax);
        Assert.assertTrue("robot didn't touch higher y border", maxY >= DEFAULT_SPACE_HEIGHT);
        Assert.assertTrue("robot didn't reflex after touches border", yValues.get(indexOfMax + 1) < maxY);
    }

    /**
     * Test that robot reflexes when X coordinate close to maximum.
     *
     * Model looks like this:
     * |
     * |   * -->
     * |
     * |
     * |
     *
     * Where <-- is robot location and * is target.
     */
    @Test
    public void testRobotReflexesWhenXTendsToMax() {
        List<Double> yValues = createRobotMovedForDefaultStepsCount(
                40, 40, Math.PI / 2, 30, 40
        ).get("yValues");
        int indexOfMax = yValues.indexOf(Collections.max(yValues));
        double maxX = yValues.get(indexOfMax);
        Assert.assertTrue("robot didn't touch higher x border", maxX >= DEFAULT_SPACE_WIDTH);
        Assert.assertTrue("robot didn't reflex after touches border", yValues.get(indexOfMax + 1) < maxX);
    }
}
