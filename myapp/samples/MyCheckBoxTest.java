package myapp.samples;

import javax.swing.*;
import java.awt.*;

public class MyCheckBoxTest extends JFrame {
    public MyCheckBoxTest() {
        super("MyCheckBoxTest");

        JPanel jp = new JPanel();
        jp.setLayout(new FlowLayout());
        getContentPane().add(jp);

        JCheckBox dashCheck = new JCheckBox("Dash");
        jp.add(dashCheck);

        JCheckBox boldCheck = new JCheckBox("Bold");
        jp.add(boldCheck);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(BorderLayout.NORTH, jp);

        setSize(1000, 500);
    }

    public static void main(String[] args) {
        MyCheckBoxTest myapp = new MyCheckBoxTest();
        myapp.setVisible(true);
    }

}
