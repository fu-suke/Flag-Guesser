package myapp.components.buttons.clipbordbuttons;

import myapp.states.StateManager;

import java.awt.event.*;
import javax.swing.*;

public class CopyButton extends JButton {
    private StateManager stateManager;

    public CopyButton(StateManager stateManager) {
        super("Copy");
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
