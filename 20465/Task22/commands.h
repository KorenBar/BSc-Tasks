#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdarg.h>
#include <stdbool.h>
#include <ctype.h>
#include "buffer.h"
#include "strhelper.h"

typedef struct {
    string command;
    string* params;
    ushort params_count;
} command;

/* init a new command */
command* new_command();
void free_command(command* cmd);
/* copy a string and add it as param of a given command */
bool add_param(command* cmd, char *str);
/* read a command from the standard input */
command* read_command();
void print_command(command* cmd);
