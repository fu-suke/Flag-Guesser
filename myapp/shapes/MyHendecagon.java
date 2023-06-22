package myapp.shapes;

import java.awt.*;

import java.util.List;
import java.util.ArrayList;

public class MyHendecagon extends MyDrawing {
    // テスト用
    public MyHendecagon() {
        super(); // デフォルト値を代入
    }

    public MyHendecagon(int xpt, int ypt, int width, int height) {
        super(xpt, ypt, width, height);
    }

    // 本番用
    public MyHendecagon(int xpt, int ypt, int width, Color lineColor, Color fillColor) {
        super(xpt, ypt, width, 0, lineColor, fillColor);
    }

    @Override
    void paint(Graphics2D g, int x, int y, int w, int h, Color lineColor, Color fillColor) {
        // 11角形の頂点の座標を計算する
        List<int[]> points = calculatePolygonPoints(x, y, w, h);
        int[] xPoints = points.get(0);
        int[] yPoints = points.get(1);

        // 線スタイルを設定する
        g.setStroke(getStroke());
        // 11角形の中を塗りつぶす
        Polygon p = new Polygon(xPoints, yPoints, xPoints.length);
        g.setColor(fillColor);
        g.fill(p);
        g.setColor(lineColor);
        g.draw(p);
    }

    // 頂点を計算する関数
    private List<int[]> calculatePolygonPoints(int x, int y, int w, int h) {
        int[] xPoints = new int[11];
        int[] yPoints = new int[11];

        for (int i = 0; i < 11; i++) {
            xPoints[i] = (int) (w * Math.cos(-2 * Math.PI * i / 11) / 2) + x + w / 2;
            yPoints[i] = (int) (w * Math.sin(-2 * Math.PI * i / 11) / 2) + y + h / 2;
        }

        List<int[]> points = new ArrayList<>();
        points.add(xPoints);
        points.add(yPoints);

        return points;
    }

    @Override
    public void setRegion() {
        int[] params = getAdjustedParams();
        int x = params[0];
        int y = params[1];
        int w = params[2];
        int h = params[3];
        List<int[]> points = calculatePolygonPoints(x, y, w, h);
        int[] xPoints = points.get(0);
        int[] yPoints = points.get(1);
        region = new Polygon(xPoints, yPoints, 11);
    }

}
