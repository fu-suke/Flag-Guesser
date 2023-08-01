package myapp.canvas;

import java.awt.event.*;
import myapp.states.*;

// マウスの動きを取得するためのクラス
class MyMouseAdapter extends MouseAdapter {
    private int lastX = 0;
    private int lastY = 0;
    private StateManager stateManager;

    public MyMouseAdapter(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    public void mousePressed(MouseEvent e) {
        // 左クリックされたら描画モードに切り替えて描画開始
        if (e.getButton() == MouseEvent.BUTTON1) {
            // StateManagerのStateがSelectStateのときはまず描画ステートに復元する
            if (stateManager.isSelectState()) {
                stateManager.restoreState();
            }
            stateManager.mouseDown(e.getX(), e.getY());
        }
        // 右クリックされたら選択モードに切り替えて選択開始
        else if (e.getButton() == MouseEvent.BUTTON3) {
            if (!stateManager.isSelectState()) {
                stateManager.setState(new SelectState(stateManager));
            }
            stateManager.mouseDown(e.getX(), e.getY());
        }
    }

    public void mouseDragged(MouseEvent e) {
        stateManager.mouseDrag(e.getX(), e.getY());
    }

    public void mouseReleased(MouseEvent e) {
        stateManager.mouseUp(e.getX(), e.getY());
        stateManager.getCanvas().requestFocusInWindow(); // キャンバスがクリックされたらフォーカスをキャンバスに移す
    }

    public void mouseEntered(MouseEvent e) {
        stateManager.getCanvas().requestFocusInWindow();
    }

    public void mouseMoved(MouseEvent e) {
        lastX = e.getX();
        lastY = e.getY();
    }

    public int getLastX() {
        return lastX;
    }

    public int getLastY() {
        return lastY;
    }
}