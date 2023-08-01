package myapp.components.menuitems.clipboardMenus;

import java.awt.event.*;
import javax.swing.*;
import myapp.states.StateManager;

public class PasteMenu extends JMenuItem {
    private StateManager stateManager;

    public PasteMenu(StateManager stateManager) {
        super("貼り付け");
        this.stateManager = stateManager;
        addActionListener(new PasteListener());
    }

    class PasteListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stateManager.getCanvas().getMediator().paste(0, 0);
            stateManager.notifyObservers(); // フォーカスをCanvasに戻すために呼び出す
        }
    }
}
