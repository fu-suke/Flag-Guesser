package myapp.states;

import myapp.shapes.MyDrawing;

public abstract class DrawState extends State {
    MyDrawing currentDrawing;

    public DrawState(StateManager stateManager) {
        super(stateManager);
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
    public void setProperties() {
        currentDrawing.sethasShadow(stateManager.gethasShadow());
        currentDrawing.setStroke(stateManager.getStroke());
        currentDrawing.setLineMultiplicity(stateManager.getlineMultiplicity());
        currentDrawing.setColor(stateManager.getLineColor(), stateManager.getFillColor());
        currentDrawing.setAlpha(stateManager.getAlpha());

        stateManager.getCanvas().getMediator().addDrawing(currentDrawing);
    }
}
