package myapp.components.labels;

import myapp.states.StateManager;

public class LineColorLabel extends ObserverLabel {
    public LineColorLabel(StateManager stateManager) {
        super("●", stateManager);
        this.setForeground(stateManager.getLineColor());
        stateManager.addObserver(this);
    }

    @Override
    public void update() {
        this.setForeground(stateManager.getLineColor());
    }
}
