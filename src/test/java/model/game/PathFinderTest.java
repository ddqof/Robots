//package model.game;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import robots.model.game.*;
//
//import java.util.List;
//import java.util.Stack;
//
//public class PathFinderTest {
//    private Stack<Target> path;
//    private Level level;
//
//    @Before
//    public void setUp() {
//        List<Border> borders = List.of(new Border(
//                Levels.SPACE,
//                Levels.SPACE,
//                (double) GameModel.HEIGHT / 2 + Levels.SPACE * 3,
//                (double) GameModel.HEIGHT / 2 - Levels.SPACE,
//                Side.RIGHT));
//        Target target = new Target((int) Levels.SPACE * 2, GameModel.HEIGHT / 2);
//        Robot robot = new Robot(0, (double) GameModel.HEIGHT / 2, 0);
//        level = new Level(borders, target, robot, 0);
//        path = new PathFinder(level).findPath();
//    }
//
//    @Test
//    public void finalPathTargetNearFinish() {
//        Target target = path.firstElement();
//        Target finalTarget = level.getFinalTarget();
//        Assert.assertEquals(target, finalTarget);
//    }
//
//    @Test
//    public void robotTurnUp() {
//        for (Target nextTarget : path){
//            Assert.assertTrue(nextTarget.getPositionY() <= level.getRobots().getPositionY());
//        }
//    }
//}
