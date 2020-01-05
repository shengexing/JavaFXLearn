import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.*;
import javafx.stage.Stage;

public class CanvasTest extends Application {

    private Canvas canvas = new Canvas(200, 200);
    private GraphicsContext gc = canvas.getGraphicsContext2D();
    private Group root = new Group();

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
        primaryStage.setTitle("Canvas Test");
        moveCanvas(0,0);
        drawDShape();
        drawRadialGradient(Color.RED, Color.YELLOW);
        drawLinearGradient(Color.BLUE, Color.GREEN);
        drawDropShadow(Color.GRAY, Color.BLUE, Color.GREEN, Color.RED);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }

    /**
     * Moves the canvas to a new location within the Scene. This is accomplished
     * by performing a translation transformation on the Canvas object, passing
     * in the desired x and y coordinates. Passing in values of 0,0 will position
     * the Canvas in the upper left corner of the Scene.
     * @param x The new x coordinate
     * @param y The new y coordinate
     */
    private void moveCanvas(int x, int y) {
        canvas.setTranslateX(x);
        canvas.setTranslateY(y);
    }

    /**
     * Draws an area in the shape of a capital letter "D."
     * The user can try substituting numbers
     * of their own in the bezierCurveTo parameters to
     * warp the shape away from the letter "D."
     */
    private void drawDShape() {
        gc.beginPath();
        gc.moveTo(50, 50);
        gc.bezierCurveTo(150, 20, 150, 150, 75, 150);
        gc.closePath();
    }

    /**
     * Draws a radial gradient on the Canvas object, which appears as a series of
     * circles radiating outward. This demo uses RED and YELLOW by default.
     *
     * @param firstColor The color used in the first Stop of the gradient.
     * @param lastColor  The color used in the last Stop of the gradient.
     */
    private void drawRadialGradient(Color firstColor, Color lastColor) {
        gc.setFill(new RadialGradient(0, 0, 0.5, 0.5, 0.1, true,
                CycleMethod.REFLECT,
                new Stop(0.0, firstColor),
                new Stop(1.0, lastColor)));
        gc.fill();
    }

    /**
     * Draws a linear gradient on the Canvas object, which colors the letter "D"
     * from top to bottom. The default colors used in this demo are BLUE and GREEN.
     *
     * @param firstColor
     * @param secondColor
     */
    private void drawLinearGradient(Color firstColor, Color secondColor) {
        LinearGradient lg = new LinearGradient(0, 0, 1, 1, true,
                CycleMethod.REFLECT,
                new Stop(0.0, firstColor),
                new Stop(1.0, secondColor));
        gc.setStroke(lg);
        gc.setLineWidth(20);
        gc.stroke();
    }

    /**
     * Draws a four separate drop shadows around the letter "D." The default
     * colors used in the demo are GREY, BLUE, GREEN, and RED.
     *
     * @param firstColor
     * @param secondColor
     * @param thirdColor
     * @param fourthColor
     */
    private void drawDropShadow(Color firstColor, Color secondColor,
                                Color thirdColor, Color fourthColor) {
        gc.applyEffect(new DropShadow(20, 20, 0, firstColor));
        gc.applyEffect(new DropShadow(20, 0, 20, secondColor));
        gc.applyEffect(new DropShadow(20, -20, 0, thirdColor));
        gc.applyEffect(new DropShadow(20, 0, -20, fourthColor));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
