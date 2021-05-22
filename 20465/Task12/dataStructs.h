/*
 * This file contains the dynamic struct types and declaration of methods that will do operations on them
 */

#include <stdio.h>
#include <stdbool.h> /* instead of using 1 and 0 we'll write true and false. */

#define BUFFER_SIZE 64

typedef struct node node;
struct node {
    char arr[BUFFER_SIZE];
    node *next;
};

typedef struct {
    node *head;
    node *last; /* instead of using a recursive function to add a new node */
} linked_list;

typedef enum { ARRAY, LINKED_LIST } datatype; /* our dynamic data types */

datatype toDataType(char); /* convert a char to a datatype enum value */
bool add_node(linked_list*); /* returns true if succeeded to create a new node or false if can't allocate more memory */
void free_data(datatype, void *); /* free any type of data struct */
void free_list(linked_list*); /* free each node from a linked list */
bool put_to_list(linked_list*, char); /* returns true if succeeded or false if can't allocate more memory */
/*
 * print a linked list data out with a specific lines length
 * if the line length is 0 or less than all of the data will be printed as a single line
 */
void print_list(linked_list*, int);
/*
 * allocate more memory for an existing string
 * returns true if succeeded or false if can't allocate memory
 */
bool extendBuffer(char** arr, size_t *currLength, int charsToAdd); /* size_t = unsigned long */
bool isLastInArray(char* theArray, size_t length, char* theChar); /* check whether a char is the last in the string */
void * createDataStruct(datatype type); /* allocate a memory for a new data struct */
void printString(char *arr, int line_len);
