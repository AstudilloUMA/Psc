#include <stdlib.h>
#include <stdio.h>
#include "arbolbb.h"

// Crea la estructura utilizada para gestionar el árbol.
void crear(T_Arbol* arbol){
    *arbol = NULL;
}

// Destruye la estructura utilizada y libera la memoria.
void destruir(T_Arbol* arbol){
    if (*arbol != NULL)
    {
        destruir(&((*arbol)->izq));
        destruir(&((*arbol)->der));
        free(arbol);
    }
}

// Inserta num en el árbol. Si ya está insertado, no hace nada
void insertar(T_Arbol* arbol,unsigned num){
    if(*arbol != NULL)
    {
        if((*arbol)->dato != num){
            if(num > (*arbol)->dato)
            {
                insertar(&((*arbol)->der),num);
            }
            else
            {
                insertar(&((*arbol)->izq),num);
            }
        }
    }
    else
    {
        *arbol = (T_Arbol)malloc(sizeof(struct T_Nodo));
        if (*arbol == NULL)
        {
            perror("Insertar: No se puede reservar memoria");
        }
        else
        {   
            (*arbol)->dato = num;
            (*arbol)->izq = NULL;
            (*arbol)->der = NULL;
        }
    }
}

// Muestra el contenido del árbol en InOrden
void mostrar(T_Arbol arbol){
    if(arbol != NULL)
    {
        mostrar(arbol->izq);
        printf(" %d ",arbol->dato);
        mostrar(arbol->der);
    }
}

// Guarda en disco el contenido del fichero
void salvar(T_Arbol arbol, FILE* fichero){
    if(fichero == NULL)
    {
        perror("Salvar: El fichero no se ha abierto correctamente");
    }
    else
    {
        if (arbol != NULL)
        {
            salvar(arbol->izq,fichero);
            fwrite(&(arbol->dato),sizeof(int),1,fichero);
            salvar(arbol->der,fichero);
        }
    }
}