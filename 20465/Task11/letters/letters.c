#include <stdio.h>
#include <stdbool.h> /* instead of using 1 and 0 we'll write true and false. */
#include <ctype.h>

/* spacial characters */
#define SENTENCE_END '.'
#define APOSTROPHE '\"'

int main()
{
    int c; /* int cuz it could be EOF (-1) */
    bool inQuote = false; /* inside apostrophe "" */
    bool sentenceStarted = false;

    printf("Write your text here and I will fix it for you:\n");

    while((c = getchar()) != EOF)
    { /* we got a character */
        switch (c)
        {
            case APOSTROPHE: /* quote was started or ended */
                inQuote = !inQuote;
                break;
            case SENTENCE_END: 
                if (!inQuote)  /* otherwise it doesn't matter */
                    sentenceStarted = false;
                break;
            default: /* not a spacial character */
                /* tolower and toupper will not effect any character other than a letter */
                /* upper case inside  a quote or      the first character of a sentence */
                /* lower case outside a quote and not the first character of a sentence */
                c = !inQuote && sentenceStarted ? tolower(c) : toupper(c);
                if (!isspace(c)) sentenceStarted = true; /* unless is type of a space, a sentence was started (whether at this character or before) */
                if (isdigit(c)) continue; /* skip (don't print out a digit) */
                break;
        }

        printf("%c", c);
    }

    return 0;
}