#if !defined INITHEADER

#define INITHEADER
#define _create(TYPE) (TYPE##*)malloc(sizeof(TYPE))
#define _memory_is_full printf("memory is full\n")
#include <stdio.h>
#include <stdbool.h>
#include <malloc.h>

#endif