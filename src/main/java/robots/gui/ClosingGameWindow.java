package robots.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class ClosingGameWindow extends JInternalFrameClosing {
    private final GameVisualizer visualizer;

    public ClosingGameWindow() {
        super(
                "Игровое поле",
                true,
                true,
                true,
                true,
                "Do you want to exit game window?",
                "Exit game window"
        );
        visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();

    }
}
