package myapp.components.buttons.shapebuttons;

import javax.swing.JToggleButton;
import java.awt.event.*;

import myapp.states.StateManager;
import myapp.shapes.MyDrawing;
import myapp.states.State;
import myapp.common.Observer;

public class ShapeButton extends JToggleButton implements Observer {

    StateManager stateManager;
    State state;

    public ShapeButton(String text, StateManager stateManager, State state) {
        super(text);
        this.stateManager = stateManager;
        this.state = state;
        addActionListener(new ShapeButtonListener());
    }

    @Override
    public void update() {
        // StateManagerのStateが自分のStateと同じかどうかでトグルのON/OFFを切り替える
        this.setSelected(stateManager.getState() == state);
    }

    class ShapeButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stateManager.setState(state);
            MyDrawing selectedDrawing = stateManager.getCanvas().getMediator().getSelectedDrawing();
            // 選択状態の図形を非選択状態にする
            if (selectedDrawing != null) {
                selectedDrawing.setSelected(false);
                stateManager.getCanvas().getMediator().clearSelectedDrawing();
                stateManager.getCanvas().repaint();
            }
            stateManager.notifyObservers();
        }
    }

}