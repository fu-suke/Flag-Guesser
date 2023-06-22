package myapp.components.buttons.clipbordbuttons;

import java.awt.event.*;
import javax.swing.*;

import myapp.states.*;
import myapp.states.StateManager;
import myapp.common.Observer;

public class SelectButton extends JToggleButton implements Observer {
    StateManager stateManager;
    State state;

    public SelectButton(StateManager stateManager) {
        super("Select");
        this.stateManager = stateManager;
        this.state = new SelectState(stateManager);
        addActionListener(new SelectButtonButtonListener());
    }

    @Override
    public void update() {
        this.setSelected(stateManager.getState() == state);
    }

    class SelectButtonButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // StateManagerのStateをSelectStateに変更する
            stateManager.setState(state);
            stateManager.notifyObservers();
        }
    }

}