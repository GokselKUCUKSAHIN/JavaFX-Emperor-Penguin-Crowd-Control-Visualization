import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lib.Vec2D;

import java.util.ArrayList;

public class Penguin extends Group
{

    public static final double R = 30;
    public static ArrayList<Penguin> penguins = new ArrayList<>();
    public Vec2D pos = new Vec2D();

    // BODY ELEMENTS
    private Circle body;
    private Circle hitBorder;
    private static Color bodyColor = Color.hsb(0, 0.01, 1);
    private Circle neighborBorder;
    //

    public Penguin()
    {
        draw();
        penguins.add(this);
    }

    public void draw()
    {
        // Create body
        body = new Circle(0, 0, 16); //was 12 before
        body.setFill(bodyColor); // Fill with SNOW Color
        // Create border
        hitBorder = new Circle(0, 0, R, Color.TRANSPARENT);
        hitBorder.setStrokeWidth(1.2);
        hitBorder.setStroke(Color.YELLOW);
        hitBorder.setVisible(false); // hide while launching
        //
        neighborBorder = new Circle(0, 0, 3 * R, Color.TRANSPARENT); //80 //90 is good too
        neighborBorder.setVisible(false);
        neighborBorder.setStrokeWidth(1.3);
        neighborBorder.setStroke(Color.GREEN);
        neighborBorder.setVisible(false); // hide while launching
        // Place in them into Group
        this.getChildren().addAll(body, hitBorder, neighborBorder);
    }

    public void hideShowBorder()
    {
        // Hide / Show Border Property
        this.hitBorder.setVisible(!this.hitBorder.isVisible());
    }

    public void hideShowNeigborBorder()
    {
        // Hide / Show Neigbor Border Property
        this.neighborBorder.setVisible(!this.neighborBorder.isVisible());
    }
}