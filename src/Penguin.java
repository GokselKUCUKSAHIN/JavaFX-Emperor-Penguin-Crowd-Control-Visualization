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
    private Circle neighborBorder;
    private Point2D vel = new Point2D(0, 0);
    private Point2D acc = new Point2D(0, 0);

    private ArrayList<Penguin> neighbors = new ArrayList<>();
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
        //
        neighborBorder = new Circle(0, 0, 75, Color.TRANSPARENT);
        neighborBorder.setVisible(false);
        neighborBorder.setStrokeWidth(1.3);
        neighborBorder.setStroke(Color.GREEN);
        // Place in them into Group
        group.getChildren().addAll(body, hitBorder, neighborBorder);
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
        acc = (vel.multiply(-0.13));
        vel = vel.add(acc);
        double span = Utils.span(vel);
        if (span >= 0.1)
        {
            // if vel bigger or equals to 0.1
            setPos(getPos().add(vel));
            //it means it's moving now
            //set color value according vel
            body.setFill(Color.hsb(0, Utils.map(span, 0.1, 1, 0.01, 1), 1));
        } else
        {
            // if too slow just stop it.
            vel = vel.multiply(0);
            //it means it's static now
            body.setFill(bodyColor);
        }
        if (neighbors.size() != 0)
        {
            moveToNeighbor();
            /*if(!Utils.isHit(this.getPos(),neighbors.get(0).getPos(),this.hitBorder.getRadius()*2))
            {

            }
            else
            {
                vel = vel.multiply(0);
            }*/
        }
    }

    public void move()
    {
        vel = new Point2D(Utils.getRandom(-15, 15), Utils.getRandom(-15, 15));
        //System.out.println(acc.getX() + ", " + acc.getY());
    }

    public void hideShowBorder()
    {
        hitBorder.setVisible(!hitBorder.isVisible());
    }

    public void hideShowNeighborBorder()
    {
        neighborBorder.setVisible(!neighborBorder.isVisible());
    }

    public ArrayList<Penguin> getNeighbors()
    {
        return this.neighbors;
    }

    public void moveToNeighbor()
    {
        // Define the Closest neighbor and approach
        // Currenty in test only 1 neighbor
        if (neighbors.size() >= 1)
        {
            double fdist = Utils.fastDistance(neighbors.get(0).getPos(), this.getPos());
            if (fdist >= Math.pow(hitBorder.getRadius() * 2 + 0.1, 2))
            {
                // Approach
                System.out.println("Approach");
                double angle = Utils.calculateAngle(this.getPos(), neighbors.get(0).getPos());
                Point2D pnt = Utils.endPoint(this.getPos(), angle, 0.15);
                vel = vel.add(pnt.getX() - getPos().getX(), pnt.getY() - getPos().getY());
            } else if (fdist < Math.pow(hitBorder.getRadius() * 2 - 0.1, 2))
            {
                // Dispach
                System.out.println("Dispach");
                double angle = Utils.calculateAngle(this.getPos(), neighbors.get(0).getPos());
                angle += 180;
                Point2D pnt = Utils.endPoint(this.getPos(), angle, 0.15);
                vel = vel.add(pnt.getX() - getPos().getX(), pnt.getY() - getPos().getY());
            } else
            {
                // Stop!
                System.out.println("Stop");
                vel = vel.multiply(0);
            }
        }
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
