package myapp.components.sliders;

import javax.swing.event.*;

import myapp.canvas.Mediator;
import myapp.states.StateManager;

import javax.swing.JSlider;

public class AlphaSlider extends JSlider {
    StateManager stateManager;

    public AlphaSlider(StateManager stateManager) {
        super(0, 255, 0);
        this.stateManager = stateManager;
        addChangeListener(new AlphaListener());
    }

    class AlphaListener implements ChangeListener {

        // スライダーにチェンジリスナーを追加
        // スライダーの値が変更された時に動く
        public void stateChanged(ChangeEvent e) {
            JSlider source = (JSlider) e.getSource();
            if (!source.getValueIsAdjusting()) {
                int alpha = 255 - source.getValue();
                stateManager.setAlpha(alpha);
                // 選択状態の図形があれば、図形の透明度を変更する
                Mediator mediator = stateManager.getCanvas().getMediator();
                mediator.updateSelectedDrawings(drawing -> drawing.setAlpha(alpha));
            }
        }
    }
}
