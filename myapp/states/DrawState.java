package myapp.states;

public abstract class DrawState extends State {

    public DrawState(StateManager stateManager) {
        super(stateManager);
    }

    @Override
    public void mouseUp(int x, int y) {
        // 大きさが小さすぎるインスタンスを消去して描画処理を終了する
        if (Math.abs(currentDrawing.getW()) < 20 && Math.abs(currentDrawing.getH()) < 20) {
            stateManager.getCanvas().getMediator().removeDrawing(currentDrawing);
        }
        currentDrawing.setRegion(); // 描画する場合は、描画領域を設定する
    }

    // 初期位置からの差分をとって大きさを決定する
    @Override
    public void mouseDrag(int x, int y) {
        currentDrawing.setSize(x - currentDrawing.getX(), y - currentDrawing.getY());
    }

    // 描画処理を初期化する
    public void initializeDrawing() {
        currentDrawing.sethasShadow(stateManager.gethasShadow());
        currentDrawing.setStroke(stateManager.getStroke());
        currentDrawing.setLineMultiplicity(stateManager.getlineMultiplicity());
        currentDrawing.setColor(stateManager.getLineColor(), stateManager.getFillColor());

        stateManager.getCanvas().getMediator().addDrawing(currentDrawing);
    }
}
