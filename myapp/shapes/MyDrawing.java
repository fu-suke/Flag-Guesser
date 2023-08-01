package myapp.shapes;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public abstract class MyDrawing implements Cloneable, Serializable {
    private int x, y, w, h; // wは横幅、h縦幅
    private Color lineColor, fillColor; // 線の色と塗りつぶしの色
    private boolean hasShadow; // 影をつけるかどうか
    private SerializableBasicStroke stroke; // 線種
    private int lineMultiplicity; // n重線
    private transient boolean isSelected = false; // 選択状態は保存しない
    Shape region; // 図形の領域
    final int SIZE = 10; // 選択表示のための四角形のサイズ
    private int aplha = 0; // 透明度
    private int resizeRectIndex = -1;
    List<Shape> resizeRectRegions = new ArrayList<>(); // 四隅と中辺の四角形の領域

    // 引数がないときはデフォルトの値を設定する
    public MyDrawing() {
        x = y = 100;
        w = h = 100;
        lineColor = Color.BLACK;
        fillColor = Color.WHITE;
        hasShadow = false;
        // BasicStrokeクラスはイミュータブル（一度インスタンスを生成したら、属性を変更できない）なので注意
        stroke = new SerializableBasicStroke();
        lineMultiplicity = 1;
        isSelected = false;
        setRegion(); // 領域を設定する;
    }

    public MyDrawing(int xpt, int ypt, int width, int height) {
        this();
        setLocation(xpt, ypt);
        setSize(width, height);
        setRegion(); // 領域を設定する
    }

    public MyDrawing(int xpt, int ypt, int width, int height, Color lineColor, Color fillColor) {
        this(xpt, ypt, width, height);
        setColor(lineColor, fillColor);
    }

    public void draw(Graphics g) {
        int[] params = getAdjustedParams();
        int x = params[0];
        int y = params[1];
        int w = params[2];
        int h = params[3];

        int tmpx = x;
        int tmpy = y;
        int tmpw = w;
        int tmph = h;

        // 影を追加する
        if (hasShadow()) {
            Graphics2D shadow = (Graphics2D) g;
            Stroke tmp = getStroke();
            setStroke(new BasicStroke());
            paint(shadow, x + 10, y + 10, w, h, Color.BLACK, Color.BLACK);
            setStroke((BasicStroke) tmp);
        }
        // 図形を描画する
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 1; i <= getLineMultiplicity(); i++) {
            if (w < 0 || h < 0) {
                break;
            }
            paint(g2, x, y, w, h, getLineColor(), getFillColor());
            int lineWidth = (int) getStroke().getLineWidth();
            x += (4 + lineWidth) * 2;
            y += (4 + lineWidth) * 2;
            w -= (4 + lineWidth) * 4;
            h -= (4 + lineWidth) * 4;
        }

        // 選択されているときは四角形を描画する
        if (isSelected) {
            drawResizeRect(g, tmpx, tmpy, tmpw, tmph);
        }
    }

    // 負の幅や高さを許容するための処理
    public int[] getAdjustedParams() {
        int x = getX();
        int y = getY();
        int w = getW();
        int h = getH();

        // 高さや横幅が負になったときのための処理
        if (w < 0) {
            x += w;
            w *= -1;
        }
        if (h < 0) {
            y += h;
            h *= -1;
        }

        return new int[] { x, y, w, h };
    }

    // 負の幅や高さを正にするための処理
    public void setAdjustedParams() {
        int[] params = getAdjustedParams();
        setLocation(params[0], params[1]);
        setSize(params[2], params[3]);
    }

    abstract void paint(Graphics2D g, int x, int y, int w, int h, Color lineColor, Color fillColor);

    private void drawResizeRect(Graphics g, int x, int y, int w, int h) {
        g.setColor(Color.BLACK);

        int[] edge = getEdge();
        int upperleftX = edge[0];
        int upperleftY = edge[1];
        int lowerrightX = edge[2];
        int lowerrightY = edge[3];

        // 四隅と中辺の四角形を描画して、領域を保存する
        resizeRectRegions.clear();
        for (int i = 0; i < 8; i++) {
            // クリックされた四角形を赤くする
            g.setColor(i == resizeRectIndex ? Color.RED : Color.BLACK);
            // 四角形の座標を計算する
            int rectX = (i % 4 == 0 ? (upperleftX + lowerrightX) / 2 : i <= 3 ? upperleftX : lowerrightX)
                    - SIZE / 2;
            int rectY = (i % 4 == 2 ? (upperleftY + lowerrightY) / 2 : i / 3 == 1 ? lowerrightY : upperleftY)
                    - SIZE / 2;
            g.fillRect(rectX, rectY, SIZE, SIZE);
            resizeRectRegions.add(new Rectangle(rectX, rectY, SIZE, SIZE));
        }
    }

    // 引数で受け取った座標が図形の隅にある四角形の領域内にあるかどうかを判定し、あればその四角形のインデックスを返す
    public int containsResizeRect(int dx, int dy) {
        for (int i = 0; i < resizeRectRegions.size(); i++) {
            if (resizeRectRegions.get(i).contains(dx, dy)) {
                resizeRectIndex = i;
                return i;
            }
        }
        resizeRectIndex = -1;
        return -1;
    }

    // 図形の左上と右下の座標を返す
    public abstract int[] getEdge();

    public int[] getCenter() {
        int[] edge = getEdge();
        return new int[] { (edge[0] + edge[2]) / 2, (edge[1] + edge[3]) / 2 };
    }

    // 引数で受け取った座標が図形の領域内にあるかどうかを判定する
    public boolean contains(int x, int y) {
        return region.contains(x, y);
    }

    public boolean intersects(int x, int y, int w, int h) {
        return region.intersects(x, y, w, h);
    }

    public void setSelected(boolean b) {
        isSelected = b;
        // 選択状態でないなら、リサイズ用の四角形の領域を消す
        if (b == false) {
            resizeRectRegions.clear();
            resizeRectIndex = -1; // 追加: 図形が選択されていないときにリセットする
        }
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setAlpha(int alpha) {
        this.aplha = alpha;
        setfillColor(fillColor);
    }

    public int getAlpha() {
        return this.aplha;
    }

    // 自身の領域を設定する
    // 図形が動かされたときやリサイズされたとき、領域を再設定することを忘れない！
    public abstract void setRegion();

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
        setRegion(); // 座標がズレると領域がおかしくなるので、座標が変更されたら領域を再設定する
    }

    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
        setRegion();
    }

    public void setSize(int w, int h) {
        this.w = w;
        this.h = h;
        setRegion();
    }

    public void resize(int dw, int dh) {
        this.w += dw;
        this.h += dh;
        setRegion();
    }

    public void resize(int dx, int dy, int resizeMode) {
        switch (resizeMode) {
            // 上方向へのリサイズ
            case 0:
                move(0, dy);
                resize(0, -dy);
                break;
            // 左上方向へのリサイズ
            case 1:
                move(dx, dy);
                resize(-dx, -dy);
                break;
            // 左方向へのリサイズ
            case 2:
                move(dx, 0);
                resize(-dx, 0);
                break;
            // 左下方向へのリサイズ
            case 3:
                move(dx, 0);
                resize(-dx, dy);
                break;
            // 下方向へのリサイズ
            case 4:
                move(0, 0);
                resize(0, dy);
                break;
            // 右下方向へのリサイズ
            case 5:
                move(0, 0);
                resize(dx, dy);
                break;
            // 右方向へのリサイズ
            case 6:
                move(0, 0);
                resize(dx, 0);
                break;
            // 右上方向へのリサイズ
            case 7:
                move(0, dy);
                resize(dx, -dy);
                break;
        }
        setRegion();
    }

    public void setColor(Color lineColor, Color fillColor) {
        this.lineColor = lineColor;
        this.fillColor = fillColor;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getW() {
        return this.w;
    }

    public int getH() {
        return this.h;
    }

    public Color getLineColor() {
        return this.lineColor;
    }

    public Color getFillColor() {
        return this.fillColor;
    }

    public void setfillColor(Color color) {
        Color newColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), aplha);
        this.fillColor = newColor;
    }

    public void setlineColor(Color color) {
        this.lineColor = color;
    }

    public void sethasShadow(boolean b) {
        this.hasShadow = b;
    }

    public boolean hasShadow() {
        return hasShadow;
    }

    public void setStroke(BasicStroke stroke) {
        this.stroke = new SerializableBasicStroke(stroke);
    }

    public BasicStroke getStroke() {
        return this.stroke.toBasicStroke();
    }

    public void setLineMultiplicity(int n) {
        this.lineMultiplicity = n;
    }

    public int getLineMultiplicity() {
        return this.lineMultiplicity;
    }

    // isSelectedの値を返す
    public boolean getisSelected() {
        return isSelected;
    }

    @Override
    public MyDrawing clone() {
        try {
            return (MyDrawing) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}