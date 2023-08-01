package myapp.shapes;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

// 正n角形の頂点を全て結んだ図形を描画するクラス
public class MyDiagonalPolygon extends MyDrawing {
    private int vertex; // 頂点数

    // テスト用
    public MyDiagonalPolygon(int vertex) {
        super();
        this.vertex = vertex;
    }

    public MyDiagonalPolygon(int xpt, int ypt, int width, int height, int vertex) {
        super(xpt, ypt, width, height);
        this.vertex = vertex;
    }

    // 本番用
    public MyDiagonalPolygon(int xpt, int ypt, int width, Color lineColor, Color fillColor, int vertex) {
        super(xpt, ypt, width, 0, lineColor, fillColor);
        this.vertex = vertex;
    }

    @Override
    void paint(Graphics2D g, int x, int y, int w, int h, Color lineColor, Color fillColor) {
        // n角形の頂点の座標を計算する
        List<int[]> points = calculatePolygonPoints(x, y, w, h);
        int[] xPoints = points.get(0);
        int[] yPoints = points.get(1);
        // 線スタイルを設定する
        g.setStroke(getStroke());
        // n角形の中を塗りつぶす
        Polygon p = new Polygon(xPoints, yPoints, vertex);
        g.setColor(fillColor);
        g.fillPolygon(p);
        // 全ての頂点と頂点を結ぶ直線を描画する
        g.setColor(lineColor);
        for (int i = 0; i < vertex; i++) {
            for (int j = 0; j < vertex; j++) {
                g.drawLine(xPoints[i], yPoints[i], xPoints[j], yPoints[j]);
            }
        }
    }

    // 頂点を計算する関数
    private List<int[]> calculatePolygonPoints(int x, int y, int w, int h) {
        int[] xPoints = new int[vertex];
        int[] yPoints = new int[vertex];

        for (int i = 0; i < vertex; i++) {
            xPoints[i] = (int) (w * Math.cos(-2 * Math.PI * i / vertex) / 2) + x + w / 2;
            yPoints[i] = (int) (h * Math.sin(-2 * Math.PI * i / vertex) / 2) + y + h / 2;
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
        region = new Polygon(xPoints, yPoints, vertex);
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
