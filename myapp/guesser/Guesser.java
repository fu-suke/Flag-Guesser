package myapp.guesser;

import java.awt.Graphics2D;
import java.io.*;
import java.awt.Color;
import java.awt.image.*;
import java.util.*;

import javax.imageio.ImageIO;

public class Guesser {
    List<BufferedImage> images = new ArrayList<>();
    List<String> imageNames = new ArrayList<>();
    List<int[]> imageVectors = new ArrayList<>();
    final int WIDTH = 120;
    final int HEIGHT = 80;
    final int N = 5; // 返す画像の枚数
    final String PATH = "images"; // 画像のフォルダ

    public Guesser() {
        // フォルダ中の画像を読み込んでおく
        loadImagesFromDirectory();
        // あらかじめ決めたサイズにリサイズしておく
        List<BufferedImage> resizedImages = getResizedImages(images, WIDTH, HEIGHT);
        // 輝度のベクトルに変換しておく
        imageVectors = extractBrightnessVector(resizedImages);
    }

    // 引数imageと最も類似した画像を返す
    public Map<String, BufferedImage> guess(BufferedImage inputImage) {
        System.out.println("---GUESS!---");
        // 入力画像のリサイズ
        BufferedImage resizedInputImage = resizeImage(inputImage, WIDTH, HEIGHT);

        // 画像を輝度+RGBの成分に分解する
        int[] inputImageVector = extractBrightnessVector(resizedInputImage);

        // 類似度が高い画像n枚のインデックスを計算する
        int[] idx = searchNearVectors(inputImageVector, imageVectors);

        Map<String, BufferedImage> res = new LinkedHashMap<>();

        for (int i = 0; i < N; i++) {
            String name = imageNames.get(idx[i]);
            BufferedImage image = images.get(idx[i]);
            res.put(name, image);
        }
        return res;
    }

    // フォルダから画像を読み込んで返す
    public void loadImagesFromDirectory() {
        imageNames = new ArrayList<>();
        File dir = new File(PATH);
        File[] files = dir.listFiles(
                (d, name) -> name.toLowerCase().endsWith(".png") ||
                        name.toLowerCase().endsWith(".jpg") ||
                        name.toLowerCase().endsWith(".jpeg"));
        if (files != null) {
            for (File file : files) {
                try {
                    BufferedImage image = ImageIO.read(file);
                    this.images.add(image);
                    // 画像を読み込むときにその名前もリストに追加
                    String fileName = file.getName();
                    // 拡張子を取り除く
                    int pos = fileName.lastIndexOf(".");
                    if (pos > 0) {
                        fileName = fileName.substring(0, pos);
                    }
                    fileName = fileName.replace("-", " ");
                    imageNames.add(fileName);
                } catch (IOException e) {
                    System.err.println("画像の読み込みに失敗しました: " + file.getPath());
                    e.printStackTrace();
                }
            }
        }
    }

    // 画像をw*hに圧縮する
    public List<BufferedImage> getResizedImages(List<BufferedImage> images, int w, int h) {
        List<BufferedImage> resizedImages = new ArrayList<>();
        for (BufferedImage image : images) {
            image = resizeImage(image, w, h);
            resizedImages.add(image);
        }
        return resizedImages;
    }

    public BufferedImage resizeImage(BufferedImage image, int w, int h) {
        // 元の画像の左右の余白をトリミングする
        BufferedImage trimmedImage = image.getSubimage(10, 8, image.getWidth() - 20,
                image.getHeight() - 16);
        BufferedImage outputImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = outputImage.createGraphics();
        g.drawImage(trimmedImage, 0, 0, w, h, null);
        g.dispose();

        return outputImage;
    }

    // BufferdImageをRGBと輝度の成分に変換する
    public List<int[]> extractBrightnessVector(List<BufferedImage> images) {
        List<int[]> vectors = new ArrayList<>();
        for (BufferedImage image : images) {
            vectors.add(extractBrightnessVector(image));
        }
        return vectors;
    }

    public int[] extractBrightnessVector(BufferedImage image) {
        int[] vectors = new int[WIDTH * HEIGHT * 4];

        int index = 0;
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Color color = new Color(image.getRGB(x, y));
                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();
                // 輝度
                vectors[index++] = (int) (0.299 * r + 0.587 * g + 0.114 * b);
                // RGB
                vectors[index++] = r;
                vectors[index++] = g;
                vectors[index++] = b;
            }
        }
        return vectors;
    }

    public int[] searchNearVectors(int[] inputImage, List<int[]> images) {
        // 戻り値（インデックス）
        int[] res = new int[N];
        List<int[]> copy = new ArrayList<>();

        // 配列をディープコピーする
        for (int[] image : images) {
            int[] tmp = new int[image.length];
            for (int i = 0; i < image.length; i++) {
                tmp[i] = image[i];
            }
            copy.add(tmp);
        }

        int idx = -1;
        for (int i = 0; i < N; i++) {
            // 一番近い画像のインデックスを計算する
            idx = searchNearVector(inputImage, copy);
            // そのインデックスを戻り値に格納する
            res[i] = idx;
            // 一番近い画像を無理やり遠くしておく
            copy.get(idx)[0] = Integer.MAX_VALUE;
        }

        return res;
    }

    public int searchNearVector(int[] inputImage, List<int[]> images) {
        double minDistance = Double.MAX_VALUE;
        int minIndex = -1;
        for (int i = 0; i < images.size(); i++) {
            int[] image = images.get(i);

            double distance = 0;
            for (int j = 0; j < image.length; j++) {
                // 距離の計算（ユークリッド距離）
                distance += Math.pow((image[j] - inputImage[j]), 2);
            }
            distance = Math.sqrt(distance);
            if (distance < minDistance) {
                minDistance = distance;
                minIndex = i;

            }
        }
        System.out.println(minDistance + " " + imageNames.get(minIndex));
        return minIndex;
    }

    // ベクトルを画像に戻す（デバッグ用）
    public BufferedImage convertToBufferedImage(int[] grayscaleImage, int WIDTH, int HEIGHT) {
        BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster raster = bufferedImage.getRaster();
        ColorModel model = bufferedImage.getColorModel();

        int[] pixel = new int[1];
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                int grayValue = grayscaleImage[y * WIDTH + x];
                pixel[0] = model.getRGB(grayValue);
                raster.setPixel(x, y, pixel);
            }
        }
        return bufferedImage;
    }
}
