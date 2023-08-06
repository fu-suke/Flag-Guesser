package myapp.canvas;

import java.awt.image.BufferedImage;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import myapp.common.Observer;
import myapp.shapes.MyDrawing;
import myapp.states.StateManager;

public class MyCanvas extends JPanel implements Observer {
    private Mediator mediator;
    private MyMouseAdapter mouseAdapter;
    private StateManager stateManager;

    public MyCanvas() {
        mediator = new Mediator(this);
        setBackground(Color.white);
        addKeyListener(new MyKeyAdapter());
    }

    public void createMouseAdapter(StateManager stateManager) {
        this.stateManager = stateManager;
        mouseAdapter = new MyMouseAdapter(stateManager);
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    public Mediator getMediator() {
        return mediator;
    }

    public void paint(Graphics g) {
        super.paint(g);
        // 図形
        for (MyDrawing d : mediator.getDrawings()) {
            d.draw(g);
        }
        // 補助線
        for (MyDrawing d : mediator.getGuideLines()) {
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

    // キャンバスの内容を画像として返す
    public BufferedImage getCanvasContent() {
        // TYPE_INT_RGBは、sRGBカラーモデルを使用したRGB形式
        BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        this.paint(g);
        g.dispose();
        return image;
    }

    class MyKeyAdapter extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            stateManager.setSelectState();
            // "Del"キーが押されたときの処理
            if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                mediator.delete();
            }
            // "Ctrl+C"
            if (e.getKeyCode() == KeyEvent.VK_C && e.isControlDown()) {
                mediator.copy();
            }
            // "Ctrl+V"
            if (e.getKeyCode() == KeyEvent.VK_V && e.isControlDown()) {
                mediator.paste(mouseAdapter.getLastX(), mouseAdapter.getLastY());
            }
            // "Ctrl+X"
            if (e.getKeyCode() == KeyEvent.VK_X && e.isControlDown()) {
                mediator.cut();
            }
            // "Ctrl+A"
            if (e.getKeyCode() == KeyEvent.VK_A && e.isControlDown()) {
                mediator.selectAll();
            }
            stateManager.notifyObservers();
        }
    }
}