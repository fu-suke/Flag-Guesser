package myapp.samples;

import javax.swing.*;
import java.awt.event.*;

public class ColorChangeTest {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("GUI Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        JPanel panel = new JPanel();
        String[] colors = { "Red", "Green", "Blue" }; // 色の選択肢を追加

        // 線の色の選択
        JComboBox<String> lineColor = new JComboBox<>(colors);
        lineColor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedColor = (String) lineColor.getSelectedItem();
                System.out.println("Line color changed to: " + selectedColor);
                // Line colorを変更する処理をここに追加
            }
        });
        panel.add(new JLabel("LineColor"));
        panel.add(lineColor);

        // 塗りつぶしの色の選択
        JComboBox<String> fillColor = new JComboBox<>(colors);
        fillColor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedColor = (String) fillColor.getSelectedItem();
                System.out.println("Fill color changed to: " + selectedColor);
                // Fill colorを変更する処理をここに追加
            }
        });
        panel.add(new JLabel("FillColor"));
        panel.add(fillColor);

        // ラジオボタンを作成
        JRadioButton lineButton = new JRadioButton("Change Line Color");
        JRadioButton fillButton = new JRadioButton("Change Fill Color");

        // ボタングループを作成してラジオボタンを追加
        ButtonGroup group = new ButtonGroup();
        group.add(lineButton);
        group.add(fillButton);

        panel.add(lineButton);
        panel.add(fillButton);

        // 色の選択肢が変更されたときのアクションリスナー
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                // 選択されている図形と色を取得
                String selectedLineColor = (String) lineColor.getSelectedItem();
                String selectedFillColor = (String) fillColor.getSelectedItem();

                // 選択されているボタンに応じて色を変更
                if (lineButton.isSelected()) {
                    // LineColorを変更する処理を実装
                    System.out.println("Changing line color to " + selectedLineColor);
                } else if (fillButton.isSelected()) {
                    // FillColorを変更する処理を実装
                    System.out.println("Changing fill color to " + selectedFillColor);
                }
            }
        };

        // ラジオボタンにアクションリスナーを追加
        lineButton.addActionListener(actionListener);
        fillButton.addActionListener(actionListener);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
}
