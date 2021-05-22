#include "set.h"
#include "commands.h"

#define NUM_OF_SETS 6

typedef enum {
    SETA,
    SETB,
    SETC,
    SETD,
    SETE,
    SETF } setname;

/* converts a string to commands enum value */
operation cmd2enum(string cmd);
/* converts a string to setname enum value */
setname setname2enum(string name);

bool handle_command(command* cmd);
void handle_multiset_cmd(operation cmd, string *params);
bool validate_command(command* cmd);

/* returns true if it's a command upon multiple sets, or false if it's a single-set command with numeric values */
bool is_multiset_cmd(operation cmd);
bool is_valueless_cmd(operation cmd);

set get_set(setname set);
int *get_values(string* values, int count);