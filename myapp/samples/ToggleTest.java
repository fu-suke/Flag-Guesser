package myapp.samples;

import javax.swing.*;
import java.awt.event.*;

public class ToggleTest extends JFrame {

    private JToggleButton toggleButton;

    // コンストラクタ
    public ToggleTest() {

        // トグルボタン生成
        // javax.swing.JToggleButton
        toggleButton = new JToggleButton();

        // ボタンクリック時のイベント追加
        toggleButton.setText("トグルボタン");
        toggleButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                toggleButtonMouseClicked(evt);
            }
        });

        // Containerに配置
        getContentPane().add(toggleButton);

        // フレーム設定
        getContentPane().setLayout(new java.awt.FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("コンボボックス");
        setSize(200, 200);
    }

    // ボタンクリック処理
    private void toggleButtonMouseClicked(MouseEvent evt) {
        if (toggleButton.isSelected()) {
            // lbl.setText("スイッチON");
            ;
        } else {
            // lbl.setText("スイッチOFF");
            ;
        }
    }

    public static void main(String[] args) {
        // 非同期処理
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ToggleTest().setVisible(true);
            }
        });
    }
}