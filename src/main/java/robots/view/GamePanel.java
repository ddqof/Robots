package robots.view;

import robots.model.game.GameModel;
import robots.model.game.Robot;
import robots.model.game.Target;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.Timer;
import java.util.TimerTask;

public class GamePanel extends JPanel {

    private final GameModel gameModel;
    private final Timer timer = new Timer("events generator", true);
    private volatile int clickedPositionX;
    private volatile int clickedPositionY;

    public GamePanel(GameModel gameModel) {
        this.gameModel = gameModel;
        clickedPositionX = gameModel.getTarget().getPositionX();
        clickedPositionY = gameModel.getTarget().getPositionY();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                EventQueue.invokeLater(GamePanel.this::repaint);
            }
        }, 0, 50);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameModel.update(clickedPositionX, clickedPositionY);
            }
        }, 0, 10);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point p = e.getPoint();
                clickedPositionX = p.x;
                clickedPositionY = p.y;
                gameModel.update(clickedPositionX, clickedPositionY);
                repaint();
            }
        });
        setDoubleBuffered(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        drawRobot(g2d, gameModel.getRobot());
        drawTarget(g2d, gameModel.getTarget());
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

    private void drawRobot(Graphics2D g, Robot robot) {
        int robotCenterX = round(robot.getPositionX());
        int robotCenterY = round(robot.getPositionY());
        AffineTransform t = AffineTransform.getRotateInstance(robot.getDirection(), robotCenterX, robotCenterY);
        g.setTransform(t);
        g.setColor(Color.MAGENTA);
        fillOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.WHITE);
        fillOval(g, robotCenterX + 10, robotCenterY, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX + 10, robotCenterY, 5, 5);
    }

    private void drawTarget(Graphics2D g, Target target) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        int x = target.getPositionX();
        int y = target.getPositionY();
        fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 5, 5);
    }
}
