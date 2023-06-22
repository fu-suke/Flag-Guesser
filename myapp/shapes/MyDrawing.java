package myapp.shapes;

import java.awt.*;

// 抽象クラスに変更
public abstract class MyDrawing implements Cloneable {
    private int x, y, w, h; // wは横幅、h縦幅
    private Color lineColor, fillColor; // 線の色と塗りつぶしの色
    private boolean hasShadow; // 影をつけるかどうか
    private BasicStroke stroke; // 線種
    private int lineMultiplicity; // n重線
    private boolean isSelected; // 選択されているかどうか
    Shape region; // 図形の領域
    final int SIZE = 7; // 選択表示のための四角形のサイズ

    // 引数がないときはデフォルトの値を設定する
    public MyDrawing() {
        x = y = 100;
        w = h = 100;
        lineColor = Color.BLACK;
        fillColor = Color.WHITE;
        hasShadow = false;
        // BasicStrokeクラスはイミュータブル（一度インスタンスを生成したら、属性を変更できない）なので注意
        stroke = new BasicStroke();
        setRegion(); // 領域を設定する;
        isSelected = false;
    }

    public MyDrawing(int xpt, int ypt, int width, int height) {
        this();
        setLocation(xpt, ypt);
        setSize(width, height);
        setRegion(); // 領域を設定する
        // System.out.println("isSelected: " + isSelected);
    }

    public MyDrawing(int xpt, int ypt, int width, int height, Color lineColor, Color fillColor) {
        this(xpt, ypt, width, height);
        setColor(lineColor, fillColor);
        System.out.println("MyDrawing: " + lineColor + ", " + fillColor);
    }

    public void draw(Graphics g) {
        // 新しいコード
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
        for (int i = 0; i < getLineMultiplicity(); i++) {
            if (w < 0 || h < 0) {
                break;
            }
            paint(g2, x, y, w, h, getLineColor(), getFillColor());
            x += 2 * getStroke().getLineWidth();
            y += 2 * getStroke().getLineWidth();
            w -= 4 * getStroke().getLineWidth();
            h -= 4 * getStroke().getLineWidth();
        }

        // 選択されているときは四角形を描画する
        if (isSelected) {
            g.setColor(Color.RED);
            // 四隅に描画する
            g.fillRect(tmpx - SIZE / 2, tmpy - SIZE / 2, SIZE, SIZE);
            g.fillRect(tmpx + tmpw - SIZE / 2, tmpy - SIZE / 2, SIZE, SIZE);
            g.fillRect(tmpx - SIZE / 2, tmpy + tmph - SIZE / 2, SIZE, SIZE);
            g.fillRect(tmpx + tmpw - SIZE / 2, tmpy + tmph - SIZE / 2, SIZE, SIZE);

            // 中点にも描画する
            g.fillRect(tmpx + tmpw / 2 - SIZE / 2, tmpy - SIZE / 2, SIZE, SIZE);
            g.fillRect(tmpx - SIZE / 2, tmpy + tmph / 2 - SIZE / 2, SIZE, SIZE);
            g.fillRect(tmpx + tmpw / 2 - SIZE / 2, tmpy + tmph - SIZE / 2, SIZE, SIZE);
            g.fillRect(tmpx + tmpw - SIZE / 2, tmpy + tmph / 2 - SIZE / 2, SIZE, SIZE);
        }
    }

    // 負の幅や高さを許容するための処理
    int[] getAdjustedParams() {
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

    abstract void paint(Graphics2D g, int x, int y, int w, int h, Color lineColor, Color fillColor);

    // 引数で受け取った座標が図形の領域内にあるかどうかを判定する
    public boolean contains(int x, int y) {
        return region.contains(x, y);
    }

    public void setSelected(boolean b) {
        isSelected = b;
        // System.out.println("MyDrawing.setSelected: " + isSelected);
    }

    public boolean isSelected() {
        return isSelected;
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
        this.fillColor = color;
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
        this.stroke = stroke;
    }

    public BasicStroke getStroke() {
        return this.stroke;
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