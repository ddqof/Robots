package model.game;

import org.junit.Assert;
import org.junit.Test;
import robots.model.game.GameModel;
import robots.model.game.Robot;
import robots.model.game.Target;

public class ModelPinsRobotTest {

    private GameModel createModelWithRobotMovedForOneStep(int robotPosX, int robotPosY, int borderWidth, int borderHeight) {
        GameModel model = new GameModel(
                new Robot(robotPosX, robotPosY, Math.PI / 2),
                new Target(100, 100)
        );
        model.moveRobot(borderHeight, borderWidth);
        return model;
    }

    @Test
    public void testModelPinsRobotToBorderWhenWidthDecreases() {
        GameModel model = createModelWithRobotMovedForOneStep(40, 40, 30, 40);
        Assert.assertEquals(30, model.getRobot().getPositionX(), 0);
        Assert.assertEquals(40, model.getRobot().getPositionY(), 0);
    }

    @Test
    public void testModelPinsRobotToBorderWhenHeightDecreases() {
        GameModel model = createModelWithRobotMovedForOneStep(40, 40, 40, 30);
        Assert.assertEquals(40, model.getRobot().getPositionX(), 0);
        Assert.assertEquals(30, model.getRobot().getPositionY(), 0);
    }
}
