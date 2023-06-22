package myapp.components.buttons.shapebuttons;

import myapp.states.StateManager;
import myapp.states.DiagonalPolygonState;

public class DiagonalPolygonButton extends ShapeButton {

    public DiagonalPolygonButton(StateManager stateManager) {
        super("Diagonal Polygon", stateManager, new DiagonalPolygonState(stateManager));
    }

}