package myapp.states;

import myapp.shapes.MyRectangle;

public class RectState extends DrawState {
    public RectState(StateManager stateManager) {
        super(stateManager);
    }

    public void mouseDown(int x, int y) {
        currentDrawing = new MyRectangle(x, y, 0, 0); // 大きさがゼロのインスタンスを作る
        setProperties();
    }
}