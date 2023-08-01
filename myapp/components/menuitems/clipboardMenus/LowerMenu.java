package myapp.components.menuitems.clipboardMenus;

import myapp.states.StateManager;

import java.awt.event.*;
import javax.swing.*;

public class LowerMenu extends JMenuItem {
    private StateManager stateManager;

    public LowerMenu(StateManager stateManager) {
        super("奥へ");
        this.stateManager = stateManager;
        addActionListener(new LowerListener());
    }

    class LowerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stateManager.getCanvas().getMediator().lower();
            stateManager.notifyObservers(); // フォーカスをCanvasに戻すために呼び出す
        }
    }

}
