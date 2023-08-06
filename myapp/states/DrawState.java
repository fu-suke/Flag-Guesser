package myapp.states;

import myapp.shapes.MyDrawing;
import myapp.shapes.MyRectangle;

public class DrawState extends State {
    MyDrawing drawing;
    MyDrawing currentDrawing;

    // 最初の1回だけ呼ばれる
    public DrawState(StateManager stateManager) {
        super(stateManager);
        this.drawing = new MyRectangle();
    }

    // 2回目以降はこっちが呼ばれる
    public DrawState(StateManager stateManager, MyDrawing drawing) {
        super(stateManager);
        this.drawing = drawing;
    }

    @Override
    public void mouseDown(int x, int y) {
        currentDrawing = drawing.clone();
        setProperties(x, y); // (x,y) に大きさがゼロのインスタンスを作る
    }

    @Override
    public void mouseUp(int x, int y) {
        // 大きさが小さすぎるインスタンスを消去して描画処理を終了する
        if (Math.abs(currentDrawing.getW()) < 10 || Math.abs(currentDrawing.getH()) < 10) {
            stateManager.getCanvas().getMediator().removeDrawing(currentDrawing);
        }
        // マウスを離したときに負の高さや幅を補正する
        currentDrawing.setAdjustedParams();
    }

    // 初期位置からの差分をとって大きさを決定する
    @Override
    public void mouseDrag(int x, int y) {
        currentDrawing.resize(x - currentDrawing.getX() - currentDrawing.getW(),
                y - currentDrawing.getY() - currentDrawing.getH());
    }

    // 描画処理を初期化する
    // StateManagerから図形のプロパティを取得して、図形に反映させる
    public void setProperties(int x, int y) {
        currentDrawing.setLocation(x, y);
        currentDrawing.setSize(0, 0);
        currentDrawing.sethasShadow(stateManager.gethasShadow());
        currentDrawing.setStroke(stateManager.getStroke());
        currentDrawing.setLineMultiplicity(stateManager.getlineMultiplicity());
        currentDrawing.setColor(stateManager.getLineColor(), stateManager.getFillColor());
        currentDrawing.setAlpha(stateManager.getAlpha());
        stateManager.getCanvas().getMediator().addDrawing(currentDrawing);
    }
}
