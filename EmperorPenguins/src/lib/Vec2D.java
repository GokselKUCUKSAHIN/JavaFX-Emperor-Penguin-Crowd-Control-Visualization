package lib;/*
2D Vector Lib with Using DoubleProperty
*/

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Vec2D
{

    public DoubleProperty x = new SimpleDoubleProperty(); //x coordinate
    public DoubleProperty y = new SimpleDoubleProperty(); //y coordinate

    public Vec2D(double x, double y)
    {
        this.x.set(x);
        this.y.set(y);
    }

    public Vec2D()
    {
        this(0, 0);
    }

    public Vec2D(Vec2D v)
    {
        this(v.x.get(), v.y.get());
    }

    public void setZero()
    {
        this.x.set(0);
        this.y.set(0);
    }

    public double[] getComponents()
    {
        return new double[]{x.get(), y.get()};
    }

    public double getMagnitude()
    {
        // sqrt(x^2 + y^2)
        return Math.sqrt(Math.pow(x.get(), 2) + Math.pow(y.get(), 2));
    }

    public double getMagnitudeSq()
    {
        // x^2 + y^2
        return Math.pow(x.get(), 2) + Math.pow(y.get(), 2);
    }

    public double distance(double vx, double vy)
    {
        vx -= x.get();
        vy -= y.get();
        return Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2));
    }

    public double distance(Vec2D vector)
    {
        double vx = vector.x.get() - this.x.get();
        double vy = vector.y.get() - this.y.get();
        return Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2));
    }

    public double distanceSq(double vx, double vy)
    {
        vx -= x.get();
        vy -= y.get();
        return (Math.pow(vx, 2) + Math.pow(vy, 2));
    }

    public double distanceSq(Vec2D vector)
    {
        double vx = vector.x.get() - this.x.get();
        double vy = vector.y.get() - this.y.get();
        return (Math.pow(vx, 2) + Math.pow(vy, 2));
    }

    /**
     * Performs normalize this vector
     */
    public void normalize()
    {
        double magnitude = getMagnitude();
        this.x.set(x.get() / magnitude);
        this.y.set(y.get() / magnitude);
    }

    public void setMagnitude(double value)
    {
        this.normalize();
        this.multiply(value);
    }

    public void setLimit(double limit)
    {
        if (this.getMagnitude() > limit)
        {
            setMagnitude(limit);
        }
    }

    /**
     * Performs create new vector normalized version of this
     *
     * @return This vectors Unit version
     */
    public Vec2D getNormalized()
    {
        double magnitude = getMagnitude();
        return new Vec2D(x.get() / magnitude, y.get() / magnitude);
    }

    /**
     * @return Vector Angle in Radians
     */
    public double getAngle()
    {
        return Math.atan2(-y.get(), x.get());
    }

    /**
     * @return Vector Angle in Degrees
     */
    public double getAngleDegree()
    {
        return radianToAngle(getAngle());
    }

    /**
     * Add the given double values
     *
     * @param x coordinate
     * @param y coordinate
     * @return A vector holding the product
     */
    public Vec2D add(double x, double y)
    {
        this.x.set(this.x.get() + x);
        this.y.set(this.y.get() + y);
        return this;
    }

    /**
     * Performs addition of this and given vector.
     *
     * @param addVector
     * @return A vector holding the product
     */
    public Vec2D add(Vec2D addVector)
    {
        return add(addVector.x.get(), addVector.y.get());
    }

    /**
     * Perform addition of two vectors and create new vector.
     *
     * @param v1 Vector
     * @param v2 Vector
     * @return A new Vector sum of given two vectors
     */
    public static Vec2D add(Vec2D v1, Vec2D v2)
    {
        return new Vec2D(v1.x.get() + v2.x.get(), v1.y.get() + v2.y.get());
    }

    /**
     * subtracts the given integer values
     *
     * @param x coordinate
     * @param y coordinate
     * @return A vector holding the product
     */
    public Vec2D subtract(double x, double y)
    {
        // Vect2D(this.x -= x, this.y -= y);
        this.x.set(this.x.get() - x);
        this.y.set(this.y.get() - y);
        return this;
    }

    /**
     * Performs subtraction of two vectors
     *
     * @param subVector
     */
    public Vec2D subtract(Vec2D subVector)
    {
        return subtract(subVector.x.get(), subVector.y.get());
    }

    public static Vec2D subtract(Vec2D v1, Vec2D v2)
    {
        return new Vec2D(v1.x.get() - v2.x.get(), v1.y.get() - v2.y.get());
    }

    public Vec2D divide(double dx, double dy)
    {
        this.x.set(this.x.get() / dx);
        this.y.set(this.y.get() / dy);
        return this;
    }

    public Vec2D divide(double divider)
    {
        return divide(divider, divider);
    }

    public Vec2D multiply(double mx, double my)
    {
        this.x.set(this.x.get() * mx);
        this.y.set(this.y.get() * my);
        return this;
        //return new lib.Vec2D(mx * this.x.get(), my * this.y.get());
    }

    public Vec2D multiply(double multiplier)
    {
        return multiply(multiplier, multiplier);
    }

    public Vec2D multiply(Vec2D vector)
    {
        return multiply(vector.x.get(), vector.y.get());
    }

    public static Vec2D multiply(Vec2D vector, double multiplier)
    {
        return new Vec2D(vector.x.get() * multiplier, vector.y.get() * multiplier);
    }

    public static Vec2D multiply(Vec2D v1, Vec2D v2)
    {
        return new Vec2D(v1.x.get() * v2.x.get(), v1.y.get() * v2.y.get());
    }
/*
    public void getMultiplied(double mx, double my)
    {
        this.x.set(this.x.get() * mx);
        this.y.set(this.y.get() * my);
    }

    public void getMultiplied(double multiplier)
    {
        getMultiplied(multiplier, multiplier);
    }
*/

    public double absX()
    {
        return Math.sqrt(Math.pow(this.x.get(), 2));
    }

    public double absY()
    {
        return Math.sqrt(Math.pow(this.y.get(), 2));
    }

    public double abs()
    {
        return Math.sqrt(Math.pow(this.x.get(), 2) + Math.pow(this.y.get(), 2));
    }

    public static Vec2D random2D()
    {
        double angle = Utils.getRandom(0, 360);
        return new Vec2D(Math.cos(angleToRadian(angle)), -Math.sin(angleToRadian(angle)));
    }

    public static double angleToRadian(double angle)
    {
        return (Math.PI / 180.0) * angle;
    }

    public static double radianToAngle(double radian)
    {
        return radian * (180.0 / Math.PI);
    }

    @Override
    public String toString()
    {
        return String.format("%3.3f; %3.3f", this.x.get(), this.y.get());
    }

    //
    /*TEST & DEBUG*/
    //
}
/*A GOKSEL KUCUKSAHIN SOFTWARE*/