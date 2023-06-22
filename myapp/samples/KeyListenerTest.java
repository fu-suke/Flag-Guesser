package myapp.samples;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class KeyListenerTest {
    public static void main(String[] args) {
        JFrame frame = new JFrame("KeyListenerTest");
        JPanel panel = new JPanel() {
            {
                this.setFocusable(true);
                this.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                            System.out.println("Del key pressed");
                            // "Del"キーが押されたときの処理を記述します
                        } else if (e.getKeyCode() == KeyEvent.VK_C && e.isControlDown()) {
                            System.out.println("Ctrl+C pressed");
                            // "Ctrl+C"が押されたときの処理を記述します
                        }
                    }
                });
            }
        };

        frame.add(panel, BorderLayout.CENTER);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        panel.requestFocusInWindow(); // フレーム表示後にパネルにフォーカスを移す
    }
}
