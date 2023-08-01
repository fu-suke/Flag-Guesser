package myapp.app;

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.*;

import myapp.canvas.MyCanvas;
import myapp.common.Observer;
import myapp.components.labels.*;
import myapp.components.sliders.*;
import myapp.components.menuitems.fileMenus.*;
import myapp.components.menuitems.colorMenus.*;
import myapp.components.menuitems.clipboardMenus.*;
import myapp.components.buttons.GuessButton;
import myapp.components.comboboxes.*;
import myapp.states.StateManager;

public class MyApplication extends JFrame {
    StateManager stateManager;
    MyCanvas canvas;

    // メニューバー以外のGUIを初期化するメソッド
    private void initializeComponents(JPanel jp, GridBagConstraints c) {
        c.insets = new Insets(5, 1, 5, 1); // ボタン間の余白を設定
        c.anchor = GridBagConstraints.WEST; // 左寄せに配置
        c.weightx = 1; // 水平方向の余白スペースを均等に分割
        int i = 0;
        initializeComponent(new ShapeComboBox(stateManager), jp, c, i++, 0);
        initializeComponent(new LinePatternComboBox(stateManager), jp, c, i++, 0);
        initializeComponent(new JLabel("線の幅"), jp, c, i++, 0);
        initializeComponent(new LineWidthComboBox(stateManager), jp, c, i++, 0);
        initializeComponent(new JLabel("多重線"), jp, c, i++, 0);
        initializeComponent(new LineMultiplicityComboBox(stateManager), jp, c, i++, 0);
        initializeComponent(new JLabel("透明度"), jp, c, i++, 0);
        initializeComponent(new AlphaSlider(stateManager), jp, c, i++, 0);
        initializeComponent(new JLabel("影"), jp, c, i++, 0);
        initializeComponent(new DropShadowComboBox(stateManager), jp, c, i++, 0);
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

    // メニューバーを初期化するメソッド
    private void initializeJMenuBar(JMenuBar jMenuBar) {
        initializeIOMenu(jMenuBar); // ファイルのプルダウンメニュー
        addToJMenubar(jMenuBar, new ClipBoardMenu(stateManager)); // 編集のプルダウンメニュー
        initializeColorMenu(jMenuBar); // 色のプルダウンメニュー
    }

    private void addToJMenubar(JMenuBar jMenuBar, JComponent component) {
        jMenuBar.add(component);
        if (component instanceof Observer) {
            stateManager.addObserver((Observer) component);
        }
    }

    private void initializeIOMenu(JMenuBar jMenuBar) {
        JMenu ioMenu = new JMenu("ファイル");
        JMenuItem saveItem = new SaveMenu(stateManager);
        JMenuItem loadItem = new LoadMenu(stateManager);
        JMenuItem saveIMGItem = new SaveIMGMenu(stateManager);
        ioMenu.add(saveItem);
        ioMenu.add(loadItem);
        ioMenu.add(saveIMGItem);
        jMenuBar.add(ioMenu);
    }

    private void initializeColorMenu(JMenuBar jMenuBar) {
        JMenu lineColorMenu = new JMenu("線の色");
        JMenu fillColorMenu = new JMenu("塗り色");
        JMenu backColorMenu = new JMenu("背景色");
        Map<String, Color> colorMap = new LinkedHashMap<>();
        colorMap.put("黒", Color.BLACK);
        colorMap.put("白", Color.WHITE);
        colorMap.put("赤", Color.RED);
        colorMap.put("緑", new Color(10, 99, 54));
        colorMap.put("黄緑", Color.GREEN);
        colorMap.put("青", Color.BLUE);
        colorMap.put("黄", Color.YELLOW);
        colorMap.put("オレンジ", Color.ORANGE);
        colorMap.put("ピンク", Color.PINK);
        colorMap.put("グレー", Color.GRAY);
        // 色を追加する場合はここに記述する

        // 各メニューに色を追加していく
        for (Map.Entry<String, Color> entry : colorMap.entrySet()) {
            lineColorMenu.add(new LineColorMenu(stateManager, entry.getKey(), entry.getValue()));
            fillColorMenu.add(new FillColorMenu(stateManager, entry.getKey(), entry.getValue()));
            backColorMenu.add(new BackColorMenu(stateManager, entry.getKey(), entry.getValue()));
        }
        // その他の色を追加するメニューを追加
        lineColorMenu.add(new OthersLineItem(stateManager));
        fillColorMenu.add(new OthersFillItem(stateManager));
        backColorMenu.add(new OthersBackItem(stateManager));

        JLabel lineColorLabel = new LineColorLabel(stateManager);
        JLabel fillColorLabel = new FillColorLabel(stateManager);
        JLabel backColorLabel = new BackColorLabel(stateManager);

        // メニューバーに追加
        jMenuBar.add(lineColorMenu);
        jMenuBar.add(lineColorLabel);
        jMenuBar.add(fillColorMenu);
        jMenuBar.add(fillColorLabel);
        jMenuBar.add(backColorMenu);
        jMenuBar.add(backColorLabel);
    }

    public MyApplication() {
        super("FLAG GUESSER");
        this.setFocusable(true); // キーボードの入力を受け付けるようにする

        // CanvasやStateManagerなどの初期化
        canvas = new MyCanvas();
        stateManager = new StateManager(canvas);
        canvas.createMouseAdapter(stateManager);
        canvas.getMediator().setStateManager(stateManager);
        stateManager.addObserver(canvas);
        getContentPane().add(canvas, BorderLayout.CENTER);

        // メニューバーの初期化
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JMenuBar menuBar = new JMenuBar();
        menuPanel.add(menuBar);
        setJMenuBar(menuBar);
        initializeJMenuBar(menuBar);
        getContentPane().add(menuPanel, BorderLayout.NORTH); // menuPanelを配置

        // デコレータパネルの初期化
        JPanel decolatorPanel = new JPanel();
        decolatorPanel.setLayout(new GridBagLayout()); // レイアウトマネージャーをGridBagLayoutに設定
        GridBagConstraints c = new GridBagConstraints(); // 座標を決めるためのオブジェクト
        initializeComponents(decolatorPanel, c);
        getContentPane().add(decolatorPanel, BorderLayout.NORTH); // northPanelを配置

        // GUESSボタンの初期化
        JPanel southPanel = new JPanel();
        southPanel.add(new GuessButton(stateManager));
        getContentPane().add(southPanel, BorderLayout.SOUTH); // southPanelを配置

        // WindowEventリスナを設定（無名クラスを利用している）
        this.addWindowListener(
                new WindowAdapter() {
                    // ウィンドウが閉じたら終了する処理
                    public void windowClosing(WindowEvent e) {
                        System.exit(1);
                    }
                });
    }

    public static void main(String[] args) {
        MyApplication app = new MyApplication();
        app.setSize(700, 500);
        app.setVisible(true);
    }
}