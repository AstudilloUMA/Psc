/*Implementar un programa que lea de teclado una cadena terminada en el carácter '#' y devuelva el número de apariciones de cada una de las vocales.
    Para usar scanf hay que leer carácter a carácter porque no se sabe la longitud de la cadena.*/

#include <stdio.h>
#include <stdlib.h>


int main(int argc, char *argv[]){
    
    int a = 0;
    int e = 0;
    int i = 0;
    int o = 0;
    int u = 0;

    char caracter;

    printf("Introduzca una cadena acabada en # para saber el numero de cada vocal: ");

    scanf("%c", &caracter);
    while(caracter != '#'){
        
        switch(caracter){
            case 'a':
                a++;
                break;
            case 'e':
                e++;
                break;
            case 'i':
                i++; 
                break;
            case 'o':
                o++;
                break;
            case 'u':
                u++;
                break;
        }
        
        scanf("%c", &caracter);
    }

    printf("a: %d; e: %d; i: %d; o: %d; u: %d \n", a, e, i, o, u);

    return EXIT_SUCCESS;
}