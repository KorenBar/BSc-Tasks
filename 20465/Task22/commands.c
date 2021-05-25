#include "commands.h"

command* new_command()
{
    command* cmd = malloc(sizeof(command));

    if (cmd == NULL) {
        printf("ERROR: failed to allocate a new command.");
        return NULL;
    }

    cmd->command = NULL;
    cmd->params = NULL;
    cmd->params_count = 0;

    return cmd;
}

bool add_param(command* cmd, char *str)
{
    void *new = realloc(cmd->params, (cmd->params_count + 1) * sizeof(string));
    if (new == NULL)
    {
        printf("Error while allocating memory!");
        return false;
    }
    cmd->params = new;

    new = malloc((strlen(str) + 1) * sizeof(char));
    if (new == NULL)
    {
        printf("Error while allocating memory!");
        return false;
    }

    strcpy(new, str); /* copy text */
    cmd->params[cmd->params_count] = new; /* add as string param */

    cmd->params_count++;
    return true;
}

command* read_command()
{
    int c;
    dyBuffer* buff;
    command* cmd;

    if ((buff = new_buffer()) == NULL) {
        free_buffer(buff);
        return NULL;
    }
    if ((cmd = new_command()) == NULL) {
        free_command(cmd);
        return NULL;
    }

    printf("Please write a command:\n");

    while (isspace(c = getchar())); /* skip spaces */

    /* get command name */
    do {
        if (c == '\n' || c == EOF) break;
        if (!add2buffer(buff, (char*)&c, 1))
        {
            free_buffer(buff);
            free_command(cmd);
            return NULL;
        }
    } while (!isspace(c = getchar()));

    cmd->command = buffer2string(buff);

    if (c == '\n' || c == EOF) /* there are no params, end here */
    {
        free_buffer(buff);
        return c != EOF ? cmd : NULL;
    }

    clear_buffer(buff); /* we'll reuse it instead of destroying it */

    while (isspace(c = getchar())); /* skip spaces */

    /* get params */
    do {
        /*if (isspace(c)) continue;*/
        if (c == ',')
        {
            add_param(cmd, trim(buffer2string(buff)));
            clear_buffer(buff);
            continue;
        }
        if (!add2buffer(buff, (char*)&c, 1))
        {
            free_buffer(buff);
            free_command(cmd);
            return NULL;
        }
    } while ((c = getchar()) != '\n');

    add_param(cmd, trim(buffer2string(buff)));

    free_buffer(buff);
    return cmd;
}

void print_command(command* cmd)
{
    int i;

    if (cmd == NULL) return;

    if (cmd->command != NULL)
        printf("%s ", cmd->command);

    for (i = 0; i < cmd->params_count - 1; i++)
        printf("%s, ", cmd->params[i]);

    if (i < cmd->params_count) /* print the last param */
        printf("%s", cmd->params[i]);

    printf("\n");
}

void free_command(command* cmd)
{
    int i;

    if (cmd == NULL) return;

    if (cmd->command != NULL)
        free(cmd->command);

    /* each param string */
    for (i = 0; i < cmd->params_count; i++)
        free(cmd->params[i]);

    /* the params array itself */
    if (cmd->params != NULL)
        free(cmd->params);

    /* the command itself */
    free(cmd);
}