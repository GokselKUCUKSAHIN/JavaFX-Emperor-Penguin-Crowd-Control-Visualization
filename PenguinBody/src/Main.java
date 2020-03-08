import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application
{

    public static ObservableList<Node> child;
    //
    private static final String title = "JellyBeanci";
    public static final int width = 800;
    public static final int height = 800;
    private static Color backcolor = Color.rgb(51, 51, 51);
    private static Rectangle background = new Rectangle(900, 900, backcolor);

    private static Timeline update;

    double value = 0;

    @Override
    public void start(Stage stage) throws Exception
    {
        BorderPane borderPane = new BorderPane();
        borderPane.getChildren().add(background);
        Pane root = new Pane();
        //
        borderPane.setCenter(root);
        VBox container = new VBox(10);
        container.setPadding(new Insets(20, 20, 20, 20));
        //
        Slider activitySlider = new Slider(0, 1, 0);
        Slider xSlider = new Slider(0, width, width / 2);
        Slider ySlider = new Slider(0, height, height / 2);
        //
        container.getChildren().addAll(xSlider, ySlider, activitySlider);
        borderPane.setBottom(container);
        child = root.getChildren();
        //
        PenguinBody penguin = new PenguinBody(250);
        penguin.x.set(width / 2);
        penguin.y.set(height / 2);
        //
        child.add(penguin);
        //
        activitySlider.valueProperty().addListener(new ChangeListener<Number>()
        {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val)
            {
                stage.setTitle(String.format("%.2f; Δ = %.3f", new_val, (double) new_val - (double) old_val));
                value = new_val.doubleValue();
                penguin.switchColors(value);
            }
        });
        xSlider.valueProperty().addListener(new ChangeListener<Number>()
        {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val)
            {
                penguin.x.set(new_val.doubleValue());
            }
        });
        ySlider.valueProperty().addListener(new ChangeListener<Number>()
        {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val)
            {
                penguin.y.set(new_val.doubleValue());
            }
        });

        //
        borderPane.setOnKeyPressed(e -> {
            switch (e.getCode())
            {
                case F1:
                {
                    //PLAY
                    update.play();
                    break;
                }
                case F2:
                {
                    //PAUSE
                    update.pause();
                    break;
                }
                case F3:
                {
                    //Show Child Count
                    System.out.println("Child Count: " + child.size());
                    break;
                }
                case Q:
                {
                    penguin.increaseSize();
                    break;
                }
                case W:
                {
                    penguin.decreaseSize();
                    break;
                }
            }
        });
        update = new Timeline(new KeyFrame(Duration.millis(16), e -> {
            //60 fps
            System.out.println("loop test");
        }));
        update.setCycleCount(Timeline.INDEFINITE);
        update.setRate(1);
        update.setAutoReverse(false);
        //update.play(); //uncomment for play when start
        //
        stage.setTitle(title);
        stage.setResizable(false);
        stage.setScene(new Scene(borderPane, width - 10, height - 10, backcolor));
        stage.show();
        borderPane.requestFocus();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
