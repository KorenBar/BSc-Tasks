import java.util.Scanner;

/**
 * This class will read temperature unit and degrees and will print out the temperature as each unit.
 *
 * @author Koren Bar 318810595
 * @version 23.10.2020
 */
public class Temperature
{
    // Finals 
    // named as their meaning according to Wikipedia 
    // (https://en.wikipedia.org/wiki/Conversion_of_units_of_temperature)
    final static double ABSOLUTE_ZERO_C = -273.15;
    final static double ICE_MELTS_F = 32.0;
    final static double F_C_RATIO = 1.8; // equals 9/5 (the number of degrees Fahrenheit in each degree Celsius)

    /** Entry point of the program */
    public static void main(String[] args)
    {
        // Create instance of Scanner
        Scanner reader = new Scanner(System.in);

        // Read temperature unit and degrees input from user
        System.out.println("Insert the temperature unit and degrees :");
        String readedWord = reader.next();
        char unitChar = readedWord.charAt(0);
        double degrees = reader.nextDouble();
        reader.close();

        // Calculating temperatures
        double celsiusDegrees, fahrenheitDegrees, kelvinDegrees;

        switch(unitChar)
        {
            case 'c':
            case 'C':
                // The inserted value is Celsius degrees
                celsiusDegrees = degrees; // Celsius
                fahrenheitDegrees = degrees * F_C_RATIO + ICE_MELTS_F; // Celsius to Fahrenheit
                kelvinDegrees = degrees - ABSOLUTE_ZERO_C; // Celsius to Kelvin
                break;
            case 'f':
            case 'F':
                // The inserted value is Fahrenheit degrees
                celsiusDegrees = (degrees - ICE_MELTS_F) * (1 / F_C_RATIO); // Fahrenheit to Celsius
                fahrenheitDegrees = degrees; // Fahrenheit
                kelvinDegrees = celsiusDegrees - ABSOLUTE_ZERO_C; // Fahrenheit to Kelvin
                break;
            case 'k':
            case 'K':
                // The inserted value is Kelvin degrees
                celsiusDegrees = degrees + ABSOLUTE_ZERO_C; // Kelvin to Celsius
                fahrenheitDegrees = celsiusDegrees * F_C_RATIO + ICE_MELTS_F; // Kelvin to Fahrenheit
                kelvinDegrees = degrees; // Kelvin
                break;
            default:
                System.out.println("Error: Invalid unit inserted!");
                return; // Will not continue to print the empty results (otherwise exception will be thrown)
        }

        // Printing out the results
        System.out.println(celsiusDegrees + " C");
        System.out.println(fahrenheitDegrees + " F");
        System.out.println(kelvinDegrees + " K");
    }
}