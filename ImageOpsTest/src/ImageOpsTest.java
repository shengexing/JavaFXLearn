import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class ImageOpsTest extends Application {
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
        // 创建 Image 和 ImageView 对象
        Image image = new Image(new FileInputStream("./ImageOpsTest/src/javafx-documentation.png"));
        ImageView imageView = new ImageView();
        imageView.setImage(image);

        // 获取 PixelReader
        PixelReader pixelReader = image.getPixelReader();
        System.out.println("Image Width:" + image.getWidth());
        System.out.println("Image Height:" + image.getHeight());
        System.out.println("Pixel Format:" + pixelReader.getPixelFormat());

        // 创建 WritableImage
        WritableImage wImage = new WritableImage((int)image.getWidth(), (int)image.getHeight());
        PixelWriter pixelWriter = wImage.getPixelWriter();

        // 确定图片中三个像素的颜色
        // 在一个给定的行中确定每个像素的颜色
        for (int readY = 0; readY < image.getHeight(); readY++) {
            for (int readX = 0; readX < image.getWidth(); readX++) {
                Color color = pixelReader.getColor(readX, readY);
                System.out.println("\nPixel color at coordinates (" + readX + ", " + readY + ") " + color.toString());
                System.out.println("R = " + color.getRed());
                System.out.println("G = " + color.getGreen());
                System.out.println("B = " + color.getBlue());
                System.out.println("Opacity = " + color.getOpacity());
                System.out.println("Saturation = " + color.getSaturation());

                // 现在写入一个更为明亮的颜色到 PixelWriter 中
                color = color.brighter();
                pixelWriter.setColor(readX, readY, color);
            }
        }

        // 在屏幕上显示图像
        imageView.setImage(wImage);
        StackPane root = new StackPane();
        root.getChildren().add(imageView);
        Scene scene = new Scene(root, 300, 250);
        primaryStage.setTitle("Image Read Test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
