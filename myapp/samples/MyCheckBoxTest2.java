package myapp.samples;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MyCheckBoxTest2 extends JFrame {
    public MyCheckBoxTest2() {
        super("MyCheckBoxTest");

        JPanel jp = new JPanel();
        jp.setLayout(new FlowLayout());
        getContentPane().add(jp);

        JCheckBox dashCheck = new JCheckBox("Dash");
        jp.add(dashCheck);
        dashCheck.addItemListener(new DashCheckListener());

        JCheckBox boldCheck = new JCheckBox("Bold");
        jp.add(boldCheck);
        boldCheck.addItemListener(new BoldCheckListener());

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(jp, BorderLayout.NORTH);

        setSize(1000, 500);
    }

    public static void main(String[] args) {
        MyCheckBoxTest2 myapp = new MyCheckBoxTest2();
        myapp.setVisible(true);
    }
}

class DashCheckListener implements ItemListener {
    public void itemStateChanged(ItemEvent e) {
        int state = e.getStateChange();
        if (state == ItemEvent.SELECTED) {
            System.out.println("Dash is selected");
        } else {
            System.out.println("Dash is deselected");
        }
    }
}

class BoldCheckListener implements ItemListener {
    public void itemStateChanged(ItemEvent e) {
        int state = e.getStateChange();
        if (state == ItemEvent.SELECTED) {
            System.out.println("Bold is selected");
        } else {
            System.out.println("Bold is deselected");
        }
    }
}
