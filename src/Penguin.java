import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Penguin
{

    private static Color bodyColor = Color.hsb(0, 0.01, 1);
    public static final double R = 30;
    private Group group = new Group();
    private Circle body;
    private Circle hitBorder;
    private Circle neighborBorder;
    private Point2D vel = new Point2D(0, 0);
    private Point2D acc = new Point2D(0, 0);
    private boolean state = false;

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
        // clear neighbor array list for avoiding duplicating object
        neighbors.clear();

        for (Penguin penguin : Penguin.penguins)
        {
            // for all penguin
            if (penguin != this)
            {
                // if current penguin not this
                if (Utils.isInTheCircle(group.getLayoutX(), group.getLayoutY(), neighborBorder.getRadius(), penguin.getPos()))
                {
                    // inInTheCircle function determines whether the specified object is in a circle.
                    neighbors.add(penguin);
                }
            }
        }
        //System.out.println(neighbors.size()); //debug
    }

    public void update()
    {
        // Position = time x Velocity
        // Velocity = time * Acceleration;
        acc = (vel.multiply(-0.1)); //-0.13
        vel = vel.add(acc);
        double span = Utils.span(vel);
        if (span >= 0.1)
        {
            // if vel bigger or equals to 0.1
            setPos(getPos().add(vel));
            // it means it's moving now
            // set color value according vel
            body.setFill(Color.hsb(0, Utils.map(span, 0.1, 1, 0.01, 1), 1));
        } else
        {
            // if too slow just stop it.
            vel = vel.multiply(0);
            // it means it's static now
            body.setFill(bodyColor);
        }
        setNeighbors();
        if (neighbors.size() != 0)
        {
            moveToNeighbor();
        }
    }

    public void move()
    {
        //double power = 4;
        //vel = new Point2D(Utils.getRandom(-power, power), Utils.getRandom(-power, power));
        double powerX = Utils.getRandom(2, 3.5);
        double powerY = Utils.getRandom(2, 3.5);
        if (Utils.getRandom(1) > 0.5)
        {
            powerX *= -1;
        }
        if (Utils.getRandom(1) > 0.5)
        {
            powerY *= -1;
        }
        vel = new Point2D(powerX, powerY);
        resetAllStates();
        this.state = true;
    }

    public void moveForward(Point2D pos)
    {
        // calculate angle from neighbor to this
        double angle = Utils.calculateAngle(pos, getPos());
        // find next point using angle polar coordinates
        Point2D pnt = Utils.endPoint(this.getPos(), angle, 0.2);
        // pushing object amount of differance of 'pnt' and 'pos'
        vel = vel.add(pnt.getX() - getPos().getX(), pnt.getY() - getPos().getY());
        state = false; // set state to false for next step
    }

    public void moveToNeighbor()
    {
        if (neighbors.size() != 0)
        {
            // if has at least 1 neighbor
            if (state)
            {
                // has been pushed
                for (Penguin neighbor : neighbors)
                {
                    // for all state = true neigbors
                    if (Utils.isHit(getPos(), neighbor.getPos(), hitBorder.getRadius() * 2)) //2
                    {
                        //is hit ?
                        neighbor.moveForward(getPos()); // push contacting neighbor toForward relative this
                        neighbor.setState(true); // set contacting neighbor's state to true
                        state = false; // set state to false
                    }
                }
            } else
            {
                // has not been pushed
                for (Penguin neighbor : neighbors)
                {
                    // for all state = false neighbors
                    if (Utils.fastDistance(getPos(), neighbor.getPos()) >= Math.pow(hitBorder.getRadius() * 2.36, 2))
                    {
                        //2.6 was default //2.3 was cool too //2.3 has some glitches
                        // Distance between this and neighbor greater than R * 2.6 //2.4 currently
                        double angle = Utils.calculateAngle(this.getPos(), neighbor.getPos());
                        // find next point using angle polar coordinates
                        Point2D pnt = Utils.endPoint(this.getPos(), angle, 0.25);
                        vel = vel.add(pnt.getX() - getPos().getX(), pnt.getY() - getPos().getY());
                    }
                }
            }
        }
    }

    public void increaseSize()
    {
        if (body.getRadius() < R - 9)
        {
            // if current size less than 25
            this.body.setRadius(body.getRadius() + 1);
        }
    }

    public void decreaseSize()
    {
        if (body.getRadius() > R - 23)
        {
            // if current size greater than 7
            this.body.setRadius(body.getRadius() - 1);
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
           /* pushedReset.setAutoReverse(false);
        pushedReset.setCycleCount(10);
        pushedReset.setRate(1);
        pushedReset.setOnFinished(e -> {
            pushed = false;
            vel = vel.multiply(0);
        });*/
//endregion