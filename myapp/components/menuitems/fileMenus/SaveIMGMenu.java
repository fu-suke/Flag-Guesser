package myapp.components.menuitems.fileMenus;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.BufferedImage;

import myapp.states.StateManager;

public class SaveIMGMenu extends JMenuItem {
    private StateManager stateManager;

    public SaveIMGMenu(StateManager stateManager) {
        super("画像として保存");
        this.stateManager = stateManager;
        this.addActionListener(new SaveIMGMenuistener());
    }

    class SaveIMGMenuistener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            BufferedImage image = stateManager.getCanvas().getCanvasContent();
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    String filePath = file.getPath();
                    // ファイルの拡張子を取得し、画像の形式として使用
                    if (!filePath.toLowerCase().endsWith(".png")) {
                        file = new File(filePath + ".png");
                    }
                    ImageIO.write(image, "png", file);
                } catch (IOException err) {
                    System.err.println("画像の保存に失敗しました。");
                    err.printStackTrace();
                }
            }
        }
    }
}
