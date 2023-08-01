package myapp.states;

import myapp.shapes.MyStar;

public class StarState extends DrawState {
    public StarState(StateManager stateManager) {
        super(stateManager);
    }

    public void mouseDown(int x, int y) {
        // System.out.println("StarState.mouseDown");
        currentDrawing = new MyStar(x, y, 0, 0); // 大きさがゼロのインスタンスを作る
        setProperties();
    }
}