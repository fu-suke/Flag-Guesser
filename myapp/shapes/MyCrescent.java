package myapp.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
import java.awt.geom.Area;

public class MyCrescent extends MyDrawing {
    private final int N = 360;
    private int inner_w, inner_h, inner_x, inner_y;
    private int mode = -1; // -1: 初期化状態、0: 右下ドラッグ、1: 右上、2: 左下、3: 左上ドラッグ

    public MyCrescent() {
        super(); // デフォルト値を代入
    }

    public MyCrescent(int xpt, int ypt, int width, int height) {
        super(xpt, ypt, width, height);
    }

    public MyCrescent(int xpt, int ypt, int width, int height,
            Color lineColor, Color fillColor) {
        super(xpt, ypt, width, height, lineColor, fillColor);
    }

    @Override
    void paint(Graphics2D g, int x, int y, int w, int h, Color lineColor, Color fillColor) {
        // モードの決定
        if (this.mode == -1) {
            // マウスが図形の右下にあるなら
            if (getH() > 0 && getW() > 0) {
                mode = 0;
                System.out.println("mode: " + mode);
            }
            // マウスが図形の右上にあるなら
            else if (getH() < 0 && getW() > 0) {
                mode = 1;
                System.out.println("mode: " + mode);
            }
            // マウスが図形の左下にあるなら
            else if (getH() > 0 && getW() < 0) {
                mode = 2;
                System.out.println("mode: " + mode);
            }
            // マウスが図形の左上にあるなら
            else if (getH() < 0 && getW() < 0) {
                mode = 3;
                System.out.println("mode: " + mode);
            }
        }

        List<int[]> points = calculatePolygonPoints(x, y, w, h);
        int[] xPoints = points.get(0);
        int[] yPoints = points.get(1);

        g.setStroke(getStroke());
        Polygon p = new Polygon(xPoints, yPoints, xPoints.length);
        g.setColor(fillColor);
        g.fill(p);
        g.setColor(lineColor);
        g.draw(p);

    }

    private List<int[]> calculatePolygonPoints(int x, int y, int w, int h) {
        // 外側の円の円周上の頂点の座標を計算する
        List<int[]> outerPoints = calculateOuterPolygonPoints(x, y, w, h);

        // 外側の円の円周上の頂点の座標を計算する
        setInnerShape(x, y, w, h);
        List<int[]> innerPoints = calculateInnerPolygonPoints(inner_x, inner_y, inner_w, inner_h);
        List<int[]> points = combine(outerPoints, innerPoints);

        return points;

    }

    // 外側の頂点を計算する関数（時計回り）
    private List<int[]> calculateOuterPolygonPoints(int x, int y, int w, int h) {
        // モードに応じて、円の頂点の座標を計算する
        int from = 0;
        switch (mode) {
            case -1:
                break;
            case 0:
                from = 30;
                break;
            case 1:
                from = 120;
                break;
            case 2:
                from = 300;
                break;
            case 3:
                from = 210;
                break;
        }

        int n = 300; // from(度)から時計周りに300度ぶんの頂点を計算する
        int[] xPoints = new int[n];
        int[] yPoints = new int[n];

        int idx = 0;
        if (mode != -1) {
            for (int i = from; i < from + n; i++) {
                xPoints[idx] = (int) (w * Math.cos(-2 * Math.PI * i / N) / 2) + x + w / 2;
                yPoints[idx] = (int) (h * Math.sin(-2 * Math.PI * i / N) / 2) + y + h / 2;
                idx++;
            }
        }

        List<int[]> points = new ArrayList<>();
        points.add(xPoints);
        points.add(yPoints);

        return points;
    }

    // 内側の頂点を計算する関数（反時計回り）
    private List<int[]> calculateInnerPolygonPoints(int x, int y, int w, int h) {
        // モードに応じて、円の頂点の座標を計算する
        int from = 0;
        switch (mode) {
            case -1:
                break;
            case 0:
                from = 45;
                break;
            case 1:
                from = 315;
                break;
            case 2:
                from = 135;
                break;
            case 3:
                from = 225;
                break;
        }

        int n = 270;
        int[] xPoints = new int[n];
        int[] yPoints = new int[n];

        int idx = 0;
        if (mode != -1) {
            for (int i = from; i < from + n; i++) {
                xPoints[idx] = (int) (w * Math.cos(2 * Math.PI * i / N) / 2) + x + w / 2;
                yPoints[idx] = (int) (h * Math.sin(2 * Math.PI * i / N) / 2) + y + h / 2;
                idx++;
            }
        }

        List<int[]> points = new ArrayList<>();
        points.add(xPoints);
        points.add(yPoints);

        return points;
    }

    // 2つの配列を結合する関数

    private List<int[]> combine(List<int[]> list1, List<int[]> list2) {
        // 要素の取り出し
        int[] list1_x = list1.get(0);
        int[] list1_y = list1.get(1);
        int[] list2_x = list2.get(0);
        int[] list2_y = list2.get(1);

        // 長い配列を作る
        int[] x_array = new int[list1_x.length + list2_x.length];
        int[] y_array = new int[list1_y.length + list2_y.length];

        // 長い配列に要素をコピーする
        System.arraycopy(list1_x, 0, x_array, 0, list1_x.length);
        System.arraycopy(list2_x, 0, x_array, list1_x.length, list2_x.length);
        System.arraycopy(list1_y, 0, y_array, 0, list1_y.length);
        System.arraycopy(list2_y, 0, y_array, list1_y.length, list2_y.length);

        List<int[]> list = new ArrayList<>();
        list.add(x_array);
        list.add(y_array);

        return list;
    }

    public void setInnerShape(int x, int y, int w, int h) {
        this.inner_w = (int) (w / 1.3);
        this.inner_h = (int) (h / 1.3);
        switch (mode) {
            // 右下ドラッグ
            case 0:
                inner_x = x + inner_w / 3;
                inner_y = y + inner_h / 7;
                break;
            // 右上ドラッグ
            case 1:
                inner_x = x + inner_w / 6;
                inner_y = y - inner_h / 20;
                break;

            // 左下ドラッグ
            case 2:
                inner_x = x + inner_w / 6;
                inner_y = y + inner_h / 3;
                break;
            // 左上ドラッグ
            case 3:
                inner_x = x - inner_w / 20;
                inner_y = y + inner_h / 6;
                break;

        }
    }

    @Override
    public void setRegion() {
        int[] params = getAdjustedParams();
        int x = params[0];
        int y = params[1];
        int w = params[2];
        int h = params[3];
        region = new Ellipse2D.Float(x, y, w, h);

        // 円範囲の定義
        Ellipse2D outerCircle = new Ellipse2D.Double(x, y, w, h);
        Ellipse2D innerCircle = new Ellipse2D.Double(inner_x, inner_y, inner_w, inner_h);

        // 外側と内側の円のAreaを作成
        Area outerArea = new Area(outerCircle);
        Area innerArea = new Area(innerCircle);

        // 外側の円から内側の円の部分を引く
        outerArea.subtract(innerArea);
        this.region = outerArea;
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
