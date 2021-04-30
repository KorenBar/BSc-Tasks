#include <stdio.h>
#include <string.h>

#define FIRST_UPPER_LETTER 'A'
#define LAST_UPPER_LETTER 'Z'
#define FIRST_LOWER_LETTER 'a'
#define LAST_LOWER_LETTER 'z'
#define FIRST_DIGIT '0'
#define LAST_DIGIT '9'

bool in_range(int x, int min, int max)
{
    return x >= min && x <= max;
}

bool is_digit(char c)
{ /* 48-57 according to the ascii table */
    return in_range(c, FIRST_DIGIT, LAST_DIGIT); 
}

bool is_upper(char c)
{ /* 65-90 according to the ascii table */
    return in_range(c, FIRST_UPPER_LETTER, LAST_UPPER_LETTER); 
}

bool is_lower(char c)
{ /* 97-122 according to the ascii table */
    return in_range(c, FIRST_LOWER_LETTER, LAST_LOWER_LETTER); 
}

char to_upper(char c)
{
    if (!is_lower(c)) return c; /* not a lower letter, nothing to do. */
    return FIRST_UPPER_LETTER + (c - FIRST_LOWER_LETTER);
}

char to_lower(char c)
{
    if (!is_upper(c)) return c; /* not an upper letter, nothing to do. */
    return FIRST_LOWER_LETTER + (c - FIRST_UPPER_LETTER);
}

bool is_alpha(char c)
{ /* 97-122 or 65-90 according to the ascii table */
    return is_upper(c) || is_lower(c);
}