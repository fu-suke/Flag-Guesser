package myapp.components.menuitems.colorMenus;

import javax.swing.*;
import java.awt.event.*;

import myapp.states.StateManager;
import myapp.canvas.Mediator;

import java.awt.*;

public class FillColorMenu extends JMenuItem {
    private Color color;
    private StateManager stateManager;

    public FillColorMenu(StateManager stateManager, String text, Color color) {
        super(text);
        this.color = color;
        this.stateManager = stateManager;
        addActionListener(new FillColorMenuListener());
    }

    class FillColorMenuListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            // StateManagerに変更を反映
            stateManager.setFillColor(color);
            // 図形の塗り色を変更する
            Mediator mediator = stateManager.getCanvas().getMediator();
            mediator.updateSelectedDrawings(drawing -> drawing.setfillColor(color));
        }
    }
}
