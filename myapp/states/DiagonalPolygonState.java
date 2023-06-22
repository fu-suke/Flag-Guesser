package myapp.states;

import myapp.shapes.MyDiagonalPolygon;

public class DiagonalPolygonState extends DrawState {

    public DiagonalPolygonState(StateManager stateManager) {
        super(stateManager);
    }

    public void mouseDown(int x, int y) {
        currentDrawing = new MyDiagonalPolygon(x, y, 0, 0, 6);
        initializeDrawing();
    }
}