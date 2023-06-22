package myapp.components.buttons.clipbordbuttons;

import java.awt.event.*;
import javax.swing.*;
import myapp.states.StateManager;

public class PasteButton extends JButton {
    private StateManager stateManager;

    public PasteButton(StateManager stateManager) {
        super("Paste");
        this.stateManager = stateManager;
        addActionListener(new PasteListener());
    }

    class PasteListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stateManager.getCanvas().getMediator().paste(10, 10); // とりあえず10,10に出す
            stateManager.notifyObservers(); // フォーカスをCanvasに戻すために呼び出す
        }
    }
}
