package myapp.canvas;

import java.util.*;
import java.util.function.Consumer;

import myapp.shapes.MyDrawing;
import myapp.shapes.MyLine;
import myapp.states.StateManager;

// drawings、および現在選択されている図形に対して操作を行うクラス
public class Mediator {
    private List<MyDrawing> drawings;

    private List<MyDrawing> guideLines = new ArrayList<>();
    private MyCanvas canvas;
    private List<MyDrawing> selectedDrawings = new ArrayList<>();
    private StateManager stateManager;
    private List<MyDrawing> buffer = new ArrayList<>();
    private int resizeMode = -1;

    public Mediator(MyCanvas canvas) {
        this.canvas = canvas;
        drawings = new ArrayList<>();
    }

    /* 上下に移動機能に関連する関数たち */

    public void upper() {
        // 選択状態の図形を一番手前に移動する
        for (MyDrawing d : selectedDrawings) {
            drawings.remove(d);
            drawings.add(d);
        }
        repaint();
    }

    public void lower() {
        // 選択状態の図形を一番奥に移動する
        // 後ろから順に移動しないと前後関係が逆転してしまうことに注意する
        for (int i = selectedDrawings.size() - 1; i >= 0; i--) {
            MyDrawing d = selectedDrawings.get(i);
            drawings.remove(d);
            drawings.add(0, d);
        }
        repaint();
    }

    /* 補助線機能に関連する関数たち */

    public void createGuideLines() {
        // 選択状態以外の図形の中心に、ガイドラインを作る
        guideLines.clear();
        for (MyDrawing d : drawings) {
            if (d.isSelected()) {
                continue;
            }
            int[] center = d.getCenter();
            guideLines.add(new MyLine(center[0], center[1], 0, 0)); // タテ用
            guideLines.add(new MyLine(center[0], center[1], 0, 0)); // ヨコ用
        }
    }

    public List<MyDrawing> getGuideLines() {
        return guideLines;
    }

    public void displayGuideLines() {
        // 一度ガイドラインをリセットする
        resetGuideLines();

        // ドラッグ中の図形とガイドラインの中心が重なっているかどうかを判定する
        for (int i = 0; i < guideLines.size(); i += 2) {
            // 垂直方向の線は偶数インデックス、水平方向の線は奇数インデックスに保存されている
            MyLine verticalLine = (MyLine) guideLines.get(i);
            MyLine horizontalLine = (MyLine) guideLines.get(i + 1);
            int[] v_line_center = verticalLine.getCenter();
            int[] h_line_center = horizontalLine.getCenter();

            for (MyDrawing d : drawings) {
                // 選択状態以外の図形は無視する
                if (!d.isSelected()) {
                    continue;
                }
                // ドラッグ中の図形の中心が重なっているかをチェック
                int[] d_center = d.getCenter();
                boolean isXMatched = isInRange(d_center[0], v_line_center[0]);

                if (isXMatched) {
                    verticalLine.setSize(0, 9999);
                    d.setLocation(v_line_center[0] - d.getW() / 2, d.getY());
                    break;
                }
            }

            for (MyDrawing d : drawings) {
                // 選択状態以外の図形は無視する
                if (!d.isSelected()) {
                    continue;
                }
                // ドラッグ中の図形の中心が重なっているかをチェック
                int[] d_center = d.getCenter();
                boolean isYMatched = isInRange(d_center[1], h_line_center[1]);

                if (isYMatched) {
                    horizontalLine.setSize(9999, 0);
                    d.setLocation(d.getX(), h_line_center[1] - d.getH() / 2);
                    break;
                }
            }
        }
    }

    private boolean isInRange(int target, int center) {
        return center - 10 <= target && target <= center + 10;
    }

    private void resetGuideLines() {
        for (MyDrawing d : guideLines) {
            d.setSize(0, 0);
        }
    }

    public void clearGuideLines() {
        guideLines.clear();
    }

    /* リサイズ機能に関連する関数たち */

    public void searchResizeRect(int mouseX, int mouseY) {
        this.resizeMode = -1;
        for (int i = selectedDrawings.size() - 1; i >= 0; i--) {
            MyDrawing d = selectedDrawings.get(i);
            // クリックした場所が選択状態の図形の短径内にあるかどうかを判定する
            int idx = d.containsResizeRect(mouseX, mouseY);
            if (idx != -1) {
                this.resizeMode = idx;
            }
        }
    }

    public boolean isResizeMode() {
        return this.resizeMode != -1;
    }

    public void dragOrResize(int dx, int dy) {
        // 選択図形が1つかつ、リサイズモードのとき
        if (this.resizeMode != -1) {
            // 選択状態の図形のリサイズ
            for (MyDrawing d : selectedDrawings) {
                d.resize(dx, dy, resizeMode);
            }
        }
        // 選択図形が複数か、リサイズモードでないとき
        else {
            // 選択状態の図形を動かす
            for (MyDrawing d : selectedDrawings) {
                d.move(dx, dy);
                // 補助線を出す
                displayGuideLines();
            }
        }
    }

    public void selectAll() {
        if (!stateManager.isSelectState()) {
            return;
        }
        // 全ての図形を選択状態にする
        for (MyDrawing d : drawings) {
            setAsSelectedDrawing(d);
        }
        repaint();
    }

    // 関数を引数にとって、selectedDrawingsに対して実行する
    public void updateSelectedDrawings(Consumer<MyDrawing> function) {
        for (MyDrawing d : selectedDrawings) {
            function.accept(d);
        }
        stateManager.notifyObservers();
    }

    public void clearBuffer() {
        buffer.clear();
    }

    public void copy() {
        if (!stateManager.isSelectState()) {
            return;
        }
        // バッファをクリアする
        clearBuffer();
        if (selectedDrawings.isEmpty()) {
            return;
        }
        // selectedDrawingsをbufferにコピーする
        for (MyDrawing d : selectedDrawings) {
            buffer.add(d.clone());
        }
    }

    public void cut() {
        if (!stateManager.isSelectState()) {
            return;
        }
        // バッファをクリアする
        clearBuffer();
        if (selectedDrawings.isEmpty()) {
            return;
        }
        // selectedDrawingsをbufferにコピーして削除する
        for (MyDrawing d : selectedDrawings) {
            buffer.add(d.clone());
            drawings.remove(d);
        }
        repaint();

    }

    public void paste(int x, int y) {
        if (!stateManager.isSelectState()) {
            return;
        }
        if (buffer.isEmpty()) {
            return;
        }
        int[] average = getAverage(); // selectedDrawingsの中心座標の平均を求める
        clearSelectedDrawings(); // 今ある図形を非選択状態にする
        for (MyDrawing d : buffer) {
            int dx = d.getX() - average[0];
            int dy = d.getY() - average[1];

            MyDrawing clone = (MyDrawing) d.clone();
            // x,yを表示
            if (x == 0 && y == 0) {
                clone.setLocation(0, 0);
            } else {
                clone.setLocation(x + dx, y + dy);

            }
            addDrawing(clone);
            // ペーストで出てきたやつをすべて選択状態にする
            setAsSelectedDrawing(clone);
        }
        repaint();
    }

    public int[] getAverage() {
        // bufferにいる図形の中心座標の平均を求める
        int[] average = new int[2];
        for (MyDrawing d : buffer) {
            int[] center = d.getCenter();
            average[0] += center[0];
            average[1] += center[1];
        }
        average[0] /= buffer.size();
        average[1] /= buffer.size();
        return average;
    }

    public List<MyDrawing> getDrawings() {
        return drawings;
    }

    public void setDrawings(List<MyDrawing> drawings) {
        this.drawings = drawings; // ロードされたデータをセットする
        clearSelectedDrawings(); // 念のため選択状態をクリアする
        canvas.repaint();
    }

    public void addDrawing(MyDrawing d) {
        drawings.add(d);
    }

    public void removeDrawing(MyDrawing d) {
        selectedDrawings.remove(d);
        drawings.remove(d);
    }

    public void delete() {
        if (!stateManager.isSelectState()) {
            return;
        }
        for (MyDrawing d : selectedDrawings) {
            // キャンバスから取り除く
            drawings.remove(d);
        }
        // selectedDrawingsをクリアする
        clearSelectedDrawings();
        repaint();
    }

    public List<MyDrawing> getSelectedDrawings() {
        return selectedDrawings;
    }

    public void repaint() {
        canvas.repaint();
    }

    public boolean hasBuffer() {
        return !buffer.isEmpty();
    }

    public boolean hasSelectedDrawings() {
        return !selectedDrawings.isEmpty();
    }

    public void setAsSelectedDrawing(MyDrawing d) {
        d.setSelected(true);
        selectedDrawings.add(d);
    }

    public void setSelected(int x, int y) {
        // 一番手前にある図形から順に調べていく
        for (int i = drawings.size() - 1; i >= 0; i--) {
            MyDrawing d = drawings.get(i);
            // クリックされた座標にリサイズ用の■があるなら
            int idx = d.containsResizeRect(x, y);
            if (idx != -1) {
                // 何もせず処理を終了する
                return;
            }
            if (d.contains(x, y)) { // クリックされた座標に図形があるなら
                // かつ、その図形が選択状態なら
                if (d.isSelected()) {
                    return; // 何もせず処理を終了する
                }
                // そうでないなら、その図形を選択状態にしてその他の図形を非選択状態にする
                else {
                    for (MyDrawing drawing : drawings) {
                        drawing.setSelected(false);
                    }
                    d.setSelected(true);
                    selectedDrawings.clear();
                    selectedDrawings.add(d);
                    return;
                }
            }
        }
        // 何も図形がないときはクリアする
        clearSelectedDrawings();
    }

    public void clearSelectedDrawings() {
        // 全ての図形を非選択状態にする
        for (MyDrawing d : selectedDrawings) {
            d.setSelected(false);
        }
        // selectedDrawingsも空にする
        selectedDrawings.clear();
    }

    // 四角形の内部にある図形を全て選択状態にする
    public void setSelectedInRect(MyDrawing rect) {
        clearSelectedDrawings();
        int[] params = rect.getAdjustedParams();
        int x = params[0];
        int y = params[1];
        int w = params[2];
        int h = params[3];
        for (MyDrawing d : drawings) {
            // 図形dの内部がrectの短形に含まれているかどうかを調べる
            if (d.intersects(x, y, w, h)) {
                setAsSelectedDrawing(d); // 含まれているなら選択状態にする
            }
        }
        // rect自身は削除するので、選択状態にしない
        selectedDrawings.remove(rect);
        rect.setSelected(false);
    }

    public void setStateManager(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    public StateManager getStateManager() {
        return stateManager;
    }

    public MyCanvas getCanvas() {
        return canvas;
    }
}