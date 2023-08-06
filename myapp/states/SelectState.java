package myapp.states;

import java.awt.BasicStroke;
import java.awt.Color;

import myapp.shapes.MyRectangle;
import myapp.canvas.Mediator;

public class SelectState extends State {
    private MyRectangle selectRect = null; // 範囲選択用の四角形
    private MyRectangle trashRect = null; // ゴミ箱用の四角形
    private int lastX, lastY; // 1つ前のマウスの位置

    public SelectState(StateManager stateManager) {
        super(stateManager);
    }

    public void mouseDown(int x, int y) {
        lastX = x;
        lastY = y;

        // マウスクリックされた位置にある図形を選択する
        Mediator mediator = stateManager.getCanvas().getMediator();
        mediator.setSelected(x, y); // 適切な図形を選択状態にする

        // 選択した図形がなければ、選択範囲を作る
        if (mediator.getSelectedDrawings().isEmpty()) {
            createSelectRect(x, y);
        } else {
            mediator.searchResizeRect(x, y);
            mediator.createGuideLines();

            // リサイズモードでない（＝ドラッグモードのとき）
            if (!mediator.isResizeMode()) {
                // ゴミ箱を作る
                createTrashRect();
            }
        }
    }

    public void mouseDrag(int x, int y) {
        Mediator mediator = stateManager.getCanvas().getMediator();
        if (selectRect != null) { // 複数選択モードのとき
            // 選択範囲の大きさのリサイズ
            selectRect.setSize(x - selectRect.getX(), y - selectRect.getY());
            mediator.setSelectedInRect(selectRect);

        } else { // 既に選択されている図形があるとき
            int dx = x - lastX;
            int dy = y - lastY;
            mediator.mouseDrag(dx, dy);
            // ゴミ箱の色を変える
            if (trashRect != null) {
                if (trashRect.contains(x, y)) {
                    trashRect.setlineColor(Color.RED);
                } else {
                    trashRect.setlineColor(Color.GRAY);
                }
            }
        }
        lastX = x;
        lastY = y;
    }

    public void mouseUp(int x, int y) {
        Mediator mediator = stateManager.getCanvas().getMediator();
        // マウスがゴミ箱の上にあるとき、選択されている図形を削除する
        if (trashRect != null) {
            if (trashRect.contains(x, y)) {
                mediator.delete();
            }
            // ゴミ箱を削除する
            mediator.removeDrawing(trashRect);
            trashRect = null;
        }
        // 選択範囲を作っていたら、選択範囲を削除する
        if (selectRect != null) {
            mediator.removeDrawing(selectRect);
            selectRect = null;
        }
        // リサイズした図形のパラメータを正の値に直す
        if (mediator.isResizeMode()) {
            mediator.normalizeDrawings();
        }
        mediator.clearGuideLines();
    }

    private void createSelectRect(int x, int y) {
        selectRect = new MyRectangle(x, y, 0, 0);
        // 点線の線種を設定する
        selectRect.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 1.0f, new float[] { 5.0f }, 0.0f));
        // 塗り色を透明にする
        selectRect.setfillColor(new Color(0, 0, 0, 0));
        selectRect.setAlpha(0);

        // キャンバスに追加する
        Mediator mediator = stateManager.getCanvas().getMediator();
        mediator.addDrawing(selectRect);
    }

    // ゴミ箱用の四角形をキャンバスの右下に作る
    private void createTrashRect() {
        int rectX = stateManager.getCanvas().getWidth() - 50;
        int rectY = stateManager.getCanvas().getHeight() - 50;
        trashRect = new MyRectangle(rectX, rectY, 40, 40);
        trashRect.setlineColor(Color.GRAY);
        // 破線にする
        trashRect.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 1.0f, new float[] { 5.0f }, 0.0f));
        // 透明にする
        trashRect.setAlpha(0);
        // キャンバスに追加する
        Mediator mediator = stateManager.getCanvas().getMediator();
        mediator.addDrawing(trashRect);
    }
}
