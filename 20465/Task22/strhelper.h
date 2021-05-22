/* left trim - returns a pointer to the first non-space char
 * note that returned pointer is pointing to an existing memory so don't free it */
char *ltrim_pointer(char *s);
/* left trim - moving all characters form the first non-space to the begin
 * returns the same pointer param as is */
char *ltrim(char *s);
/* right trim - ending the string with \0 before the white spaces at end
 * returns the same pointer param as is */
char *rtrim(char *s);
/* trim a string from both sides - returns a pointer to the first non-space char
 * note that returned pointer is pointing to an existing memory so don't free it */
char *trim(char *s);
