package myapp.samples;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MyButtonTest2 extends JFrame {
    public MyButtonTest2() {
        super("MyButtonTest");

        JPanel jp = new JPanel();
        jp.setLayout(new FlowLayout());
        getContentPane().add(jp);

        Button rectButton = new Button("Rectangle");
        jp.add(rectButton);
        rectButton.addActionListener(new RectButtonListener());

        Button circleButton = new Button("Circle");
        jp.add(circleButton);
        circleButton.addActionListener(new CircleButtonListener());

        setSize(1000, 500);
    }

    public static void main(String[] args) {
        MyButtonTest2 myapp = new MyButtonTest2();
        myapp.setVisible(true);
    }

}

// Rectボタンのイベントリスナ
class RectButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "RectButton is clicked");
    }
}

// Circleボタンのイベントリスナ
class CircleButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "CircleButton is clicked");
    }
}
