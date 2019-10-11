import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.util.Duration;

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
    private boolean state = false;

    //private boolean pushed = false;
    /*private Timeline pushedReset = new Timeline(new KeyFrame(Duration.millis(20), e -> {
        //
    }));*/

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

       /* pushedReset.setAutoReverse(false);
        pushedReset.setCycleCount(10);
        pushedReset.setRate(1);
        pushedReset.setOnFinished(e -> {
            pushed = false;
            vel = vel.multiply(0);
        });*/

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
        neighborBorder = new Circle(0, 0, 90, Color.TRANSPARENT); //80
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

    public void setState(boolean state)
    {
        this.state = state;
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
        }
        setNeighbors();
    }

    public void move()
    {
        double power = 5;
        vel = new Point2D(Utils.getRandom(-power, power), Utils.getRandom(-power, power));
        resetAllStates();
        this.state = true;
    }
    public static void resetAllStates()
    {
        for (Penguin penguin : Penguin.penguins)
        {
            penguin.setState(false);
        }
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

    public void setNeighbors()
    {
        neighbors.clear();
        for (Penguin penguin : Penguin.penguins)
        {
            if (penguin != this)
            {
                if (Utils.isInTheCircle(group.getLayoutX(), group.getLayoutY(), neighborBorder.getRadius(), penguin.getPos()))
                {
                    neighbors.add(penguin);
                }
            }
        }
        System.out.println(neighbors.size());
    }

    public void moveForward(Point2D pos)
    {
        double angle = Utils.calculateAngle(pos, getPos());
        Point2D pnt = Utils.endPoint(this.getPos(), angle, 0.4);
        vel = vel.add(pnt.getX() - getPos().getX(), pnt.getY() - getPos().getY());
        state = false;
    }

    public void moveToNeighbor()
    {
        if (neighbors.size() != 0)
        {
            // if has at least 1 neighbor

            if(state)
            {
                // has been pushed
                for (Penguin neighbor : neighbors)
                {
                    if (Utils.isHit(getPos(), neighbor.getPos(), hitBorder.getRadius() * 2))
                    {
                        //is hit
                        neighbor.moveForward(getPos());
                        neighbor.setState(true);
                        state = false;
                    }
                }
            }
            else
            {
                for (Penguin neighbor : neighbors)
                {
                    if (Utils.fastDistance(getPos(), neighbor.getPos()) >= Math.pow(hitBorder.getRadius() * 2.6, 2))
                    {
                        double angle = Utils.calculateAngle(this.getPos(), neighbor.getPos());
                        Point2D pnt = Utils.endPoint(this.getPos(), angle, 0.30);
                        vel = vel.add(pnt.getX() - getPos().getX(), pnt.getY() - getPos().getY());
                        state = false;
                    }
                }
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
