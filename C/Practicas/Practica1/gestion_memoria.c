#include <stdio.h>
#include <stdlib.h>
#include "gestion_memoria.h"

const unsigned MAX = 950;

/* Crea la estructura utilizada para gestionar la memoria disponible. Inicialmente, s�lo un nodo desde 0 a MAX */
	void crear(T_Manejador* manejador){
        *manejador = (T_Manejador)malloc(sizeof(struct T_Nodo));
        if(*manejador == NULL){
            perror("Error al crear");
            return;
        }

        (*manejador)->inicio = 0;
        (*manejador)->fin = MAX-1;
        (*manejador)->sig = NULL;
    }

/* Destruye la estructura utilizada (libera todos los nodos de la lista. El par�metro manejador debe terminar apuntando a NULL */
	void destruir(T_Manejador* manejador){
        T_Manejador aux;
        while (*manejador != NULL)
        {
            aux = *manejador;
            *manejador = (*manejador)->sig;
            free(aux);
        }  
    }   

/* Devuelve en �dir� la direcci�n de memoria �simulada� (unsigned) donde comienza el trozo de memoria continua de tama�o �tam� solicitada.
Si la operaci�n se pudo llevar a cabo, es decir, existe un trozo con capacidad suficiente, devolvera TRUE (1) en �ok�; FALSE (0) en otro caso.
 */
	void obtener(T_Manejador *manejador, unsigned tam, unsigned* dir, unsigned* ok){
        T_Manejador actual = *manejador;
        T_Manejador anterior = NULL;

        while (actual != NULL && (actual->fin - actual->inicio+1 <tam))
        {
            anterior = actual;
            actual = actual->sig;
        }

        if(actual == NULL) *ok = 0;
        else
        {
            *ok = 1;
            *dir = actual->inicio;
            if (actual->fin - actual->inicio+1 == tam)
            {
                if (anterior == NULL) *manejador = actual->sig;
                else  anterior->sig = actual->sig;
                free(actual);
            } else
            {
                actual->inicio += tam;
            }
            
        }

    }

/* Muestra el estado actual de la memoria, bloques de memoria libre */
	void mostrar (T_Manejador manejador){
        int numBloque = 0;
        printf("\n**** Mostrando Memoria ****\n");
        while (manejador != NULL)
        {
            printf("Bloque %d\t Inicio: %d\t Fin: %d\n", numBloque, manejador->inicio, manejador->fin);
            manejador = manejador->sig;
            numBloque++;
        }    
    } 

    void compactar (T_Manejador *manejador){
        T_Manejador actual = *manejador;

        while (actual != NULL && actual->sig != NULL)
        {
             if (actual->fin+1 == actual->sig->inicio)
             {
                actual->fin = actual->sig->fin;
                T_Manejador borrar = actual->sig;
                actual->sig = borrar->sig;
                free(borrar);
             } else actual = actual->sig;   
        }
    }

/* Devuelve el trozo de memoria continua de tama�o �tam� y que
 * comienza en �dir�.
 * Se puede suponer que se trata de un trozo obtenido previamente.
 */
	void devolver(T_Manejador *manejador,unsigned tam,unsigned dir){
        T_Manejador actual = *manejador;
        T_Manejador anterior = NULL;

        while (actual != NULL && (actual->inicio < dir))
        {
            anterior = actual;
            actual = actual-> sig;
        }

        T_Manejador nuevoNodo = (T_Manejador)malloc(sizeof(struct T_Nodo));

        if(nuevoNodo == NULL) perror("Error al devolver");
        else
        {
            nuevoNodo->inicio = dir;
            nuevoNodo->fin = dir + tam-1;
            nuevoNodo->sig = actual;

            if(anterior == NULL) *manejador = nuevoNodo;
            else anterior->sig = nuevoNodo;

            compactar(manejador);
        }
    }
