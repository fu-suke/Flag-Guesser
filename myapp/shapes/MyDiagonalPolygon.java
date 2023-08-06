package myapp.shapes;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

// 正n角形の頂点を全て結んだ図形を描画するクラス
public class MyDiagonalPolygon extends MyDrawing {
    private final int N = 6; // 頂点数

    // テスト用
    public MyDiagonalPolygon() {
        super();
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
        Polygon p = new Polygon(xPoints, yPoints, N);
        g.setColor(fillColor);
        g.fillPolygon(p);
        // 全ての頂点と頂点を結ぶ直線を描画する
        g.setColor(lineColor);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                g.drawLine(xPoints[i], yPoints[i], xPoints[j], yPoints[j]);
            }
        }
    }

    // 頂点を計算する関数
    private List<int[]> calculatePolygonPoints(int x, int y, int w, int h) {
        int[] xPoints = new int[N];
        int[] yPoints = new int[N];

        for (int i = 0; i < N; i++) {
            xPoints[i] = (int) (w * Math.cos(-2 * Math.PI * i / N) / 2) + x + w / 2;
            yPoints[i] = (int) (h * Math.sin(-2 * Math.PI * i / N) / 2) + y + h / 2;
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
        region = new Polygon(xPoints, yPoints, N);
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
