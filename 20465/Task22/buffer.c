#include "buffer.h"

dyBuffer *new_buffer()
{
    dyBuffer* dbuff = malloc(sizeof(dyBuffer));

    if (dbuff == NULL) {
        printf("ERROR: failed to allocate a new buffer.");
        return NULL;
    }

    dbuff->data = NULL;
    dbuff->size = 0;
    dbuff->length = 0;

    return dbuff;
}

void clear_buffer(dyBuffer* dbuff)
{
    dbuff->length = 0;
    if (dbuff->data != NULL)
        dbuff->data[0] = '\0';
}

void free_buffer(dyBuffer* dbuff)
{
    if (dbuff == NULL) return;
    free(dbuff->data);
    free(dbuff);
}

string buffer2string(dyBuffer* dbuff)
{
    string res = malloc((dbuff->length + 1) * sizeof(char));

    if (res == NULL)
        return NULL;

    if (dbuff->length)
        strcpy(res, dbuff->data);
    else
        res[0] = '\0';

    return res;
}

bool add2buffer(dyBuffer* dbuff, string data, ulong count)
{
    ulong i;

    long test = buffer_memory_left(dbuff);
    if (test < (long)count) /* missing space, extend the buffer */
        if (!extend_buffer(dbuff, count))
            return false; /* failed to extend buffer */

    for (i = 0; i < count; i++)
        dbuff->data[dbuff->length + i] = data[i];

    dbuff->data[dbuff->length + count] = '\0';
    dbuff->length += count;

    return true;
}

bool put2buffer(dyBuffer* dbuff, char c)
{
    return add2buffer(dbuff, (string)&c, 1);
}

long buffer_memory_left(dyBuffer* dbuff)
{
    return dbuff->size - dbuff->length - 1;
}

bool resize_buffer(dyBuffer* dbuff, ulong free_necessary)
{
    string tmpStr;

    free_necessary -= buffer_memory_left(dbuff);

    tmpStr = realloc(dbuff->data, (dbuff->size + free_necessary) * sizeof(char));
    if (tmpStr == NULL)
    {
        printf("ERROR: failed to reallocate buffer.");
        return false;
    }

    dbuff->data = tmpStr;
    dbuff->size += free_necessary;

    return true;
}

bool extend_buffer(dyBuffer* dbuff, ulong free_necessary)
{
    if (free_necessary < BUFFER_SIZE_INTERVAL)
        free_necessary = BUFFER_SIZE_INTERVAL;
    return resize_buffer(dbuff, free_necessary);
}

bool cut_buffer(dyBuffer* dbuff)
{
    return resize_buffer(dbuff, 0);
}
