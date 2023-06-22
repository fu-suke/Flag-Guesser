package myapp.states;

import java.awt.BasicStroke;
import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import myapp.canvas.MyCanvas;
import myapp.common.Observer;

public class StateManager implements Observable {
    private State state;
    private MyCanvas canvas;
    private boolean hasShadow;
    private BasicStroke stroke;
    private int lineMultiplicity;
    private Color lineColor;
    private Color fillColor;
    private List<Observer> observers = new ArrayList<>(); // 観察対象のリスト

    public StateManager(MyCanvas canvas) {
        state = new RectState(this); // 最初はRectStateで固定
        this.canvas = canvas;
        hasShadow = false;
        stroke = new BasicStroke();
        lineMultiplicity = 1;
        lineColor = Color.BLACK;
        fillColor = Color.WHITE;
    }

    // 色のアクセッサ
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
        this.state = state;
        System.out.println("State changed to " + state);
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
        canvas.repaint();
    }

    public void mouseUp(int x, int y) {
        state.mouseUp(x, y);
        canvas.repaint();
    }

    public void mouseDrag(int x, int y) {
        state.mouseDrag(x, y);
        canvas.repaint();
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
