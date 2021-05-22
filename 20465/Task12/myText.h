/*
 * This header file of our program
 * contains declaration for the required methods
 */

#include "dataStructs.h"

#define LINE_SIZE 60

int readText(datatype, void*); /* read an unlimited input using a dynamic data struct until receiving an EOF */
void printText(datatype, void*); /* print out all of the data from a dynamic data struct */
void printError(int); /* print out an error code as a readable message */
static char getChar(); /* read the next char that is not a space from the standard input and then go to the next line */
static datatype askForDatatype(); /* wait for a valid datatype input from the user */
