#include "Tree.h"
#include <stdio.h>
#include <stdlib.h>
/*
Main generado para testeo 
*/

int main(void) {

    Tree comisarias;
    inicializarArbol(&comisarias);;
    insertarComisaria(&comisarias,"metropolitana 1",36.72,-4.43);
    insertarComisaria(&comisarias,"areasur",56.72,-3.43);
    insertarComisaria(&comisarias,"comisaria ACAB",36.72,-4.43);
    insertarComisaria(&comisarias,"pueblonuevo",32.72,-4.01);
    mostrarArbol(comisarias);

    printf("la distancia menor es a la comisaria: %s\n",localizarComisariaCercana(comisarias,0,0));

    destruirArbol(&comisarias);

    FILE *fichero=fopen("comisarias.txt","rt");  
    if( fichero==NULL){
        perror("error en la apertura del fichero");
        exit(-1);
    }
    cargarComisarias(fichero,&comisarias);

    mostrarArbol(comisarias);

    
    guardarBinario("comisariasBinario.bin",comisarias);


    return 0;
}
