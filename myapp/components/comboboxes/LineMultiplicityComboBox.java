package myapp.components.comboboxes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import myapp.states.StateManager;
import myapp.canvas.Mediator;

public class LineMultiplicityComboBox extends JComboBox<Integer> {
    StateManager stateManager;

    public LineMultiplicityComboBox(StateManager stateManager) {
        this.setPrototypeDisplayValue(100); // 幅の設定("100"という文字が収まるサイズになる)
        // 1から10までの値を持つコンボボックスを作成
        Integer[] numbers = new Integer[10];
        for (int i = 0; i < 10; i++) {
            numbers[i] = (Integer) i + 1;
        }
        for (Integer number : numbers) {
            this.addItem(number);
        }
        this.stateManager = stateManager;

        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 選択された値を取得する
                JComboBox<Integer> comboBox = (JComboBox<Integer>) e.getSource();
                Integer lineMultiplicity = (Integer) comboBox.getSelectedItem();
                // StateManagerの値を更新する
                stateManager.setlineMultiplicity(lineMultiplicity);
                // 選択状態の図形を更新する
                Mediator mediator = stateManager.getCanvas().getMediator();
                mediator.updateSelectedDrawings(drawing -> drawing.setLineMultiplicity(lineMultiplicity));
            }
        });

    }
}
