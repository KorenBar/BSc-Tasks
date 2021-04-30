/**
 * Segment1 represents a line (parallel to the x-axis) using two Points.
 *
 * @author Koren Bar 318810595
 * @version 04/11/2020
 */
public class Segment1 {

    private Point _poLeft, _poRight;

    /**
     * Constructs a new segment using two Points. if the y coordinates are different, changing the y of the right point to be equal to the y of the left point.
     * @param left The left point of this segment.
     * @param right The left point of this segment.
     */
    public Segment1(Point left, Point right)
    {
        // Create a copy of these points bcs they should be private (also prevents from a copied segment and the original segment to effect each other)
        _poLeft = new Point(left);
        _poRight = new Point(right);

        // Make it parallel to the X-axis
        if (_poRight.getY() != _poLeft.getY())
            _poRight.setY(_poLeft.getY());
    }

    /**
     * Constructs a new segment using 4 specified x y coordinates: Two coordinates for the left point and two coordinates for the right point.
     * @param leftX The X for the left point of this segment.
     * @param leftY The Y for the left point of this segment.
     * @param rightX The X for the right point of this segment.
     * @param rightY The Y for the right point of this segment.
     */
    public Segment1(double leftX ,double leftY, double rightX ,double rightY)
    {
        this(new Point(leftX, leftY), new Point(rightX, rightY));
    }

    /**
     * Copy Constructor.
     * @param other The other segment to copy.
     */
    public Segment1(Segment1 other)
    {
        this(other._poLeft, other._poRight);
    }

    /**
     * Get the left point of this segment.
     * @return The left point of this segment.
     */
    public Point getPoLeft()
    { // Return a copy so it will not be changed later by this class!
        return new Point(_poLeft);
    }

    /**
     * Get the right point of this segment.
     * @return The right point of this segment.
     */
    public Point getPoRight()
    { // Return a copy so it will not be changed later by this class!
        return new Point(_poRight);
    }

    /**
     * Get the length of this segment.
     * @return The length of this segment.
     */
    public double getLength()
    {
        return _poRight.distance(_poLeft);
    }

    /**
     * Check if this segment points are equals to the other segment points.
     * 
     * @param other The other segment to be compared with.
     * @return true if this segment equals other.
     */
    public boolean equals(Segment1 other)
    {
        return other._poLeft.equals(_poLeft) && other._poRight.equals(_poRight);
    }

    /**
     * Check if this segment is above the other segment.
     * 
     * @param other The other segment to be compared with.
     * @return ture if this segment is above the other segment.
     */
    public boolean isAbove(Segment1 other)
    {
        return _poLeft.isAbove(other._poLeft);
    }

    /**
     * Check if this segment is under the other segment.
     * 
     * @param other The other segment to be compared with.
     * @return ture if this segment is under the other segment.
     */
    public boolean isUnder(Segment1 other)
    {
        return other.isAbove(this);
    }

    /**
     * Check if this segment's right point is left to the the other segment's left point.
     * 
     * @param other The other segment to be compared with.
     * @return ture if this segment's right point is left to the the other segment's left point.
     */
    public boolean isLeft(Segment1 other)
    { // if the right point's X are equals to the other left point's X, false will be returned (as required).
        return _poRight.isLeft(other._poLeft);
    }

    /**
     * Check if this segment's left point is right to the the other segment's right point.
     * 
     * @param other The other segment to be compared with.
     * @return ture if this segment's left point is right to the the other segment's right point.
     */
    public boolean isRight(Segment1 other)
    { // if the left point's X are equals to the other right point's X, true will be returned (*make sure that is not the case*).
        return !isLeft(other) && _poLeft.getX() != other._poRight.getX();
    }

    /**
     * Move this segment on the X-axis.
     * (if a part of this segment will be outside the first quadrant (I) after moving, this segment will stay in place without any change)
     * @param delta The distance to move.
     */
    public void moveHorizontal(double delta)
    {
        if (_poLeft.getX() + delta < 0) return; // Invalid movement, don't move!
        _poLeft.setX(_poLeft.getX() + delta);
        _poRight.setX(_poRight.getX() + delta);
    }

    /**
     * Move this segment on the Y-axis.
     * (if a part of this segment will be outside the first quadrant (I) after moving, this segment will stay in place without any change)
     * @param delta The distance to move.
     */
    public void moveVertical(double delta)
    {
        if (_poLeft.getY() + delta < 0) return; // Invalid movement, don't move!
        _poLeft.setY(_poLeft.getY() + delta);
        _poRight.setY(_poRight.getY() + delta);
    }

    /**
     * Change the size of this segment by moving the right point (on the X-axis).
     * (if resizing will cause the right point to be in the left to the left point, this segment will stay in place without any change)
     * @param delta Positive value to increase or negative value to decrease.
     */
    public void changeSize(double delta)
    {
        if (_poRight.getX() + delta < _poLeft.getX()) return; // Invalid resize, don't perform!
        _poRight.setX(_poRight.getX() + delta);
    }

    /**
     * Check if a point is located on the segment.
     * 
     * @param p The point to check.
     * @return true if the point is on thit segment.
     */
    public boolean pointOnSegment(Point p)
    {
        return p.getY() == _poLeft.getY() && p.getX() >= _poLeft.getX() && p.getX() <= _poRight.getX();
    }

    /**
     * Check if this segment is bigger than the other segment.
     * 
     * @param other The other segment to check.
     * @return true if this segment is bigger than the other segment.
     */
    public boolean isBigger(Segment1 other)
    {
        return getLength() > other.getLength();
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
    public double overlap(Segment1 other)
    {
        // The overlap is the minimum right x - the maximum left x.
        double overlap = min(_poRight.getX(), other.getPoRight().getX()) - max(_poLeft.getX(), other.getPoLeft().getX());
        // If the result is negative, there is no overlap and we should return 0.
        return overlap > 0 ? overlap : 0;
    }

    /**
     * Compute the trapeze perimeter, which is constructed by this segment and a reference segment.
     * 
     * @param other The reference segment
     * @return The trapeze perimeter
     */
    public double trapezePerimeter(Segment1 other)
    {
        return getLength()                      // Side A (This)
            + _poRight.distance(other._poRight) // Side B
            + other.getLength()                 // Side C
            + other._poLeft.distance(_poLeft);  // Side D
    }

    /**
     * Return a string representation of this segment in the format (3.0,4.0)---(3.0,6.0).
     * 
     * @return String representation of this segment
     */
    public String toString()
    {
        return _poLeft.toString() + "---" + _poRight.toString();
    }
}