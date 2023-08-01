package myapp.shapes;

import java.awt.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class MyStar extends MyDrawing {
    // テスト用
    public MyStar() {
        super(); // デフォルト値を代入
    }

    public MyStar(int xpt, int ypt, int width, int height) {
        super(xpt, ypt, width, height);
    }

    // 本番用
    public MyStar(int xpt, int ypt, int width, Color lineColor, Color fillColor) {
        super(xpt, ypt, width, 0, lineColor, fillColor);
    }

    @Override
    void paint(Graphics2D g, int x, int y, int w, int h, Color lineColor, Color fillColor) {
        // 5角形の頂点の座標を計算する
        List<int[]> points = calculatePolygonPoints(x, y, w, h);
        int[] xPoints = points.get(0);
        int[] yPoints = points.get(1);

        // 線スタイルを設定する
        g.setStroke(getStroke());

        Polygon p = new Polygon(xPoints, yPoints, xPoints.length);
        g.setColor(fillColor);
        g.fill(p);
        g.setColor(lineColor);
        g.draw(p);

    }

    private List<int[]> calculatePolygonPoints(int x, int y, int w, int h) {
        // 5角形の頂点の座標を計算する
        List<int[]> points = calculatePolygonPoints1(x, y, w, h);
        int[] xPoints = points.get(0);
        int[] yPoints = points.get(1);

        // へこんでいる部分の頂点の座標を計算する
        List<int[]> points2 = calculatePolygonPoints2(x, y, w, h);
        int[] xPoints2 = points2.get(0);
        int[] yPoints2 = points2.get(1);

        int[] xPoints3 = new int[10];
        int[] yPoints3 = new int[10];
        int offset = 1;

        // 合体させる
        for (int i = 0; i < 5; i++) {
            xPoints3[i * 2] = xPoints[i];
            xPoints3[i * 2 + 1] = xPoints2[(i + offset) % 5];
            yPoints3[i * 2] = yPoints[i];
            yPoints3[i * 2 + 1] = yPoints2[(i + offset) % 5];
        }

        List<int[]> points3 = new ArrayList<>();
        points3.add(xPoints3);
        points3.add(yPoints3);

        return points3;

    }

    // 外側の頂点を計算する関数
    private List<int[]> calculatePolygonPoints1(int x, int y, int w, int h) {
        int[] xPoints = new int[5];
        int[] yPoints = new int[5];

        for (int i = 0; i < 5; i++) {
            xPoints[i] = (int) (w * Math.cos(-2 * Math.PI * i / 5 - 2 * Math.PI / 20) /
                    2) + x + w / 2;
            yPoints[i] = (int) (h * Math.sin(-2 * Math.PI * i / 5 - 2 * Math.PI / 20) /
                    2) + y + h / 2;
        }

        List<int[]> points = new ArrayList<>();
        points.add(xPoints);
        points.add(yPoints);

        return points;
    }

    // 内側の頂点を計算する関数
    private List<int[]> calculatePolygonPoints2(int x, int y, int w, int h) {
        int[] xPoints = new int[5];
        int[] yPoints = new int[5];

        for (int i = 0; i < 5; i++) {
            xPoints[i] = (int) (w * 0.4 * Math.cos(-2 * Math.PI * i / 5 + 2 * Math.PI /
                    20) / 2) + x + w / 2;
            yPoints[i] = (int) (h * 0.4 * Math.sin(-2 * Math.PI * i / 5 + 2 * Math.PI /
                    20) / 2) + y + h / 2;
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
        region = new Polygon(xPoints, yPoints, xPoints.length);
    }

    @Override
    public int[] getEdge() {
        int[] params = getAdjustedParams();
        int x = params[0];
        int y = params[1];
        int w = params[2];
        int h = params[3];
        int[] xpoints = calculatePolygonPoints(x, y, w, h).get(0);
        int[] ypoints = calculatePolygonPoints(x, y, w, h).get(1);

        int upperleftX = Arrays.stream(xpoints).min().getAsInt();
        int upperleftY = Arrays.stream(ypoints).min().getAsInt();
        int lowerrightX = Arrays.stream(xpoints).max().getAsInt();
        int lowerrightY = Arrays.stream(ypoints).max().getAsInt();

        int[] edge = { upperleftX, upperleftY, lowerrightX, lowerrightY };
        return edge;
    }
}
