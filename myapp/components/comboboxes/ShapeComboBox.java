package myapp.components.comboboxes;

import javax.swing.*;
import java.awt.event.*;

import myapp.states.*;

public class ShapeComboBox extends JComboBox<ShapeString> {
    StateManager stateManager;

    public ShapeComboBox(StateManager stateManager) {
        this.stateManager = stateManager;
        ShapeString rect = new ShapeString("四角", new RectState(stateManager));
        ShapeString triangle = new ShapeString("三角", new TriangleState(stateManager));
        ShapeString oval = new ShapeString("楕円", new OvalState(stateManager));
        ShapeString crescent = new ShapeString("三日月", new CrescentState(stateManager));
        ShapeString star = new ShapeString("星", new StarState(stateManager));
        // ShapeString hendecagon = new ShapeString("11角形", new
        // HendecagonState(stateManager));
        ShapeString diagonal = new ShapeString("陣", new DiagonalPolygonState(stateManager));

        // コンボボックスの幅を変更
        this.setPrototypeDisplayValue(crescent);// 一番長い文字列を基準に幅を決める
        this.setMaximumSize(this.getMinimumSize());// そのときの最小幅を最大幅にも適用

        this.addItem(rect);
        this.addItem(triangle);
        this.addItem(oval);
        this.addItem(crescent);
        this.addItem(star);
        // this.addItem(hendecagon);
        this.addItem(diagonal);

        // 図形を追加したい場合はここに追加する

        // アクションリスナーを追加
        this.addActionListener(new ShapeComboBoxActionListener());

    }

    class ShapeComboBoxActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox<ShapeString> comboBox = (JComboBox<ShapeString>) e.getSource();
            ShapeString selectedItem = (ShapeString) comboBox.getSelectedItem();

            if (selectedItem != null) {
                State selectedState = selectedItem.getState();
                stateManager.setState(selectedState);
                // 選択された図形をクリア
                stateManager.getCanvas().getMediator().clearSelectedDrawings();
                stateManager.notifyObservers();
            }
        }
    }
}

class ShapeString {
    private String string;
    private State state;

    public ShapeString(String string, State state) {
        this.string = string;
        this.state = state;
    }

    public State getState() {
        return this.state;
    }

    // toStringメソッドをオーバーライドして文字列表現を提供
    @Override
    public String toString() {
        return this.string;
    }
}
