package myapp.components.buttons.shapebuttons;

import myapp.states.StateManager;
import myapp.states.RectState;

public class RectButton extends ShapeButton {

    public RectButton(StateManager stateManager) {
        super("Rect", stateManager, new RectState(stateManager));
    }

}