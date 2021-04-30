/**
 * Represents a polygon by an array of vertices.
 * @author Koren Bar 318810595
 * @version 11.12.2020
 */
public class Polygon { // I love concise and readable code and I didn't find a reason to use the while loop somewhere.
    
    private final int MAX_VERTICES = 10; // Will be the size of the array.
    private final int MIN_VERTICES_CALC = 3; // For considering how to calculate.

    private Point [] _vertices;
    private int _noOfVertices;

    /**
     * Create a new instance of Polygon.
     */
    public Polygon()
    {
        _vertices = new Point [MAX_VERTICES];
        _noOfVertices = 0;
    }

    /**
     * Add a vertex point to this polygon. (make sure it is a valid vertex that doesn't exists already)
     * @param x X coordinate of the new vertex point.
     * @param y Y coordinate of the new vertex point.
     * @return true if the new vertex was added or false if the array is full.
     */
    public boolean addVertex(double x, double y)
    {
        if (_noOfVertices == MAX_VERTICES) return false; // the array is full.

        // add a new point to the end of the array and then add 1 to the counter.
        _vertices[_noOfVertices++] = new Point(x, y);
        return true;
    }

    /**
     * Returns the highest vertex on this polygon or null if there is no vertices.
     * @return The first vertex with the greatest Y coordinate or null if there is no vertices.
     */
    public Point highestVertex()
    {
        return getExtremeVertex("TOP");
    }

    // Writing this private method to prevent a lot of code duplication.
    // enum could be great here as parameter! (using string instead)
    private Point getExtremeVertex(String location)
    {
        if (_noOfVertices == 0) return null; // there is no vertices, return null.

        Point extremePoint = _vertices[0]; // Start from the first vertex and replace it when a higher one was found (DON'T REPLACE IF EQUALS).
        for(int i = 1; i < _noOfVertices; i++) // Start the loop from the second one (if exists).
            if (isInLocation(location, _vertices[i], extremePoint)) // The current vertex is the new extreme one.
                extremePoint = _vertices[i];

        return new Point(extremePoint);
    }

    /**
     * Check the location of one vertex to the other.
     * @param location The location of vertex1 relative to vertex2 (string instead of enum)
     * @param vertex1 The vertex on the location.
     * @param vertex2 The vertex to check from.
     * @return true if vertex1 is {location} to vertex2, otherwise false.
     */
    private static boolean isInLocation(String location, Point vertex1, Point vertex2)
    { // *Assumption: All of these check methods will NOT return true in case of equal.
        switch (location)
        {   // on each case I will check the relevant condition.
            case "TOP": return vertex1.isAbove(vertex2);
            case "BOTTOM": return vertex1.isUnder(vertex2);
            case "LEFT": return vertex1.isLeft(vertex2);
            case "RIGHT": return vertex1.isRight(vertex2);
            default: // Unknown location
                return false;
        }
    }

    /**
     * Calculate the perimeter of this polygon.
     * @return The perimeter of this polygon, if there are only two vertices - the distance between them will be returned, if there is less than 2 vertices - 0 will be returned.
     */
    public double calcPerimeter()
    {
        if (_noOfVertices < 2) return 0; // Less than 2 vertices, return 0.

        // At least 2 vertices, we can continue.
        double perimeter = _vertices[0].distance(_vertices[_noOfVertices - 1]); // The distance between the last and first vertices.

        if (_noOfVertices < MIN_VERTICES_CALC) return perimeter; // There are only two vertices, return the distance. (remove this line to return the distance back and forth)

        for (int i = 1; i < _noOfVertices; i++) // Start the loop from the second vertex.
            perimeter += _vertices[i].distance(_vertices[i - 1]); // Add the distance between the vertex[i] and the one before it.

        return perimeter;
    }

    // Calculate a triangular by 3 vertices points (a,b,c).
    private static double calcTriangularArea(Point a, Point b, Point c)
    { // Using the Heron’s formula.
        double ab = a.distance(b), bc = b.distance(c), ac = a.distance(c);  // the sides
        double s = (ab + bc + ac) / 2;                                      // semi perimeter
        return Math.sqrt(s*(s-ab)*(s-bc)*(s-ac));                           // the formula
    }

    /**
     * Calculate the area of this polygon.
     * @return The area of this polygon or 0 if there are less than 3 vertices.
     */
    public double calcArea()
    {
        if (_noOfVertices < MIN_VERTICES_CALC) return 0; // Less than 3 vertices, return 0.
        
        double area = 0;
        // Divide this polygon to triangulars while the first vertex (A) is the common one.
        for (int i = 1; i < _noOfVertices - 1; i++) // Start the loop from the second vertex to one before the last.
            area += calcTriangularArea(_vertices[0], _vertices[i], _vertices[i + 1]); // sum it to the polygon area variable.

        return area;
    }
    
    /**
     * Check if this polygon area is bigger than another polygon area.
     * @param other The another polygon to compare with.
     * @return true if this polygon is bigger or false if the other one is bigger.
     */
    public boolean isBigger(Polygon other)
    {
        return this.calcArea() > other.calcArea();
    }

    /**
     * Search for vertex and get his index on the array. (-1 will be returned if not exists)
     * @param p The vertex to search for.
     * @return The index of a given vertex.
     */
    public int findVertex(Point p)
    {
        // Search for the first vertex that equals to this point parameter.
        for(int i = 0; i < _noOfVertices; i++)
            if (_vertices[i].equals(p)) // The vertex on that index equals to the given point.
                return i; // Return the index.

        return -1; // Was not found, return the default value.
    }

    /**
     * Get the vertex after a given vertex. (if there is only a single vertex on this polygon and it is equals to the given one, it will be returned)
     * @param p The vertex preceding the required vertex.
     * @return The vertex that located after the given one or null if the given vertex does not exists.
     */
    public Point getNextVertex(Point p)
    {
        for (int i = 0; i < _noOfVertices; i++)                                     // Search for this vertex point.
            if (_vertices[i].equals(p))                                             // The vertex was found:
                if (i < _noOfVertices - 1)                                          // It's not the last vertex:
                    return new Point(_vertices[i + 1]);                             // Return the next vertex.
                else return new Point(_vertices[0]);                                // It's the last vertex: Return the first vertex.
                //return new Point(_vertices[i < _noOfVertices - 1 ? i + 1 : 0]);   // looks better as a single line.

        return null; // default result (if does not exists)
    }

    /**
     * Create a new polygon that represents a square that bounding this polygon.
     * @return a polygon with 4 vertices that represents a square that bounding this polygon.
     */
    public Polygon getBoundingBox()
    {
        if (_noOfVertices < MIN_VERTICES_CALC) return null; // There are less than 3 vertices, return null.

        // Get the extreme vertices points.
        Point leftPoint = getExtremeVertex("LEFT"), 
              rightPoint = getExtremeVertex("RIGHT"), 
              topPoint = getExtremeVertex("TOP"), 
              bottomPoint = getExtremeVertex("BOTTOM");

        Polygon bBox = new Polygon();
        bBox.addVertex(leftPoint.getX(), bottomPoint.getY());   // BottomLeft
        bBox.addVertex(rightPoint.getX(), bottomPoint.getY());  // BottomRight
        bBox.addVertex(rightPoint.getX(), topPoint.getY());     // TopRight
        bBox.addVertex(leftPoint.getX(), topPoint.getY());      // TopLeft

        return bBox;
    }

    /**
     * String representation of this polygon.
     * @return a string that represents this polygon
     */
    public String toString()
    {
        String result = "";
        
        // Append all of the vertices:
        for (int i = 0; i < _noOfVertices; i++)
        {
            if (i > 0) result += ','; // Append ',' if it's not the first vertex.
            result += _vertices[i].toString(); // Append the string representation of vertex[i].
        }

        return result == ""
            ? "The polygon has 0 vertices."                         // No result (_noOfVertices == 0)
            : "The polygon has " + _noOfVertices + " vertices:\n"   // There is result, we have to return a string with 2 lines.
                + '(' + result + ')';                               // Adding brackets to the result.
    }
}
