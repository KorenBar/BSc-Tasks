#include <string.h>
#include <ctype.h>
#include "strhelper.h"

char *ltrim_pointer(char *s)
{
    while(isspace(*s)) s++;
    return s;
}

char *ltrim(char *s)
{
    char *lp, *dist;
    lp = ltrim_pointer(s);
    dist = (char *)(lp - s);
    for (; *(lp) != '\0'; lp++) *(char *)(lp - dist) = *lp;
    *(char *)(lp - dist) = '\0';
    return s;
}

char *rtrim(char *s)
{
    char* back = s + strlen(s);
    while(isspace(*--back));
    *(back+1) = '\0';
    return s;
}

char *trim(char *s)
{
    return ltrim(rtrim(s));
}
