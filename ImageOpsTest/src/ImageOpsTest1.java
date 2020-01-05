import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

public class ImageOpsTest1 extends Application {

    // 图像数据
    private static final int IMAGE_WIDTH = 10;
    private static final int IMAGE_HEIGHT = 10;
    private byte[] imageData = new byte[IMAGE_WIDTH * IMAGE_HEIGHT * 3];

    // 绘制表面（Canvas）
    private GraphicsContext gc;
    private Canvas canvas;
    private Group root;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set. The primary stage will be embedded in
     *                     the browser if the application was launched as an applet.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages and will not be embedded in the browser.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("PiexlWriter Test");
        root = new Group();
        canvas = new Canvas(200, 200);
        canvas.setTranslateX(100);
        canvas.setTranslateY(100);
        gc = canvas.getGraphicsContext2D();
        createImageData();
        drawImageData();
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();

        // 截取场景的快照
        WritableImage writableImage = primaryStage.getScene().snapshot(null);

        // 将快照作为 png 图像写入到文件系统中
        File outFile = new File("./ImageOpsTest/src/imageops-snapshot.png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", outFile);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    // 设置像素的RGB值
    private void createImageData() {
        int i = 0;
        for (int y = 0; y < IMAGE_HEIGHT; y++) {
            int r = y*255/IMAGE_HEIGHT;
            for (int x = 0; x < IMAGE_WIDTH; x++) {
                int g = x*255/IMAGE_WIDTH;
                imageData[i] = (byte) r;
                imageData[i + 1] = (byte) g;
                System.out.println("\t\tR: " + (byte) r);
                System.out.println("\t\tG: " + (byte) g);
                i += 3;
            }
        }
    }

    // 渲染像素点
    private void drawImageData() {
        boolean on = true;
        PixelWriter pixelWriter = gc.getPixelWriter();
        PixelFormat<ByteBuffer> pixelFormat = PixelFormat.getByteRgbInstance();
        for (int y = 50; y < 150; y += IMAGE_HEIGHT) {
            for (int x = 50; x < 150; x += IMAGE_WIDTH) {
                if (on) {
                    pixelWriter.setPixels(x, y, IMAGE_WIDTH, IMAGE_HEIGHT, pixelFormat, imageData, 0, IMAGE_WIDTH * 3);
                }
                on = !on;
            }
            on = !on;
        }

        // 增加阴影效果
        gc.applyEffect(new DropShadow(20, 20, 20, Color.GRAY));
        root.getChildren().add(canvas);
    }

}
