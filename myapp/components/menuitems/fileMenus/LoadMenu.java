package myapp.components.menuitems.fileMenus;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.List;
import java.awt.Color;

import myapp.shapes.MyDrawing;
import myapp.states.StateManager;

public class LoadMenu extends JMenuItem {
    private StateManager stateManager;

    public LoadMenu(StateManager stateManager) {
        super("プロジェクトの読み込み");
        this.stateManager = stateManager;
        this.addActionListener(new LoadMenuListener());
    }

    class LoadMenuListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            // File入力
            try {
                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(null); // ファイルロード用のダイアログを開く

                if (returnVal == JFileChooser.APPROVE_OPTION) { // OKボタンが押されたとき
                    File file = fc.getSelectedFile();

                    // ここで得たファイルをFileInputStreamに与える
                    FileInputStream fin = new FileInputStream(file);
                    ObjectInputStream in = new ObjectInputStream(fin);
                    HashMap<String, Object> data = (HashMap<String, Object>) in.readObject();
                    List<MyDrawing> drawings = (List<MyDrawing>) data.get("drawings");
                    Color color = (Color) data.get("color");

                    stateManager.getCanvas().getMediator().setDrawings(drawings);
                    stateManager.setBackColor(color);

                    fin.close();
                    repaint();
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

}
