package robots.view.panels;

import robots.model.game.*;
import robots.model.game.Robot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GamePanel extends JPanel {

    private static final String TIMER_NAME = "Events generator";
    private final GameModel gameModel;
    private volatile int targetPositionX;
    private volatile int targetPositionY;

    public GamePanel(GameModel gameModel) {
        this.gameModel = gameModel;
        targetPositionX = gameModel.getTarget().getPositionX();
        targetPositionY = gameModel.getTarget().getPositionY();
        Timer timer = new Timer(TIMER_NAME, true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                EventQueue.invokeLater(GamePanel.this::repaint);
            }
        }, 0, 50);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int height = getHeight();
                int width = getWidth();
                gameModel.setDefaultBorders(height, width);
                if (height == 0 && width == 0) {
                    return;
                }
                if (targetPositionX > width) {
                    targetPositionX = width;
                }
                if (targetPositionY > height) {
                    targetPositionY = height;
                }
                gameModel.updateTarget(new Target(targetPositionX, targetPositionY));
                GamePanel.this.gameModel.moveRobot(height, width);
                repaint();
            }
        }, 0, 10);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point p = e.getPoint();
                targetPositionX = p.x;
                targetPositionY = p.y;
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
        drawBorders(g2d, gameModel.getBorders());
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

    private void drawBorders(Graphics g, ArrayList<Border> borders){
        for (Border border: borders){
            int x1 = round(border.getStartX());
            int x2 = round(border.getFinishX());
            int y1 = round(border.getStartY());
            int y2 = round(border.getFinishY());
            g.drawLine(x1, y1, x2, y2);
        }
    }
}
