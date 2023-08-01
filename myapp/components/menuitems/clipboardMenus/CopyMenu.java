package myapp.components.menuitems.clipboardMenus;

import myapp.states.StateManager;

import java.awt.event.*;
import javax.swing.*;

public class CopyMenu extends JMenuItem {
    private StateManager stateManager;

    public CopyMenu(StateManager stateManager) {
        super("コピー");
        this.stateManager = stateManager;
        addActionListener(new CopyListener());
    }

    class CopyListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stateManager.getCanvas().getMediator().copy();
            stateManager.notifyObservers(); // フォーカスをCanvasに戻すために呼び出す
        }
    }

}
