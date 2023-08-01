package myapp.components.menuitems.clipboardMenus;

import javax.swing.*;
import java.awt.event.*;
import myapp.states.StateManager;

// SelectStateで選択状態にある図形を削除するボタン
public class DeleteMenu extends JMenuItem {
    StateManager stateManager;

    public DeleteMenu(StateManager stateManager) {
        super("削除");
        this.stateManager = stateManager;
        addActionListener(new DeleteMenuListener());
    }

    class DeleteMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stateManager.getCanvas().getMediator().delete();
            stateManager.notifyObservers();// フォーカスをCanvasに戻すために呼び出す
        }
    }
}
