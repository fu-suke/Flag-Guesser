package myapp.states;

import myapp.shapes.MyTriangle;

public class TriangleState extends DrawState {
    public TriangleState(StateManager stateManager) {
        super(stateManager);
    }

    public void mouseDown(int x, int y) {
        // System.out.println("TriangleState.mouseDown");
        currentDrawing = new MyTriangle(x, y, 0, 0); // 大きさがゼロのインスタンスを作る
        setProperties();
    }
}