/*
 * This file implementing the method that was declared on the header file
 */

#include <stdio.h>
#include <stdlib.h>
#include "dataStructs.h"

datatype toDataType(char c)
{
    switch (c) {
        case 'a':
        case 'A':
            return ARRAY;
        case 'l':
        case 'L':
            return LINKED_LIST;
        default:
            return (datatype)-1;
    }
}

bool add_node(linked_list *ll)
{
    node *b = (node *)malloc(sizeof(node));
    if (!b) return false; /* memory allocation failed */

    b->arr[0] = '\0';
    b->next = NULL;

    if (!ll->last) ll->head = b; /* the first node */
    else ll->last->next = b;
    ll->last = b;

    return true;
}

bool put_to_list(linked_list *ll, char c)
{
    char *p;
    
    if (!(ll->last)) /* was not created yet*/
        if (!add_node(ll)) return false; /* can't allocate memory */

    for (p = ll->last->arr;
        *p != '\0'  /* search for the null on this node array */
        && p < ll->last->arr + (BUFFER_SIZE - 1) -1; /* -1 cuz we don't want to get the null char if it's the last one */
        p++);

    if (*p != '\0') /* node is full or the null doesn't exists*/
    { /* create a new node */
        if (!add_node(ll)) return false; /* can't allocate memory */
        p = ll->last->arr;
    }

    *p = c;
    p[1] = '\0';

    return true;
}

void print_list(linked_list *ll, int line_len) { /* we're assuming here that linked list is valid */
    int lineCounter = 0;
    node *n = ll->head;
    char *c = n->arr;

    while (n != NULL) {
        if (*c == '\0' || c == (n->arr + BUFFER_SIZE - 1)) { /* end of the current node array, move to the next node */
            n = n->next;
            c = n->arr; /* it's ok even if n is null now, cuz we'll not read or write the content of this pointer */
        } else { /* print the next char and move on */

            if (lineCounter == line_len) { /* add a new line and reset the counter */
                printf("\n");
                lineCounter = 0;
            }

            printf("%c", *c);
            c++; /* if the current node doesn't have a null somewhere we'll get out of range */
            lineCounter++;
        }
    }
}

void free_data(datatype dt, void *p)
{
    switch (dt) {
        case LINKED_LIST:
            free_list((linked_list *)p);
            break;
        case ARRAY:
        default:
            free(p);
            break;
    }
}

void free_list(linked_list *ll)
{
    node *tmp, *p = ll->head;
    while(p != NULL)
    {
        tmp = p;
        p = p->next;
        free(tmp);
    }
    free(ll);
}

bool extendBuffer(char **arr, size_t *currLength, int charCountToAdd)
{
    size_t newSize = (*currLength + charCountToAdd) * sizeof(char);
    char *res = (char *)realloc(*arr, newSize);
    if(res == NULL) return false; /* can't allocate more memory */
    *arr = res;
    *currLength += charCountToAdd;
    return true; /* succeeded */
}

bool isLastInArray(char* arr, size_t length, char* c)
{
    return c == arr + length - 1;
}

void * createDataStruct(datatype type)
{
    if (type == LINKED_LIST) return calloc(1, sizeof(linked_list));
    return calloc(BUFFER_SIZE, sizeof(char)); /* char array as default */
}

void printString(char *arr, int line_len)
{
    char *p;
    int lineSeq = 0;
    for(p = arr; *p != '\0'; p++) /* while string not ended */
    {
        if (lineSeq == line_len)
        { /* add a new line */
            lineSeq = 0;
            printf("\n");
        }
        printf("%c", *p);
        lineSeq++;
    }
}
