#include <stdio.h>
#include <stdlib.h>
#include <errno.h>

int main(int argc, char *argv[]){

    int fent = fopen("fichero.txt","r");
    if(fent == NULL) perror("Error abriendo fichero.txt");

    return EXIT_SUCCESS;
}