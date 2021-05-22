myText: myText.o dataStructs.o
	gcc -ansi -Wall -pedantic myText.o dataStructs.o -o myText

myText.o: myText.c myText.h dataStructs.o
	gcc -c -ansi -Wall -pedantic myText.c -o myText.o


dataStructs.o: dataStructs.c dataStructs.h
	gcc -c -ansi -Wall -pedantic dataStructs.c -o dataStructs.o
