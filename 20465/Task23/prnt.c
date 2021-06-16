#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define CEXTENSION ".c"

typedef char* string;

char* get_extension(string str); /* returns the extension dot or NULL if there is no extension */
string change_extension(string str, string newExt); /* allocate a new file name string with another extension */
void* allocate_memory(size_t size); /* allocate memory or exit with an error code if was failed */
void test();

int main(int arg_count, char** args) {
    int c;
    FILE* file;
    string source_file;

    if (arg_count > 1 && !strcmp(args[1], "-test")) test();

    source_file = change_extension(args[0], CEXTENSION);
    printf("%s\n%s\n\n", args[0], source_file);
    file = fopen(source_file, "r");

    if (file != NULL)
        while ((c = fgetc(file)) != EOF) printf("%c", c);
    else
        printf(".c source file was not found or can't be read, please make sure you're running that program from the source folder!");

    fclose(file);
    free(source_file);
    return 0;
}

char* get_extension(string str) {
    string end, p = str;

    while (*p != '\0') p++; /* go to end */
    end = p;

    for (p--; /* start from the last char before the \0 */
         p >= str && *p != '\\' && *p != '/'; /* while in range and not a slash */
         p--) /* move back */
        if (*p == '.') return p;

    return end; /* the real end of that string! */
}

string change_extension(string str, string newExt) {
    long i, len; /* the exact required size to allocate */
    string res;
    char* ext = get_extension(str);
    len = strlen(newExt);
    len += ext - str;

    res = allocate_memory((len + 1) * sizeof(char)); /* +1 for the '\0' */

    /* don't use strcpy or strcat here since str maybe longer than res */
    for (i = 0; str + i < ext; i++) res[i] = str[i];
    res[i] = '\0';

    strcat(res, newExt); /* append the new extension */

    return res; /* we have got a new allocated string in the exact required size and with the new extension */
}

void* allocate_memory(size_t size) {
    void* mem = malloc(size);
    if (mem == NULL)
    {
        printf("ERROR: failed to allocate memory!");
        exit(1);
    }
    return mem;
}

void test()
{
    string a_src, a_res, b_src, b_res, c_src, c_res, d_src, d_res, e_src, e_res, f_src, f_res;

    a_src = "./test.exe";
    a_res = change_extension(a_src, ".c");
    b_src = "test";
    b_res = change_extension(b_src, ".c");
    c_src = "test.";
    c_res = change_extension(c_src, ".c");
    d_src = "C:\\mydir\\subdir\\test.";
    d_res = change_extension(d_src, ".c");
    e_src = "C:\\mydir\\subdir\\test.exe";
    e_res = change_extension(e_src, ".c");
    f_src = "/mydir/subdir/test.exe";
    f_res = change_extension(f_src, ".c");

    printf("%s\n%s\n\n%s\n%s\n\n%s\n%s\n\n%s\n%s\n\n%s\n%s\n\n%s\n%s\n\n",
           a_src, a_res, b_src, b_res, c_src, c_res, d_src, d_res, e_src, e_res, f_src, f_res);

    free(a_res);
    free(b_res);
    free(c_res);
    free(d_res);
    free(e_res);
    free(f_res);
    exit(0);
}
