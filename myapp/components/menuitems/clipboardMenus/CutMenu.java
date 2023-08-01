package myapp.components.menuitems.clipboardMenus;

import myapp.states.StateManager;

import java.awt.event.*;
import javax.swing.*;

public class CutMenu extends JMenuItem {
    private StateManager stateManager;

    public CutMenu(StateManager stateManager) {
        super("切り取り");
        this.stateManager = stateManager;
        addActionListener(new CutListener());
    }

    class CutListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stateManager.getCanvas().getMediator().cut();
            stateManager.notifyObservers(); // フォーカスをCanvasに戻すために呼び出す
        }
    }

}
