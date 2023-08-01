package myapp.states;

public abstract class State {
    StateManager stateManager;

    public State(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    public abstract void mouseDown(int x, int y);

    public abstract void mouseUp(int x, int y);

    public abstract void mouseDrag(int x, int y);
}
