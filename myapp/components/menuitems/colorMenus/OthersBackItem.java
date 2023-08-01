package myapp.components.menuitems.colorMenus;

import javax.swing.JColorChooser;
import javax.swing.JMenuItem;
import java.awt.Color;
import java.awt.event.*;

import myapp.states.StateManager;

public class OthersBackItem extends JMenuItem {

    public OthersBackItem(StateManager stateManager) {
        super("その他…");
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Color initialBackground = stateManager.getLineColor(); // 現在のLineColorの値を取得
                Color color = JColorChooser.showDialog(null, "Select a Color", initialBackground);
                if (color != null) {
                    // StateManagerに変更を反映
                    stateManager.setBackColor(color);
                    stateManager.notifyObservers();
                }
            }
        });
    }
}
