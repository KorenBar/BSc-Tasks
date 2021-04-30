/**
 * Represents 2 dimensional points.
 * @author Koren Bar 318810595
 * @version 06/11/2020
 */
public class Point {

    private final static double DEGREE_RADIAN_RATIO = 180.0 / Math.PI; // Degrees in 1 radian
    private final double MIN_VALUE = 0; // The first quadrant (I)
    private final int ROUNDING_DECIMALS = 10; // For rounding the coordinates (*it is irrational to not rounding, your testers failed because of it*)

    // initialized to (0,0)
    // must initializing since we have to set x and y separately and we can't create additional members for x and y.
    private double _radius = 0, _alpha = 90;

    /**
     * Copy constructor that will create a new Point object with the same values.
     * param other The other Point to copy from.
     */
    public Point(Point other)
    {
        // We can do it that way and re-validate the params
        this(other.getX(), other.getY());
        // or just copy the members without caring about the data validation, bcs the other object is Point and will take care of it.
        //_alpha = other._alpha;
        //_radius = other._radius;
    }

    /**
     * Copy constructor that will create a new Point object with the same values.
     * param x The X coordinate of the Point.
     * param y The Y coordinate of the Point.
     */
    public Point(double x, double y)
    {
        // if one of these values is smaller than the minimum value, set the minimum value.
        setX(x > MIN_VALUE ? x : MIN_VALUE);
        setY(y > MIN_VALUE ? y : MIN_VALUE);
    }

    /**
     * Rounding a double by given max decimals.
     * Necessary for toString and the getters. (*it is irrational to not rounding, your testers failed because of it*)
     * @param num The number to round
     * @param decimals Max number of decimals (max 18, in circumstances of variables size)
     * @return Rounded double
     */
    private static double round(double num, int decimals)
    {   // Separating the integer and the decimal in order to prevent variables size limitation problems.
        double template = Math.pow(10, decimals);
        int integer = (int)num; // The integer
        double rest = num - integer; // The rest of the number
        double roundedRest = Math.round(rest * template) / template;

        return integer + roundedRest;
    }

    /**
     * This method returns the x coordinate of the point.
     * @return The X coordinate of this Point.
     */
    public double getX()
    {
        // cos(0) = 1, so if the alpha is 0 we'll get the radius.
        // cos(90) = 0, so if the the alpha is 90 we'll get 0.
        // but cos(90) will not return 0 in this case, nearly 0 will be returned (https://stackoverflow.com/a/5263743)
        // So we MUST round it!
        return round(Math.cos(_alpha / DEGREE_RADIAN_RATIO) * _radius, ROUNDING_DECIMALS);
    }

    /**
     * This method returns the y coordinate of the point.
     * @return The Y coordinate of this Point.
     */
    public double getY()
    {
        return round(Math.sin(_alpha / DEGREE_RADIAN_RATIO) * _radius, ROUNDING_DECIMALS);
    }

    /**
     * This method sets the x coordinate of the point. If the new x coordinate is negative the old x coordinate will remain unchanged.
     * @param x - The new x coordinate
     */
    public void setX(double x)
    {
        if (x < MIN_VALUE) return; // if smaller than the min value, don't set!
        calculate(x, getY());
    }

    /**
     * This method sets the y coordinate of the point. If the new y coordinate is negative the old y coordinate will remain unchanged.
     * @param  y - The new y coordinate
     */
    public void setY(double y)
    {
        if (y < MIN_VALUE) return; // if smaller than the min value, don't set!
        calculate(getX(), y);
    }

    // Calculating the alpha and radius.
    // API not required here since it is a private method.
    private void calculate(double x, double y)
    { 
        // Radius Calculation:
        // Get the distance from the 0 point.
        _radius = distance(0, 0, x, y);

        // Alpha Calculation:
        // We must make sure that x is not 0 (otherwise dividing in 0 exception will be thrown)
        if (x == 0) _alpha = 90;
        else if (y == 0) _alpha = 0;
        else // x and y are not 0, calculate it.
            _alpha = Math.atan(y / x) * DEGREE_RADIAN_RATIO;
    }

    /**
     * Check if this point values equals to other point values.
     * 
     * @param other The other point to be compared with.
     * @return true if this point equals other.
     */
    public boolean equals(Point other)
    {
        return other.getX() == getX() && other.getY() == getY();
    }

    /**
     * Check if the Y of this point is bigger than the other Y.
     * 
     * @param other The other point to be compared with.
     * @return ture if this Y coordinate is bigger than the other Y coordinate.
     */
    public boolean isAbove(Point other)
    {
        return getY() > other.getY();
    }

    /**
     * Check if the Y of this point is smaller than the other Y.
     * 
     * @param other The other point to be compared with.
     * @return ture if this Y coordinate is smaller than or equals the other Y coordinate.
     */
    public boolean isUnder(Point other)
    {
        return other.isAbove(this);
    }

    /**
     * Check if the X of this point is smaller than the other X.
     * 
     * @param other The other point to be compared with.
     * @return ture if this X coordinate is smaller than the other X coordinate.
     */
    public boolean isLeft(Point other)
    {
        return getX() < other.getX();
    }

    /**
     * Check if the X of this point is bigger than the other X.
     * 
     * @param other The other point to be compared with.
     * @return ture if this X coordinate is bigger than or equals the other x coordinate.
     */
    public boolean isRight(Point other)
    {
        return other.isLeft(this);
    }

    // Created another private method to get distance based on the "Pythagorean theorem", bcs we have to use it also in the radius calculation.
    // API not required here since it is a private function.
    private static double distance(double x1, double y1, double x2, double y2)
    {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    /**
     * Check the distance between this point and a given point.
     * @param other - The point to check the distance from.
     * @return The distance between this point to the other.
     */
    public double distance(Point other)
    {
        return distance(getX(), getY(), other.getX(), other.getY());
    }

    /**
     * Move this point.
     * (if the new point after moving will be outside the first quadrant (I), the point will stay in place)
     * 
     * @param dx X distance to move.
     * @param dy Y distance to move.
     */
    public void move(double dx, double dy)
    {
        double newX = getX() + dx;
        double newY = getY() + dy;

        // If the new point is outside the quadrant I, don't set!
        if (newX < 0 || newY < 0) return;

        // The new point is inside the quadrant I
        setX(newX);
        setY(newY);
    }

    /**
     * Get the string that represents this point.
     * @return The string representation of this point in the format (x,y)
     */
    public String toString()
    {
        return "(" + round(getX(), 4) + "," + round(getY(), 4) + ")";
    }
}
