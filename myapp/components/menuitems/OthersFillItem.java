package myapp.components.menuitems;

import javax.swing.JColorChooser;
import javax.swing.JMenuItem;

import java.awt.Color;
import java.awt.event.*;

import myapp.states.StateManager;
import myapp.canvas.Mediator;

public class OthersFillItem extends JMenuItem {

    public OthersFillItem(StateManager stateManager) {
        super("Others...");
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Color initialBackground = stateManager.getFillColor(); // 現在のFillColorの値を取得
                Color color = JColorChooser.showDialog(null, "Select a Color", initialBackground);
                if (color != null) {
                    // StateManagerに変更を反映
                    stateManager.setFillColor(color);
                    // 選択状態の図形があれば、図形の塗り色を変更する
                    Mediator mediator = stateManager.getCanvas().getMediator();
                    mediator.updateSelectedDrawings(drawing -> drawing.setfillColor(color));
                }
            }
        });
    }
}
