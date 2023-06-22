package myapp.samples;

//The program should display the message "mouseClicked" 
// when the mouse is clicked on the frame.
import javax.swing.*;
import java.awt.event.*;

public class MyMouseEventTest extends JFrame {
    MyMouseEventTest() {
        super("MouseEventTest");
        addMouseListener(new MyMouseListener()); // 画面の中に（？）MouseListenerを追加
        setSize(500, 300);
    }

    public static void main(String[] args) {
        MyMouseEventTest myapp = new MyMouseEventTest(); // ウィンドウを作成
        myapp.setVisible(true);
    }
}

class MyMouseListener extends MouseAdapter {
    public void mouseClicked(MouseEvent e) { // マウスがクリック（一定秒数内に押して離す）されたときに呼び出される関数
        System.out.println("mouseClicked");
    }

    public void mouseEntered(MouseEvent e) { // マウスがウィンドウに入ったときに呼び出される関数
        System.out.println("mouseEntered");
    }

    public void mouseExited(MouseEvent e) {
        System.out.println("mouseExited");
    }

    public void mousePressed(MouseEvent e) {
        System.out.println("mousePressed");
    }

    public void mouseReleased(MouseEvent e) {
        System.out.println("mouseReleased");
    }

    public void mouseDragged(MouseEvent e) {
        System.out.println("mouseDragged");
    }

    public void mouseMoved(MouseEvent e) {
        System.out.println("mouseMoved");
    }

    public void mouseWheelMoved(MouseEvent e) {
        System.out.println("mouseWheelMoved");
    }
}
