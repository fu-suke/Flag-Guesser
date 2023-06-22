package myapp.components.buttons.shapebuttons;

import myapp.states.StateManager;
import myapp.states.OvalState;

public class OvalButton extends ShapeButton {
    StateManager stateManager;

    public OvalButton(StateManager stateManager) {
        super("Oval", stateManager, new OvalState(stateManager));
    }

}