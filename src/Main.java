import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application
{

    public static ObservableList<Node> child;
    //
    private static final String title = "JellyBeanci";
    public static final int width = 600;
    public static final int height = 600;
    private static Color backcolor = Color.rgb(51, 51, 51);


    Line force = new Line(300, 400, 600, 400);

    private static Timeline update;

    @Override
    public void start(Stage stage) throws Exception
    {
        Pane root = new Pane();
        child = root.getChildren();

        // Outer Border Circle
        Circle outer = new Circle(width / 2, height / 2, height * 0.42, Color.TRANSPARENT);
        outer.setStrokeWidth(5);
        outer.setStroke(Color.RED);
        outer.setVisible(false); // default invisible

        // initial Offsets
        double startX = Penguin.R;
        double staryY = Penguin.R;

        // Placing Penguins in a Circle
        for (int i = -1; i < 12; i++) // 13 Times
        {
            for (int j = 0; j < 10; j++) // 10 Times
            {
                Point2D pos; //temp Point2D Object
                if (j % 2 == 0)
                {
                    // even
                    pos = new Point2D(startX + j * 2 * Penguin.R, staryY + i * 2 * Penguin.R);
                } else
                {
                    // odd
                    pos = new Point2D(startX + j * 2 * Penguin.R, staryY + i * 2 * Penguin.R + Penguin.R); // + 30 for offset on height
                }
                // check this point in the borders
                if (Utils.isInTheCircle(outer.getCenterX(), outer.getCenterY(), outer.getRadius(), pos))
                {
                    // In the Circle
                    new Penguin(pos);
                }
            }
        }

        //
        for (Penguin penguin : Penguin.penguins)
        {
            penguin.setNeighbors();
        }

        //
        // DEBUG
        //
        /*
        Penguin pingu = new Penguin(width / 2 + 150, height / 2);
        Penguin test = new Penguin(width / 2 - 100, height / 2 - 100);
        test.getNeighbors().add(pingu);
        */

        child.add(outer);
        for (Penguin penguin : Penguin.penguins)
        {
            child.add(penguin.getBody());
        }
        for (Penguin penguin : Penguin.penguins)
        {
            penguin.getBody().setOnMouseClicked(e -> {
                if (e.getButton() == MouseButton.PRIMARY)
                {
                    // Left Mouse Button
                    // System.out.println("hey"); //debug event testing
                    penguin.move();
                }
            });
        }
        //
        force.setStrokeWidth(4);//default
        force.setVisible(false);//default
        child.add(force);
        root.setOnDragDetected(e->root.startFullDrag());

        //DRAG
        for (Penguin penguin : Penguin.penguins)
        {
            penguin.getBody().setOnMouseDragged(e->{
                if(e.getButton() == MouseButton.SECONDARY)
                {
                    force.setStartX(penguin.getX());
                    force.setStartY(penguin.getY());
                    force.setEndX(e.getSceneX());
                    force.setEndY(e.getSceneY());

                    // Calculate Distance
                    double clr = Utils.distance(force.getStartX(),force.getStartY(),force.getEndX(),force.getEndY());
                    clr = Utils.map(clr,0,500,160,0);

                    force.setStroke(Color.hsb(clr,1,1));
                    force.setVisible(true);
                }
            });
        }

        //RELEASE EVENT
        for (Penguin penguin:Penguin.penguins)
        {
            penguin.getBody().setOnMouseReleased(e->{
                if(e.getButton() == MouseButton.SECONDARY)
                {
                    force.setVisible(false);
                    penguin.move(e.getSceneX(),e.getSceneY());
                }
            });
        }

        //
        root.setOnKeyPressed(e -> {
            switch (e.getCode())
            {
                case F1:
                {
                    // PLAY
                    update.play();
                    break;
                }
                case F2:
                {
                    // PAUSE
                    update.pause();
                    break;
                }
                case F3:
                {
                    // Show Child Count
                    System.out.println("Child Count: " + child.size());
                    break;
                }
                case F5:
                {
                    // Hide/Show Borders
                    for (Penguin penguin : Penguin.penguins)
                    {
                        penguin.hideShowBorder();
                    }
                    break;
                }
                case F6:
                {
                    // Hide/Show neighbor Borders;
                    for (Penguin penguin : Penguin.penguins)
                    {
                        penguin.hideShowNeighborBorder();
                    }
                    break;
                }
                case F7:
                {
                    // Hide/Show Outer
                    outer.setVisible(!outer.isVisible());
                    break;
                }
                case F9:
                {
                    // Decrease Size by 1
                    for (Penguin penguin : Penguin.penguins)
                    {
                        penguin.decreaseSize();
                    }
                    break;
                }
                case F10:
                {
                    // Increase Size by 1
                    for (Penguin penguin : Penguin.penguins)
                    {
                        penguin.increaseSize();
                    }
                    break;
                }
                default:
                {
                    // Continue
                    break;
                }
            }
        });
        update = new Timeline(new KeyFrame(Duration.millis(20), e -> {
            // 60 fps
            // System.out.println("loop test");
            for (Penguin penguin : Penguin.penguins)
            {
                // Update All Penguins in Penguin ArrayList
                penguin.update();
            }
        }));
        update.setCycleCount(Timeline.INDEFINITE);
        update.setRate(1);
        update.setAutoReverse(false);
        update.play(); // uncomment for play when start //defult start on launch
        //
        stage.setTitle(title);
        stage.setResizable(false); // Fixed size Window
        stage.setScene(new Scene(root, width - 10, height - 10, backcolor));
        stage.show();
        root.requestFocus(); // for Keyboard Event handling
    }
    public static void main(String[] args)
    {
        launch(args);
    }
}
