#include <stdio.h>
#include <stdlib.h>
#include <stdarg.h>
#include <stdbool.h>

/* 16byte*8bit=128bit */
#define SET_SIZE 16
#define MAX_SET_VALUE 127
#define MULTI_SET_COUNT 3
#define SINGLE_SET_COUNT 1

typedef enum {
    READ,
    PRINT,
    UNION,
    INTERSECT,
    SUB,
    SYMDIFF,
    STOP } operation;

typedef unsigned char byte; /* not sure if I can use rpcndr.h, so I'm writing it myself */
typedef byte *set;

/* init a new set */
set new_set();
void free_set(set set);

set read_set(int* values, int count);
void print_set(set s);
set union_set(set s1, set s2);
set intersect_set(set s1, set s2);
set sub_set(set s1, set s2);
set symdiff_set(set s1, set s2);

bool is_empty_set(set s);
