BIN = ./bin/

$(shell mkdir -p $(BIN))

myset: myset.o
	gcc -g -ansi -Wall -pedantic ${BIN}myset.o ${BIN}set.o ${BIN}commands.o ${BIN}buffer.o ${BIN}strhelper.o -o ${BIN}myset

myset.o: myset.c myset.h set.o commands.o
	gcc -c -g -ansi -Wall -pedantic myset.c -o ${BIN}myset.o

commands.o: commands.c commands.h buffer.o strhelper.o
	gcc -c -g -ansi -Wall -pedantic commands.c -o ${BIN}commands.o

buffer.o: buffer.c buffer.h
	gcc -c -g -ansi -Wall -pedantic buffer.c -o ${BIN}buffer.o

strhelper.o: strhelper.c strhelper.h
	gcc -c -g -ansi -Wall -pedantic strhelper.c -o ${BIN}strhelper.o

set.o: set.c set.h
	gcc -c -g -ansi -Wall -pedantic set.c -o ${BIN}set.o
