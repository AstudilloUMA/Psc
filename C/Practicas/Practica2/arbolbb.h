/*
 * arbolbb.h
 *
 *  Created on: 25 ene. 2019
 *      Author: Laura
 */

#ifndef ARBOLBB_H_
#define ARBOLBB_H_

#include <stdio.h>

typedef struct T_Nodo* T_Arbol;
struct T_Nodo {
unsigned dato;
T_Arbol izq, der;
};


// Crea la estructura utilizada para gestionar el árbol.
void crear(T_Arbol* arbol);
// Destruye la estructura utilizada y libera la memoria.
void destruir(T_Arbol* arbol);
// Inserta num en el árbol. Si ya está insertado, no hace nada
void insertar(T_Arbol* arbol,unsigned num);
// Muestra el contenido del árbol en InOrden
void mostrar(T_Arbol arbol);
// Guarda en disco el contenido del fichero
void salvar(T_Arbol arbol, FILE* fichero);

#endif /* ARBOLBB_H_ */
