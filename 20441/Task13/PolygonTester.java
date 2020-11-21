/**
 * Tester for task 13 on course no. 20441 (2020)
 * 
 * @author Koren Bar
 * @version 2020.11.21-2
 */
public class PolygonTester {

    public static void main(String[] args)
    {
        final int ROUNGING_DECIMALS = 10; // no more than 15

        //#region Print information
        System.out.println("***                       טסטר זה אינו רשמי ואינו תחליף לטסטר הרשמי                         ***");
        System.out.println("***             This tester is not official and isn't substitute for the official              ***\n");

        System.out.println("*** This tester will not check your Point class and based on that your Point class is correct  ***\n");

        System.out.println("***                       Case of invalid vertices will not be tested                          ***");
        System.out.println("***          (vertices will be added in order and will not be equals to each other)            ***\n");
        //#endregion
        System.out.println("_______________________________________");

        //#region Fill the polygons arrays
        Polygon fullPoly = new Polygon();
        double[][] fullXYList = {
            new double[]{1, 6}, // A
            new double[]{3, 8}, // B
            new double[]{5, 8}, // C
            new double[]{7, 7}, // D
            new double[]{9, 5}, // E
            new double[]{9, 3}, // F
            new double[]{7, 2}, // G
            new double[]{5, 2}, // H
            new double[]{2, 3}, // I
            new double[]{1, 4}};// J

        int added = addPointsToPolygon(fullPoly, fullXYList);
        checkCorrectness("Polygon.addVertex", 10, added, String.format("Only %d of %d vertices was added, CHECK THAT METHOD.", added, fullXYList.length));
        checkCorrectness("Polygon.addVertex", false, fullPoly.addVertex(0, 5), String.format("Was able to add more than %d vertices, CHECK THAT METHOD.", added, fullXYList.length));

        Polygon polyWith1 = new Polygon();
        double[][] p1XYList = {
            new double[]{3, 9}};// A
        addPointsToPolygon(polyWith1, p1XYList);

        Polygon polyWith2 = new Polygon();
        double[][] p2XYList = {
            new double[]{2, 5}, // A
            new double[]{3, 2}};// B
        addPointsToPolygon(polyWith2, p2XYList);

        Polygon polyWith3 = new Polygon();
        double[][] p3XYList = {
            new double[]{3, 3}, // A
            new double[]{3, 7}, // B
            new double[]{1, 6}};// C
        addPointsToPolygon(polyWith3, p3XYList);

        Polygon polyWith6 = new Polygon();
        double[][] p6XYList = {
            new double[]{6, 5}, // A
            new double[]{5, 8}, // B
            new double[]{3, 8}, // C
            new double[]{1, 6}, // D
            new double[]{2, 5}, // E
            new double[]{4, 4}};// F
        addPointsToPolygon(polyWith6, p6XYList);

        Polygon emptyPoly = new Polygon();
        //#endregion
        
        //#region Check methods
        // toString
        checkCorrectness("Polygon.toString", "The polygon has 5 vertices:\n((6.0,5.0),(5.0,7.0),(3.0,8.0),(1.0,6.0),(2.0,5.0),(4.0,4.0))", polyWith6.toString(), "toString method is incorrect, FIX IT.");
        checkCorrectness("Polygon.toString", "The polygon has 0 vertices.", emptyPoly.toString(), "toString method is incorrect, FIX IT.");

        // highestVertex
        String errTip = "If your Point.equals is correct then check the highestVertex method!";
        checkCorrectness("Polygon.highestVertex", new Point(3, 8), fullPoly.highestVertex(), errTip);
        checkCorrectness("Polygon.highestVertex", new Point(3, 7), polyWith3.highestVertex(), errTip);
        checkCorrectness("Polygon.highestVertex", new Point(5, 8), polyWith6.highestVertex(), errTip);

        // calcPerimeter
        errTip = "CHECK THAT METHOD!";
        checkCorrectness("Polygon.calcPerimeter", round(22.705481427033433, ROUNGING_DECIMALS), round(fullPoly.calcPerimeter(), ROUNGING_DECIMALS), errTip);
        checkCorrectness("Polygon.calcPerimeter", round(9.84161925296378, ROUNGING_DECIMALS), round(polyWith3.calcPerimeter(), ROUNGING_DECIMALS), errTip);
        checkCorrectness("Polygon.calcPerimeter", round(13.877054302287245, ROUNGING_DECIMALS), round(polyWith6.calcPerimeter(), ROUNGING_DECIMALS), errTip);

        // calcArea
        errTip = "CHECK THAT METHOD!";
        checkCorrectness("Polygon.calcArea", 37.0, fullPoly.calcArea(), errTip);
        checkCorrectness("Polygon.calcArea", 4.0, polyWith3.calcArea(), errTip);
        checkCorrectness("Polygon.calcArea", 13.0, polyWith6.calcArea(), errTip);

        // isBigger
        errTip = "CHECK THAT METHOD!";
        checkCorrectness("Polygon.isBigger", true, fullPoly.isBigger(polyWith6), errTip);
        checkCorrectness("Polygon.isBigger", false, polyWith3.isBigger(polyWith6), errTip);
        checkCorrectness("Polygon.isBigger", true, polyWith6.isBigger(polyWith3), errTip);

        // findVertex
        errTip = "If your Point.equals is correct then check the findVertex method!";
        checkCorrectness("Polygon.findVertex", 7, fullPoly.findVertex(new Point(5, 2)), errTip);
        checkCorrectness("Polygon.findVertex", -1, polyWith3.findVertex(new Point(0, 0)), errTip);
        checkCorrectness("Polygon.findVertex", 5, polyWith6.findVertex(new Point(4, 4)), errTip);

        // getNextVertex
        errTip = "If your Point.equals is correct then check the getNextVertex method!";
        checkCorrectness("Polygon.getNextVertex", new Point(2, 3), fullPoly.getNextVertex(new Point(5, 2)), errTip);
        checkCorrectness("Polygon.getNextVertex", null, polyWith3.getNextVertex(new Point(0, 0)), errTip);
        checkCorrectness("Polygon.getNextVertex", new Point(6, 5), polyWith6.getNextVertex(new Point(4, 4)), errTip);
        checkCorrectness("Polygon.getNextVertex", new Point(3, 9), polyWith1.getNextVertex(new Point(3, 9)), errTip);
    
        // getBoundingBox
        errTip = "If the Polygon.toString mehtod is correct then check the getBoundingBox method!"
            + "\nnote that you have to find the most outer vertex from each side and use their X and Y to create a new polygon with 4 vertices in the required order. (BottomLeft, BottomRight, TopRight, TopLeft)";
        checkCorrectness("Polygon.getBoundingBox", "The polygon has 4 vertices:\n((1.0,2.0),(9.0,2.0),(9.0,8.0),(1.0,8.0))", fullPoly.getBoundingBox().toString(), errTip);
        checkCorrectness("Polygon.getBoundingBox", "The polygon has 4 vertices:\n((1.0,3.0),(3.0,3.0),(3.0,7.0),(1.0,7.0))", polyWith3.getBoundingBox().toString(), errTip);
        checkCorrectness("Polygon.getBoundingBox", "The polygon has 4 vertices:\n((1.0,4.0),(6.0,4.0),(6.0,8.0),(1.0,8.0))", polyWith6.getBoundingBox().toString(), errTip);
        checkCorrectness("Polygon.getBoundingBox", null, polyWith2.getBoundingBox(), errTip);
        checkCorrectness("Polygon.getBoundingBox", null, polyWith1.getBoundingBox(), errTip);
        //#endregion

        //#region Check aliasing
        boolean wasNotCloned = polyWith6.highestVertex() == polyWith6.highestVertex() || polyWith3.highestVertex() == polyWith3.highestVertex() || fullPoly.highestVertex() == fullPoly.highestVertex();
        checkCorrectness("Polygon.highestVertex aliasing", false, wasNotCloned, "Alias was returned, USE THE COPY CONSTRUCTOR OF THE POINT!");

        Point p = polyWith6.highestVertex();
        wasNotCloned = polyWith6.getNextVertex(p) == polyWith6.getNextVertex(p) || polyWith3.getNextVertex(p) == polyWith3.getNextVertex(p) || fullPoly.getNextVertex(p) == fullPoly.getNextVertex(p);
        checkCorrectness("Polygon.getNextVertex aliasing", false, wasNotCloned, "Alias was returned, USE THE COPY CONSTRUCTOR OF THE POINT!");

        wasNotCloned = polyWith6.getBoundingBox() == polyWith6.getBoundingBox() || polyWith3.getBoundingBox() == polyWith3.getBoundingBox() || fullPoly.getBoundingBox() == fullPoly.getBoundingBox();
        checkCorrectness("Polygon.getBoundingBox aliasing", false, wasNotCloned, "The same object was returned, YOU HAVE TO RE-CREATE THE BOUNDING BOX!");
        //#endregion

        // Was error(s) ?
        if (!wasError) System.out.println("Well done! No errors was found ...");
        System.out.println("_______________________________________");
    }

    static boolean wasError = false;
    public static boolean checkCorrectness(String testName, Object expected, Object actual, String theTip)
    {
        boolean isCorrect = expected == actual || expected != null && expected.equals(actual);

        if (!isCorrect) // incorrect
        {
            wasError = true;
            System.out.println(String.format("ERROR:  %s  -  EXPECTED: %s  ACTUAL: %s", testName, expected != null ? expected.toString() : "null", actual != null ? actual.toString() : "null"));
            if (!theTip.isEmpty()) System.out.println("TIP: " + theTip);
            System.out.println();
        }
        return isCorrect;
    }

    public static int addPointsToPolygon(Polygon polygon, double[]... xyArray)
    {
        int added = 0;

        for (double[] xy : xyArray) {
            if (polygon.addVertex(xy[0], xy[1])) added++;
            else break;
        }

        return added;
    }

    public static double round(double num, int decimals)
    {   // Separating the integer and the decimal in order to prevent variables size limitation problems.
        double template = Math.pow(10, decimals);
        int integer = (int)num; // The integer
        double rest = num - integer; // The rest of the number
        double roundedRest = Math.round(rest * template) / template;

        return integer + roundedRest;
    }
}
