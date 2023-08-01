package myapp.components.menuitems.clipboardMenus;

import myapp.states.StateManager;

import java.awt.event.*;
import javax.swing.*;

public class UpperMenu extends JMenuItem {
    private StateManager stateManager;

    public UpperMenu(StateManager stateManager) {
        super("手前へ");
        this.stateManager = stateManager;
        addActionListener(new UpperListener());
    }

    class UpperListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stateManager.getCanvas().getMediator().upper();
            stateManager.notifyObservers(); // フォーカスをCanvasに戻すために呼び出す
        }
    }

}
