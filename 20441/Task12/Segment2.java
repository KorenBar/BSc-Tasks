/**
 * Segment2 represents a line (parallel to the x-axis) using a center point and length.
 *
 * @author Koren Bar 318810595
 * @version 04/11/2020
 */
public class Segment2 {
    // The location of the point on this segment (0 => Left, 0.5 => Center, 1 => Right)
    private static final double POINT_LOCATION = 0.5;

    private Point _poCenter;
    private double _length;

    /**
     * Constructs a new segment using a center point and the segment length.
     * @param poCenter The Center Point
     * @param length The segment length
     */
    public Segment2(Point poCenter, double length)
    {
        // Create a copy of the center point bcs it should be private (also prevents from a copied segment and the original segment to effect each other)
        _poCenter = new Point(poCenter);
        _length = length;
    }

    /**
     * Constructs a new segment using 4 specified x y coordinates: two coordinates for the left point and two coordinates for the right point. if the y coordinates are different, the y will be taken just from the left point.
     * @param leftX X value of left point
     * @param leftY Y value of left point
     * @param rightX X value of right point
     * @param rightY Y value of right point
     */
    public Segment2(double leftX, double leftY, double rightX, double rightY)
    { // These point should be valid inside the first quadrant (I).
        // will work just in that case that left point is actually left and the right point is actually right (as promised)
        // the center x is the length / 2 + leftX, and the length is rightX - leftX
        this(new Point(leftX + (rightX - leftX) * POINT_LOCATION, leftY), rightX - leftX);
    }

    /**
     * Constructs a new segment using two Points. if the y coordinates are different, the y will be taken just from the left point.
     * @param left The left point of the segment
     * @param right The right point of the segment
     */
    public Segment2(Point left, Point right)
    { 
        this(left.getX(), left.getY(), right.getX(), right.getY());        
    }

    /**
     * Copy Constructor. Construct a segment using a reference segment.
     * @param other The reference segment
     */
    public Segment2(Segment2 other)
    {
        this(other._poCenter, other._length);
    }

    /**
     * Change the size of this segment by moving the right point (on the X-axis).
     * (if resizing will cause the right point to be in the left to the left point, this segment will stay in place without any change)
     * @param delta Positive value to increase or negative value to decrease.
     */
    public void changeSize(double delta)
    {
        if (_length + delta < 0) return; // Invalid resize, don't perform!
        _length += delta;
        _poCenter.move(delta * POINT_LOCATION, 0); // Move the center point x coordinate by half of the delta.
    }

    /**
     * Check if the reference segment is equal to this segment.
     * @param other The reference segment
     * @return true if the reference segment is equal to this segment
     */
    public boolean equals(Segment2 other)
    {
        return other._poCenter.equals(_poCenter) && other._length == _length;
    }

    /**
     * Returns the segment length.
     * @return The segment length
     */
    public double getLength()
    {
        return _length;
    }

    /**
     * Returns the left point of the segment.
     * @return The left point of the segment
     */
    public Point getPoLeft()
    {
        Point p = new Point(_poCenter);
        // Minus half of the length (when POINT_LOCATION is 0.5)
        p.move(-(_length * POINT_LOCATION), 0);
        return p;
    }

    /**
     * Returns the right point of the segment.
     * @return The right point of the segment
     */
    public Point getPoRight()
    {
        Point p = new Point(_poCenter);
        // Reversing the POINT_LOCATION to get the rest of the segment from the right side (in case that POINT_LOCATION is not center) by (1.0 - POINT_LOCATION)
        // bcs of the POINT_LOCATION is 0.5 it will still be 0.5 after that.
        p.move(_length * (1.0 - POINT_LOCATION), 0);
        return p;
    }

    /**
     * Check if this segment is above a reference segment.
     * @param other The reference segment
     * @return True if this segment is above the reference segment
     */
    public boolean isAbove(Segment2 other)
    {
        return _poCenter.getY() > other._poCenter.getY();
    }

    /**
     * Check if this segment is bigger than a reference segment.
     * @param other The reference segment
     * @return True if this segment is bigger than the reference segment
     */
    public boolean isBigger(Segment2 other)
    {
        return _length > other._length;
    }

    /**
     * Check if this segment's right point is left to the the other segment's left point.
     * @param other The other segment to be compared with.
     * @return ture if this segment's right point is left to the the other segment's left point.
     */
    public boolean isLeft(Segment2 other)
    { // if the right point's X are equals to the other left point's X, false will be returned (as required).
        return getPoRight().isLeft(other.getPoLeft());
    }

    /**
     * Check if this segment's left point is right to the the other segment's right point.
     * @param other The other segment to be compared with.
     * @return ture if this segment's left point is right to the the other segment's right point.
     */
    public boolean isRight(Segment2 other)
    { // if the left point's X are equals to the other right point's X, true will be returned (*make sure that is not the case*).
        return !isLeft(other) && getPoLeft().getX() != other.getPoRight().getX();
    }

    /**
     * Check if this segment is under a reference segment.
     * @param other The reference segment
     * @return True if this segment is under the reference segment
     */
    public boolean isUnder(Segment2 other)
    {
        return other.isAbove(this);
    }

    /**
     * Move this segment on the X-axis.
     * (if a part of this segment will be outside the first quadrant (I) after moving, this segment will stay in place without any change)
     * @param delta The distance to move.
     */
    public void moveHorizontal(double delta)
    {
        if (getPoLeft().getX() + delta < 0) return; // Invalid move, don't perform!
        _poCenter.move(delta, 0);
    }

    /**
     * Move this segment on the Y-axis.
     * (if a part of this segment will be outside the first quadrant (I) after moving, this segment will stay in place without any change)
     * @param delta The distance to move.
     */
    public void moveVertical(double delta)
    {
        if (getPoLeft().getY() + delta < 0) return; // Invalid move, don't perform!
        _poCenter.move(0, delta);
    }

    // We can't use the Math.min and Math.max, so I gonna write them here:
    private static double min(double a, double b)
    {
        return a < b ? a : b;
    }
    private static double max(double a, double b)
    {
        return a > b ? a : b;
    }

    /**
     * Get the overlap length between two segments.
     * 
     * @param other The other segment.
     * @return The overlap length between this and the other segments.
     */
    public double overlap(Segment2 other)
    {
        // The overlap is the minimum right x - the maximum left x.
        double overlap = min(getPoRight().getX(), other.getPoRight().getX()) - max(getPoLeft().getX(), other.getPoLeft().getX());
        // If the result is negative, there is no overlap and we should return 0.
        return overlap > 0 ? overlap : 0;
    }

    /**
     * Check if the point is on this segment.
     * 
     * @param p The point to check.
     * @return true if the point is on thit segment.
     */
    public boolean pointOnSegment(Point p)
    {
        return p.getY() == _poCenter.getY() && p.getX() >= getPoLeft().getX() && p.getX() <= getPoRight().getX();
    }

    /**
     * Calculate perimeter for the trapeze represented with this segment and the other.
     * 
     * @param other The other segment that represent the trapeze.
     * @return The perimeter of the trapeze.
     */
    public double trapezePerimeter(Segment2 other)
    {
        return getLength()                              // Side A (This)
            + getPoRight().distance(other.getPoRight()) // Side B
            + other.getLength()                         // Side C
            + other.getPoLeft().distance(getPoLeft());  // Side D
    }

    /**
     * Get the string that represents this segment.
     * @return The string representation of this segment in the (x1,y1)---(x2,y2)
     */
    public String toString()
    {
        return getPoLeft().toString() + "---" + getPoRight().toString();
    }
}
