#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdarg.h>
#include <stdbool.h>
#include <ctype.h>

#define BUFFER_SIZE_INTERVAL 64

typedef char* string;
typedef unsigned short ushort;
typedef unsigned long ulong;

typedef struct {
    string data;
    ulong size; /* capacity */
    ulong length;
} dyBuffer;

/* init a new dynamic buffer */
dyBuffer *new_buffer();
void clear_buffer(dyBuffer* dbuff);
void free_buffer(dyBuffer* dbuff);
/* add data to the buffer and extend it as needed */
bool add2buffer(dyBuffer* dbuff, string data, ulong count);
/* put a single char to a buffer */
bool put2buffer(dyBuffer* dbuff, char c);
/* returns -1 if the buffer was not allocated yet */
long buffer_memory_left(dyBuffer* dbuff);
bool resize_buffer(dyBuffer* dbuff, ulong free_necessary);
bool extend_buffer(dyBuffer* dbuff, ulong free_necessary);
bool cut_buffer(dyBuffer* dbuff);
/* returns a copy of the buffer data as string */
string buffer2string(dyBuffer* dbuff);
