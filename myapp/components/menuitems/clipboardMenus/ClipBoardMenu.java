package myapp.components.menuitems.clipboardMenus;

import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.util.List;
import java.util.ArrayList;
import java.awt.Color;

import myapp.common.Observer;
import myapp.states.StateManager;

public class ClipBoardMenu extends JMenu implements Observer {
    private List<JMenuItem> menuItems = new ArrayList<JMenuItem>();
    private StateManager stateManager;
    private JMenuItem pasteMenu;

    public ClipBoardMenu(StateManager stateManager) {
        super("編集");
        this.stateManager = stateManager;
        menuItems.add(new CutMenu(stateManager));
        menuItems.add(new CopyMenu(stateManager));
        pasteMenu = new PasteMenu(stateManager);
        menuItems.add(pasteMenu);
        menuItems.add(new DeleteMenu(stateManager));
        menuItems.add(new SelectAllMenu(stateManager));
        menuItems.add(new UpperMenu(stateManager));
        menuItems.add(new LowerMenu(stateManager));
        for (JMenuItem menuItem : menuItems) {
            this.add(menuItem);
        }
        // 全選択は常に行えるようにし、ペーストメニューは別の視点から監視する
        menuItems.remove(4);
        menuItems.remove(2);
        this.addActionListener(new ClipBoardActionListener(stateManager));
        update(); // 初期状態では無効にする
    }

    @Override
    public void update() {
        // SelectStateかつ選択状態の図形があるならば、有効にする
        if (stateManager.isSelectState() && stateManager.getCanvas().getMediator().hasSelectedDrawings()) {
            for (JMenuItem menuItem : menuItems) {
                menuItem.setEnabled(true);
                menuItem.setForeground(Color.BLACK);
            }

        } else {
            for (JMenuItem menuItem : menuItems) {
                menuItem.setEnabled(false);
                menuItem.setForeground(Color.GRAY);
            }
        }
        // ペーストメニューの有効/無効化
        if (stateManager.getCanvas().getMediator().hasBuffer()) {
            pasteMenu.setEnabled(true);
            pasteMenu.setForeground(Color.BLACK);
        } else {
            pasteMenu.setEnabled(false);
            pasteMenu.setForeground(Color.GRAY);
        }
    }

    class ClipBoardActionListener implements ActionListener {
        private StateManager stateManager;

        public ClipBoardActionListener(StateManager stateManager) {
            this.stateManager = stateManager;
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            stateManager.notifyObservers();
        }

    }
}
