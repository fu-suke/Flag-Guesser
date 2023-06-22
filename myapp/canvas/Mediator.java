package myapp.canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import myapp.shapes.MyDrawing;
import myapp.states.StateManager;
import myapp.common.Observer;

// drawings、および現在選択されている図形に対して操作を行うクラス
public class Mediator implements Observer {
    private List<MyDrawing> drawings;
    private MyCanvas canvas;
    private MyDrawing selectedDrawing = null;
    private StateManager stateManager;

    MyDrawing buffer = null; // Cut, Copyバッファ

    public Mediator(MyCanvas canvas) {
        this.canvas = canvas;
        drawings = new ArrayList<>();
    }

    // 関数を引数にとって、selectedDrawingに対して実行する
    public void updateSelectedDrawings(Consumer<MyDrawing> function) {
        if (selectedDrawing != null) {
            function.accept(selectedDrawing);
        }
        stateManager.notifyObservers();
    }

    public void clearBuffer() {
        buffer = null;
    }

    public void copy() {
        if (selectedDrawing == null) {
            return;
        }
        // バッファをクリアする
        clearBuffer();
        buffer = selectedDrawing.clone();
    }

    public void cut() {
        if (selectedDrawing == null) {
            return;
        }
        // バッファをクリアする
        clearBuffer();
        buffer = selectedDrawing.clone();
        removeDrawing(selectedDrawing); // drawingsからselectedDrawingを削除。
        repaint();
    }

    public void paste(int x, int y) {
        MyDrawing clone = (MyDrawing) buffer.clone();
        clone.setLocation(x, y);
        addDrawing(clone);
        // ペーストで出てきたやつを選択状態にする
        setSelectedDrawing(clone);
        repaint();
    }

    public List<MyDrawing> getDrawings() {
        return drawings;
    }

    public void addDrawing(MyDrawing d) {
        drawings.add(d);
    }

    public void removeDrawing(MyDrawing d) {
        drawings.remove(d);
    }

    public void removeSelectedDrawing() {
        drawings.remove(selectedDrawing);
        selectedDrawing = null;
        repaint();
    }

    public MyDrawing getSelectedDrawing() {
        return selectedDrawing;
    }

    public void repaint() {
        canvas.repaint();
    }

    public void setSelectedDrawing(MyDrawing d) {
        // drawingsを調べ、dと一致しないものをsetSelectedでfalseにする。dと一致するものをtrueにする
        for (MyDrawing drawing : drawings) {
            if (drawing == d) {
                drawing.setSelected(true);
            } else {
                drawing.setSelected(false);
            }
        }
        selectedDrawing = d;
        System.out.println("Selected: " + selectedDrawing);
    }

    public void setSelected(int x, int y) {
        // 一番手前にある図形から順に調べていく
        for (int i = drawings.size() - 1; i >= 0; i--) {
            MyDrawing d = drawings.get(i);
            if (d.contains(x, y)) {
                setSelectedDrawing(d);
                return;
            }
        }
        // 何も図形がないときはnullをセットする
        setSelectedDrawing(null);
    }

    public void setSelectedLatest() {
        int i = drawings.size() - 1;
        MyDrawing d = drawings.get(i);
        setSelectedDrawing(d);
    }

    public void clearSelectedDrawing() {
        selectedDrawing = null;
    }

    public void setStateManager(StateManager stateManager) {
        this.stateManager = stateManager;
        stateManager.addObserver(this);
    }

    public void update() {
        // StateManagerから色や線などのプロパティを取得して、selectedDrawingに反映させる
        // if (selectedDrawing != null) {
        // selectedDrawing.setColor(stateManager.getLineColor(),
        // stateManager.getFillColor());
        // selectedDrawing.setStroke(stateManager.getStroke());
        // selectedDrawing.sethasShadow(stateManager.gethasShadow());
        // selectedDrawing.setLineMultiplicity(stateManager.getlineMultiplicity());
        // }
    }

    public StateManager getStateManager() {
        return stateManager;
    }
}