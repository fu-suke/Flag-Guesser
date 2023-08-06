package myapp.components.comboboxes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import myapp.canvas.Mediator;
import myapp.states.StateManager;

public class LinePatternComboBox extends JComboBox<StrokeString> {
    StateManager stateManager;

    public LinePatternComboBox(StateManager stateManager) {
        this.stateManager = stateManager;
        StrokeString solid = new StrokeString("───", new BasicStroke(1.0f));
        StrokeString pattern1 = new StrokeString("- - - - -", new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 10.0f, new float[] { 5.0f, 5.0f }, 0.0f));
        StrokeString pattern2 = new StrokeString("─  ─", new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 10.0f, new float[] { 10.0f, 10.0f }, 0.0f));
        StrokeString pattern3 = new StrokeString("─・─", new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 10.0f, new float[] { 10.0f, 10.0f, 2.0f, 10.0f }, 0.0f));
        // 線種を追加したい場合はここに追加する

        this.addItem(solid);
        this.addItem(pattern2);
        this.addItem(pattern1);
        this.addItem(pattern3);

        this.setPrototypeDisplayValue(solid);// 一番長い文字列を基準に幅を決める
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<StrokeString> comboBox = (JComboBox<StrokeString>) e.getSource();
                StrokeString selectedItem = (StrokeString) comboBox.getSelectedItem();

                if (selectedItem != null) {
                    BasicStroke oldStroke = selectedItem.getStroke();
                    // StateManagerから現在の線の幅を取得
                    float currentLineWidth = stateManager.getStroke().getLineWidth();
                    BasicStroke newStroke = new BasicStroke(
                            currentLineWidth,
                            oldStroke.getEndCap(),
                            oldStroke.getLineJoin(),
                            oldStroke.getMiterLimit(),
                            oldStroke.getDashArray(),
                            oldStroke.getDashPhase());
                    stateManager.setStroke(newStroke);
                    // 選択状態の図形の線の種類を変更する
                    Mediator mediator = stateManager.getCanvas().getMediator();
                    mediator.updateSelectedDrawings(drawing -> {
                        // StateManagerが保持している線幅ではなく、図形が保持している線幅を使う
                        float width = drawing.getStroke().getLineWidth();
                        BasicStroke newStroke2 = new BasicStroke(
                                width,
                                newStroke.getEndCap(),
                                newStroke.getLineJoin(),
                                newStroke.getMiterLimit(),
                                newStroke.getDashArray(),
                                newStroke.getDashPhase());
                        drawing.setStroke(newStroke2);
                    });
                }
            }
        });
    }
}

// JComboBoxに表示するためにtoString()メソッドを用意する
class StrokeString {
    private String string;
    private BasicStroke stroke;

    public StrokeString(String string, BasicStroke stroke) {
        this.string = string;
        this.stroke = stroke;
    }

    public BasicStroke getStroke() {
        return this.stroke;
    }

    // toStringメソッドをオーバーライドして文字列表現を提供
    @Override
    public String toString() {
        return this.string;
    }
}
