package myapp.states;

import myapp.shapes.MyHendecagon;

public class HendecagonState extends DrawState {

    public HendecagonState(StateManager stateManager) {
        super(stateManager);
    }

    public void mouseDown(int x, int y) {
        currentDrawing = new MyHendecagon(x, y, 0, 0); // 大きさがゼロのインスタンスを作る
        initializeDrawing();
    }
}