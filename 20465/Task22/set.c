#include "set.h"

set new_set()
{
    set res = calloc(SET_SIZE, 1);
    if (res == NULL)
        printf("ERROR: failed to allocate a new set.");
    return res;
}

set read_set(int* values, int count)
{ /* assuming that values are valid (0-127) */
    int i;
    byte mask, block_num;
    set res;

    res = new_set();
    if (res == NULL) return NULL;

    for (i = 0; i < count; i++)
    {
        mask = 1 << (values[i] % 8);
        block_num = values[i] / 8;
        if (block_num < SET_SIZE)
            res[block_num] |= mask;
    }

    return res;
}

void print_set(set s)
{
    int i, j;
    bool first = true;
    byte mask, val;

    if (is_empty_set(s))
    {
        printf("The set is empty\n");
        return;
    }

    for(i = 0; i < SET_SIZE; i++)
        for (mask = 1, j = 0; mask; mask <<= 1, j++)
        {
            if (s[i]&mask)
            {
                val = i * 8 + j;
                if (!first) printf(", ");
                printf("%d", val);
                first = false;
            }
        }

    printf("\n");
}

byte operate_bytes(byte s1, byte s2, operation op)
{
    switch (op) {
        case UNION:
            return s1 | s2;
        case INTERSECT:
            return s1 & s2;
        case SUB:
            return s1 & ~s2;
        case SYMDIFF:
            return s1 ^ s2;
        default:
            return 0x00;
    }
}

set operate_sets(set s1, set s2, operation op)
{
    int i;
    set res;

    res = new_set();
    if (res == NULL) return NULL;

    for (i = 0; i < SET_SIZE; i++)
        res[i] = operate_bytes(s1[i], s2[i], op);

    return res;
}

set union_set(set s1, set s2)
{
    return operate_sets(s1, s2, UNION);
}

set intersect_set(set s1, set s2)
{
    return operate_sets(s1, s2, INTERSECT);

}

set sub_set(set s1, set s2)
{
    return operate_sets(s1, s2, SUB);

}

set symdiff_set(set s1, set s2)
{
    return operate_sets(s1, s2, SYMDIFF);

}

bool is_empty_set(set s)
{
    int i;
    for (i = 0; i < SET_SIZE; i++)
        if (s[i]) return false;
    return true;
}

void free_set(set set)
{
    free(set);
}