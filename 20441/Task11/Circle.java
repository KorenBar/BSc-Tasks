import java.util.Scanner;

/**
 * This class will get two points that depicting a rectangle and will print out the inner and outer circles details.
 *
 * @author Koren Bar 318810595
 * @version 23.10.2020
 */
public class Circle
{
    /** Entry point of the program */
    public static void main (String [] args)
    {
        Scanner scan = new Scanner (System.in);
        System.out.println ("This program calculates the areas " +
            "and the perimeters of the excircle and the incircle " +
            "of a given rectangle ");
        
        // Get inputs to variables for the left-upper point
        System.out.println ("Please enter the two coordinates of the left-upper point of the rectangle");
        int leftUpX = scan.nextInt();
        int leftUpY = scan.nextInt();
        
        // My additional code starts here:

        // Get inputs to variables for the right-bottom point
        System.out.println ("Please enter the two coordinates of the right-bottom point of the rectangle");
        int rightBottomX = scan.nextInt();
        int rightBottomY = scan.nextInt();
        
        // The diameter of the incircle is the smaller rib.
        // getting it with Math.min (note that we have to use Math.abs to get the absolute rib length)
        double incircleDiameter = Math.min(Math.abs(rightBottomX - leftUpX), Math.abs(rightBottomY - leftUpY));
        // The diameter of the excircle is the distance between left-upper and right-bottom. (Pythagorean theorem)
        double excircleDiameter = Math.sqrt(Math.pow((leftUpX - rightBottomX), 2) + Math.pow(leftUpY - rightBottomY, 2));

        // Get radiuses of both circles
        double incircleRadius = incircleDiameter / 2, excircleRadius = excircleDiameter / 2;

        // Calculate areas
        double incircleArea = Math.pow(incircleRadius, 2) * Math.PI;
        double excircleArea = Math.pow(excircleRadius, 2) * Math.PI;

        // Calculate perimeters
        double incirclePerimeter = incircleRadius * Math.PI * 2;
        double excirclePerimeter = excircleRadius * Math.PI * 2;

        // Printing out
        System.out.println("Incircle: radius = " + incircleRadius + ", area = " + incircleArea + ", perimeter = " + incirclePerimeter);
        System.out.println("Excircle: radius = " + excircleRadius + ", area = " + excircleArea + ", perimeter = " + excirclePerimeter);

        scan.close();
    } // end of method main
} //end of class Circle