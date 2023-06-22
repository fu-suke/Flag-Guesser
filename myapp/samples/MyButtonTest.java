package myapp.samples;
import javax.swing.*;
import java.awt.*;

public class MyButtonTest extends JFrame {
    public MyButtonTest() {
        super("MyButtonTest");

        JPanel jp = new JPanel();
        jp.setLayout(new FlowLayout());
        getContentPane().add(jp);

        Button rectButton = new Button("Rectangle");
        jp.add(rectButton);

        Button circleButton = new Button("Circle");
        jp.add(circleButton);

        setSize(1000, 500);
    }

    public static void main(String[] args) {
        MyButtonTest myapp = new MyButtonTest();
        myapp.setVisible(true);
    }
}
