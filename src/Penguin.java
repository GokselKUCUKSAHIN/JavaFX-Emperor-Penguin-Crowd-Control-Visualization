import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Penguin
{

    private static Color bodyColor = Color.hsb(0, 0.01, 1);
    private Group group = new Group();
    private Circle body;
    private Circle hitBorder;
    private Point2D vel = new Point2D(0, 0);
    private Point2D acc = new Point2D(0, 0);

    private ArrayList<Penguin> neigbors;
    //
    public static ArrayList<Penguin> penguins = new ArrayList<>(); // Static Penguin Array

    public Penguin(Point2D pos)
    {
        this(pos.getX(), pos.getY());
    }

    public Penguin(double x, double y)
    {
        group.setLayoutX(x);
        group.setLayoutY(y);
        initBody();
        penguins.add(this); // Add this to Penguin Array
    }

    private void initBody()
    {
        // Create body
        body = new Circle(0, 0, 12);
        body.setFill(bodyColor); // Fill with SNOW Color
        // Create border
        hitBorder = new Circle(0, 0, 30, Color.TRANSPARENT);
        hitBorder.setStrokeWidth(1.2);
        hitBorder.setStroke(Color.YELLOW);
        // Place in them into Group
        group.getChildren().addAll(body, hitBorder);
    }

    public Node getBody()
    {
        // Polymorphism :)
        return this.group;
    }


    public Point2D getPos()
    {
        // Get x,y coordinates of Group
        return new Point2D(group.getLayoutX(), group.getLayoutY());
    }

    public void setX(double x)
    {
        //System.out.println("X: " + x); //debug
        this.group.setLayoutX(x);
    }

    public void setY(double y)
    {
        //System.out.println("Y: "+ y); //debug
        this.group.setLayoutY(y);
    }

    public void setPos(Point2D pos)
    {
        setX(pos.getX());
        setY(pos.getY());
    }

    public void setPos(double x, double y)
    {
        setX(x);
        setY(y);
    }

    public void update()
    {
        // Pos = t * Vel
        // Vel = t * Acc;
        acc = (vel.multiply(-0.03));
        vel = vel.add(acc);
        double span = Utils.span(vel);
        if (span >= 0.1)
        {
            // if vel bigger or equals to 0.1
            setPos(getPos().add(vel));
            //it means it's moving now
            //set color value according vel
            body.setFill(Color.hsb(0, Utils.map(span, 0.1, 7, 0.01, 1), 1));
        } else
        {
            // if too slow just stop it.
            vel = vel.multiply(0);
            //it means it's static now
            body.setFill(bodyColor);
        }
    }

    public void move()
    {
        vel = new Point2D(Utils.getRandom(-5, 5), Utils.getRandom(-5, 5));
        //System.out.println(acc.getX() + ", " + acc.getY());
    }

    public void hideShowBorder()
    {
        hitBorder.setVisible(!hitBorder.isVisible());
    }

    //region Old Code Blocks
    /*private void checkVel()
    {
        // If too slow just stop
        if (Utils.span(vel) < 0.1)
        {
            vel = vel.multiply(0);
        }
    }*/
    //endregion
}
