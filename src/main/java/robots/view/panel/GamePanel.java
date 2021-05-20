package robots.view.panel;

import robots.BundleUtils;
import robots.model.game.Robot;
import robots.model.game.*;
import robots.view.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class GamePanel extends JPanel implements Observer {
    private final GameModel gameModel;
    private Point mousePosition;
    private int currentTurretType = 0;
    private final Map<Integer, Color> turretTypeToColors = Map.of(
            Turret.RAPID_FIRE, Color.BLACK,
            Turret.BURST_DAMAGE, Color.RED,
            Turret.RANDOM_BONUS, Color.CYAN
    );

    public GamePanel(GameModel gameModel) {
        this.gameModel = gameModel;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    currentTurretType = ++currentTurretType % 3;
                } else if (e.getButton() == MouseEvent.BUTTON1) {
                    Point p = e.getPoint();
                    gameModel.addTurret(Turret.ofType(currentTurretType, p.x, p.y)); //todo with ratio
                }
            }
        });
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                mousePosition = mouseEvent.getPoint();
            }
        });
        setDoubleBuffered(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        Dimension d = getSize();
        double widthRatio = d.width / (double) GameModel.WIDTH;
        double heightRatio = d.height / (double) GameModel.HEIGHT;
        GameModel.State state = gameModel.getState();

        if (state == GameModel.State.ROBOTS_WON) {
            drawGameOver(g2d, widthRatio, heightRatio, "youLostTitle");
        } else if (state == GameModel.State.ROBOT_LOST) {
            drawGameOver(g2d, widthRatio, heightRatio, "youWonTitle");
        } else if (state == GameModel.State.RUNNING) {
            drawBorders(g2d, gameModel.getLevel().getBorders(), widthRatio, heightRatio);
            gameModel.getAliveRobots().forEach(x -> drawRobot(g2d, x, widthRatio, heightRatio));
            drawTarget(g2d, gameModel.getLevel().getFinalTarget(), widthRatio, heightRatio);
            drawTurrets(g2d, gameModel.getTurrets(), widthRatio, heightRatio);
            drawTurretCount(g2d, widthRatio, heightRatio);
        }
        if (mousePosition != null && (state == GameModel.State.RUNNING)) {
            if (currentTurretType == Turret.RANDOM_BONUS) {
                g.drawString("?", mousePosition.x, mousePosition.y);
            } else {
                int diam = (int) (Turret.DEFAULT_RANGE * 2);
                g.setColor(turretTypeToColors.get(currentTurretType));
                drawOval(g, mousePosition.x, mousePosition.y, (int) (diam * widthRatio), (int) (diam * heightRatio));
            }
        }
    }

    private static int round(double value) {
        return (int) (value + 0.5);
    }

    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private void drawGameOver(Graphics2D g, double widthRatio, double heightRatio, String key) {
        g.drawString(
                ResourceBundle.getBundle(BundleUtils.FRAME_LABELS_BUNDLE_NAME).getString(key),
                (float) (GameModel.WIDTH * widthRatio / 2),
                (float) (GameModel.HEIGHT * heightRatio / 2)
        );
    }

    private void drawRobot(Graphics2D g, Robot robot, double widthRatio, double heightRatio) {
        if (robot.getType() == Robot.DEFAULT)
            drawDefaultRobot(g, robot, widthRatio, heightRatio);
        else if (robot.getType() == Robot.DAMAGE_DEALER)
            drawDamagingRobot(g, robot, widthRatio, heightRatio);
        else if (robot.getType() == Robot.HEAVY)
            drawHeavyRobot(g, robot, widthRatio, heightRatio);
    }

    private void drawSomeRobot(Graphics2D g,
                               Robot robot,
                               double widthSize,
                               double heighSize,
                               double eyeSize,
                               Color mainColor,
                               Color eyeColor,
                               double widthRatio,
                               double heightRatio) {
        int robotCenterX = round(robot.getX() * widthRatio);
        int robotCenterY = round(robot.getY() * heightRatio);
        int robotWidth = round(widthSize * widthRatio);
        int robotHeight = round(heighSize * heightRatio);
        int eyeWidth = round(eyeSize * widthRatio);
        int eyeHeight = round(eyeSize * heightRatio);
        int eyeOffset = round(5 * widthRatio);
        AffineTransform t = AffineTransform.getRotateInstance(robot.getDirection(), robotCenterX, robotCenterY);
        g.setTransform(t);
        g.setColor(mainColor);
        fillOval(g, robotCenterX, robotCenterY, robotWidth, robotHeight);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX, robotCenterY, robotWidth, robotHeight);
        g.setColor(eyeColor);
        fillOval(g, robotCenterX + eyeOffset, robotCenterY, eyeWidth, eyeHeight);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX + eyeOffset, robotCenterY, eyeWidth, eyeHeight);

        g.drawString(robot.getHp() + "" ,robotCenterX, robotCenterY - robotHeight);

        //отросивка нанесения урона (она будет всего один такт, то есть почти незаметна)
        if (gameModel.getDamagedRobots().contains(robot)) {
            g.setColor(Color.RED);
            fillOval(g, robotCenterX, robotCenterY, robotHeight, robotHeight);
            fillOval(g, robotCenterX, robotCenterY, robotHeight, robotHeight);
        }

    }

    private void drawHeavyRobot(Graphics2D g, Robot robot, double widthRatio, double heightRatio) {
        drawSomeRobot(g, robot, 30, 30, 10, Color.BLUE, Color.GRAY, widthRatio, heightRatio);
    }

    private void drawDamagingRobot(Graphics2D g, Robot robot, double widthRatio, double heightRatio) {
        drawSomeRobot(g, robot, 15, 5, 4, Color.ORANGE, Color.CYAN, widthRatio, heightRatio);
    }


    private void drawDefaultRobot(Graphics2D g, Robot robot, double widthRatio, double heightRatio) {
        drawSomeRobot(g, robot, 30, 10, 5, Color.MAGENTA, Color.WHITE, widthRatio, heightRatio);
    }

    private void drawTarget(Graphics2D g, Target target, double widthRatio, double heightRatio) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        int x = round(target.getX() * widthRatio);
        int y = round(target.getY() * heightRatio);
        int targetWidth = round(5 * widthRatio);
        int targetHeight = round(5 * heightRatio);
        fillOval(g, x, y, targetWidth, targetHeight);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, targetWidth, targetHeight);
    }

    private void drawBorders(Graphics2D g, List<Border> borders, double widthRatio, double heightRatio) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        for (Border border : borders) {
            int x1 = round(border.getStartX() * widthRatio);
            int x2 = round(border.getFinishX() * widthRatio);
            int y1 = round(border.getStartY() * heightRatio);
            int y2 = round(border.getFinishY() * heightRatio);
            g.setColor(Color.YELLOW);
            g.drawLine(x1, y1, x2, y2);
            for (int i = 0; i < 25; i++)
                if (border.getSide() == Side.BOTTOM)
                    g.drawLine(x1, y1 + i, x2, y2 + i);
                else if (border.getSide() == Side.TOP)
                    g.drawLine(x1, y1 - i, x2, y2 - i);
                else if (border.getSide() == Side.LEFT)
                    g.drawLine(x1 - i, y1, x2 - i, y2);
                else if (border.getSide() == Side.RIGHT)
                    g.drawLine(x1 + i, y1, x2 + i, y2);

            g.setColor(Color.BLACK);
        }
    }

    private void drawTurrets(Graphics2D g, List<Turret> turrets, double widthRatio, double heightRatio) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        for (Turret turret : turrets) {
            g.setColor(turretTypeToColors.get(turret.getType()));
            int x = round(turret.getX() * widthRatio);
            int y = round(turret.getY() * heightRatio);
            int targetWidth = round(10 * widthRatio);
            int targetHeight = round(10 * heightRatio);
            fillOval(g, x, y, targetWidth, targetHeight);
            g.setColor(Color.BLACK);
            drawOval(g, x, y, targetWidth, targetHeight);
        }
    }

    public void drawTurretCount(Graphics2D g, double widthRatio, double heightRatio) {
        g.drawString(
                ResourceBundle.getBundle(BundleUtils.FRAME_LABELS_BUNDLE_NAME).getString("availableTurretsCountTitle")
                        + " " + (gameModel.getLevel().getTurretsCount() - gameModel.getTurrets().size()),
                (float) (0),
                (float) (10)
        );
    }

    @Override
    public void onModelUpdate() {
        EventQueue.invokeLater(this::repaint);
    }
}
