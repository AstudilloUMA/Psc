#include <stdio.h>
#include <stdlib.h>

int esPrimo(int n){
    return n % 2 != 0 || n == 2;
}

int main(int argc, char *argv[]){
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
