package robots.view.menus;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class LookAndFeelMenu extends JMenu {

    public LookAndFeelMenu(String title) {
        super(title);
        setMnemonic(KeyEvent.VK_V);
        getAccessibleContext().setAccessibleDescription(
                "Управление режимом отображения приложения"
        );
    }
}
