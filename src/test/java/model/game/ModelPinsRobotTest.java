package model.game;

import org.junit.Assert;
import org.junit.Test;
import robots.model.game.*;

public class ModelPinsRobotTest {

    private GameModel createModelWithRobotMovedForOneStep() {
        GameModel model = new GameModel(
                Levels.getLevel(0)
        );
        model.moveRobot();
        return model;
    }

    @Test
    public void testModelPinsRobotToBorderWhenWidthDecreases() {
        GameModel model = createModelWithRobotMovedForOneStep();
        Assert.assertEquals(30, model.getLevel().getRobot().getPositionX(), 0);
        Assert.assertEquals(40, model.getLevel().getRobot().getPositionY(), 0);
    }

    @Test
    public void testModelPinsRobotToBorderWhenHeightDecreases() {
        GameModel model = createModelWithRobotMovedForOneStep();
        Assert.assertEquals(40, model.getLevel().getRobot().getPositionX(), 0);
        Assert.assertEquals(30, model.getLevel().getRobot().getPositionY(), 0);
    }
}
