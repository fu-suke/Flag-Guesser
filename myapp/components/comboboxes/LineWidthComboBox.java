package myapp.components.comboboxes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import myapp.states.StateManager;
import myapp.shapes.MyDrawing;
import myapp.canvas.Mediator;

public class LineWidthComboBox extends JComboBox<Float> {
    StateManager stateManager;

    public LineWidthComboBox(StateManager stateManager) {
        // 1から10までの値を持つコンボボックスを作成
        Float[] numbers = new Float[10];
        for (int i = 0; i < 10; i++) {
            numbers[i] = (float) i + 1;
        }
        for (Float number : numbers) {
            this.addItem(number);
        }

        // アクションリスナーを追加
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<Float> comboBox = (JComboBox<Float>) e.getSource();
                Float lineWidth = (Float) comboBox.getSelectedItem(); // 選択された値を取得
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
