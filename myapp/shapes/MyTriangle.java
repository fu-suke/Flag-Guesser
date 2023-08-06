package myapp.shapes;

import java.awt.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class MyTriangle extends MyDrawing {
    final int N = 3; // 頂点の数
    private int mode = -1; // -1: 初期化状態、0: 右下ドラッグ、1: 右上、2: 左下、3: 左上ドラッグ

    @Override
    void paint(Graphics2D g, int x, int y, int w, int h, Color lineColor, Color fillColor) {
        // n角形の頂点の座標を計算する
        List<int[]> points = calculatePolygonPoints(x, y, w, h);
        int[] xPoints = points.get(0);
        int[] yPoints = points.get(1);

        // モードが未決定状態なら
        if (this.mode == -1) {
            // マウスが図形の右下にあるなら
            if (getH() > 0 && getW() > 0) {
                mode = 0;
            }
            // マウスが図形の右上にあるなら
            else if (getH() < 0 && getW() > 0) {
                mode = 1;
            }
            // マウスが図形の左下にあるなら
            else if (getH() > 0 && getW() < 0) {
                mode = 2;
            }
            // マウスが図形の左上にあるなら
            else if (getH() < 0 && getW() < 0) {
                mode = 3;
            }
        }

        // 線スタイルを設定する
        g.setStroke(getStroke());
        // n角形の中を塗りつぶす
        Polygon p = new Polygon(xPoints, yPoints, xPoints.length);
        g.setColor(fillColor);
        g.fill(p);
        g.setColor(lineColor);
        g.draw(p);
    }

    // 頂点を計算する関数
    private List<int[]> calculatePolygonPoints(int x, int y, int w, int h) {
        int[] xPoints = new int[N];
        int[] yPoints = new int[N];

        switch (mode) {
            case -1:
                ;
                // 右下ドラッグ
            case 0:
                xPoints[0] = x;
                yPoints[0] = y;

                xPoints[1] = x;
                yPoints[1] = y + h;

                xPoints[2] = x + w;
                yPoints[2] = y + h / 2;
                break;
            // 右上
            case 1:
                xPoints[0] = x;
                yPoints[0] = y + h;

                xPoints[1] = x + w;
                yPoints[1] = y + h;

                xPoints[2] = x + w / 2;
                yPoints[2] = y;
                break;
            // 左下
            case 2:
                xPoints[0] = x;
                yPoints[0] = y;

                xPoints[1] = x + w;
                yPoints[1] = y;

                xPoints[2] = x + w / 2;
                yPoints[2] = y + h;
                break;
            // 右上
            case 3:
                xPoints[0] = x + w;
                yPoints[0] = y + h;

                xPoints[1] = x + w;
                yPoints[1] = y;

                xPoints[2] = x;
                yPoints[2] = y + h / 2;
                break;
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
