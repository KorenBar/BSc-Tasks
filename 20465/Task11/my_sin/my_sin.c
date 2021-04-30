#include <stdio.h>
#include <stdbool.h> /* instead of using 1 and 0 we'll write true and false. */
#include <math.h>

#define DECIMAL_PRECISION 0.000001

double my_sin(double);
double recursive_sin(double, int, double, double, bool);

int main()
{
    double value, mysin, realsin;

    printf("Please insert a value, I beg you :\n"); /* hope it's kind enough ;) */

    while (scanf("%lf", &value) != 1)
        printf("Not valid value, please insert a valid float value:");
    
    mysin = my_sin(value);
    realsin = sin(value);

    printf("My sin is: %f, Real sin is: %f", mysin, realsin);
    if ((int)mysin != (int)realsin)
        printf("\tERROR!");
    printf("\n"); /* new line */

    return 0;
}

double my_sin(double x)
{
    return recursive_sin(x, 1, x, 1, false); /* with the first index parameters */
}

double recursive_sin(double x, int i, double pow, double facto, bool negative)
{ /* note that double can represent a value that much mcuh bigger than integer */
    double res = pow/facto;
    if (negative) res *= -1;
    /* printf("x: %f, i: %d, pow: %f, facto: %f, res: %f\n", x, i, pow, facto, res); // for debuging */
    if ((res >= 0 && res < DECIMAL_PRECISION) || (res < 0 && res > -DECIMAL_PRECISION)) return 0; /* Base case - that precise enough ! */

    i += 2;
    pow *= x * x; /* x^2 */
    facto *= (i - 1) * i;
    return res + recursive_sin(x, i, pow, facto, !negative);
}