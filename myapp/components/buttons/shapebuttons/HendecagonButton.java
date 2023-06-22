package myapp.components.buttons.shapebuttons;

import myapp.states.StateManager;
import myapp.states.HendecagonState;

public class HendecagonButton extends ShapeButton {
    StateManager stateManager;

    public HendecagonButton(StateManager stateManager) {
        super("Hendecagon", stateManager, new HendecagonState(stateManager));
    }
}