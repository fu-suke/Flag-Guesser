package myapp.components.labels;

import myapp.states.StateManager;

public class FillColorLabel extends ObserverLabel {

    public FillColorLabel(StateManager stateManager) {
        super("●", stateManager);
        this.setForeground(stateManager.getFillColor());
        stateManager.addObserver(this);
    }

    @Override
    public void update() {
        this.setForeground(stateManager.getFillColor());
    }
}
