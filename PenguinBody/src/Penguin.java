import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

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
        Polygon nose = new Polygon(new double[]{0, -20, -15, -35, 15, -35});
        nose.setFill(Color.ORANGE);

        Circle leftEye = new Circle(-22, -49, 6.2, Color.BLACK);
        Circle rightEye = new Circle(22, -49, 6.2, Color.BLACK);

        Circle leftEyeShine = new Circle(-20.5, -50, 1.6, Color.SNOW);
        Circle rightEyeShine = new Circle(23.5, -50, 1.6, Color.SNOW);

        /*
        Arc leftArcEye = new Arc(-30,-44, 25,30,-35,220);
        leftArcEye.setFill(Color.SNOW);
        //leftArcEye.setType(ArcType.ROUND);
        leftArcEye.setRotate(-35);
        leftArcEye.setStroke(Color.DARKGRAY);
        leftArcEye.setStrokeWidth(2.1);
        leftArcEye.setStrokeType(StrokeType.OUTSIDE);
        */

        Ellipse leftEyePatch = new Ellipse(-25, -45, 25, 30);
        leftEyePatch.setFill(Color.SNOW);
        leftEyePatch.setRotate(-35);

        /*
        Arc rightArcEye = new Arc(29,-44,25,30,-5,220);
        rightArcEye.setFill(Color.SNOW);
        //rightArcEye.setType(ArcType.ROUND);
        rightArcEye.setRotate(35);
        rightArcEye.setStroke(Color.DARKGRAY);
        rightArcEye.setStrokeWidth(2.1);*/

        Ellipse rightEyePatch = new Ellipse(25, -45, 25, 30);
        rightEyePatch.setFill(Color.SNOW);
        rightEyePatch.setRotate(35);

        Ellipse torso = new Ellipse(0, 18, 62, 70);
        torso.setFill(Color.SNOW);
        torso.setStrokeWidth(2.1);
        //torso.setStroke(Color.DARKGRAY);

        //region Back Ground Circle
        Circle backGroundCircle = new Circle(0, 0, radius, backGroundColor);
        backGroundCircle.setStroke(Color.DARKGRAY);
        backGroundCircle.setStrokeWidth(2.1);
        //endregion

        this.getChildren().addAll(backGroundCircle, torso, leftEyePatch, rightEyePatch,
                leftEye, rightEye, leftEyeShine, rightEyeShine, nose);
    }
}