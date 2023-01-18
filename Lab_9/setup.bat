lex lang.lxi
bison -d lang.y
gcc lex.yy.c lang.tab.c -o parser.exe