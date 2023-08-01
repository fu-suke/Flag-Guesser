package myapp.components.menuitems.fileMenus;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import myapp.states.StateManager;

public class SaveMenu extends JMenuItem {
    private StateManager stateManager;

    public SaveMenu(StateManager stateManager) {
        super("プロジェクトの保存");
        this.stateManager = stateManager;
        this.addActionListener(new SaveMenuistener());
    }

    class SaveMenuistener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // セーブ処理
            JFileChooser fc = new JFileChooser();
            int returnVal = fc.showSaveDialog(null); // ファイルロード用のダイアログを開く
            // File出力
            try {
                if (returnVal == JFileChooser.APPROVE_OPTION) { // OKボタンが押されたとき
                    File file = fc.getSelectedFile();
                    FileOutputStream fout = new FileOutputStream(file);
                    ObjectOutputStream out = new ObjectOutputStream(fout);

                    HashMap<String, Object> data = new HashMap<String, Object>();
                    data.put("drawings", stateManager.getCanvas().getMediator().getDrawings());
                    data.put("color", stateManager.getCanvas().getBackground());

                    out.writeObject(data);
                    out.flush();
                    fout.close();
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }
}
