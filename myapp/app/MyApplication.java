package myapp.app;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import myapp.canvas.MyCanvas;
import myapp.common.Observer;
import myapp.components.buttons.shapebuttons.*;
import myapp.components.buttons.clipbordbuttons.*;

import myapp.components.labels.*;
import myapp.components.menuitems.*;
import myapp.components.comboboxes.*;
import myapp.states.StateManager;

// 優先事項

/*  オマケでやること */

// GUIパーツから図形のプロパティを変更するコードが汚いのをどうにかする

// 選択した図形のプロパティをStateManagerに反映させず、かつGUIパーツに反映する

// 背景色の変更

// 図形選択の■を図形ごとに変える
// ■をドラッグしてリサイズできるようにする
// リサイズしたときに、図形の中心が変わらないようにする

// 描画した直後の図形を選択状態にして、その場でリサイズなどができるようにする

// DiagonalPolygonボタンを押したら、何角形かを選択するダイアログを表示する
// それ以外のボタンを押したら、ダイアログを閉じる

// MyApplicationのコンストラクタを簡潔にする

// MyApplicationのCanvasのリスナをどこかへ移動させる

// GUIパーツのレイアウトが汚いのでどうにかする

/*
 * 図形のパターンを増やす
 * 直線や波線
 * 文字
 * ゲーミングにする
 * 図形の中でグラデーション
 * 保存機能
 * 立体（円柱・球体）
 * 鉛筆
 * 図形の中心が分かるようにする
 * 中心を基準にした補正機能
 * レイヤー
 * 図形の前後関係を変更できる機能
 * 平行な線
 * 複数選択して「平行」ボタン
 * 外部ファイル挿入
 * 文字が図形に沿って曲がる
 * ドラッグしてゴミ箱へ移動
 * ふちをぼかす
 * 鉛筆のブレを補正する
 */

public class MyApplication extends JFrame {
    StateManager stateManager;
    MyCanvas canvas;

    // GUIをまとめて初期化するメソッド
    private void initializeComponents(JPanel jp, GridBagConstraints c) {
        // 図形
        initializeComponent(new RectButton(stateManager), jp, c, 0, 0);
        initializeComponent(new OvalButton(stateManager), jp, c, 1, 0);
        initializeComponent(new HendecagonButton(stateManager), jp, c, 2, 0);
        initializeComponent(new DiagonalPolygonButton(stateManager), jp, c, 3, 0);
        // コピペ系
        initializeComponent(new SelectButton(stateManager), jp, c, 0, 1);
        initializeComponent(new CutButton(stateManager), jp, c, 1, 1);
        initializeComponent(new CopyButton(stateManager), jp, c, 2, 1);
        initializeComponent(new PasteButton(stateManager), jp, c, 3, 1);
        initializeComponent(new DeleteButton(stateManager), jp, c, 4, 1);
        // 線の種類
        initializeComponent(new JLabel("Line Pattern"), jp, c, 0, 2);
        initializeComponent(new LinePatternComboBox(stateManager), jp, c, 1, 2);
        // 装飾
        initializeComponent(new JLabel("Line Width"), jp, c, 0, 3);
        initializeComponent(new LineWidthComboBox(stateManager), jp, c, 1, 3);
        initializeComponent(new JLabel("Multiplet"), jp, c, 2, 3);
        initializeComponent(new LineMultiplicityComboBox(stateManager), jp, c, 3, 3);
        initializeComponent(new JLabel("DropShadow"), jp, c, 4, 3);
        initializeComponent(new DropShadowComboBox(stateManager), jp, c, 5, 3);
    }

    // 単体のGUIを初期化するメソッド
    private void initializeComponent(JComponent component, JPanel jp, GridBagConstraints c, int gridx, int gridy) {
        c.gridx = gridx;
        c.gridy = gridy;
        jp.add(component, c);
        // オブザーバを実装したクラスであれば、オブザーバーを追加
        if (component instanceof Observer) {
            stateManager.addObserver((Observer) component);
        }
    }

    private void initializeJMenuBar(JMenuBar jMenuBar) {
        initializeColorMenu(jMenuBar); // 色のプルダウンメニューを初期化
    }

    private void initializeColorMenu(JMenuBar jMenuBar) {
        JMenu lineColorMenu = new JMenu("Line Color");
        JMenu fillColorMenu = new JMenu("Fill Color");
        Map<String, Color> colorMap = new HashMap<>();
        colorMap.put("Black", Color.BLACK);
        colorMap.put("White", Color.WHITE);
        colorMap.put("Red", Color.RED);
        colorMap.put("Blue", Color.BLUE);
        colorMap.put("Green", Color.GREEN);
        colorMap.put("Yellow", Color.YELLOW);
        colorMap.put("Orange", Color.ORANGE);
        colorMap.put("Pink", Color.PINK);
        colorMap.put("Gray", Color.GRAY);
        // 色を追加する場合はここに記述する

        // 各メニューに色を追加していく
        for (Map.Entry<String, Color> entry : colorMap.entrySet()) {
            lineColorMenu.add(new LineColorMenu(stateManager, entry.getKey(), entry.getValue()));
            fillColorMenu.add(new FillColorMenu(stateManager, entry.getKey(), entry.getValue()));
        }
        // その他の色を追加するメニューを追加
        lineColorMenu.add(new OthersLineItem(stateManager));
        fillColorMenu.add(new OthersFillItem(stateManager));

        JLabel lineColorLabel = new LineColorLabel(stateManager);
        JLabel fillColorLabel = new FillColorLabel(stateManager);

        // メニューバーに追加
        jMenuBar.add(lineColorMenu);
        jMenuBar.add(lineColorLabel);
        jMenuBar.add(fillColorMenu);
        jMenuBar.add(fillColorLabel);
    }

    public MyApplication() {
        super("My Application");
        this.setFocusable(true); // キーボードの入力を受け付けるようにする

        canvas = new MyCanvas();
        stateManager = new StateManager(canvas);
        canvas.getMediator().setStateManager(stateManager); // MediatorにStateManagerをセット
        stateManager.addObserver(canvas); // StateManagerにCanvasをセット

        JPanel jp = new JPanel();
        jp.setLayout(new GridBagLayout()); // レイアウトマネージャーをGridBagLayoutに設定

        GridBagConstraints c = new GridBagConstraints(); // 座標を決めるためのオブジェクト
        c.fill = GridBagConstraints.HORIZONTAL; // 余白があれば横方向に引き延ばす
        c.insets = new Insets(5, 5, 5, 5); // ボタン間の余白を設定

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        initializeJMenuBar(menuBar);
        initializeComponents(jp, c);

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());
        northPanel.add(jp, BorderLayout.SOUTH); // ボタンを配置

        getContentPane().add(northPanel, BorderLayout.NORTH); // northPanelを配置
        getContentPane().add(canvas, BorderLayout.CENTER); // キャンバスを配置

        // WindowEventリスナを設定（無名クラスを利用している）
        this.addWindowListener(
                new WindowAdapter() {
                    // ウィンドウが閉じたら終了する処理
                    public void windowClosing(WindowEvent e) {
                        System.exit(1);
                    }
                });

        MyMouseAdapter ma = new MyMouseAdapter(stateManager);
        canvas.addMouseListener(ma); // クリック等のリスナ
        canvas.addMouseMotionListener(ma); // ドラッグ等のリスナ
    }

    public static void main(String[] args) {
        MyApplication app = new MyApplication();
        app.setSize(600, 500);
        app.setVisible(true);
    }
}

class MyMouseAdapter extends MouseAdapter {
    StateManager stateManager;

    public MyMouseAdapter(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    public void mousePressed(MouseEvent e) {
        stateManager.mouseDown(e.getX(), e.getY());
        // System.out.println("mousePressed");
    }

    public void mouseDragged(MouseEvent e) {
        stateManager.mouseDrag(e.getX(), e.getY());
        // System.out.println("mouseDragged");
    }

    public void mouseReleased(MouseEvent e) {
        stateManager.mouseUp(e.getX(), e.getY());
        stateManager.getCanvas().requestFocusInWindow(); // キャンバスがクリックされたらフォーカスをキャンバスに移す
    }

    public void mouseEntered(MouseEvent e) {
        stateManager.getCanvas().requestFocusInWindow();
    }
}