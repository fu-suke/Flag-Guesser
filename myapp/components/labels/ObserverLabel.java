package myapp.components.labels;

import myapp.common.Observer;
import myapp.states.StateManager;

import javax.swing.*;

// オブザーバつきのラベルの抽象クラス
public abstract class ObserverLabel extends JLabel implements Observer {
    StateManager stateManager;

    // 水平方向の配置方法に指定が無い場合（左寄せ）
    public ObserverLabel(String text, StateManager stateManager) {
        super(text);
        this.stateManager = stateManager;
    }

    // 水平方向の配置方法に指定がある場合
    public ObserverLabel(String text, int horizontalAlignment, StateManager stateManager) {
        super(text, horizontalAlignment);
        this.stateManager = stateManager;
    }
}
