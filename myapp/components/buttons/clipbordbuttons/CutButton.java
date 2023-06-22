package myapp.components.buttons.clipbordbuttons;

import myapp.states.StateManager;

import java.awt.event.*;
import javax.swing.*;

public class CutButton extends JButton {
    private StateManager stateManager;

    public CutButton(StateManager stateManager) {
        super("Cut");
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
