package myapp.states;

import myapp.shapes.MyOval;

public class OvalState extends DrawState {

    public OvalState(StateManager stateManager) {
        super(stateManager);
    }

    public void mouseDown(int x, int y) {
        currentDrawing = new MyOval(x, y, 0, 0); // 大きさがゼロのインスタンスを作る
        initializeDrawing();
    }
}