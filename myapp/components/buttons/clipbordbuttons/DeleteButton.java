package myapp.components.buttons.clipbordbuttons;

import javax.swing.*;
import java.awt.event.*;
import myapp.states.StateManager;

// SelectStateで選択状態にある図形を削除するボタン
public class DeleteButton extends JButton {
    StateManager stateManager;

    public DeleteButton(StateManager stateManager) {
        super("Delete");
        this.stateManager = stateManager;
        addActionListener(new DeleteButtonListener());
    }

    class DeleteButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stateManager.getCanvas().getMediator().removeSelectedDrawing();
            stateManager.notifyObservers();// フォーカスをCanvasに戻すために呼び出す
        }
    }
}
