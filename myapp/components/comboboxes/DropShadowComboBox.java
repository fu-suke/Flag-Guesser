package myapp.components.comboboxes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import myapp.canvas.Mediator;
import myapp.states.StateManager;


public class DropShadowComboBox extends JComboBox<String> {
    StateManager stateManager;

    public DropShadowComboBox(StateManager stateManager) {
        this.addItem("OFF");
        this.addItem("ON");
        this.stateManager = stateManager;

        // アクションリスナーを追加
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 選択された値を取得する
                JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
                String dropShadow = (String) comboBox.getSelectedItem();
                boolean hasShadow = dropShadow == "ON" ? true : false;
                // StateManagerに変更を反映する
                stateManager.sethasShadow(hasShadow);
                // 選択状態の図形に変更を反映する
                Mediator mediator = stateManager.getCanvas().getMediator();
                mediator.updateSelectedDrawings(drawing -> drawing.sethasShadow(hasShadow));
            }
        });
    }
}
