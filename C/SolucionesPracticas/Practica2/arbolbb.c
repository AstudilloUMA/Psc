#include "arbolbb.h"
#include <stdlib.h>
#include <stdio.h>

void crear(T_Arbol* arbol){
    *arbol = NULL;
}
// Destruye la estructura utilizada y libera la memoria.
void destruir(T_Arbol* arbol){
    if(*arbol!=NULL){
        destruir(&((*arbol)->izq));
        destruir(&((*arbol)->der));
        free(*arbol);
        *arbol = NULL;
    }
}
// Inserta num en el árbol. Si ya está insertado, no hace nada
void insertar(T_Arbol* arbol,unsigned num){
    if(*arbol == NULL){
        *arbol = (T_Arbol)malloc(sizeof(struct T_Nodo));
        if(*arbol== NULL){
            perror("insertar: no se puede reservar memoria");
        }else{
            (*arbol)->dato = num;
            (*arbol)->der = NULL;
            (*arbol)->izq = NULL;
        }
    }else if((*arbol)->dato > num){
            insertar(&((*arbol)->izq), num);
    }else if((*arbol)->dato < num){
            insertar(&((*arbol)->der), num);
    } 
}

// Muestra el contenido del árbol en InOrden
void mostrar(T_Arbol arbol){
    if(arbol!=NULL){        
        mostrar(arbol->izq);
        printf(" %d ", arbol->dato);
        mostrar(arbol->der);      
    }
}
// Guarda en disco el contenido del fichero
void salvar(T_Arbol arbol, FILE* fichero){    
	if(fichero==NULL){
		perror("Salvar: error el fichero no está a bierto en modo wb");
	}else{
		if(arbol!=NULL){
            salvar(arbol->izq, fichero);
            fwrite(&(arbol->dato), sizeof(int), 1, fichero);
            salvar(arbol->der, fichero);
        }
	}
}