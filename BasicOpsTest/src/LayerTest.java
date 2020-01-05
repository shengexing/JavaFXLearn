import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LayerTest extends Application {
    private Group root;
    private BorderPane borderPane;
    private Canvas layer1;
    private Canvas layer2;
    private GraphicsContext gc1;
    private GraphicsContext gc2;
    private ChoiceBox cb;

    private void createLayers(){

        // Layers 1&2 are the same size
        layer1 = new Canvas(300,250);
        layer2 = new Canvas(300,250);

        // Obtain Graphics Contexts
        gc1 = layer1.getGraphicsContext2D();
        gc1.setFill(Color.GREEN);
        gc1.fillOval(50,50,20,20);
        gc2 = layer2.getGraphicsContext2D();
        gc2.setFill(Color.BLUE);
        gc2.fillOval(100,100,20,20);
    }

    private void handleLayers(){
        // Handler for Layer 1
        layer1.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                gc1.fillOval(e.getX(),e.getY(),20,20);
            }
        });

        // Handler for Layer 2
        layer2.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                gc2.fillOval(e.getX(),e.getY(),20,20);
            }
        });
    }

    private void createChoiceBox(){
        cb = new ChoiceBox();
        cb.setItems(FXCollections.observableArrayList("Layer 1 is GREEN", "Layer 2 is BLUE"));
        cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue o, Object o1, Object o2){
                if(o2.toString().equals("Layer 1 is GREEN")){
                    layer1.toFront();
                }else if(o2.toString().equals("Layer 2 is BLUE")){
                    layer2.toFront();
                }
            }
        });
        cb.setValue("Layer 1 is GREEN");
    }

    private void addLayers(){
        // Add Layers
        borderPane.setTop(cb);
        Pane pane = new Pane();
        pane.getChildren().add(layer1);
        pane.getChildren().add(layer2);
        layer1.toFront();
        borderPane.setCenter(pane);
        root.getChildren().add(borderPane);
    }

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
        // Build GUI
        borderPane = new BorderPane();
        primaryStage.setTitle("Layer Test");
        root = new Group();
        createLayers();
        handleLayers();
        createChoiceBox();
        addLayers();

        // Show Scene
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
