import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class CanvasDoodleTest extends Application {
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
        primaryStage.setTitle("Canvas Doodle Test");
        Group root = new Group();

        // Draw background with gradient
        Rectangle rect = new Rectangle(400, 400);
        drawBackground(rect);
        root.getChildren().add(rect);

        // Create the Canvas, filled in with Blue
        final Canvas canvas = new Canvas(200, 200);
        canvas.setTranslateX(100);
        canvas.setTranslateY(100);
        reset(canvas, Color.BLUE);

        final GraphicsContext gc = canvas.getGraphicsContext2D();

        // Clear away portions as the user drags the mouse
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

                gc.clearRect(e.getX() - 2, e.getY() - 2, 5, 5);
            }
        });

        // Fill the Canvas with a Blue rectnagle when the user double-clicks
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (t.getClickCount() >1) {
                    reset(canvas, Color.BLUE);
                }
            }
        });

        // Add the Canvas to the Scene, and show the Stage
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }

    /**
     * Resets the canvas to its original look by filling in a rectangle covering
     * its entire width and height. Color.BLUE is used in this demo.
     *
     * @param canvas The canvas to reset
     * @param color The color to fill
     */
    private void reset(Canvas canvas, Color color) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(color);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    /**
     * Draws the background with a RadialGradient
     * that transitions from Red to Yellow.
     * @param rect the Rectangle to draw on the Canvas
     */
    private void drawBackground(Rectangle rect) {
        rect.setFill(new LinearGradient(0, 0, 1, 1, true,
                CycleMethod.REFLECT,
                new Stop(0, Color.RED),
                new Stop(1, Color.YELLOW)));
    }

    public static void main(String[] args) {
        launch(args);
    }

}
