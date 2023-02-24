/*
 * arbolbb.h
 *
 *  Created on: 25 ene. 2019
 *      Author: Laura
 */

#include <stdio.h>
#include <stdlib.h>
#include "arbolbb.h"

// Crea la estructura utilizada para gestionar el árbol.
void crear(T_Arbol* arbol){
    *arbol == NULL;
}
// Destruye la estructura utilizada y libera la memoria.
void destruir(T_Arbol* arbol){

    if(*arbol != NULL){
        destruir(&(*arbol)->izq);                                       // & para pasar por referencia
        destruir(&(*arbol)->der);
        free(*arbol);
    }

    *arbol = NULL;
}
// Inserta num en el árbol. Si ya está insertado, no hace nada
void insertar(T_Arbol* arbol,unsigned num){

   
    if (*arbol == NULL){
        T_Arbol nuevoNodo;
        nuevoNodo = (T_Arbol)malloc(sizeof(struct T_Nodo));
        nuevoNodo->dato = num;
        nuevoNodo->izq = NULL;
        nuevoNodo->der = NULL;
        *arbol = nuevoNodo;
    }
    else if (num < (*arbol)->dato) insertar(&(*arbol)->izq,num);
    else if (num > (*arbol)->dato) insertar(&(*arbol)->der,num);
}
// Muestra el contenido del árbol en InOrden
void mostrar(T_Arbol arbol){

    if(arbol != NULL){
        mostrar(arbol->izq);
        printf("%d ", arbol->dato);
        mostrar(arbol->der);
    }
}
// Guarda en el fichero el contenido del arbol
void salvar(T_Arbol arbol, FILE* fichero){

    if(arbol != NULL){
        salvar(arbol->izq,fichero);
        fwrite(&arbol->dato,sizeof(unsigned),1,fichero);
        salvar(arbol->der,fichero);
    }   
}

