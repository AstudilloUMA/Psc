/*Implementar la funcion int esPrimo(int n) que dado un entero n devuelve 1 
    si es primo y 0 en otro caso. Implementa un metodo main que pida al 
    usuario un numero, le pase como argumento ese numero a la funcion 
    esPrimo, y muestre por pantalla una frase indicado si el numero es o no primo.*/

#include <stdio.h>
#include <stdlib.h>

int esPrimo(int n){
    return n % 2 != 0 || n == 2;
}

int main(char *argv[], int argc){
    int n;

    printf("Escriba un numero por pantalla para saber si es primo o no: ");
    scanf("%d", &n);
    if(esPrimo(n)) {
        printf("El numero introducido es primo \n");
    } else {
        printf("El numero introducido no es primo \n");
    }

    return EXIT_SUCCESS;
}