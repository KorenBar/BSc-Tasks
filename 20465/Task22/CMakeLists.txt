cmake_minimum_required(VERSION 3.19)
project(Task22 C)

set(CMAKE_C_STANDARD 90)

add_executable(Task22 myset.c myset.h set.c set.h commands.c commands.h buffer.c buffer.h strhelper.c strhelper.h)