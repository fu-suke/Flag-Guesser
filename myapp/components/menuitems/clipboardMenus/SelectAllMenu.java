package myapp.components.menuitems.clipboardMenus;

import java.awt.event.*;
import javax.swing.*;
import myapp.states.StateManager;

public class SelectAllMenu extends JMenuItem {
    private StateManager stateManager;

    public SelectAllMenu(StateManager stateManager) {
        super("すべて選択");
        this.stateManager = stateManager;
        addActionListener(new SelectAllListener());
    }

    class SelectAllListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stateManager.getCanvas().getMediator().selectAll();
            stateManager.notifyObservers(); // フォーカスをCanvasに戻すために呼び出す
        }
    }
}
