package myapp.components.menuitems.colorMenus;

import javax.swing.*;
import java.awt.event.*;

import myapp.states.StateManager;

import java.awt.*;

public class BackColorMenu extends JMenuItem {
    private Color color;
    private StateManager stateManager;

    public BackColorMenu(StateManager stateManager, String text, Color color) {
        super(text);
        this.color = color;
        this.stateManager = stateManager;
        addActionListener(new BackColorMenuListener());
    }

    class BackColorMenuListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            // StateManagerに変更を反映
            stateManager.setBackColor(color);
            stateManager.notifyObservers();
        }
    }
}
