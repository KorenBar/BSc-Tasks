#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <ctype.h>
#include "myset.h"

set sets[NUM_OF_SETS];

int main() {
    int i;

    /* init sets */
    for (i = 0; i < NUM_OF_SETS; i++)
        if ((sets[i] = new_set()) == NULL)
            /* failed to allocate the initial sets, have nothing to do */
            return 1;

    while (!handle_command(read_command()));

    /* free sets */
    for (i = 0; i < NUM_OF_SETS; i++)
        free_set(sets[i]);

    return 0;
}

bool handle_command(command* cmd)
{
    int count;
    int *values;
    operation c;
    bool is_stop_msg;
    set set_to_free, tmp_set;
    setname output_set_name;

    if (cmd == NULL) return true;

    values = NULL;
    set_to_free = NULL;
    c = cmd2enum(cmd->command);
    is_stop_msg = c == STOP;

    if(!is_stop_msg) {
        if (validate_command(cmd)) {
            /* now it's safe to continue without errors */
            if (is_multiset_cmd(c))
                handle_multiset_cmd(c, cmd->params);
            else switch (c) {
                case READ: /* a single set with values */
                    /* get values count and convert values to integers array */
                    count = cmd->params_count - SINGLE_SET_COUNT - 1; /* count of values */
                    values = get_values(cmd->params + SINGLE_SET_COUNT, count);
                    /* get the set to work on */
                    output_set_name = setname2enum(cmd->params[0]);
                    set_to_free = get_set(output_set_name);
                    /* operate and then replace the current set */
                    tmp_set = read_set(values, count);
                    if (tmp_set != NULL)
                        sets[output_set_name] = tmp_set;
                    break;
                case PRINT: /* a single set with no values */
                    output_set_name = setname2enum(cmd->params[0]);
                    print_set(get_set(output_set_name));
                    break;
                default:
                    break;
            }
        }
    }

    /* free memory */
    if (set_to_free != NULL) free_set(set_to_free);
    if (values != NULL) free(values);
    free_command(cmd);

    return is_stop_msg;
}

void handle_multiset_cmd(operation cmd, string *params)
{ /* two sets + output set and with no values */
    set set_to_free, s1, s2, res;
    setname output_set_name;

    s1 = get_set(setname2enum(params[0]));
    s2 = get_set(setname2enum(params[1]));
    output_set_name = setname2enum(params[2]);
    set_to_free = get_set(output_set_name);

    /* operate */
    switch (cmd) {
        case UNION:
            res = union_set(s1, s2);
            break;
        case INTERSECT:
            res = intersect_set(s1, s2);
            break;
        case SUB:
            res = sub_set(s1, s2);
            break;
        case SYMDIFF:
            res = symdiff_set(s1, s2);
            break;
        default:
            res = NULL;
            break;
    }

    /* replace the current set */
    if (res != NULL) sets[output_set_name] = res;
    free_set(set_to_free);
}

set get_set(setname set)
{
    if (set >= NUM_OF_SETS) return NULL; /* default */
    return sets[set];
}

int *get_values(string* values, int count)
{
    int i;
    int *res = calloc(count, sizeof(int));

    for (i = 0; i < count; i++)
        res[i] = atoi(values[i]);

    return res;
}

operation cmd2enum(string cmd)
{
    if(!strcmp(cmd, "read_set")) return READ;
    if(!strcmp(cmd, "print_set")) return PRINT;
    if(!strcmp(cmd, "union_set")) return UNION;
    if(!strcmp(cmd, "intersect_set")) return INTERSECT;
    if(!strcmp(cmd, "sub_set")) return SUB;
    if(!strcmp(cmd, "symdiff_set")) return SYMDIFF;
    if(!strcmp(cmd, "stop")) return STOP;
    return -1; /* default */
}

setname setname2enum(string name)
{
    if(!strcmp(name, "SETA")) return SETA;
    if(!strcmp(name, "SETB")) return SETB;
    if(!strcmp(name, "SETC")) return SETC;
    if(!strcmp(name, "SETD")) return SETD;
    if(!strcmp(name, "SETE")) return SETE;
    if(!strcmp(name, "SETF")) return SETF;
    return -1; /* default */
}

/* must be done before changing the sets */
bool validate_command(command* cmd)
{
    int i;
    char* cp;
    operation cmd_enum;
    bool multiset, valueless;

    cmd_enum = cmd2enum(cmd->command);
    multiset = is_multiset_cmd(cmd_enum);
    valueless = is_valueless_cmd(cmd_enum);

    if (cmd_enum == -1)
        if (strchr(cmd->command, ',') != NULL) /* command contains a comma */
            return !printf("Illegal comma\n\n");
        else return !printf("Undefined command name\n\n");

    else if (!cmd->params_count) /* no params */
        return !printf("No given set name\n\n");

    else if (cmd->params[0][0] == '\0') /* first param is empty */
        return !printf("Illegal comma\n\n");


    /* command with values but the last param is not -1 */
    else if (!valueless && strcmp(cmd->params[cmd->params_count-1], "-1") != 0)
        return !printf("List of set members is not terminated correctly\n\n");

    else {
        /* is there a space on one of the params?
         * note that params is trimmed from sides */
        for (i = 0; i < cmd->params_count; i++)
            if (strchr(cmd->params[i], ' ') != NULL) /* this param contains a space */
                return !printf("Unnecessary space or missing comma\n\n");
            else if (cmd->params[i][0] == '\0') /* this param is empty */
                return !printf("Unnecessary comma\n\n");

        /* check sets names */
        for (i = (multiset ? MULTI_SET_COUNT : SINGLE_SET_COUNT) - 1; i >= 0; i--)
            if (cmd->params_count <= i)
                return !printf("Missing set name parameter\n\n");

            else if (setname2enum(cmd->params[i]) == -1) /* Unknown set name (returned NULL) */
                return !printf("Undefined set name \"%s\"\n\n", cmd->params[i]);

        /* check values */
        for (i = multiset ? MULTI_SET_COUNT : SINGLE_SET_COUNT;
             i < cmd->params_count - 1; i++) /* without the last -1 */
        {
            cp = cmd->params[i];
            while (*cp != '\0' && isdigit(*cp)) cp++;
            if (*cp != '\0') /* a non-digit char was found */
                return !printf("Invalid value parameter: \"%s\" is not a positive integer\n\n", cmd->params[i]);
            if (atoi(cmd->params[i]) > MAX_SET_VALUE)
                return !printf("Invalid value parameter: \"%s\" is out of range\n\n", cmd->params[i]);
        }
    }

    /* multi-set cmd is always value-less */
    if (multiset && cmd->params_count > MULTI_SET_COUNT)
        return !printf("Extraneous text after end of command\n\n");
    if (multiset && cmd->params_count < MULTI_SET_COUNT)
        return !printf("Missing set name parameter\n\n");

    if (!multiset && valueless && cmd->params_count > SINGLE_SET_COUNT)
        return !printf("Extraneous text after end of command\n\n");

    if (!multiset && !valueless && cmd->params_count <= SINGLE_SET_COUNT) /* case of read_set only */
    {
        if (cmd->params_count < SINGLE_SET_COUNT)
            return !printf("Missing set name parameter\n\n");
        else return !printf("Missing value parameter\n\n");
    }

    return true; /* an error was printed? */
}

bool is_multiset_cmd(operation cmd)
{
    switch (cmd) {
        case UNION:
        case INTERSECT:
        case SUB:
        case SYMDIFF:
            return true;
        default:
            return false;
    }
}

bool is_valueless_cmd(operation cmd)
{
    switch (cmd) {
        case PRINT:
            return true;
        default:
            return is_multiset_cmd(cmd); /* any multiset command is value-less */
    }
}