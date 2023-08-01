package myapp.components.comboboxes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import myapp.states.StateManager;
import myapp.canvas.Mediator;

public class LineWidthComboBox extends JComboBox<Integer> {
    StateManager stateManager;

    public LineWidthComboBox(StateManager stateManager) {
        this.setPrototypeDisplayValue(100); // 幅の設定("100"という文字が収まるサイズになる)
        // 1から10までの値を持つコンボボックスを作成
        Integer[] numbers = new Integer[10];
        for (int i = 0; i < 10; i++) {
            numbers[i] = (Integer) i + 1;
        }
        for (Integer number : numbers) {
            this.addItem(number);
        }

        // アクションリスナーを追加
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<Integer> comboBox = (JComboBox<Integer>) e.getSource();
                Integer lineWidth = (Integer) comboBox.getSelectedItem(); // 選択された値を取得
                BasicStroke currentStroke = stateManager.getStroke();
                // 新しいストロークをセットする
                BasicStroke newStroke = new BasicStroke(
                        lineWidth,
                        currentStroke.getEndCap(),
                        currentStroke.getLineJoin(),
                        currentStroke.getMiterLimit(),
                        currentStroke.getDashArray(),
                        currentStroke.getDashPhase());
                stateManager.setStroke(newStroke);
                // 選択されている図形のストロークを変更する
                Mediator mediator = stateManager.getCanvas().getMediator();
                mediator.updateSelectedDrawings(drawing -> {
                    // StateManagerが保持しているストロークではなく、図形が保持しているストロークを使う
                    BasicStroke stroke = drawing.getStroke();
                    BasicStroke newStroke2 = new BasicStroke(
                            lineWidth,
                            stroke.getEndCap(),
                            stroke.getLineJoin(),
                            stroke.getMiterLimit(),
                            stroke.getDashArray(),
                            stroke.getDashPhase());
                    drawing.setStroke(newStroke2);
                });
            }
        });
    }
}
