package myapp.shapes;

import java.awt.*;

public class MyLine extends MyDrawing {
    // テスト用
    public MyLine() {
        super(); // デフォルト値を代入
        // 青の破線にする
        setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f,
                new float[] { 5.0f }, 0.0f));
        setlineColor(Color.BLUE);
    }

    public MyLine(int xpt, int ypt, int width, int height) {
        super(xpt, ypt, width, height);
        // 青の破線にする
        setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f,
                new float[] { 5.0f }, 0.0f));
        setlineColor(Color.BLUE);
    }

    public MyLine(int xpt, int ypt, int width, Color lineColor, Color fillColor) {
        super(xpt, ypt, width, 0, lineColor, fillColor);
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
