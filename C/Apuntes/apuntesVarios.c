#include <stdio.h>
// #include "fichero.h" para un fichero
#include <stdlib.h> // EXIT_SUCCES or EXIT_FAILURE

int main(int argc, char *argv[]){ //Para saber que se ha ejecutado bien devolver 0, sino, cualquier otro argumento
    int i = 10;
    char c = 'a';
    float f = 2.3;
    char cadena[5] = "Hola";
    int day;

    //printf("Hola Mundo!\n");
    printf("Esto es un entero: %d\n", i);
    printf("Esto es un caracter: %c\n", c);
    printf("Esto es un float con 2 decimales: %.2f\n", f);
    printf("Esto es una cadena: %s\n", cadena);
    printf("Esto es la segunda letra de la cadena anterior: %c\n", cadena[1]);

    printf("Esto son distintas variables: %d, %c, %f, %s\n", i, c, f, cadena);

    printf("Introduce el dia (int): ");
    int result = scanf("%d", &day);
    printf("scanf devuelve el valor %d y el valor leido es %d\n", result, day);





    return EXIT_SUCCESS;
}

