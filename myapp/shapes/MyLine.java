package myapp.shapes;

import java.awt.*;

public class MyLine extends MyDrawing {
    public MyLine(int xpt, int ypt, int w, int h) {
        super(xpt, ypt, w, h);
        // 青の破線にする
        setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f,
                new float[] { 5.0f }, 0.0f));
        setlineColor(Color.BLUE);
    }

    @Override
    void paint(Graphics2D g, int x, int y, int w, int h, Color lineColor, Color fillColor) {
        g.setStroke(getStroke());
        g.setColor(lineColor);
        g.drawLine(x - w, y - h, x + w, y + h);
    }

    @Override
    public void setRegion() {
        ;
    }

    @Override
    public int[] getEdge() {
        return new int[] { getX(), getY(), getX() + getW(), getY() + getH() };
    }
}
