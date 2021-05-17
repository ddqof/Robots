package robots.view.panel;

import robots.BundleUtils;
import robots.model.game.Border;
import robots.model.game.GameModel;
import robots.model.game.Robot;
import robots.model.game.Target;
import robots.model.game.Turret;
import robots.view.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.ResourceBundle;

public class GamePanel extends JPanel implements Observer {
    private final GameModel gameModel;
    private final Turret turretForShow = new Turret();
    private Point mousePosition;

    public GamePanel(GameModel gameModel) {
        this.gameModel = gameModel;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point p = e.getPoint();
                gameModel.addTurret(new Turret(p.x, p.y));
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
        Dimension d = getSize();
        Graphics2D g2d = (Graphics2D) g;
        double widthRatio = d.width / (double) GameModel.WIDTH;
        double heightRatio = d.height / (double) GameModel.HEIGHT;
        if (mousePosition != null) {
            //drawing range of turret
            int diam = (int) (turretForShow.getRange() * 2);
            drawOval(g, mousePosition.x, mousePosition.y, diam, diam);
        }
        if (gameModel.isGameOver())
            drawGameOver(g2d, widthRatio, heightRatio);
        else {
            drawRobot(g2d, gameModel.getLevel().getRobot(), widthRatio, heightRatio);
            drawTarget(g2d, gameModel.getLevel().getFinalTarget(), widthRatio, heightRatio);
            drawBorders(g2d, gameModel.getLevel().getBorders(), widthRatio, heightRatio);
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

    private void drawGameOver(Graphics2D g, double widthRatio, double heightRatio) {
        System.out.println(gameModel.getLevel().getRobot().getHp());
        g.drawString(
                ResourceBundle.getBundle(BundleUtils.FRAME_LABELS_BUNDLE_NAME).getString("gameOverTitle"),
                (float) (GameModel.WIDTH * widthRatio / 2),
                (float) (GameModel.HEIGHT * heightRatio / 2)
        );
    }

    private void drawRobot(Graphics2D g, Robot robot, double widthRatio, double heightRatio) {
        int robotCenterX = round(robot.getPositionX() * widthRatio);
        int robotCenterY = round(robot.getPositionY() * heightRatio);
        int robotWirth = round(30 * widthRatio);
        int robotHeight = round(10 * heightRatio);
        int eyeWidth = round(5 * widthRatio);
        int eyeHeight = round(5 * heightRatio);
        int eyeOffset = round(10 * widthRatio);
        AffineTransform t = AffineTransform.getRotateInstance(robot.getDirection(), robotCenterX, robotCenterY);
        g.setTransform(t);
        g.setColor(Color.MAGENTA);
        fillOval(g, robotCenterX, robotCenterY, robotWirth, robotHeight);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX, robotCenterY, robotWirth, robotHeight);
        g.setColor(Color.WHITE);
        fillOval(g, robotCenterX + eyeOffset, robotCenterY, eyeWidth, eyeHeight);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX + eyeOffset, robotCenterY, eyeWidth, eyeHeight);
    }

    private void drawTarget(Graphics2D g, Target target, double widthRatio, double heightRatio) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        int x = round(target.getPositionX() * widthRatio);
        int y = round(target.getPositionY() * heightRatio);
        int targetWidth = round(5 * widthRatio);
        int targetHeight = round(5 * heightRatio);
        fillOval(g, x, y, targetWidth, targetHeight);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, targetWidth, targetHeight);
    }

    private void drawBorders(Graphics g, List<Border> borders, double widthRatio, double heightRatio) {
        for (Border border : borders) {
            int x1 = round(border.getStartX() * widthRatio);
            int x2 = round(border.getFinishX() * widthRatio);
            int y1 = round(border.getStartY() * heightRatio);
            int y2 = round(border.getFinishY() * heightRatio);
            g.drawLine(x1, y1, x2, y2);
            g.drawLine(x1 + 1, y1 + 1, x2 + 1, y2 + 1);
        }
    }

    @Override
    public void onUpdate() {
        EventQueue.invokeLater(this::repaint);
    }
}
