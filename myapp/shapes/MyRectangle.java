package myapp.shapes;

import java.awt.*;

public class MyRectangle extends MyDrawing {
    public MyRectangle() {
        super();
    }

    public MyRectangle(int xpt, int ypt, int w, int h) {
        super(xpt, ypt, w, h);
    }

    void paint(Graphics2D g, int x, int y, int w, int h, Color lineColor, Color fillColor) {
        g.setStroke(getStroke());
        g.setColor(fillColor);
        g.fillRect(x, y, w, h);
        g.setColor(lineColor);
        g.drawRect(x, y, w, h);
    }

    @Override
    public void setRegion() {
        int[] params = getAdjustedParams();
        int x = params[0];
        int y = params[1];
        int w = params[2];
        int h = params[3];
        region = new Rectangle(x, y, w, h);
    }

    @Override
    public int[] getEdge() {
        int[] params = getAdjustedParams();
        int x = params[0];
        int y = params[1];
        int w = params[2];
        int h = params[3];
        int[] edge = { x, y, x + w, y + h };
        return edge;
    }
}