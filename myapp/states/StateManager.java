package myapp.states;

import java.awt.BasicStroke;
import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import myapp.canvas.MyCanvas;
import myapp.common.Observer;

public class StateManager implements Observable {
    private State state;
    private State lastState; // 1つ前のStateを保存する
    private MyCanvas canvas;
    private boolean hasShadow;
    private BasicStroke stroke;
    private int lineMultiplicity;
    private Color lineColor;
    private Color fillColor;
    private int alpha = 255;
    private List<Observer> observers = new ArrayList<>(); // 観察対象のリスト

    public StateManager(MyCanvas canvas) {
        state = new RectState(this);
        lastState = state;
        this.canvas = canvas;
        hasShadow = false;
        stroke = new BasicStroke();
        lineMultiplicity = 1;
        lineColor = Color.BLACK;
        fillColor = Color.WHITE;
    }

    public void setBackColor(Color color) {
        this.canvas.setBackground(color);
        notifyObservers();
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
        notifyObservers();
    }

    public int getAlpha() {
        return this.alpha;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
        notifyObservers();
    }

    public Color getLineColor() {
        return this.lineColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
        notifyObservers();
    }

    public Color getFillColor() {
        return this.fillColor;
    }

    public void setState(State state) {
        lastState = this.state;
        this.state = state;
        System.out.println("State changed to " + state);
        getCanvas().getMediator().clearSelectedDrawings();
        notifyObservers();
    }

    public void restoreState() {
        this.state = lastState;
        getCanvas().getMediator().clearSelectedDrawings();
        System.out.println("State recovered to " + state);
        notifyObservers();
    }

    public void sethasShadow(boolean b) {
        this.hasShadow = b;
        notifyObservers();
    }

    public MyCanvas getCanvas() {
        return this.canvas;
    }

    public State getState() {
        return this.state;
    }

    public boolean gethasShadow() {
        return this.hasShadow;
    }

    public void setStroke(BasicStroke stroke) {
        this.stroke = stroke;
        notifyObservers();
    }

    public BasicStroke getStroke() {
        return stroke;
    }

    public void setlineMultiplicity(int n) {
        this.lineMultiplicity = n;
        notifyObservers();
    }

    public int getlineMultiplicity() {
        return this.lineMultiplicity;
    }

    public void mouseDown(int x, int y) {
        state.mouseDown(x, y);
        notifyObservers();
    }

    public void mouseUp(int x, int y) {
        state.mouseUp(x, y);
        notifyObservers();
    }

    public void mouseDrag(int x, int y) {
        state.mouseDrag(x, y);
        notifyObservers();
    }

    public boolean isSelectState() {
        return state instanceof SelectState;
    }

    public void setSelectState() {
        // SelectStateの二重上書きによるlastStateの消失を防ぐ
        if (!isSelectState()) {
            setState(new SelectState(this));
        }
        notifyObservers();
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}

interface Observable {
    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers();
}
