package myapp.canvas;

// MyDrawingのインスタンスに対して位置や色、大きさの変更などの様々な操作が必要になってきます。 
// ところが、これらの操作はMyCanvasクラス上に実装する必要があるため、もともとのMyCanvasクラスの役割である、「描画」という機能を大きく超えてしまいます。

// そこで、MyDrawingインスタンスに対する操作の部分をMediatorクラスとして分離し、クラスごとの機能を明確化します。 
//Mediatorクラスでは、MyDrawingを(非)選択状態にしたり、選択されたMyDrawingを保持したりする機能も持ちます。

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import myapp.common.Observer;
import myapp.shapes.MyDrawing;

public class MyCanvas extends JPanel implements Observer {
    private Mediator mediator;

    public MyCanvas() {
        mediator = new Mediator(this);
        setBackground(Color.white);
        addKeyListener(new MyKeyAdapter());
    }

    public Mediator getMediator() {
        return mediator;
    }

    public void paint(Graphics g) {
        super.paint(g);
        for (MyDrawing d : mediator.getDrawings()) {
            d.draw(g);
        }
    }

    public void update() {
        repaint();
        requestFocusInWindow(); // ボタンが押された後にフォーカスが戻ってくるようにする
    }

    // ウィンドウが表示された後に自動的に走る呪文
    // キー入力を受け付けるようになる
    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow();
    }

    class MyKeyAdapter extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            // "Del"キーが押されたときの処理
            if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                System.out.println("Del key pressed");
                mediator.removeSelectedDrawing();
            }
            // "Ctrl+C"
            if (e.getKeyCode() == KeyEvent.VK_C && e.isControlDown()) {
                System.out.println("Ctrl+C pressed");
                mediator.copy();
            }
            // "Ctrl+V"
            if (e.getKeyCode() == KeyEvent.VK_V && e.isControlDown()) {
                mediator.paste(10, 10);

                System.out.println("Ctrl+V pressed");
            }
            // "Ctrl+X"
            if (e.getKeyCode() == KeyEvent.VK_X && e.isControlDown()) {
                mediator.cut();
                System.out.println("Ctrl+X pressed");
            }
        }
    }
}
