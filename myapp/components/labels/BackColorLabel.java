package myapp.components.labels;

import myapp.states.StateManager;

public class BackColorLabel extends ObserverLabel {

    public BackColorLabel(StateManager stateManager) {
        super("●", stateManager);
        this.setForeground(stateManager.getCanvas().getBackground());
        stateManager.addObserver(this);
    }

    @Override
    public void update() {
        this.setForeground(stateManager.getCanvas().getBackground());
    }
}
