import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;

import java.util.ArrayList;

public class Penguin extends Group
{

    // variables
    double radius = 100;

    // Colors
    Color backGroundColor = Color.BLACK;
    public static ArrayList<Penguin> clubPenguin = new ArrayList<>();

    public Penguin()
    {
        this.setLayoutX(Main.width / 2);
        this.setLayoutY(Main.height / 2);
        draw();
        this.setScaleX(2);
        this.setScaleY(2);
    }

    public void draw()
    {
        Ellipse leftEye = new Ellipse(-25, -45, 25, 30);
        leftEye.setFill(Color.SNOW);
        leftEye.setRotate(-35);

        Ellipse rightEye = new Ellipse(25, -45, 25, 30);
        rightEye.setFill(Color.SNOW);
        rightEye.setRotate(35);

        Ellipse torso = new Ellipse(0, 18, 62, 70);
        torso.setFill(Color.SNOW);
        torso.setStrokeWidth(2.1);
        torso.setStroke(Color.DARKGRAY);

        //region Back Ground Circle
        Circle backGroundCircle = new Circle(0, 0, radius, backGroundColor);
        backGroundCircle.setStroke(Color.DARKGRAY);
        backGroundCircle.setStrokeWidth(2.1);
        //endregion

        this.getChildren().addAll(backGroundCircle, torso, leftEye, rightEye);
    }
}