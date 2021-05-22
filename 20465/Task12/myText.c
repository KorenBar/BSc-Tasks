/*
 * This program will get an unlimited input and will print all of it out in lines that are fixed in length
 */

#include <stdio.h>
#include <ctype.h>
#include "myText.h"

int main() {
    datatype dt = askForDatatype();
    void *buff = createDataStruct(dt);

    int exitCode = readText(dt, buff);
    if (buff != NULL) {
        printText(dt, buff);
        free_data(dt, buff);
    }

    printError(exitCode);
    return exitCode;
}

/*
 * returns an exit code:
 * 0 if EOF was received
 * 1 if can't allocate more memory
 */
int readText(datatype dataType, void *p)
{
    size_t arrLength;
    char c;
    char *last;
    if (!p) return 1;

    arrLength = BUFFER_SIZE; /* yea.. "what if isn't the real length?" - irrelevant since we was required to create the array on the main method and can't change the params of this method to get the length of it */
    last = p;
    *last = '\0'; /* no matter what, first empty the buffer */

    switch (dataType) {
        case LINKED_LIST:
            while ((c = (char)getchar()) != EOF)
                if (c != '\n' /*&& c != '\r'*/)
                    if (!put_to_list((linked_list*)p, c)) return 1; /* can't allocate more memory */
            break;
        case ARRAY:
        default:
            while ((c = (char)getchar()) != EOF)
                if (c != '\n' /*&& c != '\r'*/)
                {
                    if (isLastInArray(p, arrLength, last)) /* buffer is full, extend it */
                        if (!extendBuffer((char **)&p, &arrLength, BUFFER_SIZE)) return 1; /* can't allocate more memory */
                    *(last++) = c; /* save the char and then move next */
                    *last = '\0';
                }
            break;
    }

    return 0;
}

/*
 * prints out a data struct with fixed lines size
 */
void printText(datatype dataType, void *p)
{
    switch (dataType) {
        case LINKED_LIST:
            print_list((linked_list*)p, LINE_SIZE);
            break;
        case ARRAY:
        default:
            printString(p, LINE_SIZE);
            break;
    }
}

/*
 * prints out an error description for any possible error code
 */
void printError(int errorCode)
{
    switch (errorCode) {
        case 0: /* no error */
            break;
        case 1:
            printf("Error: Can't allocate memory. error code: %d\n", errorCode);
            break;
        default:
            printf("Unknown error code: %d\n", errorCode);
            break;
    }
}

/* get the next char from the standard input while skipping spaces */
static char getChar()
{
    char c;
    while (isspace(c = (char)getchar())); /* get the next char while skipping spaces */
    while (getchar() != '\n');          /* clear all until a new line */
    return c;
}

static datatype askForDatatype()
{
    datatype dt = -1;
    while (dt == -1)
    {
        printf("Choose which data struct you want to use and press ENTER:   A = Dynamic Array,  L = Linked List\n");
        dt = toDataType(getChar());
    }
    return dt;
}
