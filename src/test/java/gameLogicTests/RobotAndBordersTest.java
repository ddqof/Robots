package gameLogicTests;

import org.junit.*;
import robots.gui.GameVisualizer;
import java.awt.*;

public class RobotAndBordersTest {
    GameVisualizer gameVisualizer;
    @Before
    public void setUp() {
        gameVisualizer = new GameVisualizer();
    }

    private Point randomPoint(int minimum, int maximum){
        return new Point(minimum + (int)(Math.random() * ((maximum - minimum) + 1)),
                minimum + (int)(Math.random() * ((maximum - minimum) + 1)));
    }
    @Test
    public void RobotAndLeftTopCorner(){
        for (int i = 0; i < 10_000; i++){
            gameVisualizer.setTargetPosition(randomPoint(400, 400));
            while (!gameVisualizer.getRobotPosition().equals(gameVisualizer.getTargetPosition())) {
                gameVisualizer.onModelUpdateEvent();
                var x = gameVisualizer.getRobotPosition().x;
                var y = gameVisualizer.getRobotPosition().y;
                Assert.assertTrue(x >= 0 && y >= 0 && y <= 400 && x <= 400);
            }
        }
    }
}
