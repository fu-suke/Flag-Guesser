package myapp.states;

import myapp.shapes.MyCrescent;

public class CrescentState extends DrawState {
    public CrescentState(StateManager stateManager) {
        super(stateManager);
    }

    public void mouseDown(int x, int y) {
        currentDrawing = new MyCrescent(x, y, 0, 0); // 大きさがゼロのインスタンスを作る
        setProperties();
    }
}