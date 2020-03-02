import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.ArrayList;

public class PenguinBody extends Group
{

    // variables
    double radius = 100;
    DoubleProperty scale = new SimpleDoubleProperty(1);
    // Colors
    Color backGroundColor = Color.BLACK;


    public PenguinBody(double size)
    {
        //this.setLayoutX(Main.width / 2);
        //this.setLayoutY(Main.height / 2);
        draw();
        scale.set(size / radius);
        this.scaleXProperty().bind(scale);
        this.scaleYProperty().bind(scale);
        //this.setScaleX(size / radius);
        //this.setScaleY(size / radius);
    }

    public void draw()
    {
        // TODO Add yellow stains to neck regions.

        //region Nose
        Polygon nose = new Polygon(new double[]{0, -20, -15, -35, 15, -35});
        nose.setFill(Color.ORANGE);
        //endregion Nose

        //region Left Eye
        Circle leftEye = new Circle(-22, -49, 6.2, Color.BLACK);
        Circle rightEye = new Circle(22, -49, 6.2, Color.BLACK);
        //endregion Left Eye

        //region Left Eye Shine
        Circle leftEyeShine = new Circle(-20.5, -50, 1.6, Color.SNOW);
        Circle rightEyeShine = new Circle(23.5, -50, 1.6, Color.SNOW);
        //endregion Left Eye Shine

        //region Old Arc Left
        /*
        Arc leftArcEye = new Arc(-30,-44, 25,30,-35,220);
        leftArcEye.setFill(Color.SNOW);
        //leftArcEye.setType(ArcType.ROUND);
        leftArcEye.setRotate(-35);
        leftArcEye.setStroke(Color.DARKGRAY);
        leftArcEye.setStrokeWidth(2.1);
        leftArcEye.setStrokeType(StrokeType.OUTSIDE);
        */
        //endregion Old Arc Left

        //region Left Eye Patch
        Ellipse leftEyePatch = new Ellipse(-25, -45, 25, 30);
        leftEyePatch.setFill(Color.SNOW);
        leftEyePatch.setRotate(-35);
        //endregion Left Eye Patch

        //region Old Arc Right
        /*
        Arc rightArcEye = new Arc(29,-44,25,30,-5,220);
        rightArcEye.setFill(Color.SNOW);
        //rightArcEye.setType(ArcType.ROUND);
        rightArcEye.setRotate(35);
        rightArcEye.setStroke(Color.DARKGRAY);
        rightArcEye.setStrokeWidth(2.1);
        */
        //endregion Old Arc Right

        //region Right Eye Patch
        Ellipse rightEyePatch = new Ellipse(25, -45, 25, 30);
        rightEyePatch.setFill(Color.SNOW);
        rightEyePatch.setRotate(35);
        //endregion Right Eye Patch

        //region Yellow Patch
        Arc yPatch = new Arc(0, 18, 62, 70, 18, 25);
        yPatch.setFill(Color.YELLOW);
        yPatch.setType(ArcType.OPEN);
        //endregion Yellow Patch

        //region Torso
        Ellipse torso = new Ellipse(0, 18, 62, 70);
        torso.setFill(Color.SNOW);
        torso.setStrokeWidth(2.1);
        //torso.setStroke(Color.DARKGRAY);
        //endregion Torso

        //region Back Ground Circle
        Circle backGroundCircle = new Circle(0, 0, radius, backGroundColor);
        backGroundCircle.setStroke(Color.DARKGRAY);
        backGroundCircle.setStrokeWidth(2.1);
        //endregion Back Ground Circle
        this.getChildren().addAll(backGroundCircle, torso, leftEyePatch, rightEyePatch,
                yPatch,
                leftEye, rightEye, leftEyeShine, rightEyeShine, nose);
    }

    public void increaseSize()
    {
        if (scale.get() <= 5)
        {
            this.scale.set(this.scale.get() + 0.1);
        }
    }

    public void decreaseSize()
    {
        if (scale.get() >= 0.2)
        {
            this.scale.set(this.scale.get() - 0.1);
        }
    }

}
