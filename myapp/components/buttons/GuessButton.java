package myapp.components.buttons;

import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Map;

import myapp.states.StateManager;
import myapp.guesser.Guesser;

public class GuessButton extends JButton {
    StateManager stateManager;
    Guesser guesser;

    public GuessButton(StateManager stateManager) {
        super("GUESS !");
        this.stateManager = stateManager;
        this.guesser = new Guesser();
        addActionListener(new GuessButtonActionListener());
    }

    // 画像をポップアップメニューとして表示する
    public void displayImagesInPopupMenu(Map<String, BufferedImage> result_map) {
        // 選択状態を解除する
        stateManager.getCanvas().getMediator().clearSelectedDrawings();
        stateManager.notifyObservers();

        JPopupMenu popupMenu = new JPopupMenu();
        int i = 1;
        for (Map.Entry<String, BufferedImage> entry : result_map.entrySet()) {
            ImageIcon imageIcon = new ImageIcon(entry.getValue());
            String name = entry.getKey();
            String caption = String.format("%d. %s", i, name);
            JMenuItem menuItem = new JMenuItem(caption, imageIcon);
            popupMenu.add(menuItem);
            i++;
        }
        JPanel canvas = stateManager.getCanvas();
        // 画像を表示する
        popupMenu.show(canvas, canvas.getWidth(), 0);
    }

    class GuessButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Canvasからストリームを取得する
            BufferedImage image = stateManager.getCanvas().getCanvasContent();
            // Guesserに画像を渡して、結果を受け取る
            Map<String, BufferedImage> results = guesser.guess(image);
            displayImagesInPopupMenu(results);
        }
    }
}
