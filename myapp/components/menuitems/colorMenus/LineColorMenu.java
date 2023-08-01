package myapp.components.menuitems.colorMenus;

import javax.swing.*;
import java.awt.event.*;

import myapp.states.StateManager;
import myapp.canvas.Mediator;

import java.awt.*;

public class LineColorMenu extends JMenuItem {
    private Color color;
    private StateManager stateManager;

    public LineColorMenu(StateManager stateManager, String text, Color color) {
        super(text);
        this.color = color;
        this.stateManager = stateManager;
        addActionListener(new LineColorMenuListener());
    }

    class LineColorMenuListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            // StateManagerに変更を反映
            stateManager.setLineColor(color);
            // 選択状態の図形があれば、図形の線の色を変更する
            Mediator mediator = stateManager.getCanvas().getMediator();
            mediator.updateSelectedDrawings(drawing -> drawing.setlineColor(color));
        }
    }
}