package myapp.states;

public class SelectState extends State {
    // ドラッグ開始時のオフセット
    private int offsetX;
    private int offsetY;

    public SelectState(StateManager stateManager) {
        super(stateManager);
    }

    public void mouseDown(int x, int y) {
        // マウスクリックされた位置にある図形を選択する
        stateManager.getCanvas().getMediator().setSelected(x, y);
        currentDrawing = stateManager.getCanvas().getMediator().getSelectedDrawing();
        if (currentDrawing != null) { // 選択した図形があれば、オフセットを計算する
            offsetX = x - currentDrawing.getX();
            offsetY = y - currentDrawing.getY();
        }
        stateManager.notifyObservers();
    }

    public void mouseUp(int x, int y) {
    }

    public void mouseDrag(int x, int y) {
        // 選択した図形を動かす
        if (currentDrawing != null) {
            currentDrawing.move(x - currentDrawing.getX() - offsetX,
                    y - currentDrawing.getY() - offsetY);
        }
    }
}
